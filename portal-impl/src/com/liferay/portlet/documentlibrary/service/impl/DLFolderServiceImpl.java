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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.base.DLFolderServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.Iterator;
import java.util.List;

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
			long plid, long parentFolderId, String name, String description,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), plid, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.addFolder(
			getUserId(), plid, parentFolderId, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	public DLFolder addFolder(
			long plid, long parentFolderId, String name, String description,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), plid, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.addFolder(
			getUserId(), plid, parentFolderId, name, description,
			communityPermissions, guestPermissions);
	}

	public DLFolder copyFolder(
			long plid, long sourceFolderId, long parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, RemoteException, SystemException {

		DLFolder srcFolder = getFolder(sourceFolderId);

		DLFolder destFolder = addFolder(
			plid, parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions);

		copyFolder(
			srcFolder, destFolder, addCommunityPermissions,
			addGuestPermissions);

		return destFolder;
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.DELETE);

		dlFolderLocalService.deleteFolder(folderId);
	}

	public void deleteFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder folder = getFolder(groupId, parentFolderId, name);

		deleteFolder(folder.getFolderId());
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
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.UPDATE);

		return dlFolderLocalService.updateFolder(
			folderId, parentFolderId, name, description);
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

		long destPlid = layoutLocalService.getDefaultPlid(
			destFolder.getGroupId());

		List<DLFolder> srcSubfolders = getFolders(
			srcFolder.getGroupId(), srcFolder.getFolderId());

		for (DLFolder srcSubfolder : srcSubfolders) {
			String name = srcSubfolder.getName();
			String description = srcSubfolder.getDescription();

			DLFolder destSubfolder = addFolder(
				destPlid, destFolder.getFolderId(), name,
				description, addCommunityPermissions, addGuestPermissions);

			copyFolder(
				srcSubfolder, destSubfolder, addCommunityPermissions,
				addGuestPermissions);
		}
	}

	private static Log _log = LogFactory.getLog(DLFolderServiceImpl.class);

}