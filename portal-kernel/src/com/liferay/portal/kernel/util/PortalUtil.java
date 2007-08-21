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

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	public static String getPortletNamespace(String portletName) {
		Object returnObj = _invoke(_METHOD_GETPORTLETNAMESPACE, portletName);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String getUserPassword(HttpSession ses) {
		Object returnObj = _invoke(_METHOD_GETUSERPASSWORD, ses);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String getUserPassword(HttpServletRequest req) {
		Object returnObj = _invoke(_METHOD_GETUSERPASSWORD, req);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String getUserPassword(ActionRequest req) {
		Object returnObj = _invoke(_METHOD_GETUSERPASSWORD, req);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static String getUserPassword(RenderRequest req) {
		Object returnObj = _invoke(_METHOD_GETUSERPASSWORD, req);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	public static void setPageSubtitle(
		String subtitle, HttpServletRequest req) {

		_invoke(_METHOD_SETPAGESUBTITLE, subtitle, req);
	}

	public static void setPageTitle(String title, HttpServletRequest req) {
		_invoke(_METHOD_SETPAGETITLE, title, req);
	}

	private static Object _invoke(String method, Object arg) {
		return _invoke(method, new Object[] {arg});
	}

	private static Object _invoke(String method, Object arg1, Object arg2) {
		return _invoke(method, new Object[] {arg1, arg2});
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

	private static final String _METHOD_GETPORTLETNAMESPACE =
		"getPortletNamespace";

	private static final String _METHOD_GETUSERPASSWORD = "getUserPassword";

	private static final String _METHOD_SETPAGESUBTITLE = "setPageSubtitle";

	private static final String _METHOD_SETPAGETITLE = "setPageTitle";

	private static Log _log = LogFactoryUtil.getLog(PortalUtil.class);

}