package com.example.team1_be.utils.security.auth.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoOAuth {
    public KakaoOAuthToken getToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "d92567d27c897634df0c647a27b70e3c");
        params.add("redirect_uri", "http://localhost:8080/login/kakao");
        params.add("code", code);

        return executeRequest(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                headers,
                params,
                KakaoOAuthToken.class
        );
    }

    public KakaoUserProfile getProfile(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);

        return executeRequest(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                headers,
                null,
                KakaoUserProfile.class
        );
    }

    public <T> T executeRequest(String url, HttpMethod method, HttpHeaders headers, MultiValueMap<String, String> body, Class<T> clazz) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();

        HttpEntity<MultiValueMap<String, String>> requestEntity;

        if (body != null) {
            requestEntity= new HttpEntity<>(body, headers);
        } else {
            requestEntity= new HttpEntity<>(headers);
        }

        ResponseEntity<String> response = rt.exchange(url, method, requestEntity, String.class);

        ObjectMapper om = new ObjectMapper();

        return om.readValue(response.getBody(), clazz);
    }
}
