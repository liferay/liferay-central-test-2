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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="FacebookUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class FacebookUtil {

	public static final String FACEBOOK_APPS_URL = "http://apps.facebook.com/";

	public static final String FACEBOOK_SERVLET_PATH = "/facebook/";

	public static String getCallbackURL(
		String fbmlPortletURL, String facebookCanvasPageURL) {

		int pos = fbmlPortletURL.indexOf(
			StringPool.SLASH, Http.HTTPS_WITH_SLASH.length());

		StringBundler sb = new StringBundler(4);

		sb.append(fbmlPortletURL.substring(0, pos));
		sb.append(FACEBOOK_SERVLET_PATH);
		sb.append(facebookCanvasPageURL);
		sb.append(fbmlPortletURL.substring(pos));

		String callbackURL = sb.toString();

		if (!callbackURL.endsWith(StringPool.SLASH)) {
			callbackURL += StringPool.SLASH;
		}

		return callbackURL;
	}

	public static String[] getFacebookData(HttpServletRequest request) {
		String path = GetterUtil.getString(request.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf(StringPool.SLASH, 1);

		if (pos == -1) {
			return null;
		}

		String facebookCanvasPageURL = path.substring(1, pos);

		if (_log.isDebugEnabled()) {
			_log.debug("Facebook canvas page URL " + facebookCanvasPageURL);
		}

		if (Validator.isNull(facebookCanvasPageURL)) {
			return null;
		}

		String redirect = path.substring(pos);

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		if (Validator.isNull(redirect)) {
			return null;
		}

		pos = path.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		String appPath = StringPool.BLANK;

		if (pos != -1) {
			pos = path.indexOf(StringPool.SLASH, pos + 3);

			if (pos != -1) {
				appPath = path.substring(pos);
			}
		}

		return new String[] {facebookCanvasPageURL, redirect, appPath};
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

	private static Log _log = LogFactoryUtil.getLog(FacebookUtil.class);

}