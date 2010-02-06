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
		throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.addAssetCategoryProperty(assetCategoryProperty);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty createAssetCategoryProperty(
		long categoryPropertyId) {
		return _assetCategoryPropertyLocalService.createAssetCategoryProperty(categoryPropertyId);
	}

	public void deleteAssetCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryPropertyLocalService.deleteAssetCategoryProperty(categoryPropertyId);
	}

	public void deleteAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.SystemException {
		_assetCategoryPropertyLocalService.deleteAssetCategoryProperty(assetCategoryProperty);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty getAssetCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.getAssetCategoryProperty(categoryPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getAssetCategoryProperties(
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.getAssetCategoryProperties(start,
			end);
	}

	public int getAssetCategoryPropertiesCount()
		throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.getAssetCategoryPropertiesCount();
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.updateAssetCategoryProperty(assetCategoryProperty);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty,
		boolean merge) throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.updateAssetCategoryProperty(assetCategoryProperty,
			merge);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty addCategoryProperty(
		long userId, long categoryId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.addCategoryProperty(userId,
			categoryId, key, value);
	}

	public void deleteCategoryProperties(long entryId)
		throws com.liferay.portal.SystemException {
		_assetCategoryPropertyLocalService.deleteCategoryProperties(entryId);
	}

	public void deleteCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty categoryProperty)
		throws com.liferay.portal.SystemException {
		_assetCategoryPropertyLocalService.deleteCategoryProperty(categoryProperty);
	}

	public void deleteCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryPropertyLocalService.deleteCategoryProperty(categoryPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties()
		throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryProperties();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties(
		long entryId) throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryProperties(entryId);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryProperty(categoryPropertyId);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryProperty(categoryId,
			key);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.getCategoryPropertyValues(groupId,
			key);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long categoryPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryPropertyLocalService.updateCategoryProperty(categoryPropertyId,
			key, value);
	}

	public AssetCategoryPropertyLocalService getWrappedAssetCategoryPropertyLocalService() {
		return _assetCategoryPropertyLocalService;
	}

	private AssetCategoryPropertyLocalService _assetCategoryPropertyLocalService;
}