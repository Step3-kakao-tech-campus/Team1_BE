package com.example.team1_be.domain.User;

import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
import com.example.team1_be.utils.security.auth.kakao.KakaoUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public void register(User user){
        userRepository.save(user);
    }

    @Transactional
    public String login(KakaoUserProfile kakaoUserProfile){
        User user = userRepository.findByKakaoId(kakaoUserProfile.getId())
                .orElse(null);
        if (user == null) {
            user = User.builder()
                    .kakaoId(kakaoUserProfile.getId())
                    .name("안한주")
                    .phoneNumber("010-8840-3048")
                    .build();
            register(user);
        }
        return jwtProvider.createJwt(user.getId());
    }
}
