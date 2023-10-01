package com.example.team1_be.domain.User;

import com.example.team1_be.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class UserService {
    @Autowired
    private EntityManager em;

    @Value("${jwt:secretKey}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60l * 24;

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
        em.flush();
    }

    @Transactional
    public String login(UserKakaoProfile userKakaoProfile, User user){

        user = User.builder()
                .kakaoId(userKakaoProfile.getId())
                .name("안한주")
                .phoneNumber("010-8840-3048")
                .build();

        User originUser = findUser(user.getKakaoId());
        if(originUser == null){
            register(user);
        }
        return JwtUtil.createJwt(user.getId(), secretKey, expiredMs);
    }

}
