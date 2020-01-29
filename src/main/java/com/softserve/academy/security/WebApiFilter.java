package com.softserve.academy.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.academy.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class WebApiFilter extends AbstractAuthenticationProcessingFilter {

    public WebApiFilter(String processesUrl) {
        super(processesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String jsonWebToken = request.getHeader("Authorization");

        if (jsonWebToken != null) {
            return getAuthenticationManager().authenticate(
                    new WebAuthenticationToken(jsonWebToken)
            );
        }

        throw new BadCredentialsException("Bad user Credentials!");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        response.addHeader("Authorization", "Bearer " + JwtService.createJwt(authResult));
        response.setStatus(HttpServletResponse.SC_OK);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

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
