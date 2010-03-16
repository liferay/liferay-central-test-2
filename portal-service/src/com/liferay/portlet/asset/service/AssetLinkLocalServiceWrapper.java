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


/**
 * <a href="AssetLinkLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetLinkLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLinkLocalService
 * @generated
 */
public class AssetLinkLocalServiceWrapper implements AssetLinkLocalService {
	public AssetLinkLocalServiceWrapper(
		AssetLinkLocalService assetLinkLocalService) {
		_assetLinkLocalService = assetLinkLocalService;
	}

	public com.liferay.portlet.asset.model.AssetLink addAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.addAssetLink(assetLink);
	}

	public com.liferay.portlet.asset.model.AssetLink createAssetLink(
		long linkId) {
		return _assetLinkLocalService.createAssetLink(linkId);
	}

	public void deleteAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteAssetLink(linkId);
	}

	public void deleteAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteAssetLink(assetLink);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.asset.model.AssetLink getAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLink(linkId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getAssetLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLinks(start, end);
	}

	public int getAssetLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLinksCount();
	}

	public com.liferay.portlet.asset.model.AssetLink updateAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.updateAssetLink(assetLink);
	}

	public com.liferay.portlet.asset.model.AssetLink updateAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.updateAssetLink(assetLink, merge);
	}

	public com.liferay.portlet.asset.model.AssetLink addLink(long userId,
		long entryId1, long entryId2, int typeId, int weight)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.addLink(userId, entryId1, entryId2,
			typeId, weight);
	}

	public void deleteLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLink(linkId);
	}

	public void deleteLinks(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLinks(entryId);
	}

	public void deleteLinks(long linkId1, long linkId2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLinks(linkId1, linkId2);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getLinks(entryId, typeId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getReverseLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getReverseLinks(entryId, typeId);
	}

	public AssetLinkLocalService getWrappedAssetLinkLocalService() {
		return _assetLinkLocalService;
	}

	private AssetLinkLocalService _assetLinkLocalService;
}