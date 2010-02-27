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
import com.liferay.portal.model.MembershipRequest;

import java.util.List;

/**
 * <a href="MembershipRequestUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequestPersistence
 * @see       MembershipRequestPersistenceImpl
 * @generated
 */
public class MembershipRequestUtil {
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
	public static MembershipRequest remove(MembershipRequest membershipRequest)
		throws SystemException {
		return getPersistence().remove(membershipRequest);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MembershipRequest update(
		MembershipRequest membershipRequest, boolean merge)
		throws SystemException {
		return getPersistence().update(membershipRequest, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		getPersistence().cacheResult(membershipRequest);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.MembershipRequest> membershipRequests) {
		getPersistence().cacheResult(membershipRequests);
	}

	public static com.liferay.portal.model.MembershipRequest create(
		long membershipRequestId) {
		return getPersistence().create(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest remove(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest updateImpl(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(membershipRequest, merge);
	}

	public static com.liferay.portal.model.MembershipRequest findByPrimaryKey(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest fetchByPrimaryKey(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(membershipRequestId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByGroupId_PrevAndNext(
		long membershipRequestId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(membershipRequestId, groupId, obc);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByUserId_PrevAndNext(
		long membershipRequestId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(membershipRequestId, userId, obc);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, statusId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, statusId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, statusId, start, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByG_S_First(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S_First(groupId, statusId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByG_S_Last(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S_Last(groupId, statusId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByG_S_PrevAndNext(
		long membershipRequestId, long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_S_PrevAndNext(membershipRequestId, groupId,
			statusId, obc);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll(
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

	public static void removeByG_S(long groupId, int statusId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_S(groupId, statusId);
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

	public static int countByG_S(long groupId, int statusId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_S(groupId, statusId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MembershipRequestPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MembershipRequestPersistence)PortalBeanLocatorUtil.locate(MembershipRequestPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(MembershipRequestPersistence persistence) {
		_persistence = persistence;
	}

	private static MembershipRequestPersistence _persistence;
}