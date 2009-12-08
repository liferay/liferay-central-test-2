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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.base.IGFolderServiceBaseImpl;
import com.liferay.portlet.imagegallery.service.permission.IGFolderPermission;

import java.io.File;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="IGFolderServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Alexander Chow
 */
public class IGFolderServiceImpl extends IGFolderServiceBaseImpl {

	public IGFolder addFolder(
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentFolderId, ActionKeys.ADD_FOLDER);

		return igFolderLocalService.addFolder(
			getUserId(), parentFolderId, name, description, serviceContext);
	}

	public IGFolder copyFolder(
			long sourceFolderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		IGFolder srcFolder = getFolder(sourceFolderId);

		IGFolder destFolder = addFolder(
			parentFolderId, name, description, serviceContext);

		copyFolder(srcFolder, destFolder, serviceContext);

		return destFolder;
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		IGFolder folder = igFolderLocalService.getFolder(folderId);

		IGFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		igFolderLocalService.deleteFolder(folderId);
	}

	public IGFolder getFolder(long folderId)
		throws PortalException, SystemException {

		IGFolder folder = igFolderLocalService.getFolder(folderId);

		IGFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	public IGFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		IGFolder folder = igFolderLocalService.getFolder(
			groupId, parentFolderId, name);

		IGFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	public List<IGFolder> getFolders(long groupId, long parentFolderId)
		throws PortalException, SystemException {

		List<IGFolder> folders = igFolderLocalService.getFolders(
			groupId, parentFolderId);

		folders = ListUtil.copy(folders);

		Iterator<IGFolder> itr = folders.iterator();

		while (itr.hasNext()) {
			IGFolder folder = itr.next();

			if (!IGFolderPermission.contains(
					getPermissionChecker(), folder, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return folders;
	}

	public IGFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			boolean mergeWithParentFolder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		IGFolder folder = igFolderLocalService.getFolder(folderId);

		IGFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		return igFolderLocalService.updateFolder(
			folderId, parentFolderId, name, description, mergeWithParentFolder,
			serviceContext);
	}

	protected void copyFolder(
			IGFolder srcFolder, IGFolder destFolder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<IGImage> srcImages = igImageService.getImages(
			srcFolder.getGroupId(), srcFolder.getFolderId());

		for (IGImage srcImage : srcImages) {
			String name = srcImage.getName();
			String description = srcImage.getDescription();

			File file = null;

			try {
				file = FileUtil.createTempFile(srcImage.getImageType());

				Image image = imageLocalService.getImage(
					srcImage.getLargeImageId());

				byte[] bytes = image.getTextObj();

				FileUtil.write(file, bytes);
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}

			String contentType = MimeTypesUtil.getContentType(
				srcImage.getImageType());

			igImageService.addImage(
				destFolder.getGroupId(), destFolder.getFolderId(), name,
				description, file, contentType, serviceContext);

			file.delete();
		}

		List<IGFolder> srcSubfolders = getFolders(
			srcFolder.getGroupId(), srcFolder.getFolderId());

		for (IGFolder srcSubfolder : srcSubfolders) {
			String name = srcSubfolder.getName();
			String description = srcSubfolder.getDescription();

			serviceContext.setScopeGroupId(srcFolder.getGroupId());

			IGFolder destSubfolder = addFolder(
				destFolder.getFolderId(), name, description, serviceContext);

			copyFolder(srcSubfolder, destSubfolder, serviceContext);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(IGFolderServiceImpl.class);

}