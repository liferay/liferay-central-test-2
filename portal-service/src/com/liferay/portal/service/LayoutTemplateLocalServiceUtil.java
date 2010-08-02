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
 * The utility for the layout template local service. This utility wraps {@link com.liferay.portal.service.impl.LayoutTemplateLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutTemplateLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutTemplateLocalService
 * @see com.liferay.portal.service.base.LayoutTemplateLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutTemplateLocalServiceImpl
 * @generated
 */
public class LayoutTemplateLocalServiceUtil {
	public static java.lang.String getContent(
		java.lang.String layoutTemplateId, boolean standard,
		java.lang.String themeId)
		throws com.liferay.portal.kernel.exception.SystemException {
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
		java.lang.String themeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWapContent(layoutTemplateId, standard, themeId);
	}

	public static java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.lang.Boolean>> init(
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService().init(servletContext, xmls, pluginPackage);
	}

	public static java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.lang.Boolean>> init(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService()
				   .init(servletContextName, servletContext, xmls, pluginPackage);
	}

	public static void readLayoutTemplate(java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.util.Set<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.lang.Boolean>> layoutTemplateIds,
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