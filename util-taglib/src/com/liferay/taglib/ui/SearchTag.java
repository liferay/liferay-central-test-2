/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="SearchTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SearchTag extends BoxTag {

	public static void doTag(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(_TOP);

		requestDispatcher.include(request, response);

		requestDispatcher = servletContext.getRequestDispatcher(_BOTTOM);

		requestDispatcher.include(request, response);
	}

	public String getTop() {
		return _TOP;
	}

	public String getBottom() {
		return _BOTTOM;
	}

	private static final String _TOP = "/html/taglib/ui/search/start.jsp";

	private static final String _BOTTOM = "/html/taglib/ui/search/end.jsp";

}