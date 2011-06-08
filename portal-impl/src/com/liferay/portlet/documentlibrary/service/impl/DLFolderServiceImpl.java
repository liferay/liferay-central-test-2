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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFolderServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFolderServiceImpl extends DLFolderServiceBaseImpl {

	public DLFolder addFolder(
			long groupId, long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.addFolder(
			getUserId(), groupId, repositoryId, parentFolderId, name,
			description, serviceContext);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.DELETE);

		boolean hasLock = hasFolderLock(folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = _lockFolder(
				folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
		}

		try {
			dlFolderLocalService.deleteFolder(folderId);
		}
		finally {
			if (!hasLock) {

				// Unlock

				_unlockFolder(dlFolder.getGroupId(), folderId, lock.getUuid());
			}
		}
	}

	public void deleteFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder dlFolder = getFolder(groupId, parentFolderId, name);

		deleteFolder(dlFolder.getFolderId());
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.filterFindFE_FS_ByG_F_S(
			groupId, folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFolderFinder.filterCountFE_FS_ByG_F_S(
			groupId, folderId, status);
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.VIEW);

		return dlFolder;
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(
			groupId, parentFolderId, name);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.VIEW);

		return dlFolder;
	}

	public long[] getFolderIds(long groupId, long folderId)
		throws SystemException {

		List<Long> folderIds = getSubfolderIds(groupId, folderId, true);

		folderIds.add(0, folderId);

		return ArrayUtil.toArray(
			folderIds.toArray(new Long[folderIds.size()]));
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderPersistence.filterFindByG_P(
			groupId, parentFolderId, start, end, obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_S(
			groupId, folderId, status, start, end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_S(
			groupId, folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.filterCountByG_P(groupId, parentFolderId);
	}

	public List<Long> getSubfolderIds(
			long groupId, long folderId, boolean recurse)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		List<DLFolder> dlFolders = dlFolderPersistence.filterFindByG_P(
			groupId, folderId);

		for (DLFolder dlFolder : dlFolders) {
			List<Long> subFolderIds = getSubfolderIds(
				dlFolder.getGroupId(), dlFolder.getFolderId(), recurse);

			folderIds.add(dlFolder.getFolderId());
			folderIds.addAll(subFolderIds);
		}

		return folderIds;
	}

	public boolean hasFolderLock(long folderId)
		throws PortalException, SystemException {

		return lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);
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

	public boolean isFolderLocked(long folderId)
		throws PortalException, SystemException {

		return lockLocalService.isLocked(DLFolder.class.getName(), folderId);
	}

	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		return lockFolder(
			folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.UPDATE);

		return _lockFolder(folderId, owner, inheritable, expirationTime);
	}

	public DLFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.UPDATE);

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(folderId);
		}

		try {
			return dlFolderLocalService.moveFolder(
				folderId, parentFolderId, serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(dlFolder.getGroupId(), folderId, lock.getUuid());
			}
		}
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return lockLocalService.refresh(lockUuid, expirationTime);
	}

	public void unlockFolder(long groupId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		try {
			DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

			DLFolderPermission.check(
				getPermissionChecker(), dlFolder, ActionKeys.UPDATE);
		}
		catch (NoSuchFolderException nsfe) {
		}

		_unlockFolder(groupId, folderId, lockUuid);
	}

	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException, SystemException {

		DLFolder dlFolder = getFolder(groupId, parentFolderId, name);

		unlockFolder(groupId, dlFolder.getFolderId(), lockUuid);
	}

	public DLFolder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.UPDATE);

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(folderId);
		}

		try {
			return dlFolderLocalService.updateFolder(
				folderId, name, description, serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(dlFolder.getGroupId(), folderId, lock.getUuid());
			}
		}
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

	private Lock _lockFolder(long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		if ((expirationTime <= 0) ||
			(expirationTime > DLFolderImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFolderImpl.LOCK_EXPIRATION_TIME;
		}

		Lock lock = lockLocalService.lock(
			getUserId(), DLFolder.class.getName(), folderId, owner,
			inheritable, expirationTime);

		Set<Long> fileFileEntryIds = new HashSet<Long>();

		long groupId = dlFolder.getGroupId();

		try {
			List<DLFileEntry> dlFileEntries =
				dlFileEntryLocalService.getFileEntries(
					groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				dlFileEntryService.lockFileEntry(
					dlFileEntry.getFileEntryId(), owner, expirationTime);

				fileFileEntryIds.add(dlFileEntry.getFileEntryId());
			}
		}
		catch (Exception e) {
			for (long fileEntryId : fileFileEntryIds) {
				dlFileEntryService.unlockFileEntry(fileEntryId);
			}

			unlockFolder(groupId, folderId, lock.getUuid());

			if (e instanceof PortalException) {
				throw (PortalException)e;
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

	private void _unlockFolder(long groupId, long folderId, String lockUuid)
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
			List<DLFileEntry> dlFileEntries =
				dlFileEntryLocalService.getFileEntries(
					groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				dlFileEntryService.unlockFileEntry(
					dlFileEntry.getFileEntryId());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFolderServiceImpl.class);

}