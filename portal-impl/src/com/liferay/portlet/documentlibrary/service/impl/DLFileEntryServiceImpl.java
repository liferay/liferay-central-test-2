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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryModifiedDateComparator;

import java.io.File;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryServiceImpl extends DLFileEntryServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
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

		return dlFileEntryLocalService.addFileEntry(
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

		return dlFileEntryLocalService.addFileEntry(
			getUserId(), groupId, folderId, name, title, description, changeLog,
			extraSettings, is, size, serviceContext);
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
			dlFileEntryLocalService.deleteFileEntry(groupId, folderId, name);
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
			dlFileEntryLocalService.deleteFileEntry(
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

	public int getFileEntriesCount(long groupId, long folderId)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		return dlFileEntryPersistence.filterCountByG_F(groupId, folderId);
	}

	public InputStream getFileAsStream(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.VIEW);

		User user = getUser();

		return dlFileEntryLocalService.getFileAsStream(
			user.getCompanyId(), user.getUserId(), groupId, folderId, name);
	}

	public InputStream getFileAsStream(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.VIEW);

		User user = getUser();

		return dlFileEntryLocalService.getFileAsStream(
			user.getCompanyId(), user.getUserId(), groupId, folderId, name,
			version);
	}

	public DLFileEntry getFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.VIEW);

		return dlFileEntryLocalService.getFileEntry(groupId, folderId, name);
	}

	public DLFileEntry getFileEntryByTitle(
			long groupId, long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntryByTitle(
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

		long[] folderIds = dlFolderService.getFolderIds(groupId, rootFolderId);

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

		long[] folderIds = dlFolderService.getFolderIds(groupId, rootFolderId);

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

	public boolean hasFileEntryLock(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		String lockId = DLUtil.getLockId(groupId, folderId, name);

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFileEntry.class.getName(), lockId);

		if ((!hasLock) &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			hasLock = dlFolderService.hasInheritableLock(folderId);
		}

		return hasLock;
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
			fileEntry = dlFileEntryLocalService.moveFileEntry(
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
			fileEntry = dlFileEntryLocalService.updateFileEntry(
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
			fileEntry = dlFileEntryLocalService.updateFileEntry(
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
			fileEntry = dlFileEntryLocalService.updateFileEntry(
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

				verified = dlFolderService.verifyInheritableLock(
					folderId, lockUuid);
			}
			else {
				throw pe;
			}
		}

		return verified;
	}

}