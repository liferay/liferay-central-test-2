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

package com.liferay.portlet.bookmarks.service;

/**
 * <p>
 * This class is a wrapper for {@link BookmarksEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksEntryLocalService
 * @generated
 */
public class BookmarksEntryLocalServiceWrapper
	implements BookmarksEntryLocalService {
	public BookmarksEntryLocalServiceWrapper(
		BookmarksEntryLocalService bookmarksEntryLocalService) {
		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	/**
	* Adds the bookmarks entry to the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksEntry the bookmarks entry to add
	* @return the bookmarks entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry addBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.addBookmarksEntry(bookmarksEntry);
	}

	/**
	* Creates a new bookmarks entry with the primary key. Does not add the bookmarks entry to the database.
	*
	* @param entryId the primary key for the new bookmarks entry
	* @return the new bookmarks entry
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry createBookmarksEntry(
		long entryId) {
		return _bookmarksEntryLocalService.createBookmarksEntry(entryId);
	}

	/**
	* Deletes the bookmarks entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the bookmarks entry to delete
	* @throws PortalException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBookmarksEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.deleteBookmarksEntry(entryId);
	}

	/**
	* Deletes the bookmarks entry from the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksEntry the bookmarks entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.deleteBookmarksEntry(bookmarksEntry);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the bookmarks entry with the primary key.
	*
	* @param entryId the primary key of the bookmarks entry to get
	* @return the bookmarks entry
	* @throws PortalException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry getBookmarksEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getBookmarksEntry(entryId);
	}

	/**
	* Gets the bookmarks entry with the UUID and group id.
	*
	* @param uuid the UUID of bookmarks entry to get
	* @param groupId the group id of the bookmarks entry to get
	* @return the bookmarks entry
	* @throws PortalException if a bookmarks entry with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry getBookmarksEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getBookmarksEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Gets a range of all the bookmarks entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getBookmarksEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getBookmarksEntries(start, end);
	}

	/**
	* Gets the number of bookmarks entries.
	*
	* @return the number of bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int getBookmarksEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getBookmarksEntriesCount();
	}

	/**
	* Updates the bookmarks entry in the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksEntry the bookmarks entry to update
	* @return the bookmarks entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry);
	}

	/**
	* Updates the bookmarks entry in the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksEntry the bookmarks entry to update
	* @param merge whether to merge the bookmarks entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the bookmarks entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateBookmarksEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry,
			merge);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.addEntry(userId, groupId, folderId,
			name, url, description, serviceContext);
	}

	public void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.addEntryResources(entry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.addEntryResources(entry,
			communityPermissions, guestPermissions);
	}

	public void addEntryResources(long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.addEntryResources(entryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.addEntryResources(entryId,
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.deleteEntries(groupId, folderId);
	}

	public void deleteEntry(
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.deleteEntry(entry);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.deleteEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getEntries(groupId, folderId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getEntries(groupId, folderId, start,
			end, orderByComparator);
	}

	public int getEntriesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getEntriesCount(groupId, folderId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getEntry(entryId);
	}

	public int getFoldersEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getFoldersEntriesCount(groupId,
			folderIds);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getGroupEntries(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getGroupEntries(groupId, userId,
			start, end);
	}

	public int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getGroupEntriesCount(groupId);
	}

	public int getGroupEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getGroupEntriesCount(groupId, userId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> getNoAssetEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.getNoAssetEntries();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.openEntry(userId, entryId);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.bookmarks.model.BookmarksEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntryLocalService.updateAsset(userId, entry,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		long userId, long entryId, long groupId, long folderId,
		java.lang.String name, java.lang.String url,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntryLocalService.updateEntry(userId, entryId,
			groupId, folderId, name, url, description, serviceContext);
	}

	public BookmarksEntryLocalService getWrappedBookmarksEntryLocalService() {
		return _bookmarksEntryLocalService;
	}

	public void setWrappedBookmarksEntryLocalService(
		BookmarksEntryLocalService bookmarksEntryLocalService) {
		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	private BookmarksEntryLocalService _bookmarksEntryLocalService;
}