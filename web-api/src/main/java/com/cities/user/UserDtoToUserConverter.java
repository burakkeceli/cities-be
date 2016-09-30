package com.cities.user;

import com.cities.model.User;
import com.cities.security.AuthenticationRequest;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter {

    public User fromUserDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public User fromRequest(AuthenticationRequest authenticationRequest) {
        User user = new User();
        user.setName(authenticationRequest.getUsername());
        user.setPassword(authenticationRequest.getPassword());
        return user;
    }
}
