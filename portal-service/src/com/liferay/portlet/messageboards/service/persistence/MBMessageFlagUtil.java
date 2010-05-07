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

import com.liferay.portlet.messageboards.model.MBMessageFlag;

import java.util.List;

/**
 * <a href="MBMessageFlagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlagPersistence
 * @see       MBMessageFlagPersistenceImpl
 * @generated
 */
public class MBMessageFlagUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(MBMessageFlag)
	 */
	public static void clearCache(MBMessageFlag mbMessageFlag) {
		getPersistence().clearCache(mbMessageFlag);
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
	public static List<MBMessageFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBMessageFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static MBMessageFlag remove(MBMessageFlag mbMessageFlag)
		throws SystemException {
		return getPersistence().remove(mbMessageFlag);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBMessageFlag update(MBMessageFlag mbMessageFlag,
		boolean merge) throws SystemException {
		return getPersistence().update(mbMessageFlag, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag) {
		getPersistence().cacheResult(mbMessageFlag);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> mbMessageFlags) {
		getPersistence().cacheResult(mbMessageFlags);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag create(
		long messageFlagId) {
		return getPersistence().create(messageFlagId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag remove(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().remove(messageFlagId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbMessageFlag, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByPrimaryKey(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByPrimaryKey(messageFlagId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByPrimaryKey(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(messageFlagId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByUserId_PrevAndNext(
		long messageFlagId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByUserId_PrevAndNext(messageFlagId, userId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByThreadId(threadId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByThreadId_First(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByThreadId_First(threadId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByThreadId_Last(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByThreadId_Last(threadId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByThreadId_PrevAndNext(
		long messageFlagId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByThreadId_PrevAndNext(messageFlagId, threadId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByMessageId(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByMessageId(messageId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByMessageId(messageId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByMessageId_First(
		long messageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByMessageId_First(messageId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByMessageId_Last(
		long messageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByMessageId_Last(messageId, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByMessageId_PrevAndNext(
		long messageFlagId, long messageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByMessageId_PrevAndNext(messageFlagId, messageId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_F(threadId, flag);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_F(threadId, flag, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_F(threadId, flag, start, end, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByT_F_First(
		long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByT_F_First(threadId, flag, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByT_F_Last(
		long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByT_F_Last(threadId, flag, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByT_F_PrevAndNext(
		long messageFlagId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByT_F_PrevAndNext(messageFlagId, threadId, flag,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByM_F(messageId, flag);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByM_F(messageId, flag, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByM_F(messageId, flag, start, end, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByM_F_First(
		long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByM_F_First(messageId, flag, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByM_F_Last(
		long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByM_F_Last(messageId, flag, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByM_F_PrevAndNext(
		long messageFlagId, long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByM_F_PrevAndNext(messageFlagId, messageId, flag,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_T_F(userId, threadId, flag);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_T_F(userId, threadId, flag, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_T_F(userId, threadId, flag, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_T_F_First(
		long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByU_T_F_First(userId, threadId, flag, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_T_F_Last(
		long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByU_T_F_Last(userId, threadId, flag, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByU_T_F_PrevAndNext(
		long messageFlagId, long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByU_T_F_PrevAndNext(messageFlagId, userId, threadId,
			flag, orderByComparator);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_M_F(
		long userId, long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByU_M_F(userId, messageId, flag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByU_M_F(
		long userId, long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_M_F(userId, messageId, flag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByU_M_F(
		long userId, long messageId, int flag, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_M_F(userId, messageId, flag, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByThreadId(threadId);
	}

	public static void removeByMessageId(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByMessageId(messageId);
	}

	public static void removeByT_F(long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_F(threadId, flag);
	}

	public static void removeByM_F(long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByM_F(messageId, flag);
	}

	public static void removeByU_T_F(long userId, long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_T_F(userId, threadId, flag);
	}

	public static void removeByU_M_F(long userId, long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		getPersistence().removeByU_M_F(userId, messageId, flag);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByThreadId(threadId);
	}

	public static int countByMessageId(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByMessageId(messageId);
	}

	public static int countByT_F(long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_F(threadId, flag);
	}

	public static int countByM_F(long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByM_F(messageId, flag);
	}

	public static int countByU_T_F(long userId, long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_T_F(userId, threadId, flag);
	}

	public static int countByU_M_F(long userId, long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_M_F(userId, messageId, flag);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBMessageFlagPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBMessageFlagPersistence)PortalBeanLocatorUtil.locate(MBMessageFlagPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(MBMessageFlagPersistence persistence) {
		_persistence = persistence;
	}

	private static MBMessageFlagPersistence _persistence;
}