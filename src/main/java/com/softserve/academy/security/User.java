package com.softserve.academy.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class User implements UserDetails {
    private long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> roles;

    public User() {    }

    public User(long id, String username, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public User(long id, String username, String password, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
    }

    @Override
    public String toString() {
        return "User { " +
                "id = " + id +
                ", username = '" + username + '\'' +
                ", password = '" + password + '\'' +
                ", roles = " + roles +
                " }";
    }
}
