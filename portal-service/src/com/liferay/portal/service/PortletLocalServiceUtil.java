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
 * <a href="PortletLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PortletLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletLocalService
 * @generated
 */
public class PortletLocalServiceUtil {
	public static com.liferay.portal.model.Portlet addPortlet(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortlet(portlet);
	}

	public static com.liferay.portal.model.Portlet createPortlet(long id) {
		return getService().createPortlet(id);
	}

	public static void deletePortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortlet(id);
	}

	public static void deletePortlet(com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortlet(portlet);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Portlet getPortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortlet(id);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortlets(start, end);
	}

	public static int getPortletsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletsCount();
	}

	public static com.liferay.portal.model.Portlet updatePortlet(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortlet(portlet);
	}

	public static com.liferay.portal.model.Portlet updatePortlet(
		com.liferay.portal.model.Portlet portlet, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortlet(portlet, merge);
	}

	public static void checkPortlet(com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkPortlet(portlet);
	}

	public static void checkPortlets(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkPortlets(companyId);
	}

	public static void clearCache() {
		getService().clearCache();
	}

	public static com.liferay.portal.model.Portlet clonePortlet(
		long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().clonePortlet(companyId, portletId);
	}

	public static com.liferay.portal.model.Portlet deployRemotePortlet(
		com.liferay.portal.model.Portlet portlet, java.lang.String categoryName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deployRemotePortlet(portlet, categoryName);
	}

	public static void destroyPortlet(com.liferay.portal.model.Portlet portlet) {
		getService().destroyPortlet(portlet);
	}

	public static void destroyRemotePortlet(
		com.liferay.portal.model.Portlet portlet) {
		getService().destroyRemotePortlet(portlet);
	}

	public static java.util.List<com.liferay.portlet.expando.model.CustomAttributesDisplay> getCustomAttributesDisplays() {
		return getService().getCustomAttributesDisplays();
	}

	public static com.liferay.portal.model.PortletCategory getEARDisplay(
		java.lang.String xml)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEARDisplay(xml);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> getFriendlyURLMapperPortlets() {
		return getService().getFriendlyURLMapperPortlets();
	}

	public static java.util.List<com.liferay.portal.kernel.portlet.FriendlyURLMapper> getFriendlyURLMappers() {
		return getService().getFriendlyURLMappers();
	}

	public static com.liferay.portal.model.PortletApp getPortletApp(
		java.lang.String servletContextName) {
		return getService().getPortletApp(servletContextName);
	}

	public static com.liferay.portal.model.Portlet getPortletById(
		long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletById(companyId, portletId);
	}

	public static com.liferay.portal.model.Portlet getPortletById(
		java.lang.String portletId) {
		return getService().getPortletById(portletId);
	}

	public static com.liferay.portal.model.Portlet getPortletByStrutsPath(
		long companyId, java.lang.String strutsPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortletByStrutsPath(companyId, strutsPath);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> getPortlets() {
		return getService().getPortlets();
	}

	public static java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortlets(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		long companyId, boolean showSystem, boolean showPortal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortlets(companyId, showSystem, showPortal);
	}

	public static com.liferay.portal.model.PortletCategory getWARDisplay(
		java.lang.String servletContextName, java.lang.String xml)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWARDisplay(servletContextName, xml);
	}

	public static boolean hasPortlet(long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasPortlet(companyId, portletId);
	}

	public static void initEAR(javax.servlet.ServletContext servletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		getService().initEAR(servletContext, xmls, pluginPackage);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> initWAR(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService()
				   .initWAR(servletContextName, servletContext, xmls,
			pluginPackage);
	}

	public static com.liferay.portal.model.Portlet updatePortlet(
		long companyId, java.lang.String portletId, java.lang.String roles,
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortlet(companyId, portletId, roles, active);
	}

	public static PortletLocalService getService() {
		if (_service == null) {
			_service = (PortletLocalService)PortalBeanLocatorUtil.locate(PortletLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PortletLocalService service) {
		_service = service;
	}

	private static PortletLocalService _service;
}