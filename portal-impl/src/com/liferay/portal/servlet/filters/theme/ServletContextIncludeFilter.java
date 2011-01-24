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

package com.liferay.portal.servlet.filters.theme;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ServletContextIncludeFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ServletContextIncludeFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		String path = request.getRequestURI();

		String themeId = ParamUtil.getString(request, "themeId");
		long plid = ParamUtil.getLong(request, "plid");

		if (plid <= 0) {
			plid = ParamUtil.getLong(request, "p_l_id");
		}

		Theme theme = null;

		if (Validator.isNotNull(themeId)) {
			long companyId = PortalUtil.getCompanyId(request);

			theme = ThemeLocalServiceUtil.getTheme(
				companyId, themeId, false);
		}
		else if (plid > 0) {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			theme = layout.getTheme();
			themeId = theme.getThemeId();
		}
		else if (request.getAttribute(WebKeys.THEME) != null) {
			theme = (Theme)request.getAttribute(WebKeys.THEME);
			themeId = theme.getThemeId();
		}
		else if (request.getAttribute(WebKeys.THEME_DISPLAY) != null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			theme = themeDisplay.getTheme();
			themeId = theme.getThemeId();
		}
		else if (request.getAttribute(
					WebKeys.VIRTUAL_HOST_LAYOUT_SET) != null) {

			LayoutSet layoutSet = (LayoutSet)request.getAttribute(
				WebKeys.VIRTUAL_HOST_LAYOUT_SET);

			theme = layoutSet.getTheme();
			themeId = theme.getThemeId();
		}

		_log.debug("{themeId: " + themeId + ", path: " + path + "}");

		if (theme == null) {
			processFilter(
				ServletContextIncludeFilter.class, request, response,
				filterChain);
		}
		else {
			ServletContext servletContext =
				getFilterConfig().getServletContext();

			request.setAttribute(WebKeys.THEME, theme);
			request.setAttribute("path", path);

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(_WRAPPER_PATH);

			if (requestDispatcher != null) {
				requestDispatcher.include(request, response);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServletContextIncludeFilter.class);

	private static final String _WRAPPER_PATH =
		"/WEB-INF/jsp/_servlet_context_include.jsp";

}