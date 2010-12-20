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
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialEquityUser;

import java.util.List;

/**
 * The persistence utility for the social equity user service. This utility wraps {@link SocialEquityUserPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityUserPersistence
 * @see SocialEquityUserPersistenceImpl
 * @generated
 */
public class SocialEquityUserUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

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

	/**
	* Caches the social equity user in the entity cache if it is enabled.
	*
	* @param socialEquityUser the social equity user to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser) {
		getPersistence().cacheResult(socialEquityUser);
	}

	/**
	* Caches the social equity users in the entity cache if it is enabled.
	*
	* @param socialEquityUsers the social equity users to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityUser> socialEquityUsers) {
		getPersistence().cacheResult(socialEquityUsers);
	}

	/**
	* Creates a new social equity user with the primary key. Does not add the social equity user to the database.
	*
	* @param equityUserId the primary key for the new social equity user
	* @return the new social equity user
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser create(
		long equityUserId) {
		return getPersistence().create(equityUserId);
	}

	/**
	* Removes the social equity user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityUserId the primary key of the social equity user to remove
	* @return the social equity user that was removed
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
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

	/**
	* Finds the social equity user with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityUserException} if it could not be found.
	*
	* @param equityUserId the primary key of the social equity user to find
	* @return the social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByPrimaryKey(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByPrimaryKey(equityUserId);
	}

	/**
	* Finds the social equity user with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equityUserId the primary key of the social equity user to find
	* @return the social equity user, or <code>null</code> if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser fetchByPrimaryKey(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equityUserId);
	}

	/**
	* Finds all the social equity users where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the social equity users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @return the range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the social equity users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first social equity user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last social equity user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the social equity users before and after the current social equity user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityUserId the primary key of the current social equity user
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser[] findByGroupId_PrevAndNext(
		long equityUserId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(equityUserId, groupId,
			orderByComparator);
	}

	/**
	* Finds all the social equity users where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupRanked(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupRanked(groupId);
	}

	/**
	* Finds a range of all the social equity users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @return the range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupRanked(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupRanked(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the social equity users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByGroupRanked(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupRanked(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first social equity user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByGroupRanked_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByGroupRanked_First(groupId, orderByComparator);
	}

	/**
	* Finds the last social equity user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByGroupRanked_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByGroupRanked_Last(groupId, orderByComparator);
	}

	/**
	* Finds the social equity users before and after the current social equity user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityUserId the primary key of the current social equity user
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser[] findByGroupRanked_PrevAndNext(
		long equityUserId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByGroupRanked_PrevAndNext(equityUserId, groupId,
			orderByComparator);
	}

	/**
	* Finds all the social equity users where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Finds a range of all the social equity users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @return the range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Finds an ordered range of all the social equity users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Finds the first social equity user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Finds the last social equity user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Finds the social equity users before and after the current social equity user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityUserId the primary key of the current social equity user
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser[] findByUserId_PrevAndNext(
		long equityUserId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByUserId_PrevAndNext(equityUserId, userId,
			orderByComparator);
	}

	/**
	* Finds all the social equity users where rank = &#63;.
	*
	* @param rank the rank to search with
	* @return the matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByRank(
		int rank) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRank(rank);
	}

	/**
	* Finds a range of all the social equity users where rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rank the rank to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @return the range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByRank(
		int rank, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRank(rank, start, end);
	}

	/**
	* Finds an ordered range of all the social equity users where rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rank the rank to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByRank(
		int rank, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRank(rank, start, end, orderByComparator);
	}

	/**
	* Finds the first social equity user in the ordered set where rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rank the rank to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByRank_First(
		int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByRank_First(rank, orderByComparator);
	}

	/**
	* Finds the last social equity user in the ordered set where rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rank the rank to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByRank_Last(
		int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByRank_Last(rank, orderByComparator);
	}

	/**
	* Finds the social equity users before and after the current social equity user in the ordered set where rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityUserId the primary key of the current social equity user
	* @param rank the rank to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser[] findByRank_PrevAndNext(
		long equityUserId, int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByRank_PrevAndNext(equityUserId, rank, orderByComparator);
	}

	/**
	* Finds the social equity user where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquityUserException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Finds the social equity user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching social equity user, or <code>null</code> if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser fetchByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId);
	}

	/**
	* Finds the social equity user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching social equity user, or <code>null</code> if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId, retrieveFromCache);
	}

	/**
	* Finds all the social equity users where groupId = &#63; and rank = &#63;.
	*
	* @param groupId the group ID to search with
	* @param rank the rank to search with
	* @return the matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByG_R(
		long groupId, int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, rank);
	}

	/**
	* Finds a range of all the social equity users where groupId = &#63; and rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param rank the rank to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @return the range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByG_R(
		long groupId, int rank, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, rank, start, end);
	}

	/**
	* Finds an ordered range of all the social equity users where groupId = &#63; and rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param rank the rank to search with
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findByG_R(
		long groupId, int rank, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_R(groupId, rank, start, end, orderByComparator);
	}

	/**
	* Finds the first social equity user in the ordered set where groupId = &#63; and rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param rank the rank to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByG_R_First(
		long groupId, int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByG_R_First(groupId, rank, orderByComparator);
	}

	/**
	* Finds the last social equity user in the ordered set where groupId = &#63; and rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param rank the rank to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser findByG_R_Last(
		long groupId, int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence().findByG_R_Last(groupId, rank, orderByComparator);
	}

	/**
	* Finds the social equity users before and after the current social equity user in the ordered set where groupId = &#63; and rank = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityUserId the primary key of the current social equity user
	* @param groupId the group ID to search with
	* @param rank the rank to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next social equity user
	* @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser[] findByG_R_PrevAndNext(
		long equityUserId, long groupId, int rank,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		return getPersistence()
				   .findByG_R_PrevAndNext(equityUserId, groupId, rank,
			orderByComparator);
	}

	/**
	* Finds all the social equity users.
	*
	* @return the social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the social equity users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @return the range of social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the social equity users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the social equity users where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the social equity users where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupRanked(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupRanked(groupId);
	}

	/**
	* Removes all the social equity users where userId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the social equity users where rank = &#63; from the database.
	*
	* @param rank the rank to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRank(int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRank(rank);
	}

	/**
	* Removes the social equity user where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityUserException {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Removes all the social equity users where groupId = &#63; and rank = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param rank the rank to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_R(long groupId, int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_R(groupId, rank);
	}

	/**
	* Removes all the social equity users from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the social equity users where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Counts all the social equity users where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupRanked(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupRanked(groupId);
	}

	/**
	* Counts all the social equity users where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the number of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Counts all the social equity users where rank = &#63;.
	*
	* @param rank the rank to search with
	* @return the number of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRank(int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRank(rank);
	}

	/**
	* Counts all the social equity users where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the number of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Counts all the social equity users where groupId = &#63; and rank = &#63;.
	*
	* @param groupId the group ID to search with
	* @param rank the rank to search with
	* @return the number of matching social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_R(long groupId, int rank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_R(groupId, rank);
	}

	/**
	* Counts all the social equity users.
	*
	* @return the number of social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquityUserPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquityUserPersistence)PortalBeanLocatorUtil.locate(SocialEquityUserPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialEquityUserUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(SocialEquityUserPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(SocialEquityUserUtil.class,
			"_persistence");
	}

	private static SocialEquityUserPersistence _persistence;
}