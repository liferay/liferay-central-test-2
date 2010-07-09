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

package com.liferay.util.servlet.filters;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.nio.charset.CharsetEncoderUtil;
import com.liferay.portal.kernel.servlet.Header;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Young
 */
public class CacheResponseData implements Serializable {

	public CacheResponseData(StringServletResponse stringResponse) {
		if (stringResponse.isCalledGetOutputStream()) {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				stringResponse.getUnsyncByteArrayOutputStream();

			_content = unsyncByteArrayOutputStream.unsafeGetByteArray();
			_contentLength = unsyncByteArrayOutputStream.size();
		}
		else {
			String content = stringResponse.getString();

			ByteBuffer contentByteBuffer = CharsetEncoderUtil.encode(
				StringPool.UTF8, content);

			_content = contentByteBuffer.array();
			_contentLength = contentByteBuffer.limit();
		}

		_contentType = stringResponse.getContentType();
		_headers = stringResponse.getHeaders();
	}

	public CacheResponseData(
		byte[] content, int contentLength, String contentType,
		Map<String, List<Header>> headers) {

		_content = content;
		_contentLength = contentLength;
		_contentType = contentType;
		_headers = headers;
	}

	public Object getAttribute(String name) {
		return _attributes.get(name);
	}

	public byte[] getContent() {
		return _content;
	}

	public int getContentLength() {
		return _contentLength;
	}

	public String getContentType() {
		return _contentType;
	}

	public Map<String, List<Header>> getHeaders() {
		return _headers;
	}

	public void setAttribute(String name, Object value) {
		_attributes.put(name, value);
	}

	private Map<String, Object> _attributes = new HashMap<String, Object>();
	private byte[] _content;
	private int _contentLength;
	private String _contentType;
	private Map<String, List<Header>> _headers;

}