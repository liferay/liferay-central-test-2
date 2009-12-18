/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.InvalidLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFolderServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.io.File;
import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="DLFolderServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFolderServiceImpl extends DLFolderServiceBaseImpl {

	public DLFolder addFolder(
			long groupId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.addFolder(
			null, getUserId(), groupId, parentFolderId, name, description,
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

	public void deleteFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		DLFolder folder = dlFolderLocalService.getFolder(folderId);

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
			dlFolderLocalService.deleteFolder(folderId);
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

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderLocalService.getFolder(
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

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws PortalException, SystemException {

		List<DLFolder> folders = dlFolderLocalService.getFolders(
			groupId, parentFolderId);

		folders = ListUtil.copy(folders);

		Iterator<DLFolder> itr = folders.iterator();

		while (itr.hasNext()) {
			DLFolder folder = itr.next();

			if (!DLFolderPermission.contains(
					getPermissionChecker(), groupId, folder.getFolderId(),
					ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return folders;
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

	public Lock lockFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		return lockFolder(
			folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, RemoteException, SystemException {

		if ((expirationTime <= 0) ||
			(expirationTime > DLFolderImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFolderImpl.LOCK_EXPIRATION_TIME;
		}

		Lock lock = lockLocalService.lock(
			getUser().getUserId(), DLFolder.class.getName(), folderId, owner,
			inheritable, expirationTime);

		Set<String> fileNames = new HashSet<String>();

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		long groupId = folder.getGroupId();

		try {

			List<DLFileEntry> fileEntries = dlFileEntryService.getFileEntries(
				groupId, folderId);

			for (DLFileEntry fileEntry : fileEntries) {
				dlFileEntryService.lockFileEntry(
					groupId, folderId, fileEntry.getName(), owner,
					expirationTime);

				fileNames.add(fileEntry.getName());
			}
		}
		catch (Exception e) {
			for (String fileName : fileNames) {
				dlFileEntryService.unlockFileEntry(groupId, folderId, fileName);
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

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return lockLocalService.refresh(lockUuid, expirationTime);
	}

	public void reIndexSearch(long companyId)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		String[] ids = new String[] {String.valueOf(companyId)};

		dlFolderLocalService.reIndex(ids);
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
			List<DLFileEntry> fileEntries = dlFileEntryService.getFileEntries(
				groupId, folderId);

			for (DLFileEntry fileEntry : fileEntries) {
				dlFileEntryService.unlockFileEntry(
					groupId, folderId, fileEntry.getName());
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

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		DLFolder folder = dlFolderLocalService.getFolder(folderId);

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
			return dlFolderLocalService.updateFolder(
				folderId, parentFolderId, name, description, serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(folder.getGroupId(), folderId, lock.getUuid());
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

	protected void copyFolder(
			DLFolder srcFolder, DLFolder destFolder,
			ServiceContext serviceContext)
		throws PortalException, RemoteException, SystemException {

		List<DLFileEntry> srcFileEntries = dlFileEntryService.getFileEntries(
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

			dlFileEntryService.addFileEntry(
				destFolder.getGroupId(), destFolder.getFolderId(), name, title,
				description, extraSettings, file, serviceContext);

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

	private static Log _log = LogFactoryUtil.getLog(DLFolderServiceImpl.class);

}