/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.CookieNotSupportedException;
import com.liferay.util.CookieUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="CookieKeys.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CookieKeys {

	public static final String COOKIE_SUPPORT = "COOKIE_SUPPORT";

	public static final String GUEST_LANGUAGE_ID = "GUEST_LANGUAGE_ID";

	public static final String ID = "ID";

	public static final String LOGIN = "LOGIN";

	public static final String PASSWORD = "PASSWORD";

	public static final int MAX_AGE = 31536000;

	public static void addCookie(HttpServletResponse res, Cookie cookie) {
		if (GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.SESSION_ENABLE_PERSISTENT_COOKIES))) {

			if (!GetterUtil.getBoolean(PropsUtil.get(PropsUtil.TCK_URL))) {

				// Setting a cookie will cause the TCK to lose its ability
				// to track sessions

				res.addCookie(cookie);
			}
		}
	}

	public static void addSupportCookie(HttpServletResponse res) {
		Cookie cookieSupportCookie =
			new Cookie(CookieKeys.COOKIE_SUPPORT, "true");

		cookieSupportCookie.setPath("/");
		cookieSupportCookie.setMaxAge(CookieKeys.MAX_AGE);

		addCookie(res, cookieSupportCookie);
	}

	public static void validateSupportCookie(HttpServletRequest req)
		throws CookieNotSupportedException {

		if (GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.SESSION_ENABLE_PERSISTENT_COOKIES)) &&
			GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.SESSION_TEST_COOKIE_SUPPORT))) {

			String cookieSupport = CookieUtil.get(
				req.getCookies(), CookieKeys.COOKIE_SUPPORT);

			if (Validator.isNull(cookieSupport)) {
				throw new CookieNotSupportedException();
			}
		}
	}

}