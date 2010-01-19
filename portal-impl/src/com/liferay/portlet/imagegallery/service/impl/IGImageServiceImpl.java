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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.base.IGImageServiceBaseImpl;
import com.liferay.portlet.imagegallery.service.permission.IGFolderPermission;
import com.liferay.portlet.imagegallery.service.permission.IGImagePermission;

import java.io.File;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="IGImageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
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
			null, getUserId(), groupId, folderId, name, description, file,
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
		throws PortalException, SystemException {

		List<IGImage> images = igImageLocalService.getImages(groupId, folderId);

		images = ListUtil.copy(images);

		Iterator<IGImage> itr = images.iterator();

		while (itr.hasNext()) {
			IGImage image = itr.next();

			if (!IGImagePermission.contains(
					getPermissionChecker(), image, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return images;
	}

	public List<IGImage> getImagesByPermission(
			PermissionChecker permissionChecker, long groupId,
			long folderId, String actionId)
		throws PortalException, SystemException {

		List<IGImage> images = igImageLocalService.getImages(groupId, folderId);

		images = ListUtil.copy(images);

		Iterator<IGImage> itr = images.iterator();

		while (itr.hasNext()) {
			IGImage image = itr.next();

			if (!IGImagePermission.contains(
					permissionChecker, image, actionId)) {

				itr.remove();
			}
		}

		return images;
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