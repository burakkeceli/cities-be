package com.cities.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SpringSecurityUser implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Date lastPasswordReset;

    public SpringSecurityUser() { super(); }

    public SpringSecurityUser(Long id, String username, String password,
                              Collection<? extends GrantedAuthority> authorities,
                              Date lastPasswordReset) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.lastPasswordReset = lastPasswordReset;
    }

    public Date getLastPasswordReset(){
        return this.getLastPasswordReset();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority s = new SimpleGrantedAuthority("ROLE_ADMIN");
        List sadas = new ArrayList<>();
        sadas.add(s);
        return sadas;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
