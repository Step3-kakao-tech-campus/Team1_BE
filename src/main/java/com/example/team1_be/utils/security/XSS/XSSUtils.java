package com.example.team1_be.utils.security.XSS;

import org.apache.commons.text.StringEscapeUtils;

public class XSSUtils {
	public static String charEscape(String value) {
		return StringEscapeUtils.escapeHtml4(value);
	}
}
