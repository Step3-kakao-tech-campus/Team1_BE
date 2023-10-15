package com.example.team1_be.domain.User;

import com.example.team1_be.domain.Group.DTO.GetMembers;
import com.example.team1_be.domain.User.DTO.Join;
import com.example.team1_be.domain.User.DTO.Login;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
import com.example.team1_be.utils.security.auth.kakao.KakaoUserProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public String join(Join.Request request) {
        User user = User.builder()
                .kakaoId(request.getKakaoId())
                .name(request.getUserName())
                .build();
        userRepository.save(user);

        // isAdmin 처리 진행

        return jwtProvider.createJwt(user.getId());
    }

    @Transactional
    public String login(Long kakaoId){
        User user = userRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new CustomException("회원이 아닙니다."+kakaoId, HttpStatus.NOT_FOUND)    // kakaoId를 어떤 식으로 전달해야하는지
        );

        return jwtProvider.createJwt(user.getId());
    }

}
