package com.example.team1_be.domain.User;

import com.example.team1_be.utils.ApiUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login/kakao")
    public ResponseEntity<?> kakaoCallback(String code) throws JsonProcessingException {
        UserResponse.KakaoLoginDTO kakaoLoginDTO = userService.kakaoLogin(code);
        ApiUtils.ApiResult<UserResponse.KakaoLoginDTO> result = ApiUtils.success(kakaoLoginDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/auth/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO joinDTO, Errors errors) throws JsonProcessingException {
        String jwt = userService.join(joinDTO);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO loginDTO, Errors errors) throws JsonProcessingException {
        String jwt = userService.login(loginDTO);
        return ResponseEntity.ok(jwt);
    }
}
