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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

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
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetTagProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetTagProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
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
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().remove(tagPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty updateImpl(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetTagProperty, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByPrimaryKey(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByPrimaryKey(tagPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByPrimaryKey(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(tagPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByCompanyId_PrevAndNext(
		long tagPropertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(tagPropertyId, companyId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByTagId_First(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByTagId_First(tagId, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByTagId_Last(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByTagId_Last(tagId, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByTagId_PrevAndNext(
		long tagPropertyId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByTagId_PrevAndNext(tagPropertyId, tagId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_K(companyId, key);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_K(companyId, key, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_K(companyId, key, start, end, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByC_K_First(companyId, key, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByC_K_Last(companyId, key, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByC_K_PrevAndNext(
		long tagPropertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByC_K_PrevAndNext(tagPropertyId, companyId, key,
			orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty findByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByT_K(tagId, key);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByT_K(tagId, key);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByT_K(
		long tagId, java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByT_K(tagId, key, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTagId(tagId);
	}

	public static void removeByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_K(companyId, key);
	}

	public static void removeByT_K(long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		getPersistence().removeByT_K(tagId, key);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTagId(tagId);
	}

	public static int countByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_K(companyId, key);
	}

	public static int countByT_K(long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_K(tagId, key);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
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