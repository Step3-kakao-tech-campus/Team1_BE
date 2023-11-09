package com.example.team1_be.utils.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.team1_be.domain.User.Role.Roles;
import com.example.team1_be.utils.security.XSS.XSSProtectFilter;
import com.example.team1_be.utils.security.auth.CustomAccessDeniedHandler;
import com.example.team1_be.utils.security.auth.CustomAuthenticationEntryPoint;
import com.example.team1_be.utils.security.auth.jwt.JwtAuthenticationFilter;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class AuthenticationConfig {
	private final JwtProvider jwtProvider;
	private final ObjectMapper om;
	private final Environment env;

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

		http.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint(om));

		http.exceptionHandling()
			.accessDeniedHandler(new CustomAccessDeniedHandler(om));

		authorizeLogin(http);

		authorizeGroup(http);

		authorizeSchedule(http);

		authorizeError(http);

		http.authorizeHttpRequests()
			.anyRequest().denyAll();

		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
			UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(new XSSProtectFilter(om),
			ChannelProcessingFilter.class);

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
				corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
				corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
				corsConfiguration.setAllowedHeaders(List.of("*"));
				corsConfiguration.addExposedHeader("Authorization");
				return corsConfiguration;
			});
	}

	private void authorizeError(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers("/error").permitAll();
	}

	private void authorizeSchedule(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET, "/schedule/application/**")
			.hasRole(Roles.ROLE_MEMBER.getAuth())
			.antMatchers(HttpMethod.PUT, "/schedule/application")
			.hasRole(Roles.ROLE_MEMBER.getAuth())
			.antMatchers(HttpMethod.GET, "/schedule/fix/month/**")
			.hasAnyRole(Roles.ROLE_ADMIN.getAuth(), Roles.ROLE_MEMBER.getAuth())
			.antMatchers(HttpMethod.GET, "/schedule/fix/day/**")
			.hasAnyRole(Roles.ROLE_ADMIN.getAuth(), Roles.ROLE_MEMBER.getAuth())
			.antMatchers(HttpMethod.GET, "/schedule/remain/week/**")
			.hasAnyRole(Roles.ROLE_ADMIN.getAuth(), Roles.ROLE_MEMBER.getAuth())
			.antMatchers(HttpMethod.GET, "/schedule/recommend/**")
			.hasRole(Roles.ROLE_ADMIN.getAuth())
			.antMatchers(HttpMethod.POST, "/schedule/fix/**")
			.hasRole(Roles.ROLE_ADMIN.getAuth())
			.antMatchers(HttpMethod.GET, "/schedule/status/**")
			.hasAnyRole(Roles.ROLE_ADMIN.getAuth(), Roles.ROLE_MEMBER.getAuth())
			.antMatchers(HttpMethod.POST, "/schedule/worktime")
			.hasAnyRole(Roles.ROLE_ADMIN.getAuth())
			.antMatchers(HttpMethod.GET, "/schedule/worktime/**")
			.hasAnyRole(Roles.ROLE_ADMIN.getAuth());
	}

	private void authorizeGroup(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.POST, "/group").hasRole(Roles.ROLE_ADMIN.getAuth())
			.antMatchers(HttpMethod.GET, "/group").hasAnyRole(Roles.ROLE_ADMIN.getAuth(), Roles.ROLE_MEMBER.getAuth())
			.antMatchers(HttpMethod.GET, "/group/invitation").hasRole(Roles.ROLE_ADMIN.getAuth())
			.antMatchers(HttpMethod.POST, "/group/invitation").hasRole(Roles.ROLE_MEMBER.getAuth())
			.antMatchers(HttpMethod.GET, "/group/invitation/information/**").hasRole(Roles.ROLE_MEMBER.getAuth());
	}

	private void authorizeLogin(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers("/login/kakao").permitAll()
			.antMatchers("/auth/**").permitAll();
	}

	private void authorizeApiAndDocs(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers("/api/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll();
	}

	private void authorizeH2Console(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers("/h2-console/**").permitAll();
	}
}