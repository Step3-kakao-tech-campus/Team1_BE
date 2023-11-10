package com.example.team1_be.domain.User;

import com.example.team1_be.utils.errors.ClientErrorCode;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.DTO.Join;
import com.example.team1_be.domain.User.DTO.Login;
import com.example.team1_be.domain.User.Role.Service.RoleService;
import com.example.team1_be.domain.User.UnfinishedUser.UnfinishedUser;
import com.example.team1_be.domain.User.UnfinishedUser.UnfinishedUserRepository;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.errors.exception.NotUserException;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository repository;
	private final UnfinishedUserRepository unfinishedUserRepository;
	private final JwtProvider jwtProvider;

	private final RoleService roleService;

	private User createUserAndAssignRole(Join.Request request, Long kakaoId) {
		log.info("사용자 생성 및 역할 할당을 시작합니다.");
		User user = User.builder()
			.kakaoId(kakaoId)
			.name(request.getUserName())
			.phoneNumber(null)
			.isAdmin(request.getIsAdmin())
			.build();
		repository.save(user);
		roleService.createRole(user, request.getIsAdmin());
		log.info("사용자 생성 및 역할 할당이 완료되었습니다.");
		return user;
	}

<<<<<<< HEAD
	@Transactional(noRollbackFor = NotUserException.class)
=======
	@Transactional(noRollbackFor = NotFoundException.class)
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
	public Login.Response login(String code, Long kakaoId) {
		log.info("로그인을 시작합니다.");
		User user = repository.findByKakaoId(kakaoId).orElse(null);
		if (user == null) {
			log.info("회원이 아니므로 가입을 시작합니다.");
			UnfinishedUser unfinishedUser = UnfinishedUser.builder()
				.code(code)
				.kakaoId(kakaoId)
				.build();
			unfinishedUserRepository.save(unfinishedUser);
<<<<<<< HEAD

			throw new NotUserException(ClientErrorCode.NOT_USER);
=======
			throw new NotFoundException("임시 정보 저장.");
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		}
		log.info("로그인이 완료되었습니다.");
		return new Login.Response(user.getIsAdmin());
	}

	// login 시도했던 code를 통해, join 시 kakaoId와 매칭
	public Long matchKakaoId(String code) {
		log.info("카카오 ID를 매칭합니다.");
		UnfinishedUser unfinishedUser = unfinishedUserRepository.findByCode(code).orElseThrow(
			() -> new CustomException(ClientErrorCode.UNFINISHED_USER_NOT_FOUND, HttpStatus.BAD_REQUEST)
		);
		return unfinishedUser.getKakaoId();
	}

	@Transactional
	public Join.Response join(Join.Request request, Long kakaoId) {
		log.info("회원 가입을 시작합니다.");
		repository.findByKakaoId(kakaoId).ifPresent(existingUser -> {
			throw new CustomException(ClientErrorCode.DUPLICATE_KAKAO_ID, HttpStatus.BAD_REQUEST);
		});

		User user = createUserAndAssignRole(request, kakaoId);

		log.info("회원 가입이 완료되었습니다.");
		return new Join.Response(user.getIsAdmin());
	}

	public String getJWT(Long kakaoId) {
		log.info("JWT 토큰을 생성합니다.");
		User user = repository.findByKakaoId(kakaoId).orElse(null);
		return jwtProvider.createJwt(user.getId());
	}

	@Transactional
	public void updateGroup(User user, Group group) {
		log.info("사용자의 그룹을 업데이트합니다.");
		user.updateGroup(group);
		repository.save(user);
	}

	public Group findGroupByUser(User user) {
		log.info("사용자의 그룹을 찾습니다.");
		return repository.findGroupByUser(user.getId()).orElse(Group.builder().users(Collections.emptyList()).build());
	}

	public User findById(Long userId) {
		log.info("사용자를 찾습니다.");
		return repository.findById(userId)
			.orElseThrow(() -> new CustomException(ClientErrorCode.USER_ID_NOT_FOUND, HttpStatus.BAD_REQUEST));	// 존재하지 않는 유저입니다.
	}

	public boolean isAdmin(User user) {
		log.info("사용자가 관리자인지 확인합니다.");
		return user.getIsAdmin();
	}

	public Group findGroupByUserOrNull(User user) {
		log.info("사용자의 그룹을 찾습니다.");
		return repository.findGroupByUser(user.getId()).orElse(null);
	}
}