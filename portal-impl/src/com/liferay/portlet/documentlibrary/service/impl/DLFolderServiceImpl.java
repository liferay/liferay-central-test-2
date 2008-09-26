/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.lock.ExpiredLockException;
import com.liferay.lock.InvalidLockException;
import com.liferay.lock.NoSuchLockException;
import com.liferay.lock.model.Lock;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DLFolderServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFolderServiceImpl extends DLFolderServiceBaseImpl {

	public DLFolder addFolder(
			long groupId, long parentFolderId, String name, String description,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.addFolder(
			getUserId(), groupId, parentFolderId, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	public DLFolder addFolder(
			long groupId, long parentFolderId, String name, String description,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.addFolder(
			getUserId(), groupId, parentFolderId, name, description,
			communityPermissions, guestPermissions);
	}

	public DLFolder copyFolder(
			long groupId, long sourceFolderId, long parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, RemoteException, SystemException {

		DLFolder srcFolder = getFolder(sourceFolderId);

		DLFolder destFolder = addFolder(
			groupId, parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions);

		copyFolder(
			srcFolder, destFolder, addCommunityPermissions,
			addGuestPermissions);

		return destFolder;
	}

	public void deleteFolder(long folderId)
		throws PortalException, RemoteException, SystemException {

		User user = getUser();

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.DELETE);

		boolean alreadyHasLock = lockService.hasLock(
			DLFolder.class.getName(), folderId, user.getUserId());

		Lock lock = null;

		if (!alreadyHasLock) {

			// Lock

			lock = lockFolder(folderId);
		}

		try {
			dlFolderLocalService.deleteFolder(folderId);
		}
		finally {
			if (!alreadyHasLock) {

				// Unlock

				unlockFolder(folderId, lock.getUuid());
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

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.VIEW);

		return dlFolderLocalService.getFolder(folderId);
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

		Iterator<DLFolder> itr = folders.iterator();

		while (itr.hasNext()) {
			DLFolder folder = itr.next();

			if (!DLFolderPermission.contains(
					getPermissionChecker(), folder.getFolderId(),
					ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return folders;
	}

	public boolean hasInheritableLock(long folderId)
		throws PortalException, SystemException, RemoteException {

		boolean inheritable = false;

		try {
			Lock lock = lockService.getLock(DLFolder.class.getName(), folderId);

			inheritable = lock.isInheritable();
		}
		catch (ExpiredLockException ele) {
		}
		catch (NoSuchLockException nsle) {
		}

		return inheritable;
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException, RemoteException {

		boolean verified = false;

		try {
			Lock lock = lockService.getLock(DLFolder.class.getName(), folderId);

			if (!lock.isInheritable()) {
				throw new NoSuchLockException();
			}

			verified = lock.getUuid().equals(lockUuid);
		}
		catch (ExpiredLockException ele) {
			throw new NoSuchLockException(ele);
		}

		return verified;
	}

	public Lock lockFolder(long folderId)
		throws PortalException, SystemException, RemoteException {

		return lockFolder(
			folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException, RemoteException {

		if ((expirationTime <= 0) ||
			(expirationTime > DLFolderImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFolderImpl.LOCK_EXPIRATION_TIME;
		}

		Lock folderLock = lockService.lock(
			DLFolder.class.getName(), folderId, getUser().getUserId(), owner,
			inheritable, expirationTime);

		Set<String> fileNames = new HashSet<String>();

		try {
			List<DLFileEntry> entries =
				dlFileEntryService.getFileEntries(folderId);

			for (DLFileEntry entry : entries) {
				dlFileEntryService.lockFileEntry(
					folderId, entry.getName(), owner, expirationTime);

				fileNames.add(entry.getName());
			}
		}
		catch (Exception e) {
			for (String name : fileNames) {
				dlFileEntryService.unlockFileEntry(folderId, name);
			}

			unlockFolder(folderId, folderLock.getUuid());

			if (e instanceof PortalException) {
				throw (PortalException)e;
			}
			else if (e instanceof SystemException) {
				throw (SystemException)e;
			}
			else if (e instanceof RemoteException) {
				throw (RemoteException)e;
			}
			else {
				throw new PortalException(e);
			}
		}

		return folderLock;
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, RemoteException {

		return lockService.refresh(lockUuid, expirationTime);
	}

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException, RemoteException {

		if (Validator.isNotNull(lockUuid)) {
			try {
				Lock lock = lockService.getLock(
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

		lockService.unlock(DLFolder.class.getName(), folderId);

		try {
			List<DLFileEntry> entries =
				dlFileEntryService.getFileEntries(folderId);

			for (DLFileEntry entry : entries) {
				dlFileEntryService.unlockFileEntry(
					folderId, entry.getName());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException, RemoteException, SystemException {

		long folderId = getFolderId(groupId, parentFolderId, name);

		unlockFolder(folderId, lockUuid);
	}

	public void reIndexSearch(long companyId)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		String[] ids = new String[] {String.valueOf(companyId)};

		dlFolderLocalService.reIndex(ids);
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description)
		throws PortalException, RemoteException, SystemException {

		User user = getUser();

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.UPDATE);

		boolean alreadyHasLock = lockService.hasLock(
			DLFolder.class.getName(), folderId, user.getUserId());

		Lock lock = null;

		if (!alreadyHasLock) {

			// Lock

			lock = lockFolder(folderId);
		}

		try {
			return dlFolderLocalService.updateFolder(
				folderId, parentFolderId, name, description);
		}
		finally {
			if (!alreadyHasLock) {

				// Unlock

				unlockFolder(folderId, lock.getUuid());
			}
		}
	}

	protected void copyFolder(
			DLFolder srcFolder, DLFolder destFolder,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, RemoteException, SystemException {

		List<DLFileEntry> srcFileEntries = dlFileEntryService.getFileEntries(
			srcFolder.getFolderId());

		for (DLFileEntry srcFileEntry : srcFileEntries) {
			String name = srcFileEntry.getName();
			String title = srcFileEntry.getTitleWithExtension();
			String description = srcFileEntry.getDescription();
			String[] tagsEntries = null;
			String extraSettings = srcFileEntry.getExtraSettings();

			File file = null;

			try {
				file = FileUtil.createTempFile(FileUtil.getExtension(name));

				InputStream is = dlLocalService.getFileAsStream(
					srcFolder.getCompanyId(), srcFolder.getFolderId(), name);

				FileUtil.write(file, is);
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}

			dlFileEntryService.addFileEntry(
				destFolder.getFolderId(), name, title, description, tagsEntries,
				extraSettings, file, addCommunityPermissions,
				addGuestPermissions);

			file.delete();
		}

		List<DLFolder> srcSubfolders = getFolders(
			srcFolder.getGroupId(), srcFolder.getFolderId());

		for (DLFolder srcSubfolder : srcSubfolders) {
			String name = srcSubfolder.getName();
			String description = srcSubfolder.getDescription();

			DLFolder destSubfolder = addFolder(
				destFolder.getGroupId(), destFolder.getFolderId(), name,
				description, addCommunityPermissions, addGuestPermissions);

			copyFolder(
				srcSubfolder, destSubfolder, addCommunityPermissions,
				addGuestPermissions);
		}
	}

	private static Log _log = LogFactory.getLog(DLFolderServiceImpl.class);

}