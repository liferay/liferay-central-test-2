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
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * <a href="BreadcrumbTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BreadcrumbTag extends IncludeTag {

	public static void doTag(
			int displayStyle, boolean showGuestGroup, boolean showParentGroups,
			boolean showLayout, boolean showPortletBreadcrumb,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		doTag(
			_PAGE, null, null, null, displayStyle, showGuestGroup,
			showParentGroups, showLayout, showPortletBreadcrumb, servletContext,
			request, response);
	}

	public static void doTag(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		doTag(
			_PAGE, null, null, null, _DISPLAY_STYLE, _SHOW_GUEST_GROUP,
			_SHOW_PARENT_GROUPS, true, true, servletContext, request,
			response);
	}

	public static void doTag(
			String page, Layout selLayout, String selLayoutParam,
			PortletURL portletURL, int displayStyle, boolean showGuestGroup,
			boolean showParentGroups, boolean showLayout,
			boolean showPortletBreadcrumb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		request.setAttribute(
			"liferay-ui:breadcrumb:displayStyle", String.valueOf(displayStyle));
		request.setAttribute("liferay-ui:breadcrumb:portletURL", portletURL);
		request.setAttribute("liferay-ui:breadcrumb:selLayout", selLayout);
		request.setAttribute(
			"liferay-ui:breadcrumb:selLayoutParam", selLayoutParam);
		request.setAttribute(
			"liferay-ui:breadcrumb:showGuestGroup",
			String.valueOf(showGuestGroup));
		request.setAttribute(
			"liferay-ui:breadcrumb:showLayout", String.valueOf(showLayout));
		request.setAttribute(
			"liferay-ui:breadcrumb:showParentGroups",
			String.valueOf(showParentGroups));
		request.setAttribute(
			"liferay-ui:breadcrumb:showPortletBreadcrumb",
			String.valueOf(showPortletBreadcrumb));

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
				_displayStyle, _showGuestGroup, _showParentGroups, _showLayout,
				_showPortletBreadcrumb, servletContext, request,
				stringResponse);

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setDisplayStyle(int displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setSelLayout(Layout selLayout) {
		_selLayout = selLayout;
	}

	public void setSelLayoutParam(String selLayoutParam) {
		_selLayoutParam = selLayoutParam;
	}

	public void setShowGuestGroup(boolean showGuestGroup) {
		_showGuestGroup = showGuestGroup;
	}

	public void setShowLayout(boolean showLayout) {
		_showLayout = showLayout;
	}

	public void setShowParentGroups(boolean showParentGroups) {
		_showParentGroups = showParentGroups;
	}

	public void setShowPortletBreadcrumb(boolean showPortletBreadcrumb) {
		_showPortletBreadcrumb = showPortletBreadcrumb;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final int _DISPLAY_STYLE = 0;

	private static final String _PAGE = "/html/taglib/ui/breadcrumb/page.jsp";

	private static final boolean _SHOW_GUEST_GROUP =
		PropsValues.BREADCRUMB_SHOW_GUEST_GROUP;

	private static final boolean _SHOW_PARENT_GROUPS =
		PropsValues.BREADCRUMB_SHOW_PARENT_GROUPS;

	private int _displayStyle = _DISPLAY_STYLE;
	private PortletURL _portletURL;
	private Layout _selLayout;
	private String _selLayoutParam;
	private boolean _showGuestGroup = _SHOW_GUEST_GROUP;
	private boolean _showLayout = true;
	private boolean _showParentGroups = _SHOW_PARENT_GROUPS;
	private boolean _showPortletBreadcrumb = true;

}