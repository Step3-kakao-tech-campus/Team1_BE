package com.example.team1_be.utils.security.auth.kakao;

import com.example.team1_be.utils.errors.exception.Exception401;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class KakaoOAuth {
    public KakaoOAuthToken getToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "d573e4a7b2fcae0f0289d5807605d726");
        params.add("redirect_uri", "http://localhost:3000/login/kakao ");
        params.add("code", code);

        return executeRequest(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                headers,
                params,
                KakaoOAuthToken.class
        );
    }

    public Boolean getTokenValidation(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);

        try {
            executeRequest(
                    "https://kapi.kakao.com/v1/user/access_token_info",
                    HttpMethod.GET,
                    headers,
                    null,
                    JsonNode.class
            );
        } catch (Exception e) {
            return false;
        }

        return true;
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