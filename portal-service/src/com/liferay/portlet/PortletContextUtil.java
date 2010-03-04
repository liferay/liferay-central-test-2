/*
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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassInvoker;

import com.liferay.portal.model.Portlet;

import javax.servlet.ServletContext;

/**
 * <a href="PortletContextUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class PortletContextUtil {

	public static ServletContext getServletContext(
		Portlet portlet, ServletContext servletContext) {

		ServletContext value = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				_CLASS, _METHOD_GET_SERVLET_CONTEXT,
				new Object[] {portlet, servletContext}, false);

			if (returnObj != null) {
				value = (ServletContext)returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return value;
	}

	private static final String _CLASS = "com.liferay.portlet.PortletContextUtilImpl";

	private static final String _METHOD_GET_SERVLET_CONTEXT = "getServletContext";

	private static Log _log = LogFactoryUtil.getLog(PortletContextUtil.class);
}
