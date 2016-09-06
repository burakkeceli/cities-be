package com.cities.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!equalsIgnoreCase(username, "burak")) {
            throw new UsernameNotFoundException(String.format("no found by name %s", username));
        }

        Long id = 10L;
        String password = "123123";
        return new SpringSecurityUser(id,
                                      username,
                                      password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(null), null);
    }
}
