package com.cities.security;

import com.cities.error.ErrorResourceDto;
import com.cities.friend.UserLogic;
import com.cities.model.user.User;
import com.cities.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.cities.ApiConstants.Urls.REGISTER;
import static com.cities.model.user.UserRoleEnum.ROLE_USER;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping(REGISTER)
@Slf4j
public class RegisterController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private UserLogic converter;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) {

        User user = userService.getUserByName(authenticationRequest.getUsername());
        if (user != null) {
            // TODO: add constants
            return new ResponseEntity<>(singletonList(new ErrorResourceDto("already exists", "user is already exists")), CONFLICT);
        }

        user = converter.fromRequest(authenticationRequest);
        userService.saveUser(user, ROLE_USER);
        String token = tokenUtils.generateToken(user);

        return ResponseEntity.ok(new AuthenticationResponse(user.getId(), user.getUsername(), token));
    }
}
