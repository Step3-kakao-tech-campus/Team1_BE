package com.example.team1_be.domain.User;

import com.example.team1_be.domain.Group.Invite.DTO.InvitationCheck;
import com.example.team1_be.domain.User.DTO.Login;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;
import com.example.team1_be.utils.security.auth.kakao.KakaoOAuth;
import com.example.team1_be.utils.security.auth.kakao.KakaoOAuthToken;
import com.example.team1_be.utils.security.auth.kakao.KakaoUserProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoOAuth kakaoOAuth;

    @GetMapping("/login/kakao")
    public @ResponseBody ResponseEntity<?> kakaoCallback(@RequestBody @Valid Login.Request request) throws JsonProcessingException {
        KakaoOAuthToken kakaoOAuthToken = kakaoOAuth.getToken(request.getCode());

        KakaoUserProfile kakaoOAuthProfile = kakaoOAuth.getProfile(kakaoOAuthToken);
        Long kakaoId = kakaoOAuthProfile.getId();

        Login.Response responseDTO = userService.login(kakaoId);
        ApiUtils.ApiResult<Login.Response> response = ApiUtils.success(responseDTO);

        String jwt = userService.getJwt(kakaoId);
        return ResponseEntity.ok().header("Authorization", jwt).body(response);
    }


}