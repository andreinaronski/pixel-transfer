package com.pixel.transfer.service.auth.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserIdAuthentication implements Authentication {
    private final Long userId;
    private boolean authenticated = true;

    public UserIdAuthentication(Long userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return null; }
    @Override
    public Object getCredentials() { return null; }
    @Override
    public Object getDetails() { return null; }
    @Override
    public Object getPrincipal() { return userId; }
    @Override
    public boolean isAuthenticated() { return authenticated; }
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException { this.authenticated = isAuthenticated; }
    @Override
    public String getName() { return userId.toString(); }
}
