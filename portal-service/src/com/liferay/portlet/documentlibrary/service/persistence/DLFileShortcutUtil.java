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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import java.util.List;

/**
 * The persistence utility for the d l file shortcut service. This utility wraps {@link DLFileShortcutPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutPersistence
 * @see DLFileShortcutPersistenceImpl
 * @generated
 */
public class DLFileShortcutUtil {
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
	public static void clearCache(DLFileShortcut dlFileShortcut) {
		getPersistence().clearCache(dlFileShortcut);
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
	public static List<DLFileShortcut> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileShortcut> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileShortcut> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DLFileShortcut remove(DLFileShortcut dlFileShortcut)
		throws SystemException {
		return getPersistence().remove(dlFileShortcut);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DLFileShortcut update(DLFileShortcut dlFileShortcut,
		boolean merge) throws SystemException {
		return getPersistence().update(dlFileShortcut, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DLFileShortcut update(DLFileShortcut dlFileShortcut,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(dlFileShortcut, merge, serviceContext);
	}

	/**
	* Caches the d l file shortcut in the entity cache if it is enabled.
	*
	* @param dlFileShortcut the d l file shortcut to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		getPersistence().cacheResult(dlFileShortcut);
	}

	/**
	* Caches the d l file shortcuts in the entity cache if it is enabled.
	*
	* @param dlFileShortcuts the d l file shortcuts to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> dlFileShortcuts) {
		getPersistence().cacheResult(dlFileShortcuts);
	}

	/**
	* Creates a new d l file shortcut with the primary key. Does not add the d l file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new d l file shortcut
	* @return the new d l file shortcut
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut create(
		long fileShortcutId) {
		return getPersistence().create(fileShortcutId);
	}

	/**
	* Removes the d l file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the d l file shortcut to remove
	* @return the d l file shortcut that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut remove(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().remove(fileShortcutId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlFileShortcut, merge);
	}

	/**
	* Finds the d l file shortcut with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileShortcutException} if it could not be found.
	*
	* @param fileShortcutId the primary key of the d l file shortcut to find
	* @return the d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByPrimaryKey(fileShortcutId);
	}

	/**
	* Finds the d l file shortcut with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileShortcutId the primary key of the d l file shortcut to find
	* @return the d l file shortcut, or <code>null</code> if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(fileShortcutId);
	}

	/**
	* Finds all the d l file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the d l file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the d l file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first d l file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last d l file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the d l file shortcuts before and after the current d l file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByUuid_PrevAndNext(
		long fileShortcutId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByUuid_PrevAndNext(fileShortcutId, uuid,
			orderByComparator);
	}

	/**
	* Finds the d l file shortcut where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileShortcutException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @return the matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Finds the d l file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @return the matching d l file shortcut, or <code>null</code> if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Finds the d l file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @return the matching d l file shortcut, or <code>null</code> if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Finds all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @return the matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId);
	}

	/**
	* Finds a range of all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId, start, end);
	}

	/**
	* Finds an ordered range of all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F(groupId, folderId, start, end, orderByComparator);
	}

	/**
	* Finds the first d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_First(groupId, folderId, orderByComparator);
	}

	/**
	* Finds the last d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_Last(groupId, folderId, orderByComparator);
	}

	/**
	* Finds the d l file shortcuts before and after the current d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_PrevAndNext(
		long fileShortcutId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_PrevAndNext(fileShortcutId, groupId, folderId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @return the matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F(groupId, folderId, start, end,
			orderByComparator);
	}

	/**
	* Finds all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @return the matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_S(groupId, folderId, status);
	}

	/**
	* Finds a range of all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_S(groupId, folderId, status, start, end);
	}

	/**
	* Finds an ordered range of all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_S(groupId, folderId, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_First(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_S_First(groupId, folderId, status,
			orderByComparator);
	}

	/**
	* Finds the last d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_Last(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_S_Last(groupId, folderId, status,
			orderByComparator);
	}

	/**
	* Finds the d l file shortcuts before and after the current d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_S_PrevAndNext(fileShortcutId, groupId, folderId,
			status, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @return the matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F_S(groupId, folderId, status);
	}

	/**
	* Filters by the user's permissions and finds a range of all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F_S(groupId, folderId, status, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F_S(groupId, folderId, status, start, end,
			orderByComparator);
	}

	/**
	* Finds all the d l file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry id to search with
	* @return the matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByTF(
		long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTF(toFileEntryId);
	}

	/**
	* Finds a range of all the d l file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry id to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByTF(
		long toFileEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTF(toFileEntryId, start, end);
	}

	/**
	* Finds an ordered range of all the d l file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry id to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByTF(
		long toFileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByTF(toFileEntryId, start, end, orderByComparator);
	}

	/**
	* Finds the first d l file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByTF_First(
		long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByTF_First(toFileEntryId, orderByComparator);
	}

	/**
	* Finds the last d l file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByTF_Last(
		long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByTF_Last(toFileEntryId, orderByComparator);
	}

	/**
	* Finds the d l file shortcuts before and after the current d l file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param toFileEntryId the to file entry id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByTF_PrevAndNext(
		long fileShortcutId, long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByTF_PrevAndNext(fileShortcutId, toFileEntryId,
			orderByComparator);
	}

	/**
	* Finds all the d l file shortcuts.
	*
	* @return the d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the d l file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the d l file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d l file shortcuts where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the d l file shortcut where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the d l file shortcuts where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F(groupId, folderId);
	}

	/**
	* Removes all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F_S(groupId, folderId, status);
	}

	/**
	* Removes all the d l file shortcuts where toFileEntryId = &#63; from the database.
	*
	* @param toFileEntryId the to file entry id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByTF(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTF(toFileEntryId);
	}

	/**
	* Removes all the d l file shortcuts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the d l file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the d l file shortcuts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Counts all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F(groupId, folderId);
	}

	/**
	* Filters by the user's permissions and counts all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @return the number of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F(groupId, folderId);
	}

	/**
	* Counts all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F_S(groupId, folderId, status);
	}

	/**
	* Filters by the user's permissions and counts all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param status the status to search with
	* @return the number of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F_S(groupId, folderId, status);
	}

	/**
	* Counts all the d l file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry id to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByTF(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTF(toFileEntryId);
	}

	/**
	* Counts all the d l file shortcuts.
	*
	* @return the number of d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLFileShortcutPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileShortcutPersistence)PortalBeanLocatorUtil.locate(DLFileShortcutPersistence.class.getName());

			ReferenceRegistry.registerReference(DLFileShortcutUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DLFileShortcutPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DLFileShortcutUtil.class,
			"_persistence");
	}

	private static DLFileShortcutPersistence _persistence;
}