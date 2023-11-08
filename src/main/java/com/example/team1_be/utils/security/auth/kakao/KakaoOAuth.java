package com.example.team1_be.utils.security.auth.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KakaoOAuth {
	@Value("${kakao:clientId}")
	private String REDIRECT_URI;
	@Value("${kakao:redirectURI}")
	private String CLIENT_ID;

	public KakaoOAuthToken getToken(String code) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);    // BE 테스트 시 : ba5bf7b3c440fb54f054ac5c3bfff761
		params.add("redirect_uri", REDIRECT_URI);    // BE 테스트 시 : 8080
		params.add("code", code);

		return executeRequest(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			headers,
			params,
			KakaoOAuthToken.class
		);
	}

	public KakaoUserProfile getProfile(KakaoOAuthToken token) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token.getAccess_token());

		return executeRequest(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.POST,
			headers,
			null,
			KakaoUserProfile.class
		);
	}

	public <T> T executeRequest(String url, HttpMethod method, HttpHeaders headers, MultiValueMap<String, String> body,
		Class<T> clazz) throws JsonProcessingException {
		RestTemplate rt = new RestTemplate();

		HttpEntity<MultiValueMap<String, String>> requestEntity;

		if (body != null) {
			requestEntity = new HttpEntity<>(body, headers);
		} else {
			requestEntity = new HttpEntity<>(headers);
		}

		ResponseEntity<String> response = rt.exchange(url, method, requestEntity, String.class);

		ObjectMapper om = new ObjectMapper();

		return om.readValue(response.getBody(), clazz);
	}
}