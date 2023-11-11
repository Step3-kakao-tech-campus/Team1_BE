package com.example.team1_be.utils.security.XSS;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class XssRequestWrapper extends HttpServletRequestWrapper {
	private byte[] rawData;
	private final HttpServletRequest request;
	private final ResettableServletInputStream servletStream;

	public XssRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
		this.servletStream = new ResettableServletInputStream();
	}

	public void resetInputStream(byte[] data) {
		servletStream.stream = new ByteArrayInputStream(data);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (rawData == null) {
			rawData = IOUtils.toByteArray(this.request.getReader());
			servletStream.stream = new ByteArrayInputStream(rawData);
		}
		return servletStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (rawData == null) {
			rawData = IOUtils.toByteArray(this.request.getReader());
			servletStream.stream = new ByteArrayInputStream(rawData);
		}
		return new BufferedReader(new InputStreamReader(servletStream));
	}

	private class ResettableServletInputStream extends ServletInputStream {

		private InputStream stream;

		@Override
		public int read() throws IOException {
			return stream.read();
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {
		}
	}
}
