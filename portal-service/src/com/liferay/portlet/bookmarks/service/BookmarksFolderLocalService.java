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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * The interface for the bookmarks folder local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderLocalServiceUtil
 * @see com.liferay.portlet.bookmarks.service.base.BookmarksFolderLocalServiceBaseImpl
 * @see com.liferay.portlet.bookmarks.service.impl.BookmarksFolderLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BookmarksFolderLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookmarksFolderLocalServiceUtil} to access the bookmarks folder local service. Add custom service methods to {@link com.liferay.portlet.bookmarks.service.impl.BookmarksFolderLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the bookmarks folder to the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder to add
	* @return the bookmarks folder that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksFolder addBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new bookmarks folder with the primary key. Does not add the bookmarks folder to the database.
	*
	* @param folderId the primary key for the new bookmarks folder
	* @return the new bookmarks folder
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksFolder createBookmarksFolder(
		long folderId);

	/**
	* Deletes the bookmarks folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the bookmarks folder to delete
	* @throws PortalException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBookmarksFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the bookmarks folder from the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.kernel.exception.SystemException;

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
		throws com.liferay.portal.kernel.exception.SystemException;

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
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the bookmarks folder with the primary key.
	*
	* @param folderId the primary key of the bookmarks folder to get
	* @return the bookmarks folder
	* @throws PortalException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getBookmarksFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the bookmarks folder with the UUID and group id.
	*
	* @param uuid the UUID of bookmarks folder to get
	* @param groupId the group id of the bookmarks folder to get
	* @return the bookmarks folder
	* @throws PortalException if a bookmarks folder with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getBookmarksFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the bookmarks folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks folders to return
	* @param end the upper bound of the range of bookmarks folders to return (not inclusive)
	* @return the range of bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getBookmarksFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of bookmarks folders.
	*
	* @return the number of bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBookmarksFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the bookmarks folder in the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder to update
	* @return the bookmarks folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the bookmarks folder in the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder to update
	* @param merge whether to merge the bookmarks folder with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the bookmarks folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		long userId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFolderResources(long folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFolderResources(long folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}