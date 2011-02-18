/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.util;

import javax.portlet.PortletRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ContextPathUtil {

	public static String getContextPath(ServletContext servletContext) {
		String contextPath = GetterUtil.getString(
			servletContext.getContextPath(), StringPool.BLANK);

		if (contextPath.equals(StringPool.SLASH)) {
			contextPath = StringPool.BLANK;
		}

		return contextPath;
	}

	public static String getContextPath(HttpServletRequest servletRequest) {
		String contextPath = GetterUtil.getString(
			servletRequest.getContextPath(), StringPool.BLANK);

		if (contextPath.equals(StringPool.SLASH)) {
			contextPath = StringPool.BLANK;
		}

		return contextPath;
	}

	public static String getContextPath(PortletRequest portletRequest) {
		String contextPath = GetterUtil.getString(
			portletRequest.getContextPath(), StringPool.BLANK);

		if (contextPath.equals(StringPool.SLASH)) {
			contextPath = StringPool.BLANK;
		}

		return contextPath;
	}

}
