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
		throws com.liferay.portal.SystemException {
		return getService()
				   .getColorScheme(companyId, themeId, colorSchemeId, wapTheme);
	}

	public static com.liferay.portal.model.Theme getTheme(long companyId,
		java.lang.String themeId, boolean wapTheme)
		throws com.liferay.portal.SystemException {
		return getService().getTheme(companyId, themeId, wapTheme);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId) {
		return getService().getThemes(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId, long groupId, long userId, boolean wapTheme)
		throws com.liferay.portal.SystemException {
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