package com.example.team1_be.domain.Member;

import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.errors.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findByUser(User user) {
        return memberRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST));
    }

    public Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException("유효하지 않은 요청", HttpStatus.BAD_REQUEST));
    }
}
