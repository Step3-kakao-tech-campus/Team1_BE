package com.example.team1_be.domain.Group;

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(User user, Create.Request request) {
        Group group = Group.builder()
                .name(request.getMarketName())
                .address(request.getMainAddress()+request.getDetailAddress())
                .businessNumber(request.getMarketNumber())
                .build();
        groupRepository.save(group);

        Member member = Member.builder()
                .user(user)
                .isAdmin(true)
                .group(group)
                .build();
        memberRepository.save(member);
    }
}
