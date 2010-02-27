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

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Theme;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;
import com.liferay.taglib.util.ThemeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="BoxTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BoxTag extends ParamAndPropertyAncestorTagImpl {

	public int doAfterBody() {
		_bodyContentString = getBodyContent().getString();

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse stringResponse = getServletResponse();

			Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

			// Top

			if (isTheme()) {
				ThemeUtil.include(
					servletContext, request, stringResponse, pageContext,
					getTop(), theme);
			}
			else {
				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher(getTop());

				requestDispatcher.include(request, stringResponse);
			}

			pageContext.getOut().print(stringResponse.getString());

			// Body

			pageContext.getOut().print(_bodyContentString);

			// Bottom

			//res = getServletResponse();
			stringResponse.recycle();

			if (isTheme()) {
				ThemeUtil.include(
					servletContext, request, stringResponse, pageContext,
					getBottom(), theme);
			}
			else {
				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher(getBottom());

				requestDispatcher.include(request, stringResponse);
			}

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			clearParams();
			clearProperties();
		}
	}

	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public String getBottom() {
		return _bottom;
	}

	public String getTop() {
		return _top;
	}

	public boolean isTheme() {
		return false;
	}

	public void setBottom(String bottom) {
		_bottom = bottom;
	}

	public void setTop(String top) {
		_top = top;
	}

	private String _bodyContentString = StringPool.BLANK;
	private String _bottom;
	private String _top;

}