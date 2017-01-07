package com.cities.user;

import com.cities.model.user.User;
import com.cities.security.AuthenticationRequest;
import com.cities.user.model.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserDtoToUserConverter {

    public User fromUserDto(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
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
        user.setUsername(authenticationRequest.getUsername());
        user.setPassword(authenticationRequest.getPassword());
        return user;
    }
}
