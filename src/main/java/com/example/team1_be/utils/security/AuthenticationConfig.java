package com.example.team1_be.utils.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.team1_be.utils.security.auth.jwt.JwtAuthenticationFilter;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class AuthenticationConfig {
	private final JwtProvider jwtProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
			.csrf().disable()
			.cors().configurationSource(request -> {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
				corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
				corsConfiguration.setAllowedHeaders(List.of("*"));
				corsConfiguration.addExposedHeader("Authorization");
				return corsConfiguration;
			})
			.and()
			.headers().frameOptions().disable()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeHttpRequests()
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/api/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll();

		http.authorizeHttpRequests()
			.antMatchers("/login/kakao").permitAll()
			.antMatchers("/auth/**").permitAll();

		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.POST, "/group").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/group").hasAnyRole("ADMIN", "MEMBER")
			.antMatchers(HttpMethod.GET, "/group/invitation").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/group/invitation").hasRole("MEMBER")
			.antMatchers(HttpMethod.GET, "/group/invitation/information/**").hasRole("MEMBER");

		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET, "/schedule/application/**").hasRole("MEMBER")
			.antMatchers(HttpMethod.PUT, "/schedule/application").hasRole("MEMBER")
			.antMatchers(HttpMethod.GET, "/schedule/fix/month/**").hasAnyRole("ADMIN", "MEMBER")
			.antMatchers(HttpMethod.GET, "/schedule/fix/day/**").hasAnyRole("ADMIN", "MEMBER")
			.antMatchers(HttpMethod.GET, "/schedule/remain/week/**").hasAnyRole("ADMIN", "MEMBER")
			.antMatchers(HttpMethod.GET, "/schedule/recommend/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/schedule/fix/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/schedule/status/**").hasAnyRole("ADMIN", "MEMBER")
			.antMatchers(HttpMethod.POST, "/schedule/worktime").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/schedule/worktime/**").hasAnyRole("ADMIN");

		http.authorizeHttpRequests()
			.antMatchers("/error").permitAll();

		http.authorizeHttpRequests()
			.anyRequest().denyAll();

		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
			UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}