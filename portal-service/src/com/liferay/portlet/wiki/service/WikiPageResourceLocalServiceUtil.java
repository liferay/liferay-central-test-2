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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="WikiPageResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WikiPageResourceLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageResourceLocalService
 * @generated
 */
public class WikiPageResourceLocalServiceUtil {
	public static com.liferay.portlet.wiki.model.WikiPageResource addWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addWikiPageResource(wikiPageResource);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource createWikiPageResource(
		long resourcePrimKey) {
		return getService().createWikiPageResource(resourcePrimKey);
	}

	public static void deleteWikiPageResource(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWikiPageResource(resourcePrimKey);
	}

	public static void deleteWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWikiPageResource(wikiPageResource);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource getWikiPageResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPageResource(resourcePrimKey);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> getWikiPageResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPageResources(start, end);
	}

	public static int getWikiPageResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPageResourcesCount();
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource updateWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWikiPageResource(wikiPageResource);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource updateWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWikiPageResource(wikiPageResource, merge);
	}

	public static void deletePageResource(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePageResource(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource getPageResource(
		long pageResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPageResource(pageResourcePrimKey);
	}

	public static long getPageResourcePrimKey(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPageResourcePrimKey(nodeId, title);
	}

	public static WikiPageResourceLocalService getService() {
		if (_service == null) {
			_service = (WikiPageResourceLocalService)PortalBeanLocatorUtil.locate(WikiPageResourceLocalService.class.getName());
		}

		return _service;
	}

	public void setService(WikiPageResourceLocalService service) {
		_service = service;
	}

	private static WikiPageResourceLocalService _service;
}