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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.dynamicdatalists.model.DDLEntryItem;

import java.util.List;

/**
 * The persistence utility for the d d l entry item service. This utility wraps {@link DDLEntryItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLEntryItemPersistence
 * @see DDLEntryItemPersistenceImpl
 * @generated
 */
public class DDLEntryItemUtil {
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
	public static void clearCache(DDLEntryItem ddlEntryItem) {
		getPersistence().clearCache(ddlEntryItem);
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
	public static List<DDLEntryItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDLEntryItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDLEntryItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DDLEntryItem remove(DDLEntryItem ddlEntryItem)
		throws SystemException {
		return getPersistence().remove(ddlEntryItem);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DDLEntryItem update(DDLEntryItem ddlEntryItem, boolean merge)
		throws SystemException {
		return getPersistence().update(ddlEntryItem, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DDLEntryItem update(DDLEntryItem ddlEntryItem, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(ddlEntryItem, merge, serviceContext);
	}

	/**
	* Caches the d d l entry item in the entity cache if it is enabled.
	*
	* @param ddlEntryItem the d d l entry item to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem) {
		getPersistence().cacheResult(ddlEntryItem);
	}

	/**
	* Caches the d d l entry items in the entity cache if it is enabled.
	*
	* @param ddlEntryItems the d d l entry items to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> ddlEntryItems) {
		getPersistence().cacheResult(ddlEntryItems);
	}

	/**
	* Creates a new d d l entry item with the primary key. Does not add the d d l entry item to the database.
	*
	* @param entryItemId the primary key for the new d d l entry item
	* @return the new d d l entry item
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem create(
		long entryItemId) {
		return getPersistence().create(entryItemId);
	}

	/**
	* Removes the d d l entry item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryItemId the primary key of the d d l entry item to remove
	* @return the d d l entry item that was removed
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem remove(
		long entryItemId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException {
		return getPersistence().remove(entryItemId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem updateImpl(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ddlEntryItem, merge);
	}

	/**
	* Finds the d d l entry item with the primary key or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException} if it could not be found.
	*
	* @param entryItemId the primary key of the d d l entry item to find
	* @return the d d l entry item
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem findByPrimaryKey(
		long entryItemId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException {
		return getPersistence().findByPrimaryKey(entryItemId);
	}

	/**
	* Finds the d d l entry item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryItemId the primary key of the d d l entry item to find
	* @return the d d l entry item, or <code>null</code> if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem fetchByPrimaryKey(
		long entryItemId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryItemId);
	}

	/**
	* Finds all the d d l entry items where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the d d l entry items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of d d l entry items to return
	* @param end the upper bound of the range of d d l entry items to return (not inclusive)
	* @return the range of matching d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the d d l entry items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of d d l entry items to return
	* @param end the upper bound of the range of d d l entry items to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first d d l entry item in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l entry item
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a matching d d l entry item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last d d l entry item in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l entry item
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a matching d d l entry item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the d d l entry items before and after the current d d l entry item in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryItemId the primary key of the current d d l entry item
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l entry item
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem[] findByUuid_PrevAndNext(
		long entryItemId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(entryItemId, uuid, orderByComparator);
	}

	/**
	* Finds all the d d l entry items where entryId = &#63;.
	*
	* @param entryId the entry ID to search with
	* @return the matching d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findByEntryId(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEntryId(entryId);
	}

	/**
	* Finds a range of all the d d l entry items where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the entry ID to search with
	* @param start the lower bound of the range of d d l entry items to return
	* @param end the upper bound of the range of d d l entry items to return (not inclusive)
	* @return the range of matching d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findByEntryId(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	/**
	* Finds an ordered range of all the d d l entry items where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the entry ID to search with
	* @param start the lower bound of the range of d d l entry items to return
	* @param end the upper bound of the range of d d l entry items to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findByEntryId(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator);
	}

	/**
	* Finds the first d d l entry item in the ordered set where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l entry item
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a matching d d l entry item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem findByEntryId_First(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException {
		return getPersistence().findByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Finds the last d d l entry item in the ordered set where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l entry item
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a matching d d l entry item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem findByEntryId_Last(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException {
		return getPersistence().findByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Finds the d d l entry items before and after the current d d l entry item in the ordered set where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryItemId the primary key of the current d d l entry item
	* @param entryId the entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l entry item
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem[] findByEntryId_PrevAndNext(
		long entryItemId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException {
		return getPersistence()
				   .findByEntryId_PrevAndNext(entryItemId, entryId,
			orderByComparator);
	}

	/**
	* Finds all the d d l entry items.
	*
	* @return the d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the d d l entry items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l entry items to return
	* @param end the upper bound of the range of d d l entry items to return (not inclusive)
	* @return the range of d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the d d l entry items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l entry items to return
	* @param end the upper bound of the range of d d l entry items to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d d l entry items where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the d d l entry items where entryId = &#63; from the database.
	*
	* @param entryId the entry ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByEntryId(entryId);
	}

	/**
	* Removes all the d d l entry items from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the d d l entry items where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the d d l entry items where entryId = &#63;.
	*
	* @param entryId the entry ID to search with
	* @return the number of matching d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByEntryId(entryId);
	}

	/**
	* Counts all the d d l entry items.
	*
	* @return the number of d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DDLEntryItemPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DDLEntryItemPersistence)PortalBeanLocatorUtil.locate(DDLEntryItemPersistence.class.getName());

			ReferenceRegistry.registerReference(DDLEntryItemUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DDLEntryItemPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DDLEntryItemUtil.class,
			"_persistence");
	}

	private static DDLEntryItemPersistence _persistence;
}