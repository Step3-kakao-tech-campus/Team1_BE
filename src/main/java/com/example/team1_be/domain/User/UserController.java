package com.example.team1_be.domain.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class UserController {
    @GetMapping("/login/kakao")//Data 리턴해주는 컨트롤러 함수
    public @ResponseBody String kakaoCallback(String code) {

        return "인가 코드 : " + code;
    }
}
