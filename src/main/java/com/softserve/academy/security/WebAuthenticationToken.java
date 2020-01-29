package com.softserve.academy.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class WebAuthenticationToken implements Authentication {

    private UserDetails userDetails;
    private String jsonWebToken;
    private boolean isAuthenticated;

    public WebAuthenticationToken(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public WebAuthenticationToken(String jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }

    public String getJsonWebToken() {
        return jsonWebToken;
    }
}
