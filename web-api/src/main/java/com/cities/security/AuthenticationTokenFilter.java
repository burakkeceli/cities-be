package com.cities.security;

import com.cities.constant.AppConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cities.constant.AppConstant.tokenHeader;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
    static Logger log = Logger.getLogger(AuthenticationTokenFilter.class.getName());

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        tokenUtils = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext())
                .getBean(TokenUtils.class);

        userDetailsService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext())
                .getBean(UserDetailsService.class);

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        resp.setHeader("Access-Control-Allow-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " + tokenHeader);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(tokenHeader);
        String username = tokenUtils.getUsernameFromToken(authToken);
        log.debug("cities => doFilter username: " + username + " SecurityContextHolder.getContext().getAuthentication() " +SecurityContextHolder.getContext().getAuthentication());

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.debug("cities => " + userDetails.getAuthorities() + userDetails.getUsername() + userDetails.getPassword());
            if (tokenUtils.validateToken(authToken, userDetails)) {
                log.debug("cities inside validation => ");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("cities inside validation end => ");
            }
        }

        chain.doFilter(httpRequest, resp);
    }
}
