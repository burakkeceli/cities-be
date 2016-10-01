package com.cities.security;

import com.cities.error.ErrorResourceDto;
import com.cities.model.user.User;
import com.cities.service.UserService;
import com.cities.user.UserDtoToUserConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("login")
public class LoginController {
    static Logger log = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDtoToUserConverter converter;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) {

        User user = userService.get(authenticationRequest.getUsername());
        if (user != null) {
            // TODO: add constants
            return new ResponseEntity<>(singletonList(new ErrorResourceDto("already exists", "user is already exists")), CONFLICT);
        }

        user = converter.fromRequest(authenticationRequest);
        userService.saveWithRoleUser(user);
        String token = this.tokenUtils.generateToken(user);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
