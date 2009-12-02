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

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portal.webdav.WebDAVRequest;
import com.liferay.portal.webdav.WebDAVStorage;
import com.liferay.portal.webdav.WebDAVUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="UnlockMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class UnlockMethodImpl implements Method {

	public int process(WebDAVRequest webDavRequest) throws WebDAVException {
		WebDAVStorage storage = webDavRequest.getWebDAVStorage();

		String token = getToken(webDavRequest.getHttpServletRequest());

		if (!storage.isSupportsClassTwo()) {
			return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		}

		if (storage.unlockResource(webDavRequest, token)) {
			return HttpServletResponse.SC_NO_CONTENT;
		}
		else {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
	}

	protected String getToken(HttpServletRequest request) {
		String token = StringPool.BLANK;

		String value = GetterUtil.getString(request.getHeader("Lock-Token"));

		if (_log.isDebugEnabled()) {
			_log.debug("\"Lock-Token\" header is " + value);
		}

		if (value.startsWith("<") && value.endsWith(">")) {
			value = value.substring(1, value.length() - 1);
		}

		int index = value.indexOf(WebDAVUtil.TOKEN_PREFIX);

		if (index >= 0) {
			index += WebDAVUtil.TOKEN_PREFIX.length();

			if (index < value.length()) {
				token = GetterUtil.getString(value.substring(index));
			}
		}

		return token;
	}

	private static Log _log = LogFactoryUtil.getLog(UnlockMethodImpl.class);

}