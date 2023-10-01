package com.example.team1_be.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class UserService {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findUser(Long kakaoId){
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(()->{return null;});
        return user;
    }

    @Transactional
    public void register(User user){
        int id = user.getId();
        Long kakaoId = user.getKakaoId();
        String name = user.getName();
        String phoneNumber = user.getPhoneNumber();
        userRepository.save(user);
        System.out.println("전화번호 길이 : " + phoneNumber.length());
        em.flush();
    }
}
