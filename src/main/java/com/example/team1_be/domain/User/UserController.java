package com.example.team1_be.domain.User;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.team1_be.domain.User.DTO.Join;
import com.example.team1_be.domain.User.DTO.Login;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.errors.ClientErrorCode;
import com.example.team1_be.utils.errors.exception.ServerErrorException;
import com.example.team1_be.utils.security.auth.kakao.KakaoOAuth;
import com.example.team1_be.utils.security.auth.kakao.KakaoOAuthToken;
import com.example.team1_be.utils.security.auth.kakao.KakaoUserProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public ResponseEntity<ApiUtils.ApiResult<Login.Response>> login(
		@RequestBody @Valid Login.Request request) {

		String code = request.getCode();
		Long kakaoId = null;
		try {
			log.info("토큰 발행");
			KakaoOAuthToken kakaoOAuthToken = kakaoOAuth.getToken(code);
			log.info("프로필 조회");
			KakaoUserProfile kakaoOAuthProfile = kakaoOAuth.getProfile(kakaoOAuthToken);
			log.info("아이디 조회");
			kakaoId = kakaoOAuthProfile.getId();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServerErrorException("code가 만료되었거나 유효하지 않습니다.", ClientErrorCode.KAKAO_CONNECT_FAIL);
		}

		Login.Response responseDTO = userService.login(code, kakaoId);
		ApiUtils.ApiResult<Login.Response> response = ApiUtils.success(responseDTO);
		String jwt = userService.getJWT(kakaoId);
		return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(response);
	}

	@PostMapping("/auth/join")
	public ResponseEntity<ApiUtils.ApiResult<Join.Response>> join(
		@RequestBody @Valid Join.Request request) {

		Long kakaoId = userService.matchKakaoId(request.getCode());

		Join.Response responseDTO = userService.join(request, kakaoId);
		ApiUtils.ApiResult<Join.Response> response = ApiUtils.success(responseDTO);
		String jwt = userService.getJWT(kakaoId);
		return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(response);
	}

}