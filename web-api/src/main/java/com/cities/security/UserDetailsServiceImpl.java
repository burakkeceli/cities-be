package com.cities.security;

import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import com.cities.service.UserService;
import com.cities.user.UserDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    static Logger log = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("no found by name %s", username));
        }

        return new UserDto(user.getId(),
                           user.getName(),
                           user.getPassword(),
                           null,
                           createAuthorityList(getRoles(user)));
    }

    private String[] getRoles(User user) {
        List<String> rolesList = user.getUserRoles().stream().map(UserRole::getRole).collect(toList());
        return rolesList.toArray(new String[rolesList.size()]);
    }
}
