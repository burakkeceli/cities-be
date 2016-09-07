package com.cities.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    static Logger log = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("cities => pass" +username);
        if (!equalsIgnoreCase(username, "keceli")) {
            throw new UsernameNotFoundException(String.format("no found by name %s", username));
        }

        System.out.println("cities => pass username");
        Long id = 10L;
        String password = "123123";
        return new SpringSecurityUser(id,
                                      username,
                                      password,
                commaSeparatedStringToAuthorityList("ROLE_ADMIN"), null);
    }
}
