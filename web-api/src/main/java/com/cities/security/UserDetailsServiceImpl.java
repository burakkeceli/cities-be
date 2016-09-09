package com.cities.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    static Logger log = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("cities => pass " +username);
        if (!equalsIgnoreCase(username, "keceli")) {
            throw new UsernameNotFoundException(String.format("no found by name %s", username));
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("123123");
        log.debug("cities => hashedPassword " +hashedPassword);
        Long id = 10L;
        return new SpringSecurityUser(id,
                                      username,
                                      hashedPassword,
                commaSeparatedStringToAuthorityList("ROLE_ADMIN"), null);
    }
}
