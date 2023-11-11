package com.example.team1_be.utils.security.auth.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {
	private final CustomUserDetailsService customUserDetailsService;
	@Value("${jwt:secretKey}")
	private String secretKey;
	private final Long expiredMs = 1000 * 60 * 60L * 24;

	public String createJwt(Long id) {
		Claims claims = Jwts.claims();
		claims.put("id", id);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public boolean verify(String jwtString) throws JsonProcessingException {
		try {
			Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwtString);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			return false;
		} catch (UnsupportedJwtException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (ExpiredJwtException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getUserId(token);

		Object claimsId = claims.get("id");
		if (claimsId == null) {
			throw new RuntimeException("권한 정보 없음");
		}
		UserDetails principal = customUserDetailsService.loadUserByUsername(claimsId.toString());
		return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
	}

	private Claims getUserId(String token) {
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody();
	}
}