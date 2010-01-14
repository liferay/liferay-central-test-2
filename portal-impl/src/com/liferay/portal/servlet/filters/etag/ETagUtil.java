/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

		return _processETag(request, response,
			_hashCode(bytes, 0, bytes.length));
	}

	public static boolean processETag(
		HttpServletRequest request, HttpServletResponse response,
		byte[] bytes, int length) {

		if (!_ETAG_FILTER_ENABLED) {
			return false;
		}

		return _processETag(request, response, _hashCode(bytes, 0, length));
	}

	public static boolean processETag(
		HttpServletRequest request, HttpServletResponse response,
		byte[] bytes, int offset, int length) {

		if (!_ETAG_FILTER_ENABLED) {
			return false;
		}

		return _processETag(request, response,
			_hashCode(bytes, offset, length));
	}

	public static boolean processETag(
		HttpServletRequest request, HttpServletResponse response, String s) {

		if (!_ETAG_FILTER_ENABLED) {
			return false;
		}

		return _processETag(request, response, s.hashCode());
	}

	private static int _hashCode(byte[] data, int offset, int length) {
		int hashCode=0;

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