package com.cities.security;

import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import com.cities.service.user.UserService;
import com.cities.user.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("no found by name %s", username));
        }

        return new UserDto(user.getId(),
                           user.getUsername(),
                           user.getPassword(),
                           null,
                           createAuthorityList(getRoles(user)));
    }

    private String[] getRoles(User user) {
        List<String> rolesList = user.getUserRoles().stream().map(UserRole::getRole).collect(toList());
        return rolesList.toArray(new String[rolesList.size()]);
    }
}
