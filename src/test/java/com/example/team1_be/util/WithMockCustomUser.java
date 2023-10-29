package com.example.team1_be.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
	String username() default "dlwogns";

	String userId() default "1";

	String kakaoId() default "1";

	String phoneNumber() default "010-1111-1111";

	String isAdmin() default "false";

	String role() default "USER";
}
