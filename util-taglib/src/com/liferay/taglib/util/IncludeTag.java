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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletContextImpl;

import javax.portlet.PortletConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="IncludeTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class IncludeTag extends ParamAndPropertyAncestorTagImpl {

	public int doEndTag() throws JspException {
		HttpServletRequest request = null;

		try {
			ServletContext servletContext = getServletContext();
			request = getServletRequest();
			StringServletResponse stringResponse = getServletResponse();

			Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

			String page = getPage();

			if (isTheme()) {
				ThemeUtil.include(
					servletContext, request, stringResponse, pageContext, page,
					theme);
			}
			else {
				servletContext = getServletContext(servletContext, request);

				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher(page);

				requestDispatcher.include(request, stringResponse);
			}

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			if (request != null) {
				String currentURL = (String)request.getAttribute(
					WebKeys.CURRENT_URL);

				_log.error(
					"Current URL " + currentURL + " generates exception: " +
						e.getMessage());
			}

			LogUtil.log(_log, e);

			if (e instanceof JspException) {
				throw (JspException)e;
			}

			return EVAL_PAGE;
		}
		finally {
			clearParams();
			clearProperties();
		}
	}

	public boolean isTheme() {
		return false;
	}

	public String getPage() {
		if (Validator.isNull(_page)) {
			return getDefaultPage();
		}
		else {
			return _page;
		}
	}

	public void setPage(String page) {
		_page = page;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public ServletContext getServletContext() {
		if (_servletContext != null) {
			return _servletContext;
		}
		else {
			return super.getServletContext();
		}
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected String getDefaultPage() {
		return null;
	}

	protected ServletContext getServletContext(
			ServletContext servletContext, HttpServletRequest request)
		throws SystemException {

		if (Validator.isNull(_portletId)) {
			return servletContext;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), _portletId);

		if (portlet == null) {
			return servletContext;
		}

		PortletApp portletApp = portlet.getPortletApp();

		if (!portletApp.isWARFile()) {
			return servletContext;
		}

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);
		PortletContextImpl portletContextImpl =
			(PortletContextImpl)portletConfig.getPortletContext();

		return portletContextImpl.getServletContext();
	}

	private static Log _log = LogFactoryUtil.getLog(IncludeTag.class);

	private String _page;
	private String _portletId;
	private ServletContext _servletContext;

}