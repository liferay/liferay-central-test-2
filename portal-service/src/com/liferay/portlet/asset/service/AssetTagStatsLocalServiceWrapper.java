/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
		throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.addAssetTagStats(assetTagStats);
	}

	public com.liferay.portlet.asset.model.AssetTagStats createAssetTagStats(
		long tagStatsId) {
		return _assetTagStatsLocalService.createAssetTagStats(tagStatsId);
	}

	public void deleteAssetTagStats(long tagStatsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagStatsLocalService.deleteAssetTagStats(tagStatsId);
	}

	public void deleteAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException {
		_assetTagStatsLocalService.deleteAssetTagStats(assetTagStats);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.asset.model.AssetTagStats getAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.getAssetTagStats(tagStatsId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getAssetTagStatses(
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.getAssetTagStatses(start, end);
	}

	public int getAssetTagStatsesCount()
		throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.getAssetTagStatsesCount();
	}

	public com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.updateAssetTagStats(assetTagStats);
	}

	public com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge) throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.updateAssetTagStats(assetTagStats,
			merge);
	}

	public com.liferay.portlet.asset.model.AssetTagStats addTagStats(
		long tagId, long classNameId) throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.addTagStats(tagId, classNameId);
	}

	public void deleteTagStatsByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException {
		_assetTagStatsLocalService.deleteTagStatsByClassNameId(classNameId);
	}

	public void deleteTagStatsByTagId(long tagId)
		throws com.liferay.portal.SystemException {
		_assetTagStatsLocalService.deleteTagStatsByTagId(tagId);
	}

	public com.liferay.portlet.asset.model.AssetTagStats getTagStats(
		long tagId, long classNameId) throws com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.getTagStats(tagId, classNameId);
	}

	public com.liferay.portlet.asset.model.AssetTagStats updateTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagStatsLocalService.updateTagStats(tagId, classNameId);
	}

	public AssetTagStatsLocalService getWrappedAssetTagStatsLocalService() {
		return _assetTagStatsLocalService;
	}

	private AssetTagStatsLocalService _assetTagStatsLocalService;
}