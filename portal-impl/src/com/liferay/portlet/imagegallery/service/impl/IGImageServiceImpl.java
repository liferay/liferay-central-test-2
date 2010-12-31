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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.base.IGImageServiceBaseImpl;
import com.liferay.portlet.imagegallery.service.permission.IGFolderPermission;
import com.liferay.portlet.imagegallery.service.permission.IGImagePermission;
import com.liferay.portlet.imagegallery.util.comparator.ImageModifiedDateComparator;

import java.io.File;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class IGImageServiceImpl extends IGImageServiceBaseImpl {

	public IGImage addImage(
			long groupId, long folderId, String name, String description,
			File file, String contentType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		IGFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_IMAGE);

		return igImageLocalService.addImage(
			getUserId(), groupId, folderId, name, description, file,
			contentType, serviceContext);
	}

	public void deleteImage(long imageId)
		throws PortalException, SystemException {

		IGImagePermission.check(
			getPermissionChecker(), imageId, ActionKeys.DELETE);

		igImageLocalService.deleteImage(imageId);
	}

	public void deleteImageByFolderIdAndNameWithExtension(
			long groupId, long folderId, String nameWithExtension)
		throws PortalException, SystemException {

		IGImage image =
			igImageLocalService.getImageByFolderIdAndNameWithExtension(
				groupId, folderId, nameWithExtension);

		deleteImage(image.getImageId());
	}

	public int getFoldersImagesCount(long groupId, List<Long> folderIds)
		throws SystemException {

		return igImagePersistence.filterCountByG_F(
			groupId,
			ArrayUtil.toArray(folderIds.toArray(new Long[folderIds.size()])));
	}

	public List<IGImage> getGroupImages(
			long groupId, long userId, int start, int end)
		throws SystemException {

		OrderByComparator orderByComparator = new ImageModifiedDateComparator();

		if (userId <= 0) {
			return igImagePersistence.filterFindByGroupId(
				groupId, start, end, orderByComparator);
		}
		else {
			return igImagePersistence.filterFindByG_U(
				groupId, userId, start, end, orderByComparator);
		}
	}

	public int getGroupImagesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return igImagePersistence.filterCountByGroupId(groupId);
		}
		else {
			return igImagePersistence.filterCountByG_U(groupId, userId);
		}
	}

	public IGImage getImage(long imageId)
		throws PortalException, SystemException {

		IGImagePermission.check(
			getPermissionChecker(), imageId, ActionKeys.VIEW);

		return igImageLocalService.getImage(imageId);
	}

	public IGImage getImageByFolderIdAndNameWithExtension(
			long groupId, long folderId, String nameWithExtension)
		throws PortalException, SystemException {

		IGImage image =
			igImageLocalService.getImageByFolderIdAndNameWithExtension(
				groupId, folderId, nameWithExtension);

		IGImagePermission.check(
			getPermissionChecker(), image, ActionKeys.VIEW);

		return image;
	}

	public IGImage getImageByLargeImageId(long largeImageId)
		throws PortalException, SystemException {

		IGImage image = igImageLocalService.getImageByLargeImageId(
			largeImageId);

		IGImagePermission.check(
			getPermissionChecker(), image.getImageId(), ActionKeys.VIEW);

		return image;
	}

	public IGImage getImageBySmallImageId(long smallImageId)
		throws PortalException, SystemException {

		IGImage image = igImageLocalService.getImageBySmallImageId(
			smallImageId);

		IGImagePermission.check(
			getPermissionChecker(), image.getImageId(), ActionKeys.VIEW);

		return image;
	}

	public List<IGImage> getImages(long groupId, long folderId)
		throws SystemException {

		return igImagePersistence.filterFindByG_F(groupId, folderId);
	}

	public List<IGImage> getImages(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return igImagePersistence.filterFindByG_F(
			groupId, folderId, start, end);
	}

	public int getImagesCount(long groupId, long folderId)
		throws SystemException {

		return igImagePersistence.filterCountByG_F(groupId, folderId);
	}

	public IGImage updateImage(
			long imageId, long groupId, long folderId, String name,
			String description, File file, String contentType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		IGImagePermission.check(
			getPermissionChecker(), imageId, ActionKeys.UPDATE);

		return igImageLocalService.updateImage(
			getUserId(), imageId, groupId, folderId, name, description, file,
			contentType, serviceContext);
	}

}