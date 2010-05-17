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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.FileLockedException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFileEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryServiceImpl extends DLFileEntryServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String versionDescription, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
			null, getUserId(), groupId, folderId, name, title, description,
			versionDescription, extraSettings, bytes, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long groupId, long folderId, String name, String title,
			String description, String versionDescription, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
			null, getUserId(), groupId, folderId, name, title, description,
			versionDescription, extraSettings, file, serviceContext);
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
			if (!hasLock) {

				// Unlock

				unlockFileEntry(groupId, folderId, name);
			}
		}
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), groupId, folderId, name, ActionKeys.DELETE);

		boolean hasLock = hasFileEntryLock(groupId, folderId, name);

		if (hasLock) {
			throw new FileLockedException();
		}

		lockFileEntry(groupId, folderId, name);

		try {
			dlFileEntryLocalService.deleteFileEntry(
				groupId, folderId, name, version);
		}
		finally {
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

		List<DLFileEntry> fileEntries = dlFileEntryLocalService.getFileEntries(
			groupId, folderId);

		fileEntries = ListUtil.copy(fileEntries);

		Iterator<DLFileEntry> itr = fileEntries.iterator();

		while (itr.hasNext()) {
			DLFileEntry fileEntry = itr.next();

			if (!DLFileEntryPermission.contains(
					getPermissionChecker(), fileEntry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return fileEntries;
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

		if ((expirationTime <= 0) ||
			(expirationTime > DLFileEntryImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFileEntryImpl.LOCK_EXPIRATION_TIME;
		}

		String lockId = DLUtil.getLockId(groupId, folderId, name);

		return lockLocalService.lock(
			getUser().getUserId(), DLFileEntry.class.getName(), lockId, owner,
			false, expirationTime);
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
				if (pe instanceof ExpiredLockException ||
					pe instanceof NoSuchLockException) {
				}
				else {
					throw pe;
				}
			}
		}

		lockLocalService.unlock(DLFileEntry.class.getName(), lockId);
	}

	public DLFileEntry updateFileEntry(
			long groupId, long folderId, long newFolderId, String name,
			String sourceFileName, String title, String description,
			String versionDescription, boolean majorVersion,
			String extraSettings, byte[] bytes, ServiceContext serviceContext)
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
				getUserId(), groupId, folderId, newFolderId, name,
				sourceFileName, title, description, versionDescription,
				majorVersion, extraSettings, bytes, serviceContext);
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
			long groupId, long folderId, long newFolderId, String name,
			String sourceFileName, String title, String description,
			String versionDescription, boolean majorVersion,
			String extraSettings, File file, ServiceContext serviceContext)
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
				getUserId(), groupId, folderId, newFolderId, name,
				sourceFileName, title, description, versionDescription,
				majorVersion, extraSettings, file, serviceContext);
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
			if (pe instanceof ExpiredLockException ||
				pe instanceof NoSuchLockException) {

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