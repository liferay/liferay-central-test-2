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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

/**
 * The persistence interface for the d l file shortcut service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutPersistenceImpl
 * @see DLFileShortcutUtil
 * @generated
 */
public interface DLFileShortcutPersistence extends BasePersistence<DLFileShortcut> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileShortcutUtil} to access the d l file shortcut persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d l file shortcut in the entity cache if it is enabled.
	*
	* @param dlFileShortcut the d l file shortcut to cache
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut);

	/**
	* Caches the d l file shortcuts in the entity cache if it is enabled.
	*
	* @param dlFileShortcuts the d l file shortcuts to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> dlFileShortcuts);

	/**
	* Creates a new d l file shortcut with the primary key. Does not add the d l file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new d l file shortcut
	* @return the new d l file shortcut
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut create(
		long fileShortcutId);

	/**
	* Removes the d l file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the d l file shortcut to remove
	* @return the d l file shortcut that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut remove(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d l file shortcut with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileShortcutException} if it could not be found.
	*
	* @param fileShortcutId the primary key of the d l file shortcut to find
	* @return the d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the d l file shortcut with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileShortcutId the primary key of the d l file shortcut to find
	* @return the d l file shortcut, or <code>null</code> if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d l file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the last d l file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the d l file shortcuts before and after the current d l file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByUuid_PrevAndNext(
		long fileShortcutId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the d l file shortcut where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileShortcutException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the d l file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching d l file shortcut, or <code>null</code> if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d l file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching d l file shortcut, or <code>null</code> if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d l file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID to search with
	* @return the matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByToFileEntryId(
		long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry ID to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByToFileEntryId(
		long toFileEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry ID to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByToFileEntryId(
		long toFileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByToFileEntryId_First(
		long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the last d l file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByToFileEntryId_Last(
		long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the d l file shortcuts before and after the current d l file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param toFileEntryId the to file entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByToFileEntryId_PrevAndNext(
		long fileShortcutId, long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @return the matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the last d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the d l file shortcuts before and after the current d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_PrevAndNext(
		long fileShortcutId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Filters by the user's permissions and finds all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @return the matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters the d l file shortcuts before and after the current d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] filterFindByG_F_PrevAndNext(
		long fileShortcutId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @return the matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_First(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the last d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching d l file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_Last(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds the d l file shortcuts before and after the current d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Filters by the user's permissions and finds all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @return the matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters the d l file shortcuts before and after the current d l file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current d l file shortcut
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] filterFindByG_F_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Finds all the d l file shortcuts.
	*
	* @return the d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file shortcuts where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d l file shortcut where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Removes all the d l file shortcuts where toFileEntryId = &#63; from the database.
	*
	* @param toFileEntryId the to file entry ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByToFileEntryId(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file shortcuts where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file shortcuts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file shortcuts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByToFileEntryId(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the d l file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @return the number of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @return the number of matching d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the d l file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param folderId the folder ID to search with
	* @param status the status to search with
	* @return the number of matching d l file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file shortcuts.
	*
	* @return the number of d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DLFileShortcut remove(DLFileShortcut dlFileShortcut)
		throws SystemException;
}