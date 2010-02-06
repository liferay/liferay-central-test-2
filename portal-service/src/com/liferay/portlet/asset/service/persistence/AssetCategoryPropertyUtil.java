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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.asset.model.AssetCategoryProperty;

import java.util.List;

/**
 * <a href="AssetCategoryPropertyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryPropertyPersistence
 * @see       AssetCategoryPropertyPersistenceImpl
 * @generated
 */
public class AssetCategoryPropertyUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static AssetCategoryProperty remove(
		AssetCategoryProperty assetCategoryProperty) throws SystemException {
		return getPersistence().remove(assetCategoryProperty);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AssetCategoryProperty update(
		AssetCategoryProperty assetCategoryProperty, boolean merge)
		throws SystemException {
		return getPersistence().update(assetCategoryProperty, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty) {
		getPersistence().cacheResult(assetCategoryProperty);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> assetCategoryProperties) {
		getPersistence().cacheResult(assetCategoryProperties);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty create(
		long categoryPropertyId) {
		return getPersistence().create(categoryPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty remove(
		long categoryPropertyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().remove(categoryPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateImpl(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(assetCategoryProperty, merge);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByPrimaryKey(
		long categoryPropertyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByPrimaryKey(categoryPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByPrimaryKey(
		long categoryPropertyId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty[] findByCompanyId_PrevAndNext(
		long categoryPropertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(categoryPropertyId, companyId,
			obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCategoryId_First(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByCategoryId_First(categoryId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCategoryId_Last(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByCategoryId_Last(categoryId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty[] findByCategoryId_PrevAndNext(
		long categoryPropertyId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCategoryId_PrevAndNext(categoryPropertyId,
			categoryId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_K(companyId, key);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_K(companyId, key, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_K(companyId, key, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByC_K_First(companyId, key, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByC_K_Last(companyId, key, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty[] findByC_K_PrevAndNext(
		long categoryPropertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByC_K_PrevAndNext(categoryPropertyId, companyId, key,
			obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCA_K(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByCA_K(categoryId, key);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCA_K(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByCA_K(categoryId, key);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCA_K(
		long categoryId, java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByCA_K(categoryId, key, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCategoryId(categoryId);
	}

	public static void removeByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_K(companyId, key);
	}

	public static void removeByCA_K(long categoryId, java.lang.String key)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		getPersistence().removeByCA_K(categoryId, key);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	public static int countByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_K(companyId, key);
	}

	public static int countByCA_K(long categoryId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCA_K(categoryId, key);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AssetCategoryPropertyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetCategoryPropertyPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPropertyPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AssetCategoryPropertyPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetCategoryPropertyPersistence _persistence;
}