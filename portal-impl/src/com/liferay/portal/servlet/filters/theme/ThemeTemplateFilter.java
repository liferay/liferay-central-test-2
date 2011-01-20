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
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ThemeTemplateFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ThemeTemplateFilter extends BaseFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		ServletContext servletContext = getFilterConfig().getServletContext();

		long companyId = PortalUtil.getCompanyId(request);

		String themeId = ParamUtil.getString(request, "themeId");

		String path = request.getRequestURI();

		if (Validator.isNotNull(themeId)) {
			Theme theme = ThemeLocalServiceUtil.getTheme(
				companyId, themeId, false);

			request.setAttribute(WebKeys.THEME, theme);
			request.setAttribute("path", path);

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(WRAPPER_PATH);

			if (requestDispatcher != null) {
				requestDispatcher.include(request, response);
			}
		}
		else {
			processFilter(
				ThemeTemplateFilter.class, request, response, filterChain);
		}
	}

	protected Log getLog() {
		return _log;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeTemplateFilter.class);

	private static final String WRAPPER_PATH =
		PortalUtil.getPathContext().concat(
			"/html/common/themes/template_wrapper.jsp");

}