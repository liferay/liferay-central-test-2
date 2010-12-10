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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.InvalidLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLRepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLRepositoryServiceImpl extends DLRepositoryServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String title, String description,
			String changeLog, String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlRepositoryLocalService.addFileEntry(
			getUserId(), groupId, folderId, title, description, changeLog,
			extraSettings, is, size, serviceContext);
	}

	public DLFolder addFolder(
			long groupId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlRepositoryLocalService.addFolder(
			getUserId(), groupId, parentFolderId, name, description,
			serviceContext);
	}

	public DLFolder copyFolder(
			long groupId, long sourceFolderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		DLFolder srcFolder = getFolder(sourceFolderId);

		DLFolder destFolder = addFolder(
			groupId, parentFolderId, name, description, serviceContext);

		copyFolder(srcFolder, destFolder, serviceContext);

		return destFolder;
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.DELETE);

		boolean hasLock = hasFileEntryLock(fileEntryId);

		if (!hasLock) {

			// Lock

			lockFileEntry(fileEntryId);
		}

		try {
			dlRepositoryLocalService.deleteFileEntry(fileEntryId);
		}
		finally {

			// Unlock

			unlockFileEntry(fileEntryId);
		}
	}

	public void deleteFileEntryByTitle(
			long groupId, long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = getFileEntryByTitle(
			groupId, folderId, titleWithExtension);

		deleteFileEntry(fileEntry.getFileEntryId());
	}

	public void deleteFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		DLFolder folder = dlRepositoryLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(folderId);
		}

		try {
			dlRepositoryLocalService.deleteFolder(folderId);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(folder.getGroupId(), folderId, lock.getUuid());
			}
		}
	}

	public void deleteFolder(long groupId, long parentFolderId, String name)
		throws PortalException, RemoteException, SystemException {

		DLFolder folder = getFolder(groupId, parentFolderId, name);

		deleteFolder(folder.getFolderId());
	}

	public InputStream getFileAsStream(long fileEntryId, String version)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlRepositoryLocalService.getFileAsStream(
			getUserId(), fileEntryId, version);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		return dlFileEntryPersistence.filterFindByG_F(
			groupId, folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.filterFindFE_FS_ByG_F_S(
			groupId, folderIds, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderFinder.filterCountFE_FS_ByG_F_S(
			groupId, folderIds, status);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		return dlFileEntryPersistence.filterCountByG_F(groupId, folderId);
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlRepositoryLocalService.getFileEntry(fileEntryId);
	}

	public DLFileEntry getFileEntryByTitle(
			long groupId, long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlRepositoryLocalService.getFileEntryByTitle(
			groupId, folderId, titleWithExtension);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.VIEW);

		return fileEntry;
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByUUID_G(
			uuid, groupId);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.VIEW);

		return fileEntry;
	}

	public Lock getFileEntryLock(long fileEntryId) {
		try {
			return lockLocalService.getLock(
				DLFileEntry.class.getName(), fileEntryId);
		}
		catch (Exception e) {
			return null;
		}
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder folder = dlRepositoryLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder folder = dlRepositoryLocalService.getFolder(
			groupId, parentFolderId, name);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return dlFolderPersistence.filterFindByG_P(
			groupId, parentFolderId, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_S(
			groupId, folderIds, status, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderIds, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_S(
			groupId, folderIds, status);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderIds, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.filterCountByG_P(groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return dlFileEntryFinder.filterCountByG_F_S(
				groupId, folderIds, status);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int filesCount = dlFileEntryFinder.filterCountByG_F_S(
				groupId, folderIds.subList(start, end), status);

			folderIds.subList(start, end).clear();

			filesCount += getFoldersFileEntriesCount(
				groupId, folderIds, status);

			return filesCount;
		}
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		long[] folderIds = getFolderIds(groupId, rootFolderId);

		if (folderIds.length == 0) {
			return Collections.EMPTY_LIST;
		}
		else if (userId <= 0) {
			return dlFileEntryPersistence.filterFindByG_F(
				groupId, folderIds, start, end, obc);
		}
		else {
			return dlFileEntryPersistence.filterFindByG_U_F(
				groupId, userId, folderIds, start, end, obc);
		}
	}

	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws SystemException {

		long[] folderIds = getFolderIds(groupId, rootFolderId);

		if (folderIds.length == 0) {
			return 0;
		}
		else if (userId <= 0) {
			return dlFileEntryPersistence.filterCountByG_F(groupId, folderIds);
		}
		else {
			return dlFileEntryPersistence.filterCountByG_U_F(
				groupId, userId, folderIds);
		}
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId, boolean recurse)
		throws SystemException {

		List<DLFolder> folders = dlFolderPersistence.filterFindByG_P(
			groupId, folderId);

		for (DLFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId(), recurse);
		}
	}

	public boolean hasFileEntryLock(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlRepositoryLocalService.getFileEntry(
			fileEntryId);

		long folderId = fileEntry.getFolderId();

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFileEntry.class.getName(), fileEntryId);

		if ((!hasLock) &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			hasLock = hasInheritableLock(folderId);
		}

		return hasLock;
	}

	public boolean hasInheritableLock(long folderId)
		throws PortalException, SystemException {

		boolean inheritable = false;

		try {
			Lock lock = lockLocalService.getLock(
				DLFolder.class.getName(), folderId);

			inheritable = lock.isInheritable();
		}
		catch (ExpiredLockException ele) {
		}
		catch (NoSuchLockException nsle) {
		}

		return inheritable;
	}

	public boolean isFileEntryLocked(long fileEntryId)
		throws PortalException, SystemException {

		return lockLocalService.isLocked(
			DLFileEntry.class.getName(), fileEntryId);
	}

	public boolean isFolderLocked(long folderId)
		throws PortalException, SystemException {

		return lockLocalService.isLocked(DLFolder.class.getName(), folderId);
	}

	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return lockFileEntry(
			fileEntryId, null, DLFileEntryImpl.LOCK_EXPIRATION_TIME);
	}

	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		User user = getUser();

		if ((expirationTime <= 0) ||
			(expirationTime > DLFileEntryImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFileEntryImpl.LOCK_EXPIRATION_TIME;
		}

		return lockLocalService.lock(
			user.getUserId(), DLFileEntry.class.getName(), fileEntryId, owner,
			false, expirationTime);
	}

	public Lock lockFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		return lockFolder(
			folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, RemoteException, SystemException {

		User user = getUser();

		if ((expirationTime <= 0) ||
			(expirationTime > DLFolderImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFolderImpl.LOCK_EXPIRATION_TIME;
		}

		Lock lock = lockLocalService.lock(
			user.getUserId(), DLFolder.class.getName(), folderId, owner,
			inheritable, expirationTime);

		Set<Long> fileFileEntryIds = new HashSet<Long>();

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		long groupId = folder.getGroupId();

		try {
			List<DLFileEntry> fileEntries = getFileEntries(
				groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			for (DLFileEntry fileEntry : fileEntries) {
				lockFileEntry(
					fileEntry.getFileEntryId(), owner, expirationTime);

				fileFileEntryIds.add(fileEntry.getFileEntryId());
			}
		}
		catch (Exception e) {
			for (long fileEntryId : fileFileEntryIds) {
				unlockFileEntry(fileEntryId);
			}

			unlockFolder(groupId, folderId, lock.getUuid());

			if (e instanceof PortalException) {
				throw (PortalException)e;
			}
			else if (e instanceof RemoteException) {
				throw (RemoteException)e;
			}
			else if (e instanceof SystemException) {
				throw (SystemException)e;
			}
			else {
				throw new PortalException(e);
			}
		}

		return lock;
	}

	public DLFileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		boolean hasLock = hasFileEntryLock(fileEntryId);

		if (!hasLock) {

			// Lock

			lockFileEntry(fileEntryId);
		}

		DLFileEntry fileEntry = null;

		try {
			fileEntry = dlRepositoryLocalService.moveFileEntry(
				getUserId(), fileEntryId, newFolderId, serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(fileEntryId);
			}
		}

		return fileEntry;
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return lockLocalService.refresh(lockUuid, expirationTime);
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return lockLocalService.refresh(lockUuid, expirationTime);
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		boolean hasLock = hasFileEntryLock(fileEntryId);

		if (!hasLock) {

			// Lock

			lockFileEntry(fileEntryId);
		}

		try {
			dlRepositoryLocalService.revertFileEntry(
				getUserId(), fileEntryId, version, serviceContext);
		}
		finally {

			// Unlock

			unlockFileEntry(fileEntryId);
		}
	}

	public void unlockFileEntry(long fileEntryId)
		throws SystemException {

		lockLocalService.unlock(DLFileEntry.class.getName(), fileEntryId);
	}

	public void unlockFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		if (Validator.isNotNull(lockUuid)) {
			try {
				Lock lock = lockLocalService.getLock(
					DLFileEntry.class.getName(), fileEntryId);

				if (!lock.getUuid().equals(lockUuid)) {
					throw new InvalidLockException("UUIDs do not match");
				}
			}
			catch (PortalException pe) {
				if ((pe instanceof ExpiredLockException) ||
					(pe instanceof NoSuchLockException)) {
				}
				else {
					throw pe;
				}
			}
		}

		lockLocalService.unlock(DLFileEntry.class.getName(), fileEntryId);
	}

	public void unlockFolder(long groupId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		if (Validator.isNotNull(lockUuid)) {
			try {
				Lock lock = lockLocalService.getLock(
					DLFolder.class.getName(), folderId);

				if (!lock.getUuid().equals(lockUuid)) {
					throw new InvalidLockException("UUIDs do not match");
				}
			}
			catch (PortalException pe) {
				if (pe instanceof ExpiredLockException ||
					pe instanceof NoSuchLockException) {
				}
				else {
					throw pe;
				}
			}
		}

		lockLocalService.unlock(DLFolder.class.getName(), folderId);

		try {
			List<DLFileEntry> fileEntries = getFileEntries(
				groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			for (DLFileEntry fileEntry : fileEntries) {
				unlockFileEntry(fileEntry.getFileEntryId());
			}
		}
		catch (NoSuchFolderException nsfe) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException, SystemException {

		DLFolder folder = getFolder(groupId, parentFolderId, name);

		unlockFolder(groupId, folder.getFolderId(), lockUuid);
	}

	public DLFileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		boolean hasLock = hasFileEntryLock(fileEntryId);

		if (!hasLock) {

			// Lock

			lockFileEntry(fileEntryId);
		}

		DLFileEntry fileEntry = null;

		try {
			fileEntry = dlRepositoryLocalService.updateFileEntry(
				getUserId(), fileEntryId, sourceFileName, title, description,
				changeLog, majorVersion, extraSettings, is, size,
				serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(fileEntryId);
			}
		}

		return fileEntry;
	}

	public DLFileVersion updateFileVersionDescription(
			long fileVersionId, String description)
		throws PortalException, SystemException {

		DLFileVersion fileVersion = dlFileVersionPersistence.findByPrimaryKey(
			fileVersionId);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileVersion.getFileEntryId(),
			ActionKeys.UPDATE);

		return dlRepositoryLocalService.updateFileVersionDescription(
			fileVersionId, description);
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		DLFolder folder = dlRepositoryLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(folderId);
		}

		try {
			return dlRepositoryLocalService.updateFolder(
				folderId, parentFolderId, name, description, serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(folder.getGroupId(), folderId, lock.getUuid());
			}
		}
	}

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		boolean verified = false;

		try {
			Lock lock = lockLocalService.getLock(
				DLFileEntry.class.getName(), fileEntryId);

			if (lock.getUuid().equals(lockUuid)) {
				verified = true;
			}
		}
		catch (PortalException pe) {
			if ((pe instanceof ExpiredLockException) ||
				(pe instanceof NoSuchLockException)) {

				DLFileEntry fileEntry = dlRepositoryLocalService.getFileEntry(
					fileEntryId);

				verified = verifyInheritableLock(
					fileEntry.getFolderId(), lockUuid);
			}
			else {
				throw pe;
			}
		}

		return verified;
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		boolean verified = false;

		try {
			Lock lock = lockLocalService.getLock(
				DLFolder.class.getName(), folderId);

			if (!lock.isInheritable()) {
				throw new NoSuchLockException();
			}

			if (lock.getUuid().equals(lockUuid)) {
				verified = true;
			}
		}
		catch (ExpiredLockException ele) {
			throw new NoSuchLockException(ele);
		}

		return verified;
	}

	protected void copyFolder(
			DLFolder srcFolder, DLFolder destFolder,
			ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		List<DLFileEntry> srcFileEntries = getFileEntries(
			srcFolder.getGroupId(), srcFolder.getFolderId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (DLFileEntry srcFileEntry : srcFileEntries) {
			String name = srcFileEntry.getName();
			String title = srcFileEntry.getTitle();
			String description = srcFileEntry.getDescription();
			String extraSettings = srcFileEntry.getExtraSettings();
			long size = srcFileEntry.getSize();

			try {
				InputStream is = dlLocalService.getFileAsStream(
					srcFolder.getCompanyId(), srcFolder.getFolderId(), name);

				addFileEntry(
					destFolder.getGroupId(), destFolder.getFolderId(), title,
					description, null, extraSettings, is, size,
					serviceContext);
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}
		}

		List<DLFolder> srcSubfolders = getFolders(
			srcFolder.getGroupId(), srcFolder.getFolderId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (DLFolder srcSubfolder : srcSubfolders) {
			String name = srcSubfolder.getName();
			String description = srcSubfolder.getDescription();

			DLFolder destSubfolder = addFolder(
				destFolder.getGroupId(), destFolder.getFolderId(), name,
				description, serviceContext);

			copyFolder(srcSubfolder, destSubfolder, serviceContext);
		}
	}

	protected long[] getFolderIds(long groupId, long folderId)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		getSubfolderIds(folderIds, groupId, folderId, true);

		return ArrayUtil.toArray(
			folderIds.toArray(new Long[folderIds.size()]));
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLRepositoryServiceImpl.class);

}