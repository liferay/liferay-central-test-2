/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.lock.service.spring.LockServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPK;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryService;

import java.rmi.RemoteException;

/**
 * <a href="DLFileEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryServiceImpl
	extends PrincipalBean implements DLFileEntryService {

	public DLFileEntry addFileEntry(
			String folderId, String name, String title, String description,
			byte[] byteArray, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.ADD_DOCUMENT);

		return DLFileEntryLocalServiceUtil.addFileEntry(
			getUserId(), folderId, name, title, description, byteArray,
			addCommunityPermissions, addGuestPermissions);
	}

	public void deleteFileEntry(String folderId, String name)
		throws PortalException, RemoteException, SystemException {

		User user = getUser();

		DLFileEntryPermission.check(
			getPermissionChecker(), folderId, name, ActionKeys.DELETE);

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		boolean alreadyHasLock = LockServiceUtil.hasLock(
			DLFileEntry.class.getName(), pk, user.getUserId());

		if (!alreadyHasLock) {

			// Lock

			LockServiceUtil.lock(
				DLFileEntry.class.getName(), pk, user.getCompanyId(),
				user.getUserId(), DLFileEntry.LOCK_EXPIRATION_TIME);
		}

		DLFileEntryLocalServiceUtil.deleteFileEntry(folderId, name);

		if (!alreadyHasLock) {

			// Unlock

			LockServiceUtil.unlock(DLFileEntry.class.getName(), pk);
		}
	}

	public void deleteFileEntry(String folderId, String name, double version)
		throws PortalException, RemoteException, SystemException {

		User user = getUser();

		DLFileEntryPermission.check(
			getPermissionChecker(), folderId, name, ActionKeys.DELETE);

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		boolean alreadyHasLock = LockServiceUtil.hasLock(
			DLFileEntry.class.getName(), pk, user.getUserId());

		if (!alreadyHasLock) {

			// Lock

			LockServiceUtil.lock(
				DLFileEntry.class.getName(), pk, user.getCompanyId(),
				user.getUserId(), DLFileEntry.LOCK_EXPIRATION_TIME);
		}

		DLFileEntryLocalServiceUtil.deleteFileEntry(folderId, name, version);

		if (!alreadyHasLock) {

			// Unlock

			LockServiceUtil.unlock(DLFileEntry.class.getName(), pk);
		}
	}

	public DLFileEntry getFileEntry(String folderId, String name)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), folderId, name, ActionKeys.VIEW);

		return DLFileEntryLocalServiceUtil.getFileEntry(folderId, name);
	}

	public void lockFileEntry(String folderId, String name)
		throws PortalException, RemoteException, SystemException {

		User user = getUser();

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		LockServiceUtil.lock(
			DLFileEntry.class.getName(), pk, user.getCompanyId(),
			user.getUserId(), DLFileEntry.LOCK_EXPIRATION_TIME);
	}

	public void unlockFileEntry(String folderId, String name)
		throws PortalException, RemoteException, SystemException {

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		LockServiceUtil.unlock(DLFileEntry.class.getName(), pk);
	}

	public DLFileEntry updateFileEntry(
			String folderId, String newFolderId, String name,
			String sourceFileName, String title, String description,
			byte[] byteArray)
		throws PortalException, RemoteException, SystemException {

		User user = getUser();

		DLFileEntryPermission.check(
			getPermissionChecker(), folderId, name, ActionKeys.UPDATE);

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		boolean alreadyHasLock = LockServiceUtil.hasLock(
			DLFileEntry.class.getName(), pk, user.getUserId());

		if (!alreadyHasLock) {

			// Lock

			LockServiceUtil.lock(
				DLFileEntry.class.getName(), pk, user.getCompanyId(),
				user.getUserId(), DLFileEntry.LOCK_EXPIRATION_TIME);
		}

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
			getUserId(), folderId, newFolderId, name, sourceFileName, title,
			description, byteArray);

		if (!alreadyHasLock) {

			// Unlock

			LockServiceUtil.unlock(DLFileEntry.class.getName(), pk);
		}

		return fileEntry;
	}

}