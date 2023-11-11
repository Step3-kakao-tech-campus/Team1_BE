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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
	private final CustomUserDetailsService customUserDetailsService;
	@Value("${jwt:secretKey}")
	private String secretKey;
	private final Long expiredMs = 1000 * 60 * 60L * 24;

	public String createJwt(Long id) {
		log.info("ID {} 사용자를 위한 JWT 생성", id);
		Claims claims = Jwts.claims();
		claims.put("id", id);

		String token = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

		log.info("생성된 JWT: {}", token);

		return token;
	}

	public boolean verify(String jwtString) throws JsonProcessingException {
		log.info("JWT 검증: {}", jwtString);

		try {
			Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwtString);
			log.info("JWT 검증 성공");
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.error("JWT 검증 실패: 보안 오류 또는 JWT 형식 오류", e);
		} catch (UnsupportedJwtException e) {
			log.error("JWT 검증 실패: 지원하지 않는 JWT", e);
		} catch (IllegalArgumentException e) {
			log.error("JWT 검증 실패: 잘못된 인자", e);
		} catch (ExpiredJwtException e) {
			log.error("JWT 검증 실패: JWT 만료", e);
		} catch (Exception e) {
			log.error("JWT 검증 실패: 알 수 없는 오류", e);
		}

		return false;
	}

	public Authentication getAuthentication(String token) {
		log.info("토큰으로 인증 정보 얻기: {}", token);
		Claims claims = getUserId(token);

		Object claimsId = claims.get("id");
		if (claimsId == null) {
			throw new RuntimeException("권한 정보 없음");
		}
		UserDetails principal = customUserDetailsService.loadUserByUsername(claimsId.toString());
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, token,
			principal.getAuthorities());

		log.info("얻은 인증 정보: {}", authentication);

		return authentication;
	}

	private Claims getUserId(String token) {
		log.info("토큰으로부터 사용자 ID 얻기: {}", token);

		Claims claims = Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody();

		log.info("얻은 사용자 ID: {}", claims.get("id"));

		return claims;
	}
}