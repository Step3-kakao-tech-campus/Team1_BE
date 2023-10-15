package com.example.team1_be.domain.User;

import com.example.team1_be.domain.User.DTO.Join;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
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
        User user = userRepository.findByKakaoId(request.getKakaoId()).orElse(null);
        if (user != null) {
            throw new CustomException("잘못된 요청입니다. 이미 가입되었습니다.", HttpStatus.BAD_REQUEST);
        }

        user = User.builder()
                .kakaoId(request.getKakaoId())
                .name(request.getUserName())
                .phoneNumber(null)
                .build();
        userRepository.save(user);

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