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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ThemeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ThemeLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ThemeLocalService
 * @generated
 */
public class ThemeLocalServiceUtil {
	public static com.liferay.portal.model.ColorScheme getColorScheme(
		long companyId, java.lang.String themeId,
		java.lang.String colorSchemeId, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getColorScheme(companyId, themeId, colorSchemeId, wapTheme);
	}

	public static com.liferay.portal.model.Theme getTheme(long companyId,
		java.lang.String themeId, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTheme(companyId, themeId, wapTheme);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId) {
		return getService().getThemes(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId, long groupId, long userId, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getThemes(companyId, groupId, userId, wapTheme);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getWARThemes() {
		return getService().getWARThemes();
	}

	public static java.util.List<String> init(
		javax.servlet.ServletContext servletContext,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService()
				   .init(servletContext, themesPath, loadFromServletContext,
			xmls, pluginPackage);
	}

	public static java.util.List<String> init(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService()
				   .init(servletContextName, servletContext, themesPath,
			loadFromServletContext, xmls, pluginPackage);
	}

	public static void uninstallThemes(java.util.List<String> themeIds) {
		getService().uninstallThemes(themeIds);
	}

	public static ThemeLocalService getService() {
		if (_service == null) {
			_service = (ThemeLocalService)PortalBeanLocatorUtil.locate(ThemeLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ThemeLocalService service) {
		_service = service;
	}

	private static ThemeLocalService _service;
}