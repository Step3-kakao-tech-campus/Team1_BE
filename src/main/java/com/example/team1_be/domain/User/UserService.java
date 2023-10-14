package com.example.team1_be.domain.User;

import com.example.team1_be.utils.errors.exception.Exception400;
import com.example.team1_be.utils.errors.exception.Exception404;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
import com.example.team1_be.utils.security.auth.kakao.KakaoOAuth;
import com.example.team1_be.utils.security.auth.kakao.KakaoOAuthToken;
import com.example.team1_be.utils.security.auth.kakao.KakaoUserProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final KakaoOAuth kakaoOAuth;

    @Transactional
    public UserResponse.KakaoLoginDTO kakaoLogin(String code) throws JsonProcessingException {
        KakaoOAuthToken kakaoOAuthToken = kakaoOAuth.getToken(code);
        String accessToken = kakaoOAuthToken.getAccess_token();
        KakaoUserProfile kakaoOAuthProfile = kakaoOAuth.getProfile(accessToken);

        userRepository.findByKakaoId(kakaoOAuthProfile.getId()).orElseThrow(
                () -> new Exception404("가입하지 않은 유저입니다 : " +accessToken)
        );

        return new UserResponse.KakaoLoginDTO(accessToken);
    }

    @Transactional
    public String join(UserRequest.JoinDTO joinDTO) throws JsonProcessingException {
        String accessToken = joinDTO.getAccessToken();
        if (kakaoOAuth.getTokenValidation(accessToken) == false) {
            throw new Exception400("유효하지 않은 토큰입니다" + accessToken);
        }

        KakaoUserProfile kakaoOAuthProfile = kakaoOAuth.getProfile(accessToken);
        Long kakaoId = kakaoOAuthProfile.getId();
        User user = userRepository.findByKakaoId(kakaoId).orElse(null);
        if (user != null) {
            throw new Exception400("이미 가입한 유저입니다 : " + accessToken);
        }

        user = User.builder()
                .kakaoId(kakaoId)
                .name(joinDTO.getName())
                .build();
        userRepository.save(user);

        return jwtProvider.createJwt(user.getId());
    }

    @Transactional
    public String login(UserRequest.LoginDTO loginDTO) throws JsonProcessingException {
        String accessToken = loginDTO.getAccessToken();
        if (kakaoOAuth.getTokenValidation(accessToken) == false) {
            throw new Exception400("유효하지 않은 토큰입니다" + accessToken);
        }

        KakaoUserProfile kakaoOAuthProfile = kakaoOAuth.getProfile(accessToken);
        Long kakaoId = kakaoOAuthProfile.getId();
        User user = userRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new Exception404("가입하지 않은 유저입니다 : " + accessToken)
        );

        return jwtProvider.createJwt(user.getId());
    }
}
