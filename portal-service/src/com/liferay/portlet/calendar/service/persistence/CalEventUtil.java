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

package com.liferay.portlet.calendar.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.calendar.model.CalEvent;

import java.util.List;

/**
 * The persistence utility for the cal event service. This utility wraps {@link CalEventPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CalEventPersistence
 * @see CalEventPersistenceImpl
 * @generated
 */
public class CalEventUtil {
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
	public static void clearCache(CalEvent calEvent) {
		getPersistence().clearCache(calEvent);
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
	public static List<CalEvent> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CalEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CalEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static CalEvent remove(CalEvent calEvent) throws SystemException {
		return getPersistence().remove(calEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static CalEvent update(CalEvent calEvent, boolean merge)
		throws SystemException {
		return getPersistence().update(calEvent, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static CalEvent update(CalEvent calEvent, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(calEvent, merge, serviceContext);
	}

	/**
	* Caches the cal event in the entity cache if it is enabled.
	*
	* @param calEvent the cal event to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.calendar.model.CalEvent calEvent) {
		getPersistence().cacheResult(calEvent);
	}

	/**
	* Caches the cal events in the entity cache if it is enabled.
	*
	* @param calEvents the cal events to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.calendar.model.CalEvent> calEvents) {
		getPersistence().cacheResult(calEvents);
	}

	/**
	* Creates a new cal event with the primary key. Does not add the cal event to the database.
	*
	* @param eventId the primary key for the new cal event
	* @return the new cal event
	*/
	public static com.liferay.portlet.calendar.model.CalEvent create(
		long eventId) {
		return getPersistence().create(eventId);
	}

	/**
	* Removes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param eventId the primary key of the cal event to remove
	* @return the cal event that was removed
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent remove(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().remove(eventId);
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateImpl(
		com.liferay.portlet.calendar.model.CalEvent calEvent, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(calEvent, merge);
	}

	/**
	* Finds the cal event with the primary key or throws a {@link com.liferay.portlet.calendar.NoSuchEventException} if it could not be found.
	*
	* @param eventId the primary key of the cal event to find
	* @return the cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByPrimaryKey(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByPrimaryKey(eventId);
	}

	/**
	* Finds the cal event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param eventId the primary key of the cal event to find
	* @return the cal event, or <code>null</code> if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent fetchByPrimaryKey(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(eventId);
	}

	/**
	* Finds all the cal events where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the cal events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the cal events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first cal event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last cal event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the cal events before and after the current cal event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent[] findByUuid_PrevAndNext(
		long eventId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByUuid_PrevAndNext(eventId, uuid, orderByComparator);
	}

	/**
	* Finds the cal event where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.calendar.NoSuchEventException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Finds the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Finds the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Finds all the cal events where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Finds a range of all the cal events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Finds an ordered range of all the cal events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Finds the first cal event in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Finds the last cal event in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Finds the cal events before and after the current cal event in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent[] findByCompanyId_PrevAndNext(
		long eventId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(eventId, companyId,
			orderByComparator);
	}

	/**
	* Finds all the cal events where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first cal event in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last cal event in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the cal events before and after the current cal event in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent[] findByGroupId_PrevAndNext(
		long eventId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(eventId, groupId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the cal events where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds all the cal events where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by to search with
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByNotRemindBy(
		int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByNotRemindBy(remindBy);
	}

	/**
	* Finds a range of all the cal events where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param remindBy the remind by to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByNotRemindBy(
		int remindBy, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByNotRemindBy(remindBy, start, end);
	}

	/**
	* Finds an ordered range of all the cal events where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param remindBy the remind by to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByNotRemindBy(
		int remindBy, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByNotRemindBy(remindBy, start, end, orderByComparator);
	}

	/**
	* Finds the first cal event in the ordered set where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param remindBy the remind by to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByNotRemindBy_First(
		int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByNotRemindBy_First(remindBy, orderByComparator);
	}

	/**
	* Finds the last cal event in the ordered set where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param remindBy the remind by to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByNotRemindBy_Last(
		int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByNotRemindBy_Last(remindBy, orderByComparator);
	}

	/**
	* Finds the cal events before and after the current cal event in the ordered set where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param remindBy the remind by to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent[] findByNotRemindBy_PrevAndNext(
		long eventId, int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByNotRemindBy_PrevAndNext(eventId, remindBy,
			orderByComparator);
	}

	/**
	* Finds all the cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_T(groupId, type);
	}

	/**
	* Finds a range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	* Finds an ordered range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator);
	}

	/**
	* Finds the first cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByG_T_First(
		long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Finds the last cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByG_T_Last(
		long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Finds the cal events before and after the current cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent[] findByG_T_PrevAndNext(
		long eventId, long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_T_PrevAndNext(eventId, groupId, type,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_T(groupId, type);
	}

	/**
	* Filters by the user's permissions and finds a range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_T(groupId, type, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_T(groupId, type, start, end, orderByComparator);
	}

	/**
	* Finds all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, repeating);
	}

	/**
	* Finds a range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, repeating, start, end);
	}

	/**
	* Finds an ordered range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_R(groupId, repeating, start, end, orderByComparator);
	}

	/**
	* Finds the first cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByG_R_First(
		long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_R_First(groupId, repeating, orderByComparator);
	}

	/**
	* Finds the last cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent findByG_R_Last(
		long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_R_Last(groupId, repeating, orderByComparator);
	}

	/**
	* Finds the cal events before and after the current cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.calendar.model.CalEvent[] findByG_R_PrevAndNext(
		long eventId, long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_R_PrevAndNext(eventId, groupId, repeating,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_R(
		long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_R(groupId, repeating);
	}

	/**
	* Filters by the user's permissions and finds a range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_R(
		long groupId, boolean repeating, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_R(groupId, repeating, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_R(
		long groupId, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_R(groupId, repeating, start, end,
			orderByComparator);
	}

	/**
	* Finds all the cal events.
	*
	* @return the cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of cal events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the cal events where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the cal event where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the cal events where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the cal events where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the cal events where remindBy &ne; &#63; from the database.
	*
	* @param remindBy the remind by to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByNotRemindBy(int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByNotRemindBy(remindBy);
	}

	/**
	* Removes all the cal events where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_T(groupId, type);
	}

	/**
	* Removes all the cal events where groupId = &#63; and repeating = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_R(groupId, repeating);
	}

	/**
	* Removes all the cal events from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the cal events where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the cal events where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Counts all the cal events where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Counts all the cal events where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and counts all the cal events where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Counts all the cal events where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by to search with
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByNotRemindBy(int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByNotRemindBy(remindBy);
	}

	/**
	* Counts all the cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_T(groupId, type);
	}

	/**
	* Filters by the user's permissions and counts all the cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param type the type to search with
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_T(groupId, type);
	}

	/**
	* Counts all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_R(groupId, repeating);
	}

	/**
	* Filters by the user's permissions and counts all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID to search with
	* @param repeating the repeating to search with
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_R(groupId, repeating);
	}

	/**
	* Counts all the cal events.
	*
	* @return the number of cal events
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CalEventPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CalEventPersistence)PortalBeanLocatorUtil.locate(CalEventPersistence.class.getName());

			ReferenceRegistry.registerReference(CalEventUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(CalEventPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(CalEventUtil.class, "_persistence");
	}

	private static CalEventPersistence _persistence;
}