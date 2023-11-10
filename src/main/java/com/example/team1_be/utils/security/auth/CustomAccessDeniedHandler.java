package com.example.team1_be.utils.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.team1_be.utils.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper om;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		ApiUtils.ApiError apiError = new ApiUtils.ApiError("유효하지 않은 인증입니다.", ClientErrorCode.NO_PERMISSION.getValue());
		ApiUtils.ApiResult apiResult = new ApiUtils.ApiResult(false, null, apiError);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(om.writeValueAsString(apiResult));
	}
}
