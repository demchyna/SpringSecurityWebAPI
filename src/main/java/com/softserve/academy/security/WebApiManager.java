package com.softserve.academy.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component("webApiManager")
public class WebApiManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        WebAuthenticationToken webAuthenticationToken = (WebAuthenticationToken) authentication;

        String jsonWebToken = webAuthenticationToken.getJsonWebToken().substring(7);

        webAuthenticationToken.setUserDetails(
                new User(
                        JwtService.getUserIdFromToken(jsonWebToken),
                        JwtService.getUsernameFromToken(jsonWebToken),
                        JwtService.getRolesFromToken(jsonWebToken)
                )
        );

        webAuthenticationToken.setAuthenticated(true);
        return webAuthenticationToken;
    }
}
