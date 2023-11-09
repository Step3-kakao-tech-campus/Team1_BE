package com.example.team1_be.utils.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.team1_be.utils.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper om;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		ApiUtils.ApiError apiError = new ApiUtils.ApiError(ClientErrorCode.INVALID_TOKEN.getMessage(), ClientErrorCode.INVALID_TOKEN.getValue());
		ApiUtils.ApiResult apiResult = new ApiUtils.ApiResult(false, null, apiError);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(om.writeValueAsString(apiResult));
	}
}
