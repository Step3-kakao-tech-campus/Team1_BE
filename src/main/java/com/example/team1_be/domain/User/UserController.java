package com.example.team1_be.domain.User;

import javax.validation.Valid;

import com.example.team1_be.utils.errors.ClientErrorCode;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.errors.exception.ServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.team1_be.domain.User.DTO.Join;
import com.example.team1_be.domain.User.DTO.Login;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.errors.exception.BadRequestException;
import com.example.team1_be.utils.security.auth.kakao.KakaoOAuth;
import com.example.team1_be.utils.security.auth.kakao.KakaoOAuthToken;
import com.example.team1_be.utils.security.auth.kakao.KakaoUserProfile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final KakaoOAuth kakaoOAuth;

	@GetMapping("/login/kakao") // code발급받기 test용
	public @ResponseBody ResponseEntity<?> kakaoCallback(String code) {
		return ResponseEntity.ok().body(code);
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody @Valid Login.Request request) {
		String code = request.getCode();
		Long kakaoId = null;
		try {
			KakaoOAuthToken kakaoOAuthToken = kakaoOAuth.getToken(code);
			KakaoUserProfile kakaoOAuthProfile = kakaoOAuth.getProfile(kakaoOAuthToken);
			kakaoId = kakaoOAuthProfile.getId();
		} catch (Exception e) {
			throw new CustomException(ClientErrorCode.KAKAO_CONNECT_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Login.Response responseDTO = userService.login(code, kakaoId);
		ApiUtils.ApiResult<Login.Response> response = ApiUtils.success(responseDTO);
		String jwt = userService.getJWT(kakaoId);
		return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(response);
	}

	@PostMapping("/auth/join")
	public ResponseEntity<?> join(@RequestBody @Valid Join.Request request) {
		Long kakaoId = userService.matchKakaoId(request.getCode());

		Join.Response responseDTO = userService.join(request, kakaoId);
		ApiUtils.ApiResult<Join.Response> response = ApiUtils.success(responseDTO);
		String jwt = userService.getJWT(kakaoId);
		return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(response);
	}

}