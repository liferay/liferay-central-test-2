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
 * <a href="AssetTagStatsLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AssetTagStatsLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStatsLocalService
 * @generated
 */
public class AssetTagStatsLocalServiceUtil {
	public static com.liferay.portlet.asset.model.AssetTagStats addAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetTagStats(assetTagStats);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats createAssetTagStats(
		long tagStatsId) {
		return getService().createAssetTagStats(tagStatsId);
	}

	public static void deleteAssetTagStats(long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetTagStats(tagStatsId);
	}

	public static void deleteAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetTagStats(assetTagStats);
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

	public static com.liferay.portlet.asset.model.AssetTagStats getAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetTagStats(tagStatsId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getAssetTagStatses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetTagStatses(start, end);
	}

	public static int getAssetTagStatsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetTagStatsesCount();
	}

	public static com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetTagStats(assetTagStats);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetTagStats(assetTagStats, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats addTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTagStats(tagId, classNameId);
	}

	public static void deleteTagStatsByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTagStatsByClassNameId(classNameId);
	}

	public static void deleteTagStatsByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTagStatsByTagId(tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats getTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTagStats(tagId, classNameId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats updateTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTagStats(tagId, classNameId);
	}

	public static AssetTagStatsLocalService getService() {
		if (_service == null) {
			_service = (AssetTagStatsLocalService)PortalBeanLocatorUtil.locate(AssetTagStatsLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AssetTagStatsLocalService service) {
		_service = service;
	}

	private static AssetTagStatsLocalService _service;
}