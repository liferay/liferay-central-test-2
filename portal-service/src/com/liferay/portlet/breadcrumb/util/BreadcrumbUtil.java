/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.breadcrumb.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.breadcrumb.Breadcrumb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author José Manuel Navarro
 */
@ProviderType
public class BreadcrumbUtil {

	public static Breadcrumb getBreadcrumb() {
		PortalRuntimePermission.checkGetBeanProperty(BreadcrumbUtil.class);

		return _breadcrumb;
	}

	public static BreadcrumbEntry getCurrentGroupEntry(
			ThemeDisplay themeDisplay)
		throws Exception {

		return getBreadcrumb().getCurrentGroupEntry(themeDisplay);
	}

	public static BreadcrumbEntry getGuestGroupEntry(ThemeDisplay themeDisplay)
		throws Exception {

		return getBreadcrumb().getGuestGroupEntry(themeDisplay);
	}

	public static List<BreadcrumbEntry> getLayoutEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		return getBreadcrumb().getLayoutEntries(themeDisplay);
	}

	public static List<BreadcrumbEntry> getParentGroupEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		return getBreadcrumb().getParentGroupEntries(themeDisplay);
	}

	public static List<BreadcrumbEntry> getPortletEntries(
		HttpServletRequest request) {

		return getBreadcrumb().getPortletEntries(request);
	}

	public void setBreadcrumb(Breadcrumb breadcrumb) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_breadcrumb = breadcrumb;
	}

	private static Breadcrumb _breadcrumb;

}