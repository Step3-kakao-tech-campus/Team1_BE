package com.example.team1_be.utils.security.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = ((HttpServletRequest) request).getHeader("Authorization");
        String token;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            if (jwtProvider.verify(token)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authentication = jwtProvider.getAuthentication(token);
                context.setAuthentication(authentication);
            }

        }
        chain.doFilter(request, response);
    }
}