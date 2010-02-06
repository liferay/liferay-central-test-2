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
 * <a href="LayoutTemplateLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link LayoutTemplateLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutTemplateLocalService
 * @generated
 */
public class LayoutTemplateLocalServiceUtil {
	public static java.lang.String getContent(
		java.lang.String layoutTemplateId, boolean standard,
		java.lang.String themeId) throws com.liferay.portal.SystemException {
		return getService().getContent(layoutTemplateId, standard, themeId);
	}

	public static com.liferay.portal.model.LayoutTemplate getLayoutTemplate(
		java.lang.String layoutTemplateId, boolean standard,
		java.lang.String themeId) {
		return getService()
				   .getLayoutTemplate(layoutTemplateId, standard, themeId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutTemplate> getLayoutTemplates() {
		return getService().getLayoutTemplates();
	}

	public static java.util.List<com.liferay.portal.model.LayoutTemplate> getLayoutTemplates(
		java.lang.String themeId) {
		return getService().getLayoutTemplates(themeId);
	}

	public static java.lang.String getWapContent(
		java.lang.String layoutTemplateId, boolean standard,
		java.lang.String themeId) throws com.liferay.portal.SystemException {
		return getService().getWapContent(layoutTemplateId, standard, themeId);
	}

	public static java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, Boolean>> init(
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService().init(servletContext, xmls, pluginPackage);
	}

	public static java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, Boolean>> init(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService()
				   .init(servletContextName, servletContext, xmls, pluginPackage);
	}

	public static void readLayoutTemplate(java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.util.Set<com.liferay.portal.kernel.util.ObjectValuePair<String, Boolean>> layoutTemplateIds,
		com.liferay.portal.kernel.xml.Element el, boolean standard,
		java.lang.String themeId,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		getService()
			.readLayoutTemplate(servletContextName, servletContext,
			layoutTemplateIds, el, standard, themeId, pluginPackage);
	}

	public static void uninstallLayoutTemplate(
		java.lang.String layoutTemplateId, boolean standard) {
		getService().uninstallLayoutTemplate(layoutTemplateId, standard);
	}

	public static void uninstallLayoutTemplates(java.lang.String themeId) {
		getService().uninstallLayoutTemplates(themeId);
	}

	public static LayoutTemplateLocalService getService() {
		if (_service == null) {
			_service = (LayoutTemplateLocalService)PortalBeanLocatorUtil.locate(LayoutTemplateLocalService.class.getName());
		}

		return _service;
	}

	public void setService(LayoutTemplateLocalService service) {
		_service = service;
	}

	private static LayoutTemplateLocalService _service;
}