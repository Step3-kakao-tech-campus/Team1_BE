package com.example.team1_be.utils.security.XSS;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class XSSProtectFilter implements Filter {
	private final ObjectMapper om;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		XssRequestWrapper wrappedRequest = new XssRequestWrapper((HttpServletRequest)request);
		String body = IOUtils.toString(wrappedRequest.getReader());

		if (!StringUtils.isBlank(body)) {
			JsonNode root = om.readTree(body);
			processNode(root);
			wrappedRequest.resetInputStream(
				om.writeValueAsString(root).getBytes());
		}

		chain.doFilter(wrappedRequest, response);

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
