package com.example.team1_be.util;

import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final CustomUserDetails customUserDetails = new CustomUserDetails(User.builder()
                .id(1L)
                .name(annotation.username())
                .kakaoId(1L)
                .phoneNumber("010-1111-1111")
                .build());
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(customUserDetails,
                        "",
                        customUserDetails.getAuthorities());

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
