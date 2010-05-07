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
import com.liferay.portal.model.User;

import java.util.List;

/**
 * <a href="UserUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserPersistence
 * @see       UserPersistenceImpl
 * @generated
 */
public class UserUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(User)
	 */
	public static void clearCache(User user) {
		getPersistence().clearCache(user);
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
	public static List<User> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<User> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static User remove(User user) throws SystemException {
		return getPersistence().remove(user);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static User update(User user, boolean merge)
		throws SystemException {
		return getPersistence().update(user, merge);
	}

	public static void cacheResult(com.liferay.portal.model.User user) {
		getPersistence().cacheResult(user);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.User> users) {
		getPersistence().cacheResult(users);
	}

	public static com.liferay.portal.model.User create(long userId) {
		return getPersistence().create(userId);
	}

	public static com.liferay.portal.model.User remove(long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(userId);
	}

	public static com.liferay.portal.model.User updateImpl(
		com.liferay.portal.model.User user, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(user, merge);
	}

	public static com.liferay.portal.model.User findByPrimaryKey(long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(userId);
	}

	public static com.liferay.portal.model.User fetchByPrimaryKey(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(userId);
	}

	public static java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.User findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	public static com.liferay.portal.model.User findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	public static com.liferay.portal.model.User[] findByUuid_PrevAndNext(
		long userId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(userId, uuid, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.User findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.User findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.User[] findByCompanyId_PrevAndNext(
		long userId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(userId, companyId,
			orderByComparator);
	}

	public static com.liferay.portal.model.User findByContactId(long contactId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByContactId(contactId);
	}

	public static com.liferay.portal.model.User fetchByContactId(long contactId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByContactId(contactId);
	}

	public static com.liferay.portal.model.User fetchByContactId(
		long contactId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByContactId(contactId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEmailAddress(emailAddress);
	}

	public static java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEmailAddress(emailAddress, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByEmailAddress(emailAddress, start, end,
			orderByComparator);
	}

	public static com.liferay.portal.model.User findByEmailAddress_First(
		java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByEmailAddress_First(emailAddress, orderByComparator);
	}

	public static com.liferay.portal.model.User findByEmailAddress_Last(
		java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByEmailAddress_Last(emailAddress, orderByComparator);
	}

	public static com.liferay.portal.model.User[] findByEmailAddress_PrevAndNext(
		long userId, java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByEmailAddress_PrevAndNext(userId, emailAddress,
			orderByComparator);
	}

	public static com.liferay.portal.model.User findByOpenId(
		java.lang.String openId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByOpenId(openId);
	}

	public static com.liferay.portal.model.User fetchByOpenId(
		java.lang.String openId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByOpenId(openId);
	}

	public static com.liferay.portal.model.User fetchByOpenId(
		java.lang.String openId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByOpenId(openId, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByPortraitId(
		long portraitId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPortraitId(portraitId);
	}

	public static com.liferay.portal.model.User fetchByPortraitId(
		long portraitId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPortraitId(portraitId);
	}

	public static com.liferay.portal.model.User fetchByPortraitId(
		long portraitId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPortraitId(portraitId, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByC_U(long companyId,
		long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_U(companyId, userId);
	}

	public static com.liferay.portal.model.User fetchByC_U(long companyId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_U(companyId, userId);
	}

	public static com.liferay.portal.model.User fetchByC_U(long companyId,
		long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_U(companyId, userId, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByC_DU(long companyId,
		boolean defaultUser)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_DU(companyId, defaultUser);
	}

	public static com.liferay.portal.model.User fetchByC_DU(long companyId,
		boolean defaultUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_DU(companyId, defaultUser);
	}

	public static com.liferay.portal.model.User fetchByC_DU(long companyId,
		boolean defaultUser, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_DU(companyId, defaultUser, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByC_SN(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_SN(companyId, screenName);
	}

	public static com.liferay.portal.model.User fetchByC_SN(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_SN(companyId, screenName);
	}

	public static com.liferay.portal.model.User fetchByC_SN(long companyId,
		java.lang.String screenName, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_SN(companyId, screenName, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByC_EA(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_EA(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User fetchByC_EA(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_EA(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User fetchByC_EA(long companyId,
		java.lang.String emailAddress, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_EA(companyId, emailAddress, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.User> findByC_A(
		long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_A(companyId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> findByC_A(
		long companyId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_A(companyId, active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_A(companyId, active, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.User findByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_A_First(companyId, active, orderByComparator);
	}

	public static com.liferay.portal.model.User findByC_A_Last(long companyId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_A_Last(companyId, active, orderByComparator);
	}

	public static com.liferay.portal.model.User[] findByC_A_PrevAndNext(
		long userId, long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_A_PrevAndNext(userId, companyId, active,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.User> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.User> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByContactId(long contactId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByContactId(contactId);
	}

	public static void removeByEmailAddress(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByEmailAddress(emailAddress);
	}

	public static void removeByOpenId(java.lang.String openId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByOpenId(openId);
	}

	public static void removeByPortraitId(long portraitId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPortraitId(portraitId);
	}

	public static void removeByC_U(long companyId, long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_U(companyId, userId);
	}

	public static void removeByC_DU(long companyId, boolean defaultUser)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_DU(companyId, defaultUser);
	}

	public static void removeByC_SN(long companyId, java.lang.String screenName)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_SN(companyId, screenName);
	}

	public static void removeByC_EA(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_EA(companyId, emailAddress);
	}

	public static void removeByC_A(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_A(companyId, active);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByContactId(long contactId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByContactId(contactId);
	}

	public static int countByEmailAddress(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByEmailAddress(emailAddress);
	}

	public static int countByOpenId(java.lang.String openId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByOpenId(openId);
	}

	public static int countByPortraitId(long portraitId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPortraitId(portraitId);
	}

	public static int countByC_U(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_U(companyId, userId);
	}

	public static int countByC_DU(long companyId, boolean defaultUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_DU(companyId, defaultUser);
	}

	public static int countByC_SN(long companyId, java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_SN(companyId, screenName);
	}

	public static int countByC_EA(long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_EA(companyId, emailAddress);
	}

	public static int countByC_A(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_A(companyId, active);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end, orderByComparator);
	}

	public static int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	public static boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroup(pk, groupPK);
	}

	public static boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroups(pk);
	}

	public static void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, groupPK);
	}

	public static void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, group);
	}

	public static void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groupPKs);
	}

	public static void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groups);
	}

	public static void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearGroups(pk);
	}

	public static void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, groupPK);
	}

	public static void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, group);
	}

	public static void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groupPKs);
	}

	public static void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groups);
	}

	public static void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groupPKs);
	}

	public static void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groups);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getOrganizations(pk);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getOrganizations(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .getOrganizations(pk, start, end, orderByComparator);
	}

	public static int getOrganizationsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getOrganizationsSize(pk);
	}

	public static boolean containsOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsOrganization(pk, organizationPK);
	}

	public static boolean containsOrganizations(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsOrganizations(pk);
	}

	public static void addOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addOrganization(pk, organizationPK);
	}

	public static void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addOrganization(pk, organization);
	}

	public static void addOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addOrganizations(pk, organizationPKs);
	}

	public static void addOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addOrganizations(pk, organizations);
	}

	public static void clearOrganizations(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearOrganizations(pk);
	}

	public static void removeOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeOrganization(pk, organizationPK);
	}

	public static void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeOrganization(pk, organization);
	}

	public static void removeOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeOrganizations(pk, organizationPKs);
	}

	public static void removeOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeOrganizations(pk, organizations);
	}

	public static void setOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setOrganizations(pk, organizationPKs);
	}

	public static void setOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setOrganizations(pk, organizations);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getPermissions(pk);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getPermissions(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getPermissions(pk, start, end, orderByComparator);
	}

	public static int getPermissionsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getPermissionsSize(pk);
	}

	public static boolean containsPermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsPermission(pk, permissionPK);
	}

	public static boolean containsPermissions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsPermissions(pk);
	}

	public static void addPermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addPermission(pk, permissionPK);
	}

	public static void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addPermission(pk, permission);
	}

	public static void addPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addPermissions(pk, permissionPKs);
	}

	public static void addPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addPermissions(pk, permissions);
	}

	public static void clearPermissions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearPermissions(pk);
	}

	public static void removePermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removePermission(pk, permissionPK);
	}

	public static void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removePermission(pk, permission);
	}

	public static void removePermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removePermissions(pk, permissionPKs);
	}

	public static void removePermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removePermissions(pk, permissions);
	}

	public static void setPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setPermissions(pk, permissionPKs);
	}

	public static void setPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setPermissions(pk, permissions);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getRoles(pk);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getRoles(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getRoles(pk, start, end, orderByComparator);
	}

	public static int getRolesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getRolesSize(pk);
	}

	public static boolean containsRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsRole(pk, rolePK);
	}

	public static boolean containsRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsRoles(pk);
	}

	public static void addRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addRole(pk, rolePK);
	}

	public static void addRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addRole(pk, role);
	}

	public static void addRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addRoles(pk, rolePKs);
	}

	public static void addRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addRoles(pk, roles);
	}

	public static void clearRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearRoles(pk);
	}

	public static void removeRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeRole(pk, rolePK);
	}

	public static void removeRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeRole(pk, role);
	}

	public static void removeRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeRoles(pk, rolePKs);
	}

	public static void removeRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeRoles(pk, roles);
	}

	public static void setRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setRoles(pk, rolePKs);
	}

	public static void setRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setRoles(pk, roles);
	}

	public static java.util.List<com.liferay.portal.model.Team> getTeams(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getTeams(pk);
	}

	public static java.util.List<com.liferay.portal.model.Team> getTeams(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getTeams(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Team> getTeams(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getTeams(pk, start, end, orderByComparator);
	}

	public static int getTeamsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getTeamsSize(pk);
	}

	public static boolean containsTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsTeam(pk, teamPK);
	}

	public static boolean containsTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsTeams(pk);
	}

	public static void addTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addTeam(pk, teamPK);
	}

	public static void addTeam(long pk, com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addTeam(pk, team);
	}

	public static void addTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addTeams(pk, teamPKs);
	}

	public static void addTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addTeams(pk, teams);
	}

	public static void clearTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearTeams(pk);
	}

	public static void removeTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeTeam(pk, teamPK);
	}

	public static void removeTeam(long pk, com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeTeam(pk, team);
	}

	public static void removeTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeTeams(pk, teamPKs);
	}

	public static void removeTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeTeams(pk, teams);
	}

	public static void setTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setTeams(pk, teamPKs);
	}

	public static void setTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setTeams(pk, teams);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUserGroups(pk);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUserGroups(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUserGroups(pk, start, end, orderByComparator);
	}

	public static int getUserGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUserGroupsSize(pk);
	}

	public static boolean containsUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUserGroup(pk, userGroupPK);
	}

	public static boolean containsUserGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUserGroups(pk);
	}

	public static void addUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUserGroup(pk, userGroupPK);
	}

	public static void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUserGroup(pk, userGroup);
	}

	public static void addUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUserGroups(pk, userGroupPKs);
	}

	public static void addUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUserGroups(pk, userGroups);
	}

	public static void clearUserGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearUserGroups(pk);
	}

	public static void removeUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUserGroup(pk, userGroupPK);
	}

	public static void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUserGroup(pk, userGroup);
	}

	public static void removeUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUserGroups(pk, userGroupPKs);
	}

	public static void removeUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUserGroups(pk, userGroups);
	}

	public static void setUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUserGroups(pk, userGroupPKs);
	}

	public static void setUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUserGroups(pk, userGroups);
	}

	public static UserPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserPersistence)PortalBeanLocatorUtil.locate(UserPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(UserPersistence persistence) {
		_persistence = persistence;
	}

	private static UserPersistence _persistence;
}