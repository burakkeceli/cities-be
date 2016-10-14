package com.cities.user;

import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import com.cities.security.AuthenticationRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class UserDtoToUserConverter {

    public User fromUserDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getName());
        setUserRoles(user, userDto);
        userDto.setCountry(user.getCountry());
        return userDto;
    }

    private void setUserRoles(User user, UserDto userDto) {
        List<GrantedAuthority> authorities = user.getUserRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(toList());
        userDto.setAuthorities(authorities);
    }

    public User fromRequest(AuthenticationRequest authenticationRequest) {
        User user = new User();
        user.setName(authenticationRequest.getUsername());
        user.setPassword(authenticationRequest.getPassword());
        return user;
    }
}
