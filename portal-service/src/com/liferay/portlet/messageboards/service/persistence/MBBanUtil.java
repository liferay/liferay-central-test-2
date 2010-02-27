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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.messageboards.model.MBBan;

import java.util.List;

/**
 * <a href="MBBanUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBanPersistence
 * @see       MBBanPersistenceImpl
 * @generated
 */
public class MBBanUtil {
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
	public static MBBan remove(MBBan mbBan) throws SystemException {
		return getPersistence().remove(mbBan);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBBan update(MBBan mbBan, boolean merge)
		throws SystemException {
		return getPersistence().update(mbBan, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBBan mbBan) {
		getPersistence().cacheResult(mbBan);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBBan> mbBans) {
		getPersistence().cacheResult(mbBans);
	}

	public static com.liferay.portlet.messageboards.model.MBBan create(
		long banId) {
		return getPersistence().create(banId);
	}

	public static com.liferay.portlet.messageboards.model.MBBan remove(
		long banId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().remove(banId);
	}

	public static com.liferay.portlet.messageboards.model.MBBan updateImpl(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbBan, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByPrimaryKey(
		long banId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByPrimaryKey(banId);
	}

	public static com.liferay.portlet.messageboards.model.MBBan fetchByPrimaryKey(
		long banId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(banId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan[] findByGroupId_PrevAndNext(
		long banId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByGroupId_PrevAndNext(banId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan[] findByUserId_PrevAndNext(
		long banId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByUserId_PrevAndNext(banId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBanUserId(banUserId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBanUserId(banUserId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBanUserId(banUserId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByBanUserId_First(
		long banUserId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByBanUserId_First(banUserId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByBanUserId_Last(
		long banUserId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByBanUserId_Last(banUserId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan[] findByBanUserId_PrevAndNext(
		long banId, long banUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByBanUserId_PrevAndNext(banId, banUserId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByG_B(
		long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByG_B(groupId, banUserId);
	}

	public static com.liferay.portlet.messageboards.model.MBBan fetchByG_B(
		long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_B(groupId, banUserId);
	}

	public static com.liferay.portlet.messageboards.model.MBBan fetchByG_B(
		long groupId, long banUserId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_B(groupId, banUserId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByBanUserId(long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByBanUserId(banUserId);
	}

	public static void removeByG_B(long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException {
		getPersistence().removeByG_B(groupId, banUserId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByBanUserId(long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByBanUserId(banUserId);
	}

	public static int countByG_B(long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_B(groupId, banUserId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBBanPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBBanPersistence)PortalBeanLocatorUtil.locate(MBBanPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(MBBanPersistence persistence) {
		_persistence = persistence;
	}

	private static MBBanPersistence _persistence;
}