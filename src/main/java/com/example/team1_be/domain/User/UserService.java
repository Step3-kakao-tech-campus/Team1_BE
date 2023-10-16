package com.example.team1_be.domain.User;

import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.User.DTO.Join;
import com.example.team1_be.domain.User.DTO.Login;
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
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public Login.Response login(Long kakaoId){
        User user = userRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new CustomException("회원이 아닙니다.", HttpStatus.NOT_FOUND)
        );

        Member member = memberRepository.findByUser(user).orElseThrow(
                () -> new CustomException("그룹에 가입하거나 그룹을 생성하세요.", HttpStatus.NOT_FOUND) // 몇번코드로 할까요
        );
        return new Login.Response(member.getIsAdmin());
    }

    @Transactional
    public Join.Response join(Join.Request request) {
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

        return new Join.Response(request.getIsAdmin());
    }

    @Transactional
    public String getJWT(Long kakaoId){
        User user = userRepository.findByKakaoId(kakaoId).orElse(null);
        return jwtProvider.createJwt(user.getId());
    }

}