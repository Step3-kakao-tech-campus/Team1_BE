package com.example.team1_be.utils.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.team1_be.utils.security.XSS.XSSUtils;
import com.example.team1_be.utils.security.XSS.XssRequestWrapper;
import com.example.team1_be.utils.security.auth.jwt.JwtProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class CombinedFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final ObjectMapper om;

	public CombinedFilter(JwtProvider jwtProvider, ObjectMapper om) {
		this.jwtProvider = jwtProvider;
		this.om = om;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		// JWT token verification
		String authorization = request.getHeader("Authorization");
		String token;
		if (isAuthorizationValid(authorization)) {
			token = authorization.substring(7);
			if (jwtProvider.verify(token)) {
				Authentication authentication = jwtProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		// XSS protection
		XssRequestWrapper wrappedRequest = new XssRequestWrapper(request);
		String body = IOUtils.toString(wrappedRequest.getReader());

		if (!StringUtils.isBlank(body)) {
			JsonNode root = om.readTree(body);
			processNode(root);
			wrappedRequest.resetInputStream(
				om.writeValueAsString(root).getBytes());
		}

		filterChain.doFilter(wrappedRequest, response);
	}

	private boolean isAuthorizationValid(String authorization) {
		return authorization != null && authorization.startsWith("Bearer ");
	}

	private void processNode(JsonNode node) {
		if (node.isObject()) {
			processObject((ObjectNode)node);
		} else if (node.isArray()) {
			processArray((ArrayNode)node);
		}
	}

	private void processObject(ObjectNode node) {
		node.fieldNames().forEachRemaining(fieldName -> {
			JsonNode field = node.get(fieldName);
			if (field.isNull()) {
				node.put(fieldName, field);
			} else if (field.isValueNode()) {
				node.put(fieldName, applyEscape(field));
			} else {
				processNode(field);
			}
		});
	}

	private void processArray(ArrayNode node) {
		for (int i = 0; i < node.size(); i++) {
			JsonNode element = node.get(i);
			if (element.isNull()) {
				node.set(i, element);
			} else if (element.isValueNode()) {
				node.set(i, new TextNode(applyEscape(element)));
			} else {
				processNode(element);
			}
		}
	}

	private String applyEscape(JsonNode field) {
		return XSSUtils.charEscape(field.asText());
	}
}