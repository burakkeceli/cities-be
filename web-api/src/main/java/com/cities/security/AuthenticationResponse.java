package com.cities.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {

    private Integer userId;
    private String username;
    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(Integer userId, String username, String token) {
        this.username = username;
        this.userId = userId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
