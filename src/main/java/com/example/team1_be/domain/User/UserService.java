package com.example.team1_be.domain.User;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static String getCodeUrl() {
        return "https://kauth.kakao.com/oauth/authorize?client_id=ba5bf7b3c440fb54f054ac5c3bfff761&redirect_uri=http://localhost:8080/login/kakao&response_type=code";
    }


}
