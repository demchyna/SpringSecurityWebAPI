package com.softserve.academy.exception;

import com.softserve.academy.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Error notFoundExceptionHandler(HttpServletRequest request, NotFoundException exception) {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        return new Error(HttpStatus.NOT_FOUND.value(), errorURL, errorMessage);
    }
}
