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
 * This class is a wrapper for {@link DLFileEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryLocalService
 * @generated
 */
public class DLFileEntryLocalServiceWrapper implements DLFileEntryLocalService {
	public DLFileEntryLocalServiceWrapper(
		DLFileEntryLocalService dlFileEntryLocalService) {
		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	/**
	* Adds the d l file entry to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the d l file entry to add
	* @return the d l file entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry addDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.addDLFileEntry(dlFileEntry);
	}

	/**
	* Creates a new d l file entry with the primary key. Does not add the d l file entry to the database.
	*
	* @param fileEntryId the primary key for the new d l file entry
	* @return the new d l file entry
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry createDLFileEntry(
		long fileEntryId) {
		return _dlFileEntryLocalService.createDLFileEntry(fileEntryId);
	}

	/**
	* Deletes the d l file entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryId the primary key of the d l file entry to delete
	* @throws PortalException if a d l file entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteDLFileEntry(fileEntryId);
	}

	/**
	* Deletes the d l file entry from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the d l file entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteDLFileEntry(dlFileEntry);
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
		return _dlFileEntryLocalService.dynamicQuery(dynamicQuery);
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
		return _dlFileEntryLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _dlFileEntryLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _dlFileEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d l file entry with the primary key.
	*
	* @param fileEntryId the primary key of the d l file entry to get
	* @return the d l file entry
	* @throws PortalException if a d l file entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getDLFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getDLFileEntry(fileEntryId);
	}

	/**
	* Gets the d l file entry with the UUID and group id.
	*
	* @param uuid the UUID of d l file entry to get
	* @param groupId the group id of the d l file entry to get
	* @return the d l file entry
	* @throws PortalException if a d l file entry with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getDLFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getDLFileEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Gets a range of all the d l file entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file entries to return
	* @param end the upper bound of the range of d l file entries to return (not inclusive)
	* @return the range of d l file entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getDLFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getDLFileEntries(start, end);
	}

	/**
	* Gets the number of d l file entries.
	*
	* @return the number of d l file entries
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFileEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getDLFileEntriesCount();
	}

	/**
	* Updates the d l file entry in the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the d l file entry to update
	* @return the d l file entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);
	}

	/**
	* Updates the d l file entry in the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the d l file entry to update
	* @param merge whether to merge the d l file entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d l file entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry, merge);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.addFileEntry(userId, groupId, folderId,
			name, title, description, changeLog, extraSettings, bytes,
			serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.addFileEntry(userId, groupId, folderId,
			name, title, description, changeLog, extraSettings, file,
			serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.lang.String extraSettings,
		java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.addFileEntry(userId, groupId, folderId,
			name, title, description, changeLog, extraSettings, is, size,
			serviceContext);
	}

	public void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.addFileEntryResources(fileEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.addFileEntryResources(fileEntry,
			communityPermissions, guestPermissions);
	}

	public void addFileEntryResources(long fileEntryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.addFileEntryResources(fileEntryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(long fileEntryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.addFileEntryResources(fileEntryId,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addOrOverwriteFileEntry(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String sourceName, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.lang.String extraSettings, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.addOrOverwriteFileEntry(userId,
			groupId, folderId, name, sourceName, title, description, changeLog,
			extraSettings, file, serviceContext);
	}

	public void deleteFileEntries(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteFileEntries(groupId, folderId);
	}

	public void deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteFileEntry(fileEntry);
	}

	public void deleteFileEntry(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteFileEntry(groupId, folderId, name);
	}

	public void deleteFileEntry(long groupId, long folderId,
		java.lang.String name, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteFileEntry(groupId, folderId, name,
			version);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getCompanyFileEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getCompanyFileEntries(companyId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getCompanyFileEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getCompanyFileEntries(companyId, start,
			end, obc);
	}

	public int getCompanyFileEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getCompanyFileEntriesCount(companyId);
	}

	public java.io.InputStream getFileAsStream(long companyId, long userId,
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileAsStream(companyId, userId,
			groupId, folderId, name);
	}

	public java.io.InputStream getFileAsStream(long companyId, long userId,
		long groupId, long folderId, java.lang.String name,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileAsStream(companyId, userId,
			groupId, folderId, name, version);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntries(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntries(groupId, folderId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntries(groupId, folderId,
			start, end, obc);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntriesCount(groupId, folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntry(fileEntryId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntry(groupId, folderId, name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntryByTitle(groupId, folderId,
			title);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntryByUuidAndGroupId(uuid,
			groupId);
	}

	public int getFoldersFileEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFoldersFileEntriesCount(groupId,
			folderIds, status);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntries(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntries(groupId, start,
			end, obc);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntries(groupId, userId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntries(groupId, userId,
			start, end, obc);
	}

	public int getGroupFileEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntriesCount(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntriesCount(groupId, userId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getNoAssetFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getNoAssetFileEntries();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry moveFileEntry(
		long userId, long groupId, long folderId, long newFolderId,
		java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.moveFileEntry(userId, groupId,
			folderId, newFolderId, name, serviceContext);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		com.liferay.portlet.documentlibrary.model.DLFileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.updateAsset(userId, fileEntry, fileVersion,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateFileEntry(userId, groupId,
			folderId, name, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, bytes, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.lang.String extraSettings,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateFileEntry(userId, groupId,
			folderId, name, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, file, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.lang.String extraSettings,
		java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateFileEntry(userId, groupId,
			folderId, name, sourceFileName, title, description, changeLog,
			majorVersion, extraSettings, is, size, serviceContext);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateStatus(
		long userId, long fileEntryId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateStatus(userId, fileEntryId,
			status, serviceContext);
	}

	public DLFileEntryLocalService getWrappedDLFileEntryLocalService() {
		return _dlFileEntryLocalService;
	}

	private DLFileEntryLocalService _dlFileEntryLocalService;
}