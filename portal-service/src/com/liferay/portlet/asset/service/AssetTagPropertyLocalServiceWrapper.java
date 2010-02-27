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
 * <a href="AssetTagPropertyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetTagPropertyLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPropertyLocalService
 * @generated
 */
public class AssetTagPropertyLocalServiceWrapper
	implements AssetTagPropertyLocalService {
	public AssetTagPropertyLocalServiceWrapper(
		AssetTagPropertyLocalService assetTagPropertyLocalService) {
		_assetTagPropertyLocalService = assetTagPropertyLocalService;
	}

	public com.liferay.portlet.asset.model.AssetTagProperty addAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.addAssetTagProperty(assetTagProperty);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty createAssetTagProperty(
		long tagPropertyId) {
		return _assetTagPropertyLocalService.createAssetTagProperty(tagPropertyId);
	}

	public void deleteAssetTagProperty(long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyLocalService.deleteAssetTagProperty(tagPropertyId);
	}

	public void deleteAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyLocalService.deleteAssetTagProperty(assetTagProperty);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty getAssetTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getAssetTagProperty(tagPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getAssetTagProperties(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getAssetTagProperties(start, end);
	}

	public int getAssetTagPropertiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getAssetTagPropertiesCount();
	}

	public com.liferay.portlet.asset.model.AssetTagProperty updateAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.updateAssetTagProperty(assetTagProperty);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty updateAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.updateAssetTagProperty(assetTagProperty,
			merge);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty addTagProperty(
		long userId, long tagId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.addTagProperty(userId, tagId, key,
			value);
	}

	public void deleteTagProperties(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyLocalService.deleteTagProperties(tagId);
	}

	public void deleteTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty tagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyLocalService.deleteTagProperty(tagProperty);
	}

	public void deleteTagProperty(long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyLocalService.deleteTagProperty(tagPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagProperties();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagProperties(tagId);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty getTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagProperty(tagPropertyId);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty getTagProperty(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagProperty(tagId, key);
	}

	public java.lang.String[] getTagPropertyKeys(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagPropertyKeys(groupId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagPropertyValues(groupId, key);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.updateTagProperty(tagPropertyId,
			key, value);
	}

	public AssetTagPropertyLocalService getWrappedAssetTagPropertyLocalService() {
		return _assetTagPropertyLocalService;
	}

	private AssetTagPropertyLocalService _assetTagPropertyLocalService;
}