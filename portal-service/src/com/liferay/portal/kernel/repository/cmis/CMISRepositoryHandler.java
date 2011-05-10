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

package com.liferay.portal.kernel.repository.cmis;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.BaseRepositoryImpl;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public abstract class CMISRepositoryHandler extends BaseRepositoryImpl {

	public FileEntry addFileEntry(
			long folderId, String title, String description, String changeLog,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _cmisRepository.addFileEntry(
			folderId, title, description, changeLog, is, size, serviceContext);
	}

	public Folder addFolder(
			long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _cmisRepository.addFolder(
			parentFolderId, title, description, serviceContext);
	}

	public void copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		_cmisRepository.copyFileEntry(
			groupId, fileEntryId, destFolderId, serviceContext);
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		_cmisRepository.deleteFileEntry(fileEntryId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		_cmisRepository.deleteFolder(folderId);
	}

	public BaseRepository getCmisRepository() {
		return _cmisRepository;
	}

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return _cmisRepository.getFileEntries(folderId, start, end, obc);
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		return _cmisRepository.getFileEntriesCount(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return _cmisRepository.getFileEntry(fileEntryId);
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		return _cmisRepository.getFileEntry(folderId, title);
	}

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		return _cmisRepository.getFileEntryByUuid(uuid);
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		return _cmisRepository.getFileVersion(fileVersionId);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		return _cmisRepository.getFolder(folderId);
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		return _cmisRepository.getFolder(parentFolderId, title);
	}

	public List<Folder> getFolders(
			long parentFolderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return _cmisRepository.getFolders(parentFolderId, start, end, obc);
	}

	public List<Object> getFoldersAndFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return _cmisRepository.getFoldersAndFileEntries(
			folderId, start, end, obc);
	}

	public int getFoldersAndFileEntriesCount(long folderId)
		throws SystemException {

		return _cmisRepository.getFoldersAndFileEntriesCount(folderId);
	}

	public int getFoldersCount(long parentFolderId) throws SystemException {
		return _cmisRepository.getFoldersCount(parentFolderId);
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return _cmisRepository.getFoldersFileEntriesCount(folderIds, status);
	}

	public String getLatestVersionId(String objectId)
		throws PortalException, SystemException {

		return _cmisRepository.getLatestVersionId(objectId);
	}

	public String getLogin() throws RepositoryException {
		String login = PrincipalThreadLocal.getName();

		try {
			String authType =
				companyLocalService.getCompany(getCompanyId()).getAuthType();

			if (!authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				User user = userLocalService.getUser(GetterUtil.getLong(login));

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					login = user.getEmailAddress();
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					login = user.getScreenName();
				}
			}
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}

		return login;
	}

	public String getObjectName(String objectId)
		throws PortalException, SystemException {

		return _cmisRepository.getObjectName(objectId);
	}

	public List<String> getObjectPaths(String objectId)
		throws PortalException, SystemException {

		return _cmisRepository.getObjectPaths(objectId);
	}

	public abstract Session getSession()
		throws PortalException, RepositoryException;

	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws SystemException {

		return _cmisRepository.getSubfolderIds(folderId, recurse);
	}

	public void initRepository() throws PortalException, SystemException {
		_cmisRepository.initRepository();
	}

	public boolean isCancelCheckOutAllowable(String objectId)
		throws PortalException, SystemException {

		return _cmisRepository.isCancelCheckOutAllowable(objectId);
	}

	public boolean isCheckInAllowable(String objectId)
		throws PortalException, SystemException {

		return _cmisRepository.isCheckInAllowable(objectId);
	}

	public boolean isCheckOutAllowable(String objectId)
		throws PortalException, SystemException {

		return _cmisRepository.isCheckOutAllowable(objectId);
	}

	public boolean isDocumentRetrievableByVersionSeriesId() {
		return true;
	}

	public boolean isRefreshBeforePermissionCheck() {
		return false;
	}

	public void lockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		_cmisRepository.lockFileEntry(fileEntryId);
	}

	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		return _cmisRepository.lockFileEntry(
			fileEntryId, owner, expirationTime);
	}

	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		return _cmisRepository.lockFolder(folderId);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		return _cmisRepository.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _cmisRepository.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	public Folder moveFolder(
			long folderId, long newParentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _cmisRepository.moveFolder(
			folderId, newParentFolderId, serviceContext);
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return _cmisRepository.refreshFileEntryLock(lockUuid, expirationTime);
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return _cmisRepository.refreshFolderLock(lockUuid, expirationTime);
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		_cmisRepository.revertFileEntry(fileEntryId, version, serviceContext);
	}

	public void setCmisRepository(AbstractCmisRepository cmisRepository) {
		_cmisRepository = cmisRepository;
	}

	public FileEntry toFileEntry(String objectId)
		throws PortalException, SystemException {

		return _cmisRepository.toFileEntry(objectId);
	}

	public Folder toFolder(String objectId)
		throws PortalException, SystemException {

		return _cmisRepository.toFolder(objectId);
	}

	public void unlockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		_cmisRepository.unlockFileEntry(fileEntryId);
	}

	public void unlockFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		_cmisRepository.unlockFileEntry(fileEntryId, lockUuid);
	}

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException, SystemException {

		_cmisRepository.unlockFolder(folderId, lockUuid);
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _cmisRepository.updateFileEntry(
			fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, is, size, serviceContext);
	}

	public FileEntry updateFileEntry(
			String objectId, Map<String, Object> properties, InputStream is,
			String sourceFileName, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _cmisRepository.updateFileEntry(
			objectId, properties, is, sourceFileName, size, serviceContext);
	}

	public Folder updateFolder(
			long folderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _cmisRepository.updateFolder(
			folderId, title, description, serviceContext);
	}

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return _cmisRepository.verifyFileEntryLock(fileEntryId, lockUuid);
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		return _cmisRepository.verifyInheritableLock(folderId, lockUuid);
	}

	private AbstractCmisRepository _cmisRepository;

}