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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.announcements.model.AnnouncementsFlag;

import java.util.List;

/**
 * <a href="AnnouncementsFlagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagPersistence
 * @see       AnnouncementsFlagPersistenceImpl
 * @generated
 */
public class AnnouncementsFlagUtil {
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
	public static List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static AnnouncementsFlag remove(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		return getPersistence().remove(announcementsFlag);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AnnouncementsFlag update(
		AnnouncementsFlag announcementsFlag, boolean merge)
		throws SystemException {
		return getPersistence().update(announcementsFlag, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag) {
		getPersistence().cacheResult(announcementsFlag);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> announcementsFlags) {
		getPersistence().cacheResult(announcementsFlags);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag create(
		long flagId) {
		return getPersistence().create(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag remove(
		long flagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().remove(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(announcementsFlag, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByPrimaryKey(
		long flagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByPrimaryKey(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByPrimaryKey(
		long flagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(flagId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEntryId(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByEntryId_First(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_First(entryId, orderByComparator);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByEntryId_Last(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_Last(entryId, orderByComparator);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag[] findByEntryId_PrevAndNext(
		long flagId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence()
				   .findByEntryId_PrevAndNext(flagId, entryId, orderByComparator);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByU_E_V(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByU_E_V(userId, entryId, value);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_E_V(userId, entryId, value);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_E_V(userId, entryId, value, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByEntryId(entryId);
	}

	public static void removeByU_E_V(long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		getPersistence().removeByU_E_V(userId, entryId, value);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByEntryId(entryId);
	}

	public static int countByU_E_V(long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_E_V(userId, entryId, value);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AnnouncementsFlagPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AnnouncementsFlagPersistence)PortalBeanLocatorUtil.locate(AnnouncementsFlagPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AnnouncementsFlagPersistence persistence) {
		_persistence = persistence;
	}

	private static AnnouncementsFlagPersistence _persistence;
}