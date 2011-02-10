/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Shuyang Zhou
 */
public class HeaderCacheServletResponse extends HttpServletResponseWrapper {

	public HeaderCacheServletResponse(HttpServletResponse response) {
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

		super.addDateHeader(name, value);
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

		super.addHeader(name, value);
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

		super.addIntHeader(name, value);
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

	public int getStatus() {
		return _status;
	}

	public void sendError(int status) throws IOException {
		_status = status;

		super.sendError(status);
	}

	public void sendError(int status, String msg) throws IOException {
		_status = status;

		super.sendError(status, msg);
	}

	public void sendRedirect(String location) throws IOException {
		super.sendRedirect(location);

		setStatus(SC_MOVED_TEMPORARILY);
	}

	public void setContentLength(int contentLength) {
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

		super.setDateHeader(name, value);
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

		super.setHeader(name, value);
	}

	public void setIntHeader(String name, int value) {
		List<Header> values = new ArrayList<Header>();

		_headers.put(name, values);

		Header header = new Header();

		header.setType(Header.INTEGER_TYPE);
		header.setIntValue(value);

		values.add(header);

		super.setIntHeader(name, value);
	}

	public void setStatus(int status) {
		_status = status;

		super.setStatus(status);
	}

	private String _contentType;
	private Map<String, List<Header>> _headers =
		new HashMap<String, List<Header>>();
	private int _status = SC_OK;

}