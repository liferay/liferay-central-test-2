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
import com.liferay.portal.model.UserIdMapper;

import java.util.List;

/**
 * <a href="UserIdMapperUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserIdMapperPersistence
 * @see       UserIdMapperPersistenceImpl
 * @generated
 */
public class UserIdMapperUtil {
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
	public static List<UserIdMapper> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserIdMapper> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static UserIdMapper remove(UserIdMapper userIdMapper)
		throws SystemException {
		return getPersistence().remove(userIdMapper);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static UserIdMapper update(UserIdMapper userIdMapper, boolean merge)
		throws SystemException {
		return getPersistence().update(userIdMapper, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.UserIdMapper userIdMapper) {
		getPersistence().cacheResult(userIdMapper);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.UserIdMapper> userIdMappers) {
		getPersistence().cacheResult(userIdMappers);
	}

	public static com.liferay.portal.model.UserIdMapper create(
		long userIdMapperId) {
		return getPersistence().create(userIdMapperId);
	}

	public static com.liferay.portal.model.UserIdMapper remove(
		long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(userIdMapperId);
	}

	public static com.liferay.portal.model.UserIdMapper updateImpl(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(userIdMapper, merge);
	}

	public static com.liferay.portal.model.UserIdMapper findByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(userIdMapperId);
	}

	public static com.liferay.portal.model.UserIdMapper fetchByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(userIdMapperId);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.UserIdMapper findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	public static com.liferay.portal.model.UserIdMapper findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	public static com.liferay.portal.model.UserIdMapper[] findByUserId_PrevAndNext(
		long userIdMapperId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(userIdMapperId, userId,
			orderByComparator);
	}

	public static com.liferay.portal.model.UserIdMapper findByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_T(userId, type);
	}

	public static com.liferay.portal.model.UserIdMapper fetchByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_T(userId, type);
	}

	public static com.liferay.portal.model.UserIdMapper fetchByU_T(
		long userId, java.lang.String type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_T(userId, type, retrieveFromCache);
	}

	public static com.liferay.portal.model.UserIdMapper findByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_E(type, externalUserId);
	}

	public static com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByT_E(type, externalUserId);
	}

	public static com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_E(type, externalUserId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_T(userId, type);
	}

	public static void removeByT_E(java.lang.String type,
		java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_E(type, externalUserId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_T(userId, type);
	}

	public static int countByT_E(java.lang.String type,
		java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_E(type, externalUserId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static UserIdMapperPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserIdMapperPersistence)PortalBeanLocatorUtil.locate(UserIdMapperPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(UserIdMapperPersistence persistence) {
		_persistence = persistence;
	}

	private static UserIdMapperPersistence _persistence;
}