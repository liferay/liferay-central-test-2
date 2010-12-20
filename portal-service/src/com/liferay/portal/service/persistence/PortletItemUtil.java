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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the portlet item service. This utility wraps {@link PortletItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletItemPersistence
 * @see PortletItemPersistenceImpl
 * @generated
 */
public class PortletItemUtil {
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
	public static void clearCache(PortletItem portletItem) {
		getPersistence().clearCache(portletItem);
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
	public static List<PortletItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PortletItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PortletItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static PortletItem remove(PortletItem portletItem)
		throws SystemException {
		return getPersistence().remove(portletItem);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PortletItem update(PortletItem portletItem, boolean merge)
		throws SystemException {
		return getPersistence().update(portletItem, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static PortletItem update(PortletItem portletItem, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(portletItem, merge, serviceContext);
	}

	/**
	* Caches the portlet item in the entity cache if it is enabled.
	*
	* @param portletItem the portlet item to cache
	*/
	public static void cacheResult(
		com.liferay.portal.model.PortletItem portletItem) {
		getPersistence().cacheResult(portletItem);
	}

	/**
	* Caches the portlet items in the entity cache if it is enabled.
	*
	* @param portletItems the portlet items to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PortletItem> portletItems) {
		getPersistence().cacheResult(portletItems);
	}

	/**
	* Creates a new portlet item with the primary key. Does not add the portlet item to the database.
	*
	* @param portletItemId the primary key for the new portlet item
	* @return the new portlet item
	*/
	public static com.liferay.portal.model.PortletItem create(
		long portletItemId) {
		return getPersistence().create(portletItemId);
	}

	/**
	* Removes the portlet item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portletItemId the primary key of the portlet item to remove
	* @return the portlet item that was removed
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem remove(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(portletItem, merge);
	}

	/**
	* Finds the portlet item with the primary key or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	*
	* @param portletItemId the primary key of the portlet item to find
	* @return the portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem findByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(portletItemId);
	}

	/**
	* Finds the portlet item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portletItemId the primary key of the portlet item to find
	* @return the portlet item, or <code>null</code> if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem fetchByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(portletItemId);
	}

	/**
	* Finds all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	* Finds a range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of portlet items to return
	* @param end the upper bound of the range of portlet items to return (not inclusive)
	* @return the range of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	* Finds an ordered range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of portlet items to return
	* @param end the upper bound of the range of portlet items to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem findByG_C_First(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Finds the last portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem findByG_C_Last(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Finds the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param portletItemId the primary key of the current portlet item
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem[] findByG_C_PrevAndNext(
		long portletItemId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_PrevAndNext(portletItemId, groupId, classNameId,
			orderByComparator);
	}

	/**
	* Finds all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_C(groupId, portletId, classNameId);
	}

	/**
	* Finds a range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of portlet items to return
	* @param end the upper bound of the range of portlet items to return (not inclusive)
	* @return the range of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C(groupId, portletId, classNameId, start, end);
	}

	/**
	* Finds an ordered range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of portlet items to return
	* @param end the upper bound of the range of portlet items to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C(groupId, portletId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem findByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C_First(groupId, portletId, classNameId,
			orderByComparator);
	}

	/**
	* Finds the last portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem findByG_P_C_Last(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C_Last(groupId, portletId, classNameId,
			orderByComparator);
	}

	/**
	* Finds the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param portletItemId the primary key of the current portlet item
	* @param groupId the group ID to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem[] findByG_P_C_PrevAndNext(
		long portletItemId, long groupId, java.lang.String portletId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C_PrevAndNext(portletItemId, groupId, portletId,
			classNameId, orderByComparator);
	}

	/**
	* Finds the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem findByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_N_P_C(groupId, name, portletId, classNameId);
	}

	/**
	* Finds the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem fetchByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_N_P_C(groupId, name, portletId, classNameId);
	}

	/**
	* Finds the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PortletItem fetchByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_N_P_C(groupId, name, portletId, classNameId,
			retrieveFromCache);
	}

	/**
	* Finds all the portlet items.
	*
	* @return the portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the portlet items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of portlet items to return
	* @param end the upper bound of the range of portlet items to return (not inclusive)
	* @return the range of portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the portlet items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of portlet items to return
	* @param end the upper bound of the range of portlet items to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the portlet items where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	* Removes all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P_C(long groupId, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_C(groupId, portletId, classNameId);
	}

	/**
	* Removes the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_N_P_C(groupId, name, portletId, classNameId);
	}

	/**
	* Removes all the portlet items from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	* Counts all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P_C(long groupId, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_C(groupId, portletId, classNameId);
	}

	/**
	* Counts all the portlet items where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param portletId the portlet ID to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_N_P_C(groupId, name, portletId, classNameId);
	}

	/**
	* Counts all the portlet items.
	*
	* @return the number of portlet items
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PortletItemPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortletItemPersistence)PortalBeanLocatorUtil.locate(PortletItemPersistence.class.getName());

			ReferenceRegistry.registerReference(PortletItemUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(PortletItemPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(PortletItemUtil.class,
			"_persistence");
	}

	private static PortletItemPersistence _persistence;
}