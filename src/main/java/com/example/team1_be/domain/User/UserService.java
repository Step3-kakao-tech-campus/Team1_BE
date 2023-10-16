package com.example.team1_be.domain.User;

import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.User.DTO.Join;
import com.example.team1_be.domain.User.DTO.Login;
import com.example.team1_be.domain.User.UnfinishedUser.UnfinishedUser;
import com.example.team1_be.domain.User.UnfinishedUser.UnfinishedUserRepository;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UnfinishedUserRepository unfinishedUserRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public Login.Response login(String code, Long kakaoId) {
        User user = userRepository.findByKakaoId(kakaoId).orElse(null);
        if (user == null) {
            UnfinishedUser unfinishedUser = UnfinishedUser.builder()
                    .code(code)
                    .kakaoId(kakaoId)
                    .build();
            unfinishedUserRepository.save(unfinishedUser);
            throw new CustomException("회원이 아닙니다.", HttpStatus.NOT_FOUND);
        }

        Member member = memberRepository.findByUser(user).orElse(null);
        Boolean isAdmin = (member == null) ? null : member.getIsAdmin();
        return new Login.Response(isAdmin);
    }

    // login 시도했던 code를 통해, join 시 kakaoId와 매칭
    @Transactional
    public Long matchKakaoId(String code) {
        UnfinishedUser unfinishedUser = unfinishedUserRepository.findByCode(code).orElseThrow(
                () -> new CustomException("유효하지 않은 code입니다.", HttpStatus.BAD_REQUEST)
        );
        return unfinishedUser.getKakaoId();
    }

    @Transactional
    public Join.Response join(Join.Request request, Long kakaoId) {
        User user = userRepository.findByKakaoId(kakaoId).orElse(null);
        // delete도 구현
        if (user != null) {
            throw new CustomException("이미 가입되었습니다.", HttpStatus.BAD_REQUEST);
        }

        user = User.builder()
                .kakaoId(kakaoId)
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