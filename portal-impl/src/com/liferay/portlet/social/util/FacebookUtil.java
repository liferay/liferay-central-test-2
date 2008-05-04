/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="FacebookUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class FacebookUtil {

	public static final String FACEBOOK_APPS_URL = "http://apps.facebook.com/";

	public static final String FACEBOOK_SERVLET_PATH = "/facebook/";

	public static String getCallbackURL(
		String fbmlPortletURL, String facebookAppName) {

		int pos = fbmlPortletURL.indexOf(
			StringPool.SLASH, Http.HTTPS_WITH_SLASH.length());

		StringMaker sm = new StringMaker();

		sm.append(fbmlPortletURL.substring(0, pos));
		sm.append(FACEBOOK_SERVLET_PATH);
		sm.append(facebookAppName);
		sm.append(fbmlPortletURL.substring(pos));

		String callbackURL = sm.toString();

		if (!callbackURL.endsWith(StringPool.SLASH)) {
			callbackURL += StringPool.SLASH;
		}

		return callbackURL;
	}

	public static String[] getFacebookData(HttpServletRequest req) {
		String path = GetterUtil.getString(req.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf(StringPool.SLASH, 1);

		if (pos == -1) {
			return null;
		}

		String facebookAppName = path.substring(1, pos);

		if (_log.isDebugEnabled()) {
			_log.debug("Facebook application name " + facebookAppName);
		}

		if (Validator.isNull(facebookAppName)) {
			return null;
		}

		String redirect = path.substring(pos);

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		if (Validator.isNull(redirect)) {
			return null;
		}

		pos = path.indexOf("/-/");

		String appPath = StringPool.BLANK;

		if (pos != -1) {
			pos = path.indexOf(StringPool.SLASH, pos + 3);

			if (pos != -1) {
				appPath = path.substring(pos);
			}
		}

		return new String[] {facebookAppName, redirect, appPath};
	}

	public static boolean isFacebook(String currentURL) {
		String path = currentURL;

		if (currentURL.startsWith(Http.HTTP)) {
			int pos = currentURL.indexOf(
				StringPool.SLASH, Http.HTTPS_WITH_SLASH.length());

			path = currentURL.substring(pos);
		}

		if (path.startsWith(FACEBOOK_SERVLET_PATH)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(FacebookUtil.class);

}