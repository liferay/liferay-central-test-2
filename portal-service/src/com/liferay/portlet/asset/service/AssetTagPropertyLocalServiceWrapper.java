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
		throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.addAssetTagProperty(assetTagProperty);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty createAssetTagProperty(
		long tagPropertyId) {
		return _assetTagPropertyLocalService.createAssetTagProperty(tagPropertyId);
	}

	public void deleteAssetTagProperty(long tagPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagPropertyLocalService.deleteAssetTagProperty(tagPropertyId);
	}

	public void deleteAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.SystemException {
		_assetTagPropertyLocalService.deleteAssetTagProperty(assetTagProperty);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty getAssetTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getAssetTagProperty(tagPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getAssetTagProperties(
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getAssetTagProperties(start, end);
	}

	public int getAssetTagPropertiesCount()
		throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getAssetTagPropertiesCount();
	}

	public com.liferay.portlet.asset.model.AssetTagProperty updateAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.updateAssetTagProperty(assetTagProperty);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty updateAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty,
		boolean merge) throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.updateAssetTagProperty(assetTagProperty,
			merge);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty addTagProperty(
		long userId, long tagId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.addTagProperty(userId, tagId, key,
			value);
	}

	public void deleteTagProperties(long tagId)
		throws com.liferay.portal.SystemException {
		_assetTagPropertyLocalService.deleteTagProperties(tagId);
	}

	public void deleteTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty tagProperty)
		throws com.liferay.portal.SystemException {
		_assetTagPropertyLocalService.deleteTagProperty(tagProperty);
	}

	public void deleteTagProperty(long tagPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagPropertyLocalService.deleteTagProperty(tagPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties()
		throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getTagProperties();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties(
		long tagId) throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getTagProperties(tagId);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty getTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getTagProperty(tagPropertyId);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty getTagProperty(
		long tagId, java.lang.String key)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getTagProperty(tagId, key);
	}

	public java.lang.String[] getTagPropertyKeys(long groupId)
		throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getTagPropertyKeys(groupId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.getTagPropertyValues(groupId, key);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagPropertyLocalService.updateTagProperty(tagPropertyId,
			key, value);
	}

	public AssetTagPropertyLocalService getWrappedAssetTagPropertyLocalService() {
		return _assetTagPropertyLocalService;
	}

	private AssetTagPropertyLocalService _assetTagPropertyLocalService;
}