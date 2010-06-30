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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialEquityUser;

import java.util.List;

/**
 * <a href="SocialEquityUserUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUserPersistence
 * @see       SocialEquityUserPersistenceImpl
 * @generated
 */
public class SocialEquityUserUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(SocialEquityUser socialEquityUser) {
		getPersistence().clearCache(socialEquityUser);
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
	public static List<SocialEquityUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialEquityUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialEquityUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialEquityUser remove(SocialEquityUser socialEquityUser)
		throws SystemException {
		return getPersistence().remove(socialEquityUser);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialEquityUser update(SocialEquityUser socialEquityUser,
		boolean merge) throws SystemException {
		return getPersistence().update(socialEquityUser, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SocialEquityUser update(SocialEquityUser socialEquityUser,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(socialEquityUser, merge, serviceContext);
	}

	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser) {
		getPersistence().cacheResult(socialEquityUser);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityUser> socialEquityUsers) {
		getPersistence().cacheResult(socialEquityUsers);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser create(
		long equityUserId) {
		return getPersistence().create(equityUserId);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser remove(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().remove(equityUserId);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser updateImpl(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialEquityUser, merge);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByPrimaryKey(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByPrimaryKey(equityUserId);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser fetchByPrimaryKey(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equityUserId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser[] findByGroupId_PrevAndNext(
		long equityUserId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(equityUserId, groupId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupRanked(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupRanked(groupId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupRanked(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupRanked(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupRanked(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupRanked(groupId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByGroupRanked_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByGroupRanked_First(groupId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByGroupRanked_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByGroupRanked_Last(groupId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser[] findByGroupRanked_PrevAndNext(
		long equityUserId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByGroupRanked_PrevAndNext(equityUserId, groupId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser[] findByUserId_PrevAndNext(
		long equityUserId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByUserId_PrevAndNext(equityUserId, userId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByRank(
		int rank) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRank(rank);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByRank(
		int rank, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRank(rank, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByRank(
		int rank, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRank(rank, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByRank_First(
		int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByRank_First(rank, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByRank_Last(
		int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByRank_Last(rank, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser[] findByRank_PrevAndNext(
		long equityUserId, int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByRank_PrevAndNext(equityUserId, rank, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser fetchByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByG_R(
		long groupId, int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, rank);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByG_R(
		long groupId, int rank, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, rank, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByG_R(
		long groupId, int rank, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_R(groupId, rank, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByG_R_First(
		long groupId, int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByG_R_First(groupId, rank, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser findByG_R_Last(
		long groupId, int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByG_R_Last(groupId, rank, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser[] findByG_R_PrevAndNext(
		long equityUserId, long groupId, int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByG_R_PrevAndNext(equityUserId, groupId, rank,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByGroupRanked(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupRanked(groupId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByRank(int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRank(rank);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeByG_R(long groupId, int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_R(groupId, rank);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByGroupRanked(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupRanked(groupId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByRank(int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRank(rank);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countByG_R(long groupId, int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_R(groupId, rank);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquityUserPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquityUserPersistence)PortalBeanLocatorUtil.locate(SocialEquityUserPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SocialEquityUserPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialEquityUserPersistence _persistence;
}