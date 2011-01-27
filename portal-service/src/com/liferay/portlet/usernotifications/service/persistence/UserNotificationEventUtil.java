/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usernotifications.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.usernotifications.model.UserNotificationEvent;

import java.util.List;

/**
 * The persistence utility for the user notification event service. This utility wraps {@link UserNotificationEventPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventPersistence
 * @see UserNotificationEventPersistenceImpl
 * @generated
 */
public class UserNotificationEventUtil {
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
	public static void clearCache(UserNotificationEvent userNotificationEvent) {
		getPersistence().clearCache(userNotificationEvent);
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
	public static List<UserNotificationEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserNotificationEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserNotificationEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static UserNotificationEvent remove(
		UserNotificationEvent userNotificationEvent) throws SystemException {
		return getPersistence().remove(userNotificationEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static UserNotificationEvent update(
		UserNotificationEvent userNotificationEvent, boolean merge)
		throws SystemException {
		return getPersistence().update(userNotificationEvent, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static UserNotificationEvent update(
		UserNotificationEvent userNotificationEvent, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(userNotificationEvent, merge, serviceContext);
	}

	/**
	* Caches the user notification event in the entity cache if it is enabled.
	*
	* @param userNotificationEvent the user notification event to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.usernotifications.model.UserNotificationEvent userNotificationEvent) {
		getPersistence().cacheResult(userNotificationEvent);
	}

	/**
	* Caches the user notification events in the entity cache if it is enabled.
	*
	* @param userNotificationEvents the user notification events to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> userNotificationEvents) {
		getPersistence().cacheResult(userNotificationEvents);
	}

	/**
	* Creates a new user notification event with the primary key. Does not add the user notification event to the database.
	*
	* @param userNotificationEventId the primary key for the new user notification event
	* @return the new user notification event
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent create(
		long userNotificationEventId) {
		return getPersistence().create(userNotificationEventId);
	}

	/**
	* Removes the user notification event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationEventId the primary key of the user notification event to remove
	* @return the user notification event that was removed
	* @throws com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent remove(
		long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException {
		return getPersistence().remove(userNotificationEventId);
	}

	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent updateImpl(
		com.liferay.portlet.usernotifications.model.UserNotificationEvent userNotificationEvent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(userNotificationEvent, merge);
	}

	/**
	* Finds the user notification event with the primary key or throws a {@link com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException} if it could not be found.
	*
	* @param userNotificationEventId the primary key of the user notification event to find
	* @return the user notification event
	* @throws com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent findByPrimaryKey(
		long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException {
		return getPersistence().findByPrimaryKey(userNotificationEventId);
	}

	/**
	* Finds the user notification event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userNotificationEventId the primary key of the user notification event to find
	* @return the user notification event, or <code>null</code> if a user notification event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent fetchByPrimaryKey(
		long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(userNotificationEventId);
	}

	/**
	* Finds all the user notification events where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the user notification events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of user notification events to return
	* @param end the upper bound of the range of user notification events to return (not inclusive)
	* @return the range of matching user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the user notification events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of user notification events to return
	* @param end the upper bound of the range of user notification events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first user notification event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching user notification event
	* @throws com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException if a matching user notification event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last user notification event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching user notification event
	* @throws com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException if a matching user notification event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the user notification events before and after the current user notification event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next user notification event
	* @throws com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent[] findByUuid_PrevAndNext(
		long userNotificationEventId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByUuid_PrevAndNext(userNotificationEventId, uuid,
			orderByComparator);
	}

	/**
	* Finds all the user notification events where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @return the matching user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findByCompanyIdUserId(
		long companyId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyIdUserId(companyId, userId);
	}

	/**
	* Finds a range of all the user notification events where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param start the lower bound of the range of user notification events to return
	* @param end the upper bound of the range of user notification events to return (not inclusive)
	* @return the range of matching user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findByCompanyIdUserId(
		long companyId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyIdUserId(companyId, userId, start, end);
	}

	/**
	* Finds an ordered range of all the user notification events where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param start the lower bound of the range of user notification events to return
	* @param end the upper bound of the range of user notification events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findByCompanyIdUserId(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyIdUserId(companyId, userId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first user notification event in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching user notification event
	* @throws com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException if a matching user notification event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent findByCompanyIdUserId_First(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByCompanyIdUserId_First(companyId, userId,
			orderByComparator);
	}

	/**
	* Finds the last user notification event in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching user notification event
	* @throws com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException if a matching user notification event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent findByCompanyIdUserId_Last(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByCompanyIdUserId_Last(companyId, userId,
			orderByComparator);
	}

	/**
	* Finds the user notification events before and after the current user notification event in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next user notification event
	* @throws com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.usernotifications.model.UserNotificationEvent[] findByCompanyIdUserId_PrevAndNext(
		long userNotificationEventId, long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.usernotifications.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByCompanyIdUserId_PrevAndNext(userNotificationEventId,
			companyId, userId, orderByComparator);
	}

	/**
	* Finds all the user notification events.
	*
	* @return the user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the user notification events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user notification events to return
	* @param end the upper bound of the range of user notification events to return (not inclusive)
	* @return the range of user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the user notification events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user notification events to return
	* @param end the upper bound of the range of user notification events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.usernotifications.model.UserNotificationEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the user notification events where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the user notification events where companyId = &#63; and userId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyIdUserId(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyIdUserId(companyId, userId);
	}

	/**
	* Removes all the user notification events from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the user notification events where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the user notification events where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @return the number of matching user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyIdUserId(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyIdUserId(companyId, userId);
	}

	/**
	* Counts all the user notification events.
	*
	* @return the number of user notification events
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static UserNotificationEventPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserNotificationEventPersistence)PortalBeanLocatorUtil.locate(UserNotificationEventPersistence.class.getName());

			ReferenceRegistry.registerReference(UserNotificationEventUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(UserNotificationEventPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(UserNotificationEventUtil.class,
			"_persistence");
	}

	private static UserNotificationEventPersistence _persistence;
}