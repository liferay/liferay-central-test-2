/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

/**
 * <a href="HttpHeaders.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface HttpHeaders {

	// Names

	public static final String ACCEPT = "ACCEPT";

	public static final String ACCEPT_ENCODING = "Accept-Encoding";

	public static final String AUTHORIZATION = "Authorization";

	public static final String CACHE_CONTROL = "Cache-Control";

	public static final String COOKIE = "Cookie";

	public static final String CONNECTION = "Connection";

	public static final String CONTENT_DISPOSITION = "Content-Disposition";

	public static final String CONTENT_ENCODING = "Content-Encoding";

	public static final String CONTENT_ID = "Content-ID";

	public static final String CONTENT_LENGTH = "Content-Length";

	public static final String CONTENT_TYPE = "Content-Type";

	public static final String EXPIRES = "Expires";

	public static final String ETAG = "ETag";

	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

	public static final String IF_NONE_MATCH = "If-None-Match";

	public static final String KEEP_ALIVE = "Keep-Alive";

	public static final String LAST_MODIFIED = "Last-Modified";

	public static final String LIFERAY_EMAIL_ADDRESS = "LIFERAY_EMAIL_ADDRESS";

	public static final String LIFERAY_SCREEN_NAME = "LIFERAY_SCREEN_NAME";

	public static final String LIFERAY_USER_ID = "LIFERAY_USER_ID";

	public static final String LOCATION = "Location";

	public static final String PRAGMA = "Pragma";

	public static final String USER_AGENT = "User-Agent";

	public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

	// Values

	public static final String CONNECTION_CLOSE_VALUE = "close";

	public static final String CACHE_CONTROL_DEFAULT_VALUE =
		"max-age=315360000, public";

	public static final String CACHE_CONTROL_PUBLIC_VALUE = "public";

	/**
	 * @deprecated Use <code>CONNECTION_CLOSE_VALUE</code>.
	 */
	public static final String CLOSE = CONNECTION_CLOSE_VALUE;

	public static final String EXPIRES_DEFAULT_VALUE = "315360000";

	public static final String PRAGMA_PUBLIC_VALUE = "public";

	/**
	 * @deprecated Use <code>CACHE_CONTROL_PUBLIC_VALUE</code>.
	 */
	public static final String PUBLIC = CACHE_CONTROL_PUBLIC_VALUE;

}