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
 * <a href="AssetTagStatsLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetTagStatsLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStatsLocalService
 * @generated
 */
public class AssetTagStatsLocalServiceWrapper
	implements AssetTagStatsLocalService {
	public AssetTagStatsLocalServiceWrapper(
		AssetTagStatsLocalService assetTagStatsLocalService) {
		_assetTagStatsLocalService = assetTagStatsLocalService;
	}

	public com.liferay.portlet.asset.model.AssetTagStats addAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.addAssetTagStats(assetTagStats);
	}

	public com.liferay.portlet.asset.model.AssetTagStats createAssetTagStats(
		long tagStatsId) {
		return _assetTagStatsLocalService.createAssetTagStats(tagStatsId);
	}

	public void deleteAssetTagStats(long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagStatsLocalService.deleteAssetTagStats(tagStatsId);
	}

	public void deleteAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagStatsLocalService.deleteAssetTagStats(assetTagStats);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.asset.model.AssetTagStats getAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getAssetTagStats(tagStatsId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getAssetTagStatses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getAssetTagStatses(start, end);
	}

	public int getAssetTagStatsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getAssetTagStatsesCount();
	}

	public com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.updateAssetTagStats(assetTagStats);
	}

	public com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.updateAssetTagStats(assetTagStats,
			merge);
	}

	public com.liferay.portlet.asset.model.AssetTagStats addTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.addTagStats(tagId, classNameId);
	}

	public void deleteTagStatsByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagStatsLocalService.deleteTagStatsByClassNameId(classNameId);
	}

	public void deleteTagStatsByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagStatsLocalService.deleteTagStatsByTagId(tagId);
	}

	public com.liferay.portlet.asset.model.AssetTagStats getTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getTagStats(tagId, classNameId);
	}

	public com.liferay.portlet.asset.model.AssetTagStats updateTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.updateTagStats(tagId, classNameId);
	}

	public AssetTagStatsLocalService getWrappedAssetTagStatsLocalService() {
		return _assetTagStatsLocalService;
	}

	private AssetTagStatsLocalService _assetTagStatsLocalService;
}