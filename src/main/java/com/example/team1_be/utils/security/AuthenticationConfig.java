package com.example.team1_be.utils.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.team1_be.domain.User.Role.RoleType;
import com.example.team1_be.utils.security.auth.CustomAccessDeniedHandler;
import com.example.team1_be.utils.security.auth.CustomAuthenticationEntryPoint;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class AuthenticationConfig {
	private final JwtProvider jwtProvider;
	private final ObjectMapper om;
	private final Environment env;

	@Value("${cors.origin}")
	private String CORS_ORIGIN;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic()
			.disable();

		http.csrf()
			.disable();

		applyCorsPolicy(http);

		http.headers()
			.frameOptions()
			.disable();

		http.headers()
			.xssProtection();

		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		if (isLocalMode()) {
			authorizeH2Console(http);
			authorizeApiAndDocs(http);

		} else {
			http.headers()
				.contentSecurityPolicy("script-src 'self'");
		}

		authorizeLogin(http);

		authorizeGroup(http);

		authorizeSchedule(http);

		authorizeError(http);

		//        http.authorizeRequests()
		//                .anyRequest()
		//                .denyAll();

		http.addFilterBefore(new CombinedFilter(jwtProvider, om),
			UsernamePasswordAuthenticationFilter.class);

		http.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint(om));

		http.exceptionHandling()
			.accessDeniedHandler(new CustomAccessDeniedHandler(om));
		return http.build();
	}

	private boolean isLocalMode() {
		String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "local";
		return profile.equals("local");
	}

	private void applyCorsPolicy(HttpSecurity http) throws Exception {
		http.cors()
			.configurationSource(request -> {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
				corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
				corsConfiguration.setAllowCredentials(true);
				corsConfiguration.setAllowedHeaders(
					Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
				corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
				return corsConfiguration;
			});
	}

	private void authorizeError(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/error").permitAll();
	}

	private void authorizeSchedule(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/schedule/application/**")
			.hasRole(RoleType.ROLE_MEMBER.getAuthority())
			.antMatchers(HttpMethod.PUT, "/schedule/application")
			.hasRole(RoleType.ROLE_MEMBER.getAuthority())
			.antMatchers(HttpMethod.GET, "/schedule/fix/month/**")
			.hasAnyRole(RoleType.ROLE_ADMIN.getAuthority(), RoleType.ROLE_MEMBER.getAuthority())
			.antMatchers(HttpMethod.GET, "/schedule/fix/day/**")
			.hasAnyRole(RoleType.ROLE_ADMIN.getAuthority(), RoleType.ROLE_MEMBER.getAuthority())
			.antMatchers(HttpMethod.GET, "/schedule/remain/week/**")
			.hasAnyRole(RoleType.ROLE_ADMIN.getAuthority())
			.antMatchers(HttpMethod.GET, "/schedule/recommend/**")
			.hasRole(RoleType.ROLE_ADMIN.getAuthority())
			.antMatchers(HttpMethod.POST, "/schedule/fix/**")
			.hasRole(RoleType.ROLE_ADMIN.getAuthority())
			.antMatchers(HttpMethod.GET, "/schedule/status/**")
			.hasAnyRole(RoleType.ROLE_ADMIN.getAuthority(), RoleType.ROLE_MEMBER.getAuthority())
			.antMatchers(HttpMethod.POST, "/schedule/worktime")
			.hasAnyRole(RoleType.ROLE_ADMIN.getAuthority())
			.antMatchers(HttpMethod.GET, "/schedule/worktime/**")
			.hasAnyRole(RoleType.ROLE_ADMIN.getAuthority());
	}

	private void authorizeGroup(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/group")
			.hasRole(RoleType.ROLE_ADMIN.getAuthority())
			.antMatchers(HttpMethod.GET, "/group")
			.hasAnyRole(RoleType.ROLE_ADMIN.getAuthority(), RoleType.ROLE_MEMBER.getAuthority())
			.antMatchers(HttpMethod.GET, "/group/invitation")
			.hasRole(RoleType.ROLE_ADMIN.getAuthority())
			.antMatchers(HttpMethod.POST, "/group/invitation")
			.hasRole(RoleType.ROLE_MEMBER.getAuthority())
			.antMatchers(HttpMethod.GET, "/group/invitation/information/**")
			.hasRole(RoleType.ROLE_MEMBER.getAuthority());
	}

	private void authorizeLogin(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login/kakao").permitAll()
			.antMatchers("/auth/**").permitAll();
	}

	private void authorizeApiAndDocs(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll();
	}

	private void authorizeH2Console(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/h2-console/**").permitAll();
	}
}