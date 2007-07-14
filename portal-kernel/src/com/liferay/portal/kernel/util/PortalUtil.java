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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="PortalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalUtil {

	public static HttpServletRequest getHttpServletRequest(PortletRequest req) {
		Object returnObj = _invoke(_METHOD_GETHTTPSERVLETREQUEST, req);

		if (returnObj != null) {
			return (HttpServletRequest)returnObj;
		}
		else {
			return null;
		}
	}

	public static HttpServletResponse getHttpServletResponse(
		PortletResponse res) {

		Object returnObj = _invoke(_METHOD_GETHTTPSERVLETRESPONSE, res);

		if (returnObj != null) {
			return (HttpServletResponse)returnObj;
		}
		else {
			return null;
		}
	}

	private static Object _invoke(String method, Object arg) {
		return _invoke(method, new Object[] {arg});
	}

	private static Object _invoke(String method, Object[] args) {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			Thread.currentThread().setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			MethodWrapper methodWrapper = new MethodWrapper(
				_CLASS, method, args);

			return MethodInvoker.invoke(methodWrapper, false);
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	private static final String _CLASS = "com.liferay.portal.util.PortalUtil";

	private static final String _METHOD_GETHTTPSERVLETREQUEST =
		"getHttpServletRequest";

	private static final String _METHOD_GETHTTPSERVLETRESPONSE =
		"getHttpServletResponse";

	private static Log _log = LogFactoryUtil.getLog(PortalUtil.class);

}