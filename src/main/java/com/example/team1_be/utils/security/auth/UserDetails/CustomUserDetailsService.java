package com.example.team1_be.utils.security.auth.UserDetails;

import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdString) throws UsernameNotFoundException {
        User user = userRepository.findById(Integer.parseInt(userIdString)).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        }

        return new CustomUserDetails(user);
    }
}
