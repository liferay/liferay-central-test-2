/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;


/**
 * <a href="ThemeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ThemeLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ThemeLocalService
 * @generated
 */
public class ThemeLocalServiceWrapper implements ThemeLocalService {
	public ThemeLocalServiceWrapper(ThemeLocalService themeLocalService) {
		_themeLocalService = themeLocalService;
	}

	public com.liferay.portal.model.ColorScheme getColorScheme(long companyId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		boolean wapTheme) throws com.liferay.portal.SystemException {
		return _themeLocalService.getColorScheme(companyId, themeId,
			colorSchemeId, wapTheme);
	}

	public com.liferay.portal.model.Theme getTheme(long companyId,
		java.lang.String themeId, boolean wapTheme)
		throws com.liferay.portal.SystemException {
		return _themeLocalService.getTheme(companyId, themeId, wapTheme);
	}

	public java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId) {
		return _themeLocalService.getThemes(companyId);
	}

	public java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId, long groupId, long userId, boolean wapTheme)
		throws com.liferay.portal.SystemException {
		return _themeLocalService.getThemes(companyId, groupId, userId, wapTheme);
	}

	public java.util.List<com.liferay.portal.model.Theme> getWARThemes() {
		return _themeLocalService.getWARThemes();
	}

	public java.util.List<String> init(
		javax.servlet.ServletContext servletContext,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _themeLocalService.init(servletContext, themesPath,
			loadFromServletContext, xmls, pluginPackage);
	}

	public java.util.List<String> init(java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _themeLocalService.init(servletContextName, servletContext,
			themesPath, loadFromServletContext, xmls, pluginPackage);
	}

	public void uninstallThemes(java.util.List<String> themeIds) {
		_themeLocalService.uninstallThemes(themeIds);
	}

	public ThemeLocalService getWrappedThemeLocalService() {
		return _themeLocalService;
	}

	private ThemeLocalService _themeLocalService;
}