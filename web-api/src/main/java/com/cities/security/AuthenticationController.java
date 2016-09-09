package com.cities.security;

import com.cities.AppConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.dao.SaltSource;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    static Logger log = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;
    private SaltSource saltSource;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
        throws AuthenticationException {

        log.debug("cities => username" + authenticationRequest.getUsername());
        log.debug("cities => password" + authenticationRequest.getPassword());

        /*org.springframework.security.crypto.password.PasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder2.encode("123123");

        SpringSecurityUser u =new SpringSecurityUser(10L,
                "burak",
                hashedPassword,
                commaSeparatedStringToAuthorityList("ROLE_ADMIN"), null);

        Object salt = null;
        if(this.saltSource != null) {
            salt = this.saltSource.getSalt(u);
        }

        log.debug("cities => authentication.getCredentials().toString()" + authenticationRequest.getPassword());

        if (passwordEncoder.isPasswordValid(u.getPassword(), authenticationRequest.getPassword(), salt)) {
            log.debug("cities => resolved");
        } else if (passwordEncoder.isPasswordValid("123123", authenticationRequest.getPassword(), salt)) {
            log.debug("cities => resolved 2");
        } else {
            log.debug("cities => not resolved");
        }*/

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.debug("cities => load oncesi");
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        log.debug("cities => userdetails disi");
        String token = this.tokenUtils.generateToken(userDetails);

        // Return token
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @RequestMapping(value = "refresh", method = GET)
    public ResponseEntity<?> authenticatinRequest(HttpServletRequest request) {
        String token = request.getHeader(AppConstant.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())){
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
