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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * The interface for the d l app local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLAppLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLAppLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLAppLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLAppLocalServiceUtil} to access the d l app local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Adds a file entry and associated metadata. It is created based on a byte
	* array.
	*
	* @param userId the primary key of the creator/owner of the file entry
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file entry's parent folder
	* @param sourceFileName the file's original name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param bytes the file's data (optionally <code>null</code>)
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes. In a Liferay repository, it may include:
	<ul>
	<li>
	fileEntryTypeId - ID for a custom file entry type
	</li>
	<li>
	fieldsMap - mapping for fields associated with a custom file
	entry type
	</li>
	</ul>
	* @return the file entry
	* @throws PortalException if a parent folder could not be found or if the
	file entry's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a file entry and associated metadata. It is created based on a
	* {@link File} object.
	*
	* @param userId the primary key of the creator/owner of the file entry
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file entry's parent folder
	* @param sourceFileName the file's original name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param file the file's data (optionally <code>null</code>)
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes. In a Liferay repository, it may include:
	<ul>
	<li>
	fileEntryTypeId - ID for a custom file entry type
	</li>
	<li>
	fieldsMap - mapping for fields associated with a custom file
	entry type
	</li>
	</ul>
	* @return the file entry
	* @throws PortalException if a parent folder could not be found or if the
	file entry's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a file entry and associated metadata. It is created based on a
	* {@link InputStream} object.
	*
	* @param userId the primary key of the creator/owner of the file entry
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file entry's parent folder
	* @param sourceFileName the file's original name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param is the file's data (optionally <code>null</code>)
	* @param size the file's size (optionally <code>0</code>)
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes. In a Liferay repository, it may include:
	<ul>
	<li>
	fileEntryTypeId - ID for a custom file entry type
	</li>
	<li>
	fieldsMap - mapping for fields associated with a custom file
	entry type
	</li>
	</ul>
	* @return the file entry
	* @throws PortalException if a parent folder could not be found or if the
	file entry's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long userId, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a file rank to an existing file entry. This method is only supported
	* by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param companyId the primary key of the company
	* @param userId the primary key of the creator/owner of the file rank
	* @param fileEntryId the primary key of the file entry
	* @param serviceContext the file rank's service context
	* @return the file rank
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank addFileRank(
		long repositoryId, long companyId, long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a file shortcut to an existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param userId the primary key of the creator/owner of the file shortcut
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file shortcut's parent folder
	* @param toFileEntryId the primary key of the file entry to point to
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes.
	* @return the file shortcut
	* @throws PortalException if a parent folder or file entry could not be
	found or if the file shortcut's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, long repositoryId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a folder.
	*
	* @param userId the primary key of the creator/owner of the folder
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param name the folder's name
	* @param description the folder's description
	* @param serviceContext the folder's service context. In a Liferay
	repository, it may include:
	<ul>
	<li>
	mountPoint - boolean specifying whether folder is façade for
	mounting a third-party repository
	</li>
	</ul>
	* @return the folder
	* @throws PortalException if a parent folder is not found or if the new
	folder's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.Folder addFolder(
		long userId, long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Delete all data associated to the given repository. This method is only
	* supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @throws PortalException if the repository was not found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAll(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes a file entry.
	*
	* @param fileEntryId the primary key of the file entry
	* @throws PortalException if the file entry was not found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes file ranks associated to a given file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param fileEntryId the primary key of the file entry
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileRanksByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes file ranks associated to a given user. This method is only
	* supported by the Liferay repository.
	*
	* @param userId the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileRanksByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes a file shortcut. This method is only supported by the Liferay
	* repository.
	*
	* @param dlFileShortcut the file shortcut
	* @throws PortalException if the file shortcut was not found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes a file shortcut. This method is only supported by the Liferay
	* repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @throws PortalException if the file shortcut was not found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes all file shortcuts associated to a file entry. This method is
	* only supported by the Liferay repository.
	*
	* @param toFileEntryId the primary key of the associated file entry
	* @throws PortalException if the file shortcut was not found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes a folder and all of its subfolders and file entries.
	*
	* @param folderId the primary key of the folder
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves all file entries in a given folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @return the list of file entries
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of file entries in a given folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the list of file entries
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of file entries in a given folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the results by (optionally
	<code>null</code>)
	* @return the list of file entries
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of file entries and shortcuts in a given folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param status the workflow status
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the list of file entries and shortcuts
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a count of total file entries and shortcuts in a given folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param status the workflow status
	* @return the count of total file entries and shortcuts
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a count of total file entries in a given folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @return the count of total file entries
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a file entry.
	*
	* @param fileEntryId the primary key of the file entry
	* @return the file entry
	* @throws PortalException if the file entry was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a file entry.
	*
	* @param groupId the primary key of the group
	* @param folderId the primary key of the folder
	* @param title the title of the file entry
	* @return the file entry
	* @throws PortalException if the file entry was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a file entry.
	*
	* @param uuid the file entry's universally unique identifier
	* @param groupId the primary key of the group
	* @return the file entry
	* @throws PortalException if the file entry was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieve file ranks. This method is only supported by the Liferay
	* repository.
	*
	* @param repositoryId the primary key of the repository
	* @param userId the primary key of the user
	* @return list of file ranks
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long repositoryId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieve a file shortcut. This method is only supported by the Liferay
	* repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @return the file shortcut
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieve a file version.
	*
	* @param fileVersionId the primary key of the file version
	* @return the file version
	* @throws PortalException if the file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.FileVersion getFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieve a folder.
	*
	* @param folderId the primary key of the folder
	* @return the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.Folder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieve a folder.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder of the folder
	* @param name the name of the folder
	* @return the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.Folder getFolder(
		long repositoryId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieve all immediate subfolders of a given folder.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @return the list of immediate subfolders
	* @throws PortalException if the folder could not be parent found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieve all immediate subfolders of a given folder.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @return the list of immediate subfolders
	* @throws PortalException if the folder could not be parent found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of immediate subfolders of a given folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the list of immediate subfolders
	* @throws PortalException if the folder could not be parent found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of immediate subfolders of a given folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the results by (optionally
	<code>null</code>)
	* @return the list of immediate subfolders
	* @throws PortalException if the folder could not be parent found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of immediate subfolders of a given folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the list of immediate subfolders
	* @throws PortalException if the folder could not be parent found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of immediate subfolders of a given folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the folders by (optionally
	<code>null</code>)
	* @return the list of immediate subfolders
	* @throws PortalException if the folder could not be parent found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of immediate subfolders, file entries, and file
	* shortcuts of a given folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the parent folder
	* @param status the workflow status
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the results by (optionally
	<code>null</code>)
	* @return the list of immediate subfolders
	* @throws PortalException if the folder could not be parent found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a count of total immediate subfolders, file entries, and
	* file shortcuts in a given folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the parent folder
	* @param status the workflow status
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @return the count of immediate subfolders, file entries, and file
	shortcuts
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a count of total immediate subfolders in a given folder.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @return the count of immediate subfolders
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a count of total immediate subfolders in a given folder.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @return the count of immediate subfolders
	* @throws PortalException if the folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long repositoryId, long parentFolderId,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a count of total immediate subfolders and file entries across
	* several folders.
	*
	* @param repositoryId the primary key of the repository
	* @param folderIds a list of primary keys for parent folders to search
	* @param status the workflow status
	* @return the count of immediate subfolders and file entries
	* @throws PortalException if the repository was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersFileEntriesCount(long repositoryId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a mount folder. This method is only supported by the Liferay
	* repository.
	*
	* @param repositoryId the primary key of the repository
	* @return the folder used for mounting third-party repositories
	* @throws PortalException if the repository or folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.Folder getMountFolder(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves all immediate subfolders used for mounting third-party
	* repositories. This method is only supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @return the list of folders used for mounting third-party repositories
	* @throws PortalException if the repository or parent folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of immediate subfolders used for mounting third-party
	* repositories. This method is only supported by the Liferay repository.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the list of folders used for mounting third-party repositories
	* @throws PortalException if the repository or parent folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a subset of immediate subfolders used for mounting third-party
	* repositories. This method is only supported by the Liferay repository.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the results by (optionally
	<code>null</code>)
	* @return the list of folders used for mounting third-party repositories
	* @throws PortalException if the repository or parent folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		long repositoryId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retrieves a count of immediate subfolders used for mounting third-party
	* repositories. This method is only supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @return the count of folders used for mounting third-party repositories
	* @throws PortalException if the repository or parent folder was not found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMountFoldersCount(long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves a file entry to a new folder.
	*
	* @param userId the primary key of the user
	* @param fileEntryId the primary key of the file entry
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the file entry's service context
	* @return the file entry
	* @throws PortalException if the file entry or the new folder was not found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntry(
		long userId, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the asset categories, tags, and links.
	*
	* @param userId the primary key of the user
	* @param fileEntry the file entry
	* @param fileVersion the file version
	* @param assetCategoryIds the primary keys of the new asset categories
	* @param assetTagNames the new asset tag names
	* @param assetLinkEntryIds the primary keys of the new asset link entries
	* @throws PortalException the file entry or version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a file entry and associated metadata. It is updated based on a
	* byte array object.
	*
	* @param userId the primary key of the user
	* @param fileEntryId the primary key of the file entry
	* @param sourceFileName the file's original name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param majorVersion whether the new file version is a major version
	* @param bytes the file's data (optionally <code>null</code>)
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes. In a Liferay repository, it may include:
	<ul>
	<li>
	fileEntryTypeId - ID for a custom file entry type
	</li>
	<li>
	fieldsMap - mapping for fields associated with a custom file
	entry type
	</li>
	</ul>
	* @return the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a file entry and associated metadata. It is updated based on a
	* {@link File} object.
	*
	* @param userId the primary key of the user
	* @param fileEntryId the primary key of the file entry
	* @param sourceFileName the file's original name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param majorVersion whether the new file version is a major version
	* @param file the file's data (optionally <code>null</code>)
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes. In a Liferay repository, it may include:
	<ul>
	<li>
	fileEntryTypeId - ID for a custom file entry type
	</li>
	<li>
	fieldsMap - mapping for fields associated with a custom file
	entry type
	</li>
	</ul>
	* @return the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a file entry and associated metadata. It is updated based on a
	* {@link InputStream} object.
	*
	* @param userId the primary key of the user
	* @param fileEntryId the primary key of the file entry
	* @param sourceFileName the file's original name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param majorVersion whether the new file version is a major version
	* @param is the file's data (optionally <code>null</code>)
	* @param size the file's size (optionally <code>0</code>)
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes. In a Liferay repository, it may include:
	<ul>
	<li>
	fileEntryTypeId - ID for a custom file entry type
	</li>
	<li>
	fieldsMap - mapping for fields associated with a custom file
	entry type
	</li>
	</ul>
	* @return the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a file rank to an existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param companyId the primary key of the company
	* @param userId the primary key of the creator/owner of the file rank
	* @param fileEntryId the primary key of the file entry
	* @param serviceContext the file rank's service context
	* @return the file rank
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank updateFileRank(
		long repositoryId, long companyId, long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a file shortcut to an existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param userId the primary key of the creator/owner of the file shortcut
	* @param repositoryId the primary key of the repository
	* @param fileShortcutId the primary key of the file shortcut
	* @param folderId the primary key of the file shortcut's parent folder
	* @param toFileEntryId the primary key of the file entry to point to
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes.
	* @return the file shortcut
	* @throws PortalException if the file shortcut, folder, or file entry could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates all file shortcut to an existing file entry to a new file entry.
	* This method is only supported by the Liferay repository.
	*
	* @param toRepositoryId the primary key of the repository
	* @param oldToFileEntryId the primary key of the old file entry pointed to
	* @param newToFileEntryId the primary key of the new file entry to point
	to
	* @param serviceContext the file entry's service context. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes.
	* @return the file shortcut
	* @throws SystemException if a system exception occurred
	*/
	public void updateFileShortcuts(long toRepositoryId, long oldToFileEntryId,
		long newToFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a folder.
	*
	* @param folderId the primary key of the folder
	* @param parentFolderId the primary key of the new parent folder
	* @param name the folder's new name
	* @param description the folder's description
	* @param serviceContext the folder's service context. In a Liferay
	repository, it may include:
	<ul>
	<li>
	defaultFileEntryTypeId - the file entry type to default all
	Liferay file entries to
	</li>
	<li>
	fileEntryTypeSearchContainerPrimaryKeys - a comma-delimited list
	of file entry type primary keys allowed in the given folder and
	all descendants
	</li>
	<li>
	mountPoint - boolean specifying whether folder is façade for
	mounting a third-party repository
	</li>
	<li>
	overrideFileEntryTypes - boolean specifying whether to override
	ancestral folder's restriction of file entry types allowed
	</li>
	</ul>
	* @return the folder
	* @throws PortalException if the current or parent folder is not found or
	if the new folder's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.Folder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}