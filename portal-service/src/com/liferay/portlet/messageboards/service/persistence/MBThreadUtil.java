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

import com.liferay.portlet.messageboards.model.MBThread;

import java.util.List;

/**
 * <a href="MBThreadUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThreadPersistence
 * @see       MBThreadPersistenceImpl
 * @generated
 */
public class MBThreadUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(MBThread)
	 */
	public static void clearCache(MBThread mbThread) {
		getPersistence().clearCache(mbThread);
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
	public static List<MBThread> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBThread> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static MBThread remove(MBThread mbThread) throws SystemException {
		return getPersistence().remove(mbThread);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBThread update(MBThread mbThread, boolean merge)
		throws SystemException {
		return getPersistence().update(mbThread, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		getPersistence().cacheResult(mbThread);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBThread> mbThreads) {
		getPersistence().cacheResult(mbThreads);
	}

	public static com.liferay.portlet.messageboards.model.MBThread create(
		long threadId) {
		return getPersistence().create(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread remove(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().remove(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread updateImpl(
		com.liferay.portlet.messageboards.model.MBThread mbThread, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbThread, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByPrimaryKey(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByPrimaryKey(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread fetchByPrimaryKey(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread[] findByGroupId_PrevAndNext(
		long threadId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(threadId, groupId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C(groupId, categoryId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_First(groupId, categoryId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_Last(groupId, categoryId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_C_PrevAndNext(
		long threadId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_PrevAndNext(threadId, groupId, categoryId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_S_First(groupId, status, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_S_Last(groupId, status, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_S_PrevAndNext(
		long threadId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_S_PrevAndNext(threadId, groupId, status,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(categoryId, priority);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(categoryId, priority, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P(categoryId, priority, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByC_P_First(
		long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByC_P_First(categoryId, priority, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByC_P_Last(
		long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByC_P_Last(categoryId, priority, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread[] findByC_P_PrevAndNext(
		long threadId, long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByC_P_PrevAndNext(threadId, categoryId, priority,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_L(groupId, categoryId, lastPostDate);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_L(groupId, categoryId, lastPostDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_L(groupId, categoryId, lastPostDate, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_L_First(
		long groupId, long categoryId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_L_First(groupId, categoryId, lastPostDate,
			orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_L_Last(
		long groupId, long categoryId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_L_Last(groupId, categoryId, lastPostDate,
			orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_C_L_PrevAndNext(
		long threadId, long groupId, long categoryId,
		java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_L_PrevAndNext(threadId, groupId, categoryId,
			lastPostDate, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_S(groupId, categoryId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_S_First(groupId, categoryId, status,
			orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_S_Last(groupId, categoryId, status,
			orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_C_S_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_S_PrevAndNext(threadId, groupId, categoryId,
			status, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C(groupId, categoryId);
	}

	public static void removeByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_S(groupId, status);
	}

	public static void removeByC_P(long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_P(categoryId, priority);
	}

	public static void removeByG_C_L(long groupId, long categoryId,
		java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_L(groupId, categoryId, lastPostDate);
	}

	public static void removeByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_S(groupId, categoryId, status);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, categoryId);
	}

	public static int countByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_S(groupId, status);
	}

	public static int countByC_P(long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_P(categoryId, priority);
	}

	public static int countByG_C_L(long groupId, long categoryId,
		java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_L(groupId, categoryId, lastPostDate);
	}

	public static int countByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_S(groupId, categoryId, status);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBThreadPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBThreadPersistence)PortalBeanLocatorUtil.locate(MBThreadPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(MBThreadPersistence persistence) {
		_persistence = persistence;
	}

	private static MBThreadPersistence _persistence;
}