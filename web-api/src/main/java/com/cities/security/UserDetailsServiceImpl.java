package com.cities.security;

import com.cities.model.User;
import com.cities.service.UserService;
import com.cities.user.UserDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
                                      createAuthorityList("ROLE_ADMIN", "ROLE_USER"));
    }
}
