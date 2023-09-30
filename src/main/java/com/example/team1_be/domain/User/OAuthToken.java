package com.example.team1_be.domain.User;

import lombok.Data;

@Data
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private int refresh_token_expires_in;
}
