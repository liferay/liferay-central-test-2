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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AssetLinkLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AssetLinkLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLinkLocalService
 * @generated
 */
public class AssetLinkLocalServiceUtil {
	public static com.liferay.portlet.asset.model.AssetLink addAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetLink(assetLink);
	}

	public static com.liferay.portlet.asset.model.AssetLink createAssetLink(
		long linkId) {
		return getService().createAssetLink(linkId);
	}

	public static void deleteAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetLink(linkId);
	}

	public static void deleteAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetLink(assetLink);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
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

	public static com.liferay.portlet.asset.model.AssetLink getAssetLink(
		long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetLink(linkId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> getAssetLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetLinks(start, end);
	}

	public static int getAssetLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetLinksCount();
	}

	public static com.liferay.portlet.asset.model.AssetLink updateAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetLink(assetLink);
	}

	public static com.liferay.portlet.asset.model.AssetLink updateAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetLink(assetLink, merge);
	}

	public static com.liferay.portlet.asset.model.AssetLink addLink(
		long userId, long entryId1, long entryId2, int type, int weight)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addLink(userId, entryId1, entryId2, type, weight);
	}

	public static void deleteLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLink(linkId);
	}

	public static void deleteLinks(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLinks(entryId);
	}

	public static void deleteLinks(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLinks(entryId1, entryId2);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> getLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLinks(entryId, typeId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> getReverseLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getReverseLinks(entryId, typeId);
	}

	public static AssetLinkLocalService getService() {
		if (_service == null) {
			_service = (AssetLinkLocalService)PortalBeanLocatorUtil.locate(AssetLinkLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AssetLinkLocalService service) {
		_service = service;
	}

	private static AssetLinkLocalService _service;
}