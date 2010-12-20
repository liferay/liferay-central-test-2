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
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the layout service. This utility wraps {@link LayoutPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPersistence
 * @see LayoutPersistenceImpl
 * @generated
 */
public class LayoutUtil {
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
	public static void clearCache(Layout layout) {
		getPersistence().clearCache(layout);
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
	public static List<Layout> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Layout> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Layout> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Layout remove(Layout layout) throws SystemException {
		return getPersistence().remove(layout);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Layout update(Layout layout, boolean merge)
		throws SystemException {
		return getPersistence().update(layout, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Layout update(Layout layout, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(layout, merge, serviceContext);
	}

	/**
	* Caches the layout in the entity cache if it is enabled.
	*
	* @param layout the layout to cache
	*/
	public static void cacheResult(com.liferay.portal.model.Layout layout) {
		getPersistence().cacheResult(layout);
	}

	/**
	* Caches the layouts in the entity cache if it is enabled.
	*
	* @param layouts the layouts to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Layout> layouts) {
		getPersistence().cacheResult(layouts);
	}

	/**
	* Creates a new layout with the primary key. Does not add the layout to the database.
	*
	* @param plid the primary key for the new layout
	* @return the new layout
	*/
	public static com.liferay.portal.model.Layout create(long plid) {
		return getPersistence().create(plid);
	}

	/**
	* Removes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param plid the primary key of the layout to remove
	* @return the layout that was removed
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout remove(long plid)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(plid);
	}

	public static com.liferay.portal.model.Layout updateImpl(
		com.liferay.portal.model.Layout layout, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(layout, merge);
	}

	/**
	* Finds the layout with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param plid the primary key of the layout to find
	* @return the layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByPrimaryKey(long plid)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(plid);
	}

	/**
	* Finds the layout with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param plid the primary key of the layout to find
	* @return the layout, or <code>null</code> if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByPrimaryKey(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(plid);
	}

	/**
	* Finds all the layouts where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first layout in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last layout in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the layouts before and after the current layout in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout[] findByUuid_PrevAndNext(
		long plid, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(plid, uuid, orderByComparator);
	}

	/**
	* Finds the layout where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Finds the layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Finds the layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Finds all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first layout in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last layout in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the layouts before and after the current layout in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout[] findByGroupId_PrevAndNext(
		long plid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(plid, groupId, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds all the layouts where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Finds a range of all the layouts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Finds an ordered range of all the layouts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Finds the first layout in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Finds the last layout in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Finds the layouts before and after the current layout in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout[] findByCompanyId_PrevAndNext(
		long plid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(plid, companyId,
			orderByComparator);
	}

	/**
	* Finds the layout where iconImageId = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param iconImageId the icon image ID to search with
	* @return the matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByIconImageId(
		long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByIconImageId(iconImageId);
	}

	/**
	* Finds the layout where iconImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param iconImageId the icon image ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByIconImageId(
		long iconImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByIconImageId(iconImageId);
	}

	/**
	* Finds the layout where iconImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param iconImageId the icon image ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByIconImageId(
		long iconImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByIconImageId(iconImageId, retrieveFromCache);
	}

	/**
	* Finds all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	/**
	* Finds a range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout, start, end);
	}

	/**
	* Finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	/**
	* Finds the first layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_First(groupId, privateLayout, orderByComparator);
	}

	/**
	* Finds the last layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	/**
	* Finds the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout[] findByG_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_PrevAndNext(plid, groupId, privateLayout,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, privateLayout);
	}

	/**
	* Filters by the user's permissions and finds a range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, privateLayout, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @return the matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_L(groupId, privateLayout, layoutId,
			retrieveFromCache);
	}

	/**
	* Finds all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Finds a range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId, start,
			end);
	}

	/**
	* Finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId, start,
			end, orderByComparator);
	}

	/**
	* Finds the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByG_P_P_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P_First(groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	/**
	* Finds the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByG_P_P_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P_Last(groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	/**
	* Finds the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout[] findByG_P_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P_PrevAndNext(plid, groupId, privateLayout,
			parentLayoutId, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @return the matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P_P(groupId, privateLayout, parentLayoutId,
			start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P_P(groupId, privateLayout, parentLayoutId,
			start, end, orderByComparator);
	}

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @return the matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_F(groupId, privateLayout, friendlyURL,
			retrieveFromCache);
	}

	/**
	* Finds all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Finds a range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T(groupId, privateLayout, type, start, end);
	}

	/**
	* Finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T(groupId, privateLayout, type, start, end,
			orderByComparator);
	}

	/**
	* Finds the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByG_P_T_First(
		long groupId, boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T_First(groupId, privateLayout, type,
			orderByComparator);
	}

	/**
	* Finds the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout findByG_P_T_Last(
		long groupId, boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T_Last(groupId, privateLayout, type,
			orderByComparator);
	}

	/**
	* Finds the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout[] findByG_P_T_PrevAndNext(
		long plid, long groupId, boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T_PrevAndNext(plid, groupId, privateLayout, type,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @return the matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Filters by the user's permissions and finds a range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P_T(groupId, privateLayout, type, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P_T(groupId, privateLayout, type, start, end,
			orderByComparator);
	}

	/**
	* Finds all the layouts.
	*
	* @return the layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the layouts where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the layout where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the layouts where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the layouts where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes the layout where iconImageId = &#63; from the database.
	*
	* @param iconImageId the icon image ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByIconImageId(long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByIconImageId(iconImageId);
	}

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	/**
	* Removes the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P_L(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Removes the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Removes all the layouts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the layouts where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the layouts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Counts all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and counts all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Counts all the layouts where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Counts all the layouts where iconImageId = &#63;.
	*
	* @param iconImageId the icon image ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByIconImageId(long iconImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByIconImageId(iconImageId);
	}

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	/**
	* Filters by the user's permissions and counts all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the number of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, privateLayout);
	}

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P_L(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Filters by the user's permissions and counts all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @return the number of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterCountByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Filters by the user's permissions and counts all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @return the number of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Counts all the layouts.
	*
	* @return the number of layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutPersistence)PortalBeanLocatorUtil.locate(LayoutPersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutUtil.class, "_persistence");
		}

		return _persistence;
	}

	public void setPersistence(LayoutPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(LayoutUtil.class, "_persistence");
	}

	private static LayoutPersistence _persistence;
}