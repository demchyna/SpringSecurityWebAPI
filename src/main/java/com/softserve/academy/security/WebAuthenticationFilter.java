package com.softserve.academy.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.academy.model.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class WebAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    public WebAuthenticationFilter(AuthenticationManager webAuthenticationManager) {
        setAuthenticationManager(webAuthenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getParameter("username"),
                request.getParameter("password")
        );

        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.addHeader("Authorization", "Bearer " + JwtService.createJwt(authResult));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorURL = request.getRequestURL().toString();
        String errorMessage = failed.getMessage();
        Error error = new Error(HttpStatus.UNAUTHORIZED.value(), errorURL, errorMessage);

        PrintWriter out = response.getWriter();
        String jsonString = new ObjectMapper().writeValueAsString(error);

        out.print(jsonString);
        out.flush();

    }
}
