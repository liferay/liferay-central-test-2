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

package com.liferay.taglib.theme;

import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.ThemeUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

/**
 * <a href="IncludeTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class IncludeTag extends com.liferay.taglib.util.IncludeTag {

	protected void include(String page) throws Exception {
		ServletContext servletContext = getServletContext();
		HttpServletRequest request = getServletRequest();

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		ComponentContext componentContext =
			(ComponentContext)request.getAttribute(
				ComponentConstants.COMPONENT_CONTEXT);

		if (componentContext != null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String tilesTitle = (String)componentContext.getAttribute("title");
			String tilesContent =
				(String)componentContext.getAttribute("content");
			boolean tilesSelectable = GetterUtil.getBoolean(
				(String)componentContext.getAttribute("selectable"));

			themeDisplay.setTilesTitle(tilesTitle);
			themeDisplay.setTilesContent(tilesContent);
			themeDisplay.setTilesSelectable(tilesSelectable);
		}

		ThemeUtil.include(
			servletContext, request, new PipingServletResponse(pageContext),
			pageContext, page, theme);
	}

}