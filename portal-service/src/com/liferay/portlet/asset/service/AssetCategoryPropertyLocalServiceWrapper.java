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
 * <a href="AssetCategoryPropertyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetCategoryPropertyLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryPropertyLocalService
 * @generated
 */
public class AssetCategoryPropertyLocalServiceWrapper
	implements AssetCategoryPropertyLocalService {
	public AssetCategoryPropertyLocalServiceWrapper(
		AssetCategoryPropertyLocalService assetCategoryPropertyLocalService) {
		_assetCategoryPropertyLocalService = assetCategoryPropertyLocalService;
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty addAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.addAssetCategoryProperty(assetCategoryProperty);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty createAssetCategoryProperty(
		long categoryPropertyId) {
		return _assetCategoryPropertyLocalService.createAssetCategoryProperty(categoryPropertyId);
	}

	public void deleteAssetCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryPropertyLocalService.deleteAssetCategoryProperty(categoryPropertyId);
	}

	public void deleteAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryPropertyLocalService.deleteAssetCategoryProperty(assetCategoryProperty);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty getAssetCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.getAssetCategoryProperty(categoryPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getAssetCategoryProperties(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.getAssetCategoryProperties(start,
			end);
	}

	public int getAssetCategoryPropertiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.getAssetCategoryPropertiesCount();
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.updateAssetCategoryProperty(assetCategoryProperty);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.updateAssetCategoryProperty(assetCategoryProperty,
			merge);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty addCategoryProperty(
		long userId, long categoryId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.addCategoryProperty(userId,
			categoryId, key, value);
	}

	public void deleteCategoryProperties(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryPropertyLocalService.deleteCategoryProperties(entryId);
	}

	public void deleteCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty categoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryPropertyLocalService.deleteCategoryProperty(categoryProperty);
	}

	public void deleteCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryPropertyLocalService.deleteCategoryProperty(categoryPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryProperties();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryProperties(entryId);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryProperty(categoryPropertyId);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryProperty(categoryId,
			key);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryPropertyValues(groupId,
			key);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long categoryPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryPropertyLocalService.updateCategoryProperty(categoryPropertyId,
			key, value);
	}

	public AssetCategoryPropertyLocalService getWrappedAssetCategoryPropertyLocalService() {
		return _assetCategoryPropertyLocalService;
	}

	private AssetCategoryPropertyLocalService _assetCategoryPropertyLocalService;
}