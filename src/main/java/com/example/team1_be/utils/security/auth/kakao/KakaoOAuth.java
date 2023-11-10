package com.example.team1_be.utils.security.auth.kakao;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuth {
	private final Environment env;
	@Value("${kakao.clientId}")
	private String CLIENT_ID;
	@Value("${kakao.redirectURI}")
	private String REDIRECT_URI;
	@Value("${krampoline.proxy.hostName")
	private String PROXY_HOST_NAME;
	@Value("${krampoline.proxy.port")
	private String PROXY_PORT;

	public KakaoOAuthToken getToken(String code) throws JsonProcessingException {
		log.info("Getting Kakao OAuth token with code: {}", code);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("code", code);

		KakaoOAuthToken token = executeRequest(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			headers,
			params,
			KakaoOAuthToken.class
		);

		log.info("Got Kakao OAuth token: {}", token);

		return token;
	}

	public KakaoUserProfile getProfile(KakaoOAuthToken token) throws JsonProcessingException {
		log.info("Getting Kakao user profile with token: {}", token.getAccess_token());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token.getAccess_token());

		KakaoUserProfile userProfile = executeRequest(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.POST,
			headers,
			null,
			KakaoUserProfile.class
		);

		log.info("Got Kakao user profile: {}", userProfile);

		return userProfile;
	}

	public <T> T executeRequest(String url, HttpMethod method, HttpHeaders headers, MultiValueMap<String, String> body,
		Class<T> clazz) throws JsonProcessingException {

		log.info("Sending {} request to {}", method, url);

		RestTemplate rt;
		if (!isLocalMode()) {
			HttpHost proxy = new HttpHost(PROXY_HOST_NAME, Integer.parseInt(PROXY_PORT));
			CloseableHttpClient httpClient = HttpClients.custom()
				.setProxy(proxy)
				.build();

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
			factory.setConnectionRequestTimeout(360000);
			factory.setConnectTimeout(360000);
			factory.setReadTimeout(360000);

			rt = new RestTemplate(factory);
		} else {
			rt = new RestTemplate();
		}

		HttpEntity<MultiValueMap<String, String>> requestEntity;

		if (body != null) {
			requestEntity = new HttpEntity<>(body, headers);
		} else {
			requestEntity = new HttpEntity<>(headers);
		}

		ResponseEntity<String> response = null;
		try {
			response = rt.exchange(url, method, requestEntity, String.class);
		} catch (Exception e) {
			log.error("Error while sending {} request to {}: {}", method, url, e.getMessage());
		}

		if (response == null) {
			throw new RuntimeException("Failed to send request to " + url);
		}

		log.info("Received response: {}", response);

		ObjectMapper om = new ObjectMapper();

		T result = om.readValue(response.getBody(), clazz);

		log.info("Parsed response: {}", result);

		return result;
	}

	private boolean isLocalMode() {
		String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "local";
		return profile.equals("local");
	}
}