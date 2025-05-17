package com.system.core.controller;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsLoggingFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("CORS Filter: Entering doFilterInternal");
        System.out.println("CORS Filter: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("Origin: " + request.getHeader("Origin"));
        System.out.println("Access-Control-Request-Method: " + request.getHeader("Access-Control-Request-Method"));
        System.out.println("Access-Control-Request-Headers: " + request.getHeader("Access-Control-Request-Headers"));
        filterChain.doFilter(request, response);
    }
}
