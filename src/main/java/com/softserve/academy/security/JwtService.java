package com.softserve.academy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

public class JwtService {

    public static String createJwt(Authentication authentication) {
        return JWT.create()
                .withSubject(authentication.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 300000))
                .withClaim("id", ((User) authentication.getDetails()).getId())
                .withArrayClaim("roles", ((User) authentication.getDetails()).getRoles())
                .sign(Algorithm.HMAC512("SpringSecurityWebApp"));
    }

    public static String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC512("SpringSecurityWebApp"))
                .build().verify(token)
                .getSubject();
    }

    public static Long getUserIdFromToken(String token) {
        return JWT.require(Algorithm.HMAC512("SpringSecurityWebApp"))
                .build().verify(token)
                .getClaim("id")
                .asLong();
    }

    static List<Role> getRolesFromToken(String token) {
        return JWT.require(Algorithm.HMAC512("SpringSecurityWebApp"))
                .build().verify(token)
                .getClaim("roles")
                .asList(Role.class);
    }
}
