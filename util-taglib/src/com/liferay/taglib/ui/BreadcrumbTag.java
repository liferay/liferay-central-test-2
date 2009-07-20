/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.model.Layout;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

public class BreadcrumbTag extends IncludeTag {

	public static void doTag(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		doTag(
			_PAGE, null, null, null, _DISPLAY_STYLE, servletContext, request,
			response);
	}

	public static void doTag(
			String page, Layout selLayout, String selLayoutParam,
			PortletURL portletURL, int displayStyle,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		request.setAttribute("liferay-ui:breadcrumb:selLayout", selLayout);
		request.setAttribute(
			"liferay-ui:breadcrumb:selLayoutParam", selLayoutParam);
		request.setAttribute("liferay-ui:breadcrumb:portletURL", portletURL);
		request.setAttribute(
			"liferay-ui:breadcrumb:displayStyle", String.valueOf(displayStyle));

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(request, response);
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse stringResponse = getServletResponse();

			doTag(
				getPage(), _selLayout, _selLayoutParam, _portletURL,
				_displayStyle, servletContext, request, stringResponse);

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setSelLayout(Layout selLayout) {
		_selLayout = selLayout;
	}

	public void setSelLayoutParam(String selLayoutParam) {
		_selLayoutParam = selLayoutParam;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setDisplayStyle(int displayStyle) {
		_displayStyle = displayStyle;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/breadcrumb/page.jsp";

	private static final int _DISPLAY_STYLE = 0;

	private Layout _selLayout;
	private String _selLayoutParam;
	private PortletURL _portletURL;
	private int _displayStyle = _DISPLAY_STYLE;

}