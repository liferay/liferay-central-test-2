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

package com.liferay.portal.servlet.filters.etag;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ETagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ETagUtil {

	public static boolean processETag(
		HttpServletRequest request, HttpServletResponse response,
		byte[] bytes) {

		if (!_ETAG_FILTER_ENABLED) {
			return false;
		}

		return _processETag(
			request, response, _hashCode(bytes, 0, bytes.length));
	}

	public static boolean processETag(
		HttpServletRequest request, HttpServletResponse response, byte[] bytes,
		int length) {

		if (!_ETAG_FILTER_ENABLED) {
			return false;
		}

		return _processETag(request, response, _hashCode(bytes, 0, length));
	}

	public static boolean processETag(
		HttpServletRequest request, HttpServletResponse response, byte[] bytes,
		int offset, int length) {

		if (!_ETAG_FILTER_ENABLED) {
			return false;
		}

		return _processETag(
			request, response, _hashCode(bytes, offset, length));
	}

	public static boolean processETag(
		HttpServletRequest request, HttpServletResponse response, String s) {

		if (!_ETAG_FILTER_ENABLED) {
			return false;
		}

		return _processETag(request, response, s.hashCode());
	}

	private static int _hashCode(byte[] data, int offset, int length) {
		int hashCode = 0;

		for (int i = 0; i < length; i++) {
			hashCode = 31 * hashCode + data[offset++];
		}

		return hashCode;
	}

	private static boolean _processETag(
		HttpServletRequest request, HttpServletResponse response,
		int hashCode) {

		if (!_ETAG_FILTER_ENABLED) {
			return false;
		}

		String eTag = Integer.toHexString(hashCode);

		response.setHeader(HttpHeaders.ETAG, eTag);

		String ifNoneMatch = request.getHeader(HttpHeaders.IF_NONE_MATCH);

		if (eTag.equals(ifNoneMatch)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			response.setContentLength(0);

			return true;
		}
		else {
			return false;
		}
	}

	private static final boolean _ETAG_FILTER_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(ETagFilter.class.getName()), true);

}