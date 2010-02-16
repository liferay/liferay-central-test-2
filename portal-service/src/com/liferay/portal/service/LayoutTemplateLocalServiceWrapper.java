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
 * <a href="LayoutTemplateLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link LayoutTemplateLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutTemplateLocalService
 * @generated
 */
public class LayoutTemplateLocalServiceWrapper
	implements LayoutTemplateLocalService {
	public LayoutTemplateLocalServiceWrapper(
		LayoutTemplateLocalService layoutTemplateLocalService) {
		_layoutTemplateLocalService = layoutTemplateLocalService;
	}

	public java.lang.String getContent(java.lang.String layoutTemplateId,
		boolean standard, java.lang.String themeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutTemplateLocalService.getContent(layoutTemplateId,
			standard, themeId);
	}

	public com.liferay.portal.model.LayoutTemplate getLayoutTemplate(
		java.lang.String layoutTemplateId, boolean standard,
		java.lang.String themeId) {
		return _layoutTemplateLocalService.getLayoutTemplate(layoutTemplateId,
			standard, themeId);
	}

	public java.util.List<com.liferay.portal.model.LayoutTemplate> getLayoutTemplates() {
		return _layoutTemplateLocalService.getLayoutTemplates();
	}

	public java.util.List<com.liferay.portal.model.LayoutTemplate> getLayoutTemplates(
		java.lang.String themeId) {
		return _layoutTemplateLocalService.getLayoutTemplates(themeId);
	}

	public java.lang.String getWapContent(java.lang.String layoutTemplateId,
		boolean standard, java.lang.String themeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutTemplateLocalService.getWapContent(layoutTemplateId,
			standard, themeId);
	}

	public java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, Boolean>> init(
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _layoutTemplateLocalService.init(servletContext, xmls,
			pluginPackage);
	}

	public java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, Boolean>> init(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _layoutTemplateLocalService.init(servletContextName,
			servletContext, xmls, pluginPackage);
	}

	public void readLayoutTemplate(java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.util.Set<com.liferay.portal.kernel.util.ObjectValuePair<String, Boolean>> layoutTemplateIds,
		com.liferay.portal.kernel.xml.Element el, boolean standard,
		java.lang.String themeId,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_layoutTemplateLocalService.readLayoutTemplate(servletContextName,
			servletContext, layoutTemplateIds, el, standard, themeId,
			pluginPackage);
	}

	public void uninstallLayoutTemplate(java.lang.String layoutTemplateId,
		boolean standard) {
		_layoutTemplateLocalService.uninstallLayoutTemplate(layoutTemplateId,
			standard);
	}

	public void uninstallLayoutTemplates(java.lang.String themeId) {
		_layoutTemplateLocalService.uninstallLayoutTemplates(themeId);
	}

	public LayoutTemplateLocalService getWrappedLayoutTemplateLocalService() {
		return _layoutTemplateLocalService;
	}

	private LayoutTemplateLocalService _layoutTemplateLocalService;
}