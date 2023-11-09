package com.example.team1_be.utils.security.auth.kakao;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
	private String PROXY_HOST_NAME = "krmp-proxy.9rum.cc";
	private int PROXY_PORT = 3128;

	public KakaoOAuthToken getToken(String code) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", REDIRECT_URI);
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

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST_NAME, PROXY_PORT));
		requestFactory.setProxy(proxy);

		RestTemplate rt = new RestTemplate(requestFactory);

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