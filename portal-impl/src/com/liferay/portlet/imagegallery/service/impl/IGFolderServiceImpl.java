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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.util.ContentTypeUtil;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.base.IGFolderServiceBaseImpl;
import com.liferay.portlet.imagegallery.service.permission.IGFolderPermission;
import com.liferay.util.FileUtil;
import com.liferay.util.PwdGenerator;
import com.liferay.util.SystemProperties;
import com.liferay.util.Time;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="IGFolderServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGFolderServiceImpl extends IGFolderServiceBaseImpl {

	public IGFolder addFolder(
			long plid, long parentFolderId, String name, String description,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), plid, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return igFolderLocalService.addFolder(
			getUserId(), plid, parentFolderId, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	public IGFolder addFolder(
			long plid, long parentFolderId, String name, String description,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), plid, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return igFolderLocalService.addFolder(
			getUserId(), plid, parentFolderId, name, description,
			communityPermissions, guestPermissions);
	}

	public IGFolder copyFolder(
			long plid, long sourceFolderId, long parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, RemoteException, SystemException {

		IGFolder srcFolder = getFolder(sourceFolderId);

		IGFolder destFolder = addFolder(
			plid, parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions);

		copyFolder(
			srcFolder, destFolder, addCommunityPermissions,
			addGuestPermissions);

		return destFolder;
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.DELETE);

		igFolderLocalService.deleteFolder(folderId);
	}

	public IGFolder getFolder(long folderId)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.VIEW);

		return igFolderLocalService.getFolder(folderId);
	}

	public IGFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		IGFolder folder = igFolderLocalService.getFolder(
			groupId, parentFolderId, name);

		IGFolderPermission.check(
			getPermissionChecker(), folder.getFolderId(), ActionKeys.VIEW);

		return folder;
	}

	public List<IGFolder> getFolders(long groupId, long parentFolderId)
		throws PortalException, SystemException {

		List<IGFolder> folders =
			igFolderLocalService.getFolders(groupId, parentFolderId);

		List<IGFolder> sanitized = new ArrayList<IGFolder>(folders.size());

		for (IGFolder folder : folders) {
			if (IGFolderPermission.contains(
					getPermissionChecker(), folder.getFolderId(),
					ActionKeys.VIEW)) {

				sanitized.add(folder);
			}
		}

		return sanitized;
	}

	public IGFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			boolean mergeWithParentFolder)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.UPDATE);

		return igFolderLocalService.updateFolder(
			folderId, parentFolderId, name, description, mergeWithParentFolder);
	}

	protected void copyFolder(
			IGFolder srcFolder, IGFolder destFolder,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, RemoteException, SystemException {

		long srcFolderId = srcFolder.getFolderId();
		long destFolderId = destFolder.getFolderId();
		long srcGroupId = srcFolder.getGroupId();
		long destGroupId = destFolder.getGroupId();

		// Copy all viewable images

		List<IGImage> images = igImageService.getImages(srcFolderId);

		for (IGImage image : images) {
			String name = image.getName();
			String description = image.getDescription();
			String contentType =
				ContentTypeUtil.getContentType(image.getImageType());
			String[] tagsEntries = null;

			File file = null;

			try {
				String fileName =
					SystemProperties.get(SystemProperties.TMP_DIR) +
						StringPool.SLASH + Time.getTimestamp() +
							PwdGenerator.getPassword(PwdGenerator.KEY2, 8);

				file = new File(fileName);

				Image imageObj =
					ImageLocalUtil.getImage(image.getLargeImageId());

				byte[] byteArray = imageObj.getTextObj();

				InputStream is = new ByteArrayInputStream(byteArray);

				FileUtil.write(file, is);
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}

			igImageService.addImage(
				destFolderId, name, description, file, contentType,
				tagsEntries, addCommunityPermissions, addGuestPermissions);
		}

		// Copy all viewable folders

		List<IGFolder> srcSubFolders = getFolders(srcGroupId, srcFolderId);

		long destPlid = LayoutLocalServiceUtil.getDefaultPlid(destGroupId);

		for (IGFolder srcSubFolder : srcSubFolders) {
			String name = srcSubFolder.getName();
			String description = srcSubFolder.getDescription();

			IGFolder destSubFolder = addFolder(
				destPlid, destFolderId, name, description,
				addCommunityPermissions, addGuestPermissions);

			// Recursively copy all subfolders

			copyFolder(
				srcSubFolder, destSubFolder, addCommunityPermissions,
				addGuestPermissions);
		}
	}

	private static Log _log = LogFactory.getLog(IGFolderServiceImpl.class);
}