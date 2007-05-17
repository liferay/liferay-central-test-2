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

package com.liferay.portal.security.auth;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="OpenIdAutoLogin.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class OpenIdAutoLogin implements AutoLogin {

	public String[] login(HttpServletRequest req, HttpServletResponse res)
		throws AutoLoginException {
		HttpSession ses = req.getSession();
		String[] credentials = null;

		Long userId =
			(Long) ses.getAttribute(WebKeys.OPEN_ID_LOGIN);

		if (userId == null) {
			return credentials;
		}

		ses.removeAttribute(WebKeys.OPEN_ID_LOGIN);

		try {
			User user =
				UserLocalServiceUtil.getUserById(userId.longValue());

			credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();
		}
		catch (Exception e) {
			_log.error("Unexpected auto-login error", e);
		}
		return credentials;
	}

	private static Log _log = LogFactory.getLog(OpenIdAutoLogin.class);

}