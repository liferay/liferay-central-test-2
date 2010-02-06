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

import com.liferay.portlet.asset.model.AssetTagProperty;

import java.util.List;

/**
 * <a href="AssetTagPropertyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPropertyPersistence
 * @see       AssetTagPropertyPersistenceImpl
 * @generated
 */
public class AssetTagPropertyUtil {
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
	public static AssetTagProperty remove(AssetTagProperty assetTagProperty)
		throws SystemException {
		return getPersistence().remove(assetTagProperty);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AssetTagProperty update(AssetTagProperty assetTagProperty,
		boolean merge) throws SystemException {
		return getPersistence().update(assetTagProperty, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty) {
		getPersistence().cacheResult(assetTagProperty);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> assetTagProperties) {
		getPersistence().cacheResult(assetTagProperties);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty create(
		long tagPropertyId) {
		return getPersistence().create(tagPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty remove(
		long tagPropertyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().remove(tagPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty updateImpl(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(assetTagProperty, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByPrimaryKey(
		long tagPropertyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByPrimaryKey(tagPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByPrimaryKey(
		long tagPropertyId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(tagPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByCompanyId_PrevAndNext(
		long tagPropertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(tagPropertyId, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId) throws com.liferay.portal.SystemException {
		return getPersistence().findByTagId(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTagId(tagId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTagId(tagId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByTagId_First(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByTagId_First(tagId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByTagId_Last(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByTagId_Last(tagId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByTagId_PrevAndNext(
		long tagPropertyId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByTagId_PrevAndNext(tagPropertyId, tagId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_K(companyId, key);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_K(companyId, key, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_K(companyId, key, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByC_K_First(companyId, key, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByC_K_Last(companyId, key, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByC_K_PrevAndNext(
		long tagPropertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByC_K_PrevAndNext(tagPropertyId, companyId, key, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByT_K(tagId, key);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByT_K(tagId, key);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByT_K(
		long tagId, java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByT_K(tagId, key, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByTagId(long tagId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByTagId(tagId);
	}

	public static void removeByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_K(companyId, key);
	}

	public static void removeByT_K(long tagId, java.lang.String key)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		getPersistence().removeByT_K(tagId, key);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByTagId(long tagId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByTagId(tagId);
	}

	public static int countByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_K(companyId, key);
	}

	public static int countByT_K(long tagId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_K(tagId, key);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AssetTagPropertyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetTagPropertyPersistence)PortalBeanLocatorUtil.locate(AssetTagPropertyPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AssetTagPropertyPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetTagPropertyPersistence _persistence;
}