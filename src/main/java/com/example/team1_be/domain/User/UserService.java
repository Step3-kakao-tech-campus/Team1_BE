package com.example.team1_be.domain.User;

import com.example.team1_be.domain.Group.DTO.GetMembers;
import com.example.team1_be.domain.User.DTO.Login;
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
    public Login.Response login(Long kakaoId){
        User user = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        if (user == null) {
            user = User.builder()
                    .kakaoId(kakaoId)
                    .name("안한주")
                    .phoneNumber("010-8840-3048")
                    .build();
            register(user);
        }
        return new Login.Response(user.getName(), true, "");
    }

    @Transactional
    public String getJwt(Long kakaoId){
        User user = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        return jwtProvider.createJwt(user.getId());
    }

}
