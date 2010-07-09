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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspEngineInfo;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

/**
 * @author Shuyang Zhou
 */
public class JspFactoryWrapper extends JspFactory {

	public JspFactoryWrapper(JspFactory jspFactory) {
		_jspFactory = jspFactory;
	}

	public JspEngineInfo getEngineInfo() {
		return _jspFactory.getEngineInfo();
	}

	public JspApplicationContext getJspApplicationContext(
		ServletContext servletContext) {

		return _jspFactory.getJspApplicationContext(servletContext);
	}

	public PageContext getPageContext(
		Servlet servlet, ServletRequest servletRequest,
		ServletResponse servletResponse, String errorPageURL,
		boolean needsSession, int buffer, boolean autoflush) {

		PageContext pageContext = _jspFactory.getPageContext(
			servlet, servletRequest, servletResponse, errorPageURL,
			needsSession, _JSP_WRITER_BUFFER_SIZE, autoflush);

		return new PageContextWrapper(pageContext);
	}

	public void releasePageContext(PageContext pageContext) {
		if (pageContext instanceof PageContextWrapper) {
			PageContextWrapper pageContextWrapper =
				(PageContextWrapper)pageContext;

			pageContext = pageContextWrapper.getWrappedPageContext();
		}

		_jspFactory.releasePageContext(pageContext);
	}

	private static final int _JSP_WRITER_BUFFER_SIZE = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.JSP_WRITER_BUFFER_SIZE));

	private JspFactory _jspFactory;

}