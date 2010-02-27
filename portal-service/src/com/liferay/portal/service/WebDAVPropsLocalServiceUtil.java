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
 * <a href="WebDAVPropsLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WebDAVPropsLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebDAVPropsLocalService
 * @generated
 */
public class WebDAVPropsLocalServiceUtil {
	public static com.liferay.portal.model.WebDAVProps addWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addWebDAVProps(webDAVProps);
	}

	public static com.liferay.portal.model.WebDAVProps createWebDAVProps(
		long webDavPropsId) {
		return getService().createWebDAVProps(webDavPropsId);
	}

	public static void deleteWebDAVProps(long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWebDAVProps(webDavPropsId);
	}

	public static void deleteWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWebDAVProps(webDAVProps);
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

	public static com.liferay.portal.model.WebDAVProps getWebDAVProps(
		long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWebDAVProps(webDavPropsId);
	}

	public static java.util.List<com.liferay.portal.model.WebDAVProps> getWebDAVPropses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWebDAVPropses(start, end);
	}

	public static int getWebDAVPropsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWebDAVPropsesCount();
	}

	public static com.liferay.portal.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWebDAVProps(webDAVProps);
	}

	public static com.liferay.portal.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWebDAVProps(webDAVProps, merge);
	}

	public static void deleteWebDAVProps(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWebDAVProps(className, classPK);
	}

	public static com.liferay.portal.model.WebDAVProps getWebDAVProps(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWebDAVProps(companyId, className, classPK);
	}

	public static void storeWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDavProps)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().storeWebDAVProps(webDavProps);
	}

	public static WebDAVPropsLocalService getService() {
		if (_service == null) {
			_service = (WebDAVPropsLocalService)PortalBeanLocatorUtil.locate(WebDAVPropsLocalService.class.getName());
		}

		return _service;
	}

	public void setService(WebDAVPropsLocalService service) {
		_service = service;
	}

	private static WebDAVPropsLocalService _service;
}