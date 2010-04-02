/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <a href="StringServletResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @auther Shuyang Zhou
 */
public class StringServletResponse extends HttpServletResponseWrapper {

	public StringServletResponse(HttpServletResponse response) {
		super(response);
	}

	public void addDateHeader(String name, long value) {
		List<Header> values = _headers.get(name);

		if (values == null) {
			values = new ArrayList<Header>();

			_headers.put(name, values);
		}

		Header header = new Header();

		header.setType(Header.DATE_TYPE);
		header.setDateValue(value);

		values.add(header);
	}

	public void addHeader(String name, String value) {
		List<Header> values = _headers.get(name);

		if (values == null) {
			values = new ArrayList<Header>();

			_headers.put(name, values);
		}

		Header header = new Header();

		header.setType(Header.STRING_TYPE);
		header.setStringValue(value);

		values.add(header);

		if (name.equals(HttpHeaders.CONTENT_TYPE)) {
			setContentType(value);
		}
	}

	public void addIntHeader(String name, int value) {
		List<Header> values = _headers.get(name);

		if (values == null) {
			values = new ArrayList<Header>();

			_headers.put(name, values);
		}

		Header header = new Header();

		header.setType(Header.INTEGER_TYPE);
		header.setIntValue(value);

		values.add(header);
	}

	public int getBufferSize() {
		return _bufferSize;
	}

	public String getContentType() {
		return _contentType;
	}

	public String getHeader(String name) {
		List<Header> values = _headers.get(name);

		if ((values == null) || values.isEmpty()) {
			return null;
		}

		Header header = values.get(0);

		return header.toString();
	}

	public Map<String, List<Header>> getHeaders() {
		return _headers;
	}

	public ServletOutputStream getOutputStream() {
		if (_servletOutputStream == null) {
			_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
			_servletOutputStream = new PipingServletOutputStream(
				_unsyncByteArrayOutputStream);
		}

		return _servletOutputStream;
	}

	public int getStatus() {
		return _status;
	}

	public String getString() {
		if (_string != null) {
			return _string;
		}

		if (_servletOutputStream != null) {
			try {
				_string = _unsyncByteArrayOutputStream.toString(
					StringPool.UTF8);
			}
			catch (UnsupportedEncodingException uee) {
				_log.error(uee, uee);

				_string = StringPool.BLANK;
			}
		}
		else if (_printWriter != null) {
			_string = _unsyncStringWriter.toString();
		}
		else {
			_string = StringPool.BLANK;
		}

		return _string;
	}

	public UnsyncByteArrayOutputStream getUnsyncByteArrayOutputStream() {
		return _unsyncByteArrayOutputStream;
	}

	public PrintWriter getWriter() {
		if (_printWriter == null) {
			_unsyncStringWriter = new UnsyncStringWriter(true);
			_printWriter = new PrintWriter(_unsyncStringWriter);
		}

		return _printWriter;
	}

	public boolean isCalledGetOutputStream() {
		if (_servletOutputStream != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public void recycle() {
		_status = SC_OK;
		_string = null;

		resetBuffer();
	}

	public void resetBuffer() {
		if (_servletOutputStream != null) {
			_unsyncByteArrayOutputStream.reset();
		}
		else if (_printWriter != null) {
			_unsyncStringWriter.reset();
		}
	}

	public void sendError(int status) throws IOException {
		_status = status;

		super.sendError(status);
	}

	public void sendError(int status, String msg) throws IOException {
		_status = status;

		super.sendError(status, msg);
	}

	public void setBufferSize(int bufferSize) {
		_bufferSize = bufferSize;
	}

	public void setContentType(String contentType) {
		_contentType = contentType;

		super.setContentType(contentType);
	}

	public void setDateHeader(String name, long value) {
		List<Header> values = new ArrayList<Header>();

		_headers.put(name, values);

		Header header = new Header();

		header.setType(Header.DATE_TYPE);
		header.setDateValue(value);

		values.add(header);
	}

	public void setHeader(String name, String value) {
		List<Header> values = new ArrayList<Header>();

		_headers.put(name, values);

		Header header = new Header();

		header.setType(Header.STRING_TYPE);
		header.setStringValue(value);

		values.add(header);

		if (name.equals(HttpHeaders.CONTENT_TYPE)) {
			setContentType(value);
		}
	}

	public void setIntHeader(String name, int value) {
		List<Header> values = new ArrayList<Header>();

		_headers.put(name, values);

		Header header = new Header();

		header.setType(Header.INTEGER_TYPE);
		header.setIntValue(value);

		values.add(header);
	}

	public void setLocale(Locale locale) {
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setString(String string) {
		_string = string;
	}

	private static Log _log = LogFactoryUtil.getLog(
		StringServletResponse.class);

	private int _bufferSize;
	private String _contentType;
	private Map<String, List<Header>> _headers =
		new HashMap<String, List<Header>>();
	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;
	private int _status = SC_OK;
	private String _string;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;
	private UnsyncStringWriter _unsyncStringWriter;

}