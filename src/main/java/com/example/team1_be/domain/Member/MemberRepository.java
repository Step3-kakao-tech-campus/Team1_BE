package com.example.team1_be.domain.Member;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUser(User user);

    List<Member> findAllByGroup(Group group);
}
