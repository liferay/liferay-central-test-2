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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AssetCategoryPropertyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AssetCategoryPropertyLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryPropertyLocalService
 * @generated
 */
public class AssetCategoryPropertyLocalServiceUtil {
	public static com.liferay.portlet.asset.model.AssetCategoryProperty addAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.SystemException {
		return getService().addAssetCategoryProperty(assetCategoryProperty);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty createAssetCategoryProperty(
		long categoryPropertyId) {
		return getService().createAssetCategoryProperty(categoryPropertyId);
	}

	public static void deleteAssetCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAssetCategoryProperty(categoryPropertyId);
	}

	public static void deleteAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.SystemException {
		getService().deleteAssetCategoryProperty(assetCategoryProperty);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty getAssetCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAssetCategoryProperty(categoryPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getAssetCategoryProperties(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAssetCategoryProperties(start, end);
	}

	public static int getAssetCategoryPropertiesCount()
		throws com.liferay.portal.SystemException {
		return getService().getAssetCategoryPropertiesCount();
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.SystemException {
		return getService().updateAssetCategoryProperty(assetCategoryProperty);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService()
				   .updateAssetCategoryProperty(assetCategoryProperty, merge);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty addCategoryProperty(
		long userId, long categoryId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addCategoryProperty(userId, categoryId, key, value);
	}

	public static void deleteCategoryProperties(long entryId)
		throws com.liferay.portal.SystemException {
		getService().deleteCategoryProperties(entryId);
	}

	public static void deleteCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty categoryProperty)
		throws com.liferay.portal.SystemException {
		getService().deleteCategoryProperty(categoryProperty);
	}

	public static void deleteCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategoryProperty(categoryPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties()
		throws com.liferay.portal.SystemException {
		return getService().getCategoryProperties();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties(
		long entryId) throws com.liferay.portal.SystemException {
		return getService().getCategoryProperties(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCategoryProperty(categoryPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCategoryProperty(categoryId, key);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getService().getCategoryPropertyValues(groupId, key);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long categoryPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateCategoryProperty(categoryPropertyId, key, value);
	}

	public static AssetCategoryPropertyLocalService getService() {
		if (_service == null) {
			_service = (AssetCategoryPropertyLocalService)PortalBeanLocatorUtil.locate(AssetCategoryPropertyLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AssetCategoryPropertyLocalService service) {
		_service = service;
	}

	private static AssetCategoryPropertyLocalService _service;
}