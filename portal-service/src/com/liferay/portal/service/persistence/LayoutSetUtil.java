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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutSet;

import java.util.List;

/**
 * <a href="LayoutSetUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPersistence
 * @see       LayoutSetPersistenceImpl
 * @generated
 */
public class LayoutSetUtil {
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
	public static LayoutSet remove(LayoutSet layoutSet)
		throws SystemException {
		return getPersistence().remove(layoutSet);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LayoutSet update(LayoutSet layoutSet, boolean merge)
		throws SystemException {
		return getPersistence().update(layoutSet, merge);
	}

	public static void cacheResult(com.liferay.portal.model.LayoutSet layoutSet) {
		getPersistence().cacheResult(layoutSet);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutSet> layoutSets) {
		getPersistence().cacheResult(layoutSets);
	}

	public static com.liferay.portal.model.LayoutSet create(long layoutSetId) {
		return getPersistence().create(layoutSetId);
	}

	public static com.liferay.portal.model.LayoutSet remove(long layoutSetId)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(layoutSetId);
	}

	public static com.liferay.portal.model.LayoutSet updateImpl(
		com.liferay.portal.model.LayoutSet layoutSet, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(layoutSet, merge);
	}

	public static com.liferay.portal.model.LayoutSet findByPrimaryKey(
		long layoutSetId)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(layoutSetId);
	}

	public static com.liferay.portal.model.LayoutSet fetchByPrimaryKey(
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(layoutSetId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portal.model.LayoutSet findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.LayoutSet findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.LayoutSet[] findByGroupId_PrevAndNext(
		long layoutSetId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(layoutSetId, groupId, obc);
	}

	public static com.liferay.portal.model.LayoutSet findByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.LayoutSet fetchByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.LayoutSet fetchByVirtualHost(
		java.lang.String virtualHost, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByVirtualHost(virtualHost, retrieveFromCache);
	}

	public static com.liferay.portal.model.LayoutSet findByG_P(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	public static com.liferay.portal.model.LayoutSet fetchByG_P(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_P(groupId, privateLayout);
	}

	public static com.liferay.portal.model.LayoutSet fetchByG_P(long groupId,
		boolean privateLayout, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P(groupId, privateLayout, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByVirtualHost(virtualHost);
	}

	public static void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByVirtualHost(virtualHost);
	}

	public static int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutSetPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutSetPersistence)PortalBeanLocatorUtil.locate(LayoutSetPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(LayoutSetPersistence persistence) {
		_persistence = persistence;
	}

	private static LayoutSetPersistence _persistence;
}