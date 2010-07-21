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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.base.IGFolderServiceBaseImpl;
import com.liferay.portlet.imagegallery.service.permission.IGFolderPermission;

import java.io.File;

import java.util.List;

/**
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
		throws SystemException {

		return igFolderPersistence.filterFindByG_P(groupId, parentFolderId);
	}

	public List<IGFolder> getFolders(
			long groupId, long parentFolderId,  int start, int end)
		throws SystemException {

		return igFolderPersistence.filterFindByG_P(
			groupId, parentFolderId, start, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return igFolderPersistence.filterCountByG_P(groupId, parentFolderId);
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