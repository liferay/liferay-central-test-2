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


/**
 * <a href="WikiPageResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WikiPageResourceLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageResourceLocalService
 * @generated
 */
public class WikiPageResourceLocalServiceWrapper
	implements WikiPageResourceLocalService {
	public WikiPageResourceLocalServiceWrapper(
		WikiPageResourceLocalService wikiPageResourceLocalService) {
		_wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	public com.liferay.portlet.wiki.model.WikiPageResource addWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.addWikiPageResource(wikiPageResource);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource createWikiPageResource(
		long resourcePrimKey) {
		return _wikiPageResourceLocalService.createWikiPageResource(resourcePrimKey);
	}

	public void deleteWikiPageResource(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageResourceLocalService.deleteWikiPageResource(resourcePrimKey);
	}

	public void deleteWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiPageResourceLocalService.deleteWikiPageResource(wikiPageResource);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource getWikiPageResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getWikiPageResource(resourcePrimKey);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> getWikiPageResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getWikiPageResources(start, end);
	}

	public int getWikiPageResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getWikiPageResourcesCount();
	}

	public com.liferay.portlet.wiki.model.WikiPageResource updateWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.updateWikiPageResource(wikiPageResource);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource updateWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.updateWikiPageResource(wikiPageResource,
			merge);
	}

	public void deletePageResource(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageResourceLocalService.deletePageResource(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource getPageResource(
		long pageResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getPageResource(pageResourcePrimKey);
	}

	public long getPageResourcePrimKey(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageResourceLocalService.getPageResourcePrimKey(nodeId,
			title);
	}

	public WikiPageResourceLocalService getWrappedWikiPageResourceLocalService() {
		return _wikiPageResourceLocalService;
	}

	private WikiPageResourceLocalService _wikiPageResourceLocalService;
}