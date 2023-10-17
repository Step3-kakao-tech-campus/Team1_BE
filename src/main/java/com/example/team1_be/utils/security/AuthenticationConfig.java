package com.example.team1_be.utils.security;


import com.example.team1_be.utils.security.auth.jwt.JwtAuthenticationFilter;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.io.PrintWriter;
import java.util.List;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final JwtProvider jwtProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.httpBasic().disable()
                .csrf().disable()
                .cors().configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
                    corsConfiguration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    return corsConfiguration;
                })
                .and()
//                .headers().frameOptions().sameOrigin()
                .authorizeHttpRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/**","/swagger-ui/**","/v3/api-docs/**","/swagger-resources/**").permitAll()
                .antMatchers("/login/kakao").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}