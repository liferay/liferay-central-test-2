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

import java.io.File;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.InvalidLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLRepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryModifiedDateComparator;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLRepositoryServiceImpl extends DLRepositoryServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlRepositoryLocalService.addFileEntry(
			getUserId(), groupId, folderId, name, title, description, changeLog,
			extraSettings, bytes, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlRepositoryLocalService.addFileEntry(
			getUserId(), groupId, folderId, name, title, description, changeLog,
			extraSettings, file, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlRepositoryLocalService.addFileEntry(
			getUserId(), groupId, folderId, name, title, description, changeLog,
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

	public void deleteFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.DELETE);

		boolean hasLock = hasFileEntryLock(groupId, folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(groupId, folderId, name);
		}

		try {
			dlRepositoryLocalService.deleteFileEntry(groupId, folderId, name);
		}
		finally {

			// Unlock

			unlockFileEntry(groupId, folderId, name);
		}
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.DELETE);

		boolean hasLock = hasFileEntryLock(groupId, folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(groupId, folderId, name);
		}

		try {
			dlRepositoryLocalService.deleteFileEntry(
				groupId, folderId, name, version);
		}
		finally {

			// Unlock

			unlockFileEntry(groupId, folderId, name);
		}
	}

	public void deleteFileEntryByTitle(
			long groupId, long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = getFileEntryByTitle(
			groupId, folderId, titleWithExtension);

		deleteFileEntry(groupId, folderId, fileEntry.getName());
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

		long folderId = getFolderId(groupId, parentFolderId, name);

		deleteFolder(folderId);
	}

	public InputStream getFileAsStream(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.VIEW);

		User user = getUser();

		return dlRepositoryLocalService.getFileAsStream(
			user.getCompanyId(), user.getUserId(), groupId, folderId, name);
	}

	public InputStream getFileAsStream(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.VIEW);

		User user = getUser();

		return dlRepositoryLocalService.getFileAsStream(
			user.getCompanyId(), user.getUserId(), groupId, folderId, name,
			version);
	}

	public List<DLFileEntry> getFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		return dlFileEntryPersistence.filterFindByG_F(groupId, folderId);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		return dlFileEntryPersistence.filterFindByG_F(
			groupId, folderId, start, end);
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

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return dlFolderFinder.filterFindFE_FS_ByG_F_S(
			groupId, folderIds, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFolderFinder.filterCountFE_FS_ByG_F_S(
			groupId, folderIds, status);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return dlFolderFinder.filterCountFE_FS_ByG_F_S(
			groupId, folderIds, status);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		return dlFileEntryPersistence.filterCountByG_F(groupId, folderId);
	}

	public DLFileEntry getFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.VIEW);

		return dlRepositoryLocalService.getFileEntry(groupId, folderId, name);
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

	public Lock getFileEntryLock(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		String lockId = DLUtil.getLockId(groupId, folderId, name);

		return lockLocalService.getLock(DLFileEntry.class.getName(), lockId);
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

	public long getFolderId(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder folder = getFolder(groupId, parentFolderId, name);

		return folder.getFolderId();
	}

	public long[] getFolderIds(long groupId, long folderId)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		getSubfolderIds(folderIds, groupId, folderId);

		return ArrayUtil.toArray(
			folderIds.toArray(new Long[folderIds.size()]));
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.filterFindByG_P(groupId, parentFolderId);
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
			long groupId, long userId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, start,
			end, new FileEntryModifiedDateComparator());
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return getGroupFileEntries(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, start,
			end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, userId, rootFolderId, start, end,
			new FileEntryModifiedDateComparator());
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

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		return getGroupFileEntriesCount(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
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
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		getSubfolderIds(folderIds, groupId, folderId, true);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId, boolean recurse)
		throws SystemException {

		List<DLFolder> folders = dlFolderPersistence.filterFindByG_P(
			groupId, folderId);

		for (DLFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			if (recurse) {
				getSubfolderIds(
					folderIds, folder.getGroupId(), folder.getFolderId());
			}
		}
	}

	public boolean hasFileEntryLock(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		String lockId = DLUtil.getLockId(groupId, folderId, name);

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFileEntry.class.getName(), lockId);

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

	public Lock lockFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return lockFileEntry(
			groupId, folderId, name, null,
			DLFileEntryImpl.LOCK_EXPIRATION_TIME);
	}

	public Lock lockFileEntry(
			long groupId, long folderId, String name, String owner,
			long expirationTime)
		throws PortalException, SystemException {

		User user = getUser();

		String lockId = DLUtil.getLockId(groupId, folderId, name);

		if ((expirationTime <= 0) ||
			(expirationTime > DLFileEntryImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFileEntryImpl.LOCK_EXPIRATION_TIME;
		}

		return lockLocalService.lock(
			user.getUserId(), DLFileEntry.class.getName(), lockId, owner,
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

		Set<String> fileNames = new HashSet<String>();

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		long groupId = folder.getGroupId();

		try {
			List<DLFileEntry> fileEntries = getFileEntries(groupId, folderId);

			for (DLFileEntry fileEntry : fileEntries) {
				lockFileEntry(
					groupId, folderId, fileEntry.getName(), owner,
					expirationTime);

				fileNames.add(fileEntry.getName());
			}
		}
		catch (Exception e) {
			for (String fileName : fileNames) {
				unlockFileEntry(groupId, folderId, fileName);
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
			long groupId, long folderId, long newFolderId, String name,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.UPDATE);

		boolean hasLock = hasFileEntryLock(groupId, folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(groupId, folderId, name);
		}

		DLFileEntry fileEntry = null;

		try {
			fileEntry = dlRepositoryLocalService.moveFileEntry(
				getUserId(), groupId, folderId, newFolderId, name,
				serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(groupId, folderId, name);
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

	public void unlockFileEntry(long groupId, long folderId, String name)
		throws SystemException {

		String lockId = DLUtil.getLockId(groupId, folderId, name);

		lockLocalService.unlock(DLFileEntry.class.getName(), lockId);
	}

	public void unlockFileEntry(
			long groupId, long folderId, String name, String lockUuid)
		throws PortalException, SystemException {

		String lockId = DLUtil.getLockId(groupId, folderId, name);

		if (Validator.isNotNull(lockUuid)) {
			try {
				Lock lock = lockLocalService.getLock(
					DLFileEntry.class.getName(), lockId);

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

		lockLocalService.unlock(DLFileEntry.class.getName(), lockId);
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
			List<DLFileEntry> fileEntries = getFileEntries(groupId, folderId);

			for (DLFileEntry fileEntry : fileEntries) {
				unlockFileEntry(groupId, folderId, fileEntry.getName());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException, SystemException {

		long folderId = getFolderId(groupId, parentFolderId, name);

		unlockFolder(groupId, folderId, lockUuid);
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name, String sourceFileName,
			String title, String description, String changeLog,
			boolean majorVersion, String extraSettings, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.UPDATE);

		boolean hasLock = hasFileEntryLock(groupId, folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(groupId, folderId, name);
		}

		DLFileEntry fileEntry = null;

		try {
			fileEntry = dlRepositoryLocalService.updateFileEntry(
				getUserId(), groupId, folderId, name, sourceFileName, title,
				description, changeLog, majorVersion, extraSettings, bytes,
				serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(groupId, folderId, name);
			}
		}

		return fileEntry;
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name, String sourceFileName,
			String title, String description, String changeLog,
			boolean majorVersion, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.UPDATE);

		boolean hasLock = hasFileEntryLock(groupId, folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(groupId, folderId, name);
		}

		DLFileEntry fileEntry = null;

		try {
			fileEntry = dlRepositoryLocalService.updateFileEntry(
				getUserId(), groupId, folderId, name, sourceFileName, title,
				description, changeLog, majorVersion, extraSettings, file,
				serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(groupId, folderId, name);
			}
		}

		return fileEntry;
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, String name,
			String sourceFileName, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.UPDATE);

		boolean hasLock = hasFileEntryLock(groupId, folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(groupId, folderId, name);
		}

		DLFileEntry fileEntry = null;

		try {
			fileEntry = dlRepositoryLocalService.updateFileEntry(
				getUserId(), groupId, folderId, name, sourceFileName, title,
				description, changeLog, majorVersion, extraSettings, is, size,
				serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(groupId, folderId, name);
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
			getPermissionChecker(), fileVersion.getGroupId(),
			fileVersion.getFolderId(), fileVersion.getName(),
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

	public boolean verifyFileEntryLock(
			long groupId, long folderId, String name, String lockUuid)
		throws PortalException, SystemException {

		boolean verified = false;

		try {
			String lockId = DLUtil.getLockId(groupId, folderId, name);

			Lock lock = lockLocalService.getLock(
				DLFileEntry.class.getName(), lockId);

			if (lock.getUuid().equals(lockUuid)) {
				verified = true;
			}
		}
		catch (PortalException pe) {
			if ((pe instanceof ExpiredLockException) ||
				(pe instanceof NoSuchLockException)) {

				verified = verifyInheritableLock(folderId, lockUuid);
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
			srcFolder.getGroupId(), srcFolder.getFolderId());

		for (DLFileEntry srcFileEntry : srcFileEntries) {
			String name = srcFileEntry.getName();
			String title = srcFileEntry.getTitle();
			String description = srcFileEntry.getDescription();
			String extraSettings = srcFileEntry.getExtraSettings();

			File file = null;

			try {
				file = FileUtil.createTempFile(FileUtil.getExtension(title));

				InputStream is = dlLocalService.getFileAsStream(
					srcFolder.getCompanyId(), srcFolder.getFolderId(), name);

				FileUtil.write(file, is);
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}

			addFileEntry(
				destFolder.getGroupId(), destFolder.getFolderId(), name, title,
				description, null, extraSettings, file, serviceContext);

			file.delete();
		}

		List<DLFolder> srcSubfolders = getFolders(
			srcFolder.getGroupId(), srcFolder.getFolderId());

		for (DLFolder srcSubfolder : srcSubfolders) {
			String name = srcSubfolder.getName();
			String description = srcSubfolder.getDescription();

			DLFolder destSubfolder = addFolder(
				destFolder.getGroupId(), destFolder.getFolderId(), name,
				description, serviceContext);

			copyFolder(srcSubfolder, destSubfolder, serviceContext);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLRepositoryServiceImpl.class);

}