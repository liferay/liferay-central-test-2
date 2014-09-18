/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;

/**
 * Provides the local service interface for DLAppHelper. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLAppHelperLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLAppHelperLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLAppHelperLocalServiceUtil} to access the d l app helper local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void cancelCheckOut(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion draftFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void checkAssetEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteFolder(
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteRepositoryFileEntries(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getFileAsStream(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		boolean incrementCounter);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status);

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcuts(long, long,
	boolean, int)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status);

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcutsCount(long,
	long, boolean, int)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileShortcutsCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getNoAssetFileEntries();

	public void moveDependentsToTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Moves the file entry to the recycle bin.
	*
	* @param userId the primary key of the user moving the file entry
	* @param fileEntry the file entry to be moved
	* @return the moved file entry
	* @throws PortalException if a user with the primary key could not be found
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutFromTrash(
		long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Moves the file shortcut to the recycle bin.
	*
	* @param userId the primary key of the user moving the file shortcut
	* @param dlFileShortcut the file shortcut to be moved
	* @return the moved file shortcut
	* @throws PortalException if a user with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutToTrash(
		long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder,
		long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Moves the folder to the recycle bin.
	*
	* @param userId the primary key of the user moving the folder
	* @param folder the folder to be moved
	* @return the moved folder
	* @throws PortalException if a user with the primary key could not be found
	*/
	public com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void restoreDependentsFromTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#restoreDependentsFromTrash(List)}
	*/
	@java.lang.Deprecated
	public void restoreDependentsFromTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void restoreFileEntryFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void restoreFileShortcutFromTrash(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void restoreFolderFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void updateFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void updateStatus(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion latestFileVersion,
		int oldStatus, int newStatus,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException;
}