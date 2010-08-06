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

package com.liferay.portlet.documentlibrary.service;

/**
 * <p>
 * This class is a wrapper for {@link DLFolderLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFolderLocalService
 * @generated
 */
public class DLFolderLocalServiceWrapper implements DLFolderLocalService {
	public DLFolderLocalServiceWrapper(
		DLFolderLocalService dlFolderLocalService) {
		_dlFolderLocalService = dlFolderLocalService;
	}

	/**
	* Adds the d l folder to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the d l folder to add
	* @return the d l folder that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder addDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.addDLFolder(dlFolder);
	}

	/**
	* Creates a new d l folder with the primary key. Does not add the d l folder to the database.
	*
	* @param folderId the primary key for the new d l folder
	* @return the new d l folder
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder createDLFolder(
		long folderId) {
		return _dlFolderLocalService.createDLFolder(folderId);
	}

	/**
	* Deletes the d l folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the d l folder to delete
	* @throws PortalException if a d l folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteDLFolder(folderId);
	}

	/**
	* Deletes the d l folder from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the d l folder to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteDLFolder(dlFolder);
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
		return _dlFolderLocalService.dynamicQuery(dynamicQuery);
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
		return _dlFolderLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _dlFolderLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _dlFolderLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d l folder with the primary key.
	*
	* @param folderId the primary key of the d l folder to get
	* @return the d l folder
	* @throws PortalException if a d l folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder getDLFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFolder(folderId);
	}

	/**
	* Gets the d l folder with the UUID and group id.
	*
	* @param uuid the UUID of d l folder to get
	* @param groupId the group id of the d l folder to get
	* @return the d l folder
	* @throws PortalException if a d l folder with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder getDLFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFolderByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the d l folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l folders to return
	* @param end the upper bound of the range of d l folders to return (not inclusive)
	* @return the range of d l folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFolders(start, end);
	}

	/**
	* Gets the number of d l folders.
	*
	* @return the number of d l folders
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFoldersCount();
	}

	/**
	* Updates the d l folder in the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the d l folder to update
	* @return the d l folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder updateDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.updateDLFolder(dlFolder);
	}

	/**
	* Updates the d l folder in the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the d l folder to update
	* @param merge whether to merge the d l folder with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d l folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder updateDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.updateDLFolder(dlFolder, merge);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long userId, long groupId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.addFolder(userId, groupId, parentFolderId,
			name, description, serviceContext);
	}

	public void addFolderResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.addFolderResources(folder,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.addFolderResources(folder, communityPermissions,
			guestPermissions);
	}

	public void addFolderResources(long folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.addFolderResources(folderId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(long folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.addFolderResources(folderId,
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteFolder(folder);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteFolder(folderId);
	}

	public void deleteFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getCompanyFolders(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getCompanyFoldersCount(companyId);
	}

	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, java.util.List<java.lang.Long> folderIds, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFileEntriesAndFileShortcuts(groupId,
			folderIds, status, start, end);
	}

	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFileEntriesAndFileShortcuts(groupId,
			folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFileEntriesAndFileShortcutsCount(groupId,
			folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolder(folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolder(groupId, parentFolderId, name);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolders(companyId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolders(groupId, parentFolderId, start,
			end);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, java.util.List<java.lang.Long> folderIds, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderIds, status, start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersCount(groupId, parentFolderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.updateFolder(folderId, parentFolderId,
			name, description, serviceContext);
	}

	public DLFolderLocalService getWrappedDLFolderLocalService() {
		return _dlFolderLocalService;
	}

	private DLFolderLocalService _dlFolderLocalService;
}