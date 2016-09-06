package com.cities.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class SpringSecurityUser implements UserDetails {

    private Long id;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Date lastPasswordReset;

    public SpringSecurityUser() { super(); }

    public SpringSecurityUser(Long id, String userName, String password,
                              Collection<? extends GrantedAuthority> authorities,
                              Date lastPasswordReset) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.lastPasswordReset = lastPasswordReset;
    }

    public Date getLastPasswordReset(){
        return this.getLastPasswordReset();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
