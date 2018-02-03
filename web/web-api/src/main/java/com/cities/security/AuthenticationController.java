package com.cities.security;

import com.cities.user.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.cities.ApiConstants.Urls.IS_EXPIRED;
import static com.cities.ApiConstants.Urls.LOGIN;
import static com.cities.AppConstant.tokenHeader;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(LOGIN)
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(method = POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        SecurityContextHolder.getContext().setAuthentication(null);

        UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken
                (authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(userPassToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDto user = (UserDto)userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = tokenUtils.generateToken(user);

        // Return token
        return ok(new AuthenticationResponse(user.getId(), user.getUsername(), token));
    }

    @RequestMapping(value = "refresh", method = GET)
    public ResponseEntity<?> authenticatinRequest(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = tokenUtils.getUsernameFromToken(token);
        UserDto user = (UserDto) userDetailsService.loadUserByUsername(username);
        if (tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset().toDate())){
            String refreshedToken = tokenUtils.refreshToken(token);
            return ok(new AuthenticationResponse(user.getId(), user.getUsername(), refreshedToken));
        } else {
            return badRequest().body(null);
        }
    }

    @RequestMapping(value = IS_EXPIRED, method = GET)
    public ResponseEntity<?> isTokenExpired(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        Boolean isTokenExpired = tokenUtils.isTokenExpired(token);
        if (isTokenExpired) {
            return new ResponseEntity(NOT_FOUND);
        } else {
            return ok().body(null);
        }
    }
}
