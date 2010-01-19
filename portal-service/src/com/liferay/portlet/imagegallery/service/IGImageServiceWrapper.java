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

package com.liferay.portlet.imagegallery.service;


/**
 * <a href="IGImageServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link IGImageService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImageService
 * @generated
 */
public class IGImageServiceWrapper implements IGImageService {
	public IGImageServiceWrapper(IGImageService igImageService) {
		_igImageService = igImageService;
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		long groupId, long folderId, java.lang.String name,
		java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igImageService.addImage(groupId, folderId, name, description,
			file, contentType, serviceContext);
	}

	public void deleteImage(long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igImageService.deleteImage(imageId);
	}

	public void deleteImageByFolderIdAndNameWithExtension(long groupId,
		long folderId, java.lang.String nameWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_igImageService.deleteImageByFolderIdAndNameWithExtension(groupId,
			folderId, nameWithExtension);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImage(long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igImageService.getImage(imageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByFolderIdAndNameWithExtension(
		long groupId, long folderId, java.lang.String nameWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igImageService.getImageByFolderIdAndNameWithExtension(groupId,
			folderId, nameWithExtension);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igImageService.getImageByLargeImageId(largeImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igImageService.getImageBySmallImageId(smallImageId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igImageService.getImages(groupId, folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long imageId, long groupId, long folderId, java.lang.String name,
		java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _igImageService.updateImage(imageId, groupId, folderId, name,
			description, file, contentType, serviceContext);
	}

	public IGImageService getWrappedIGImageService() {
		return _igImageService;
	}

	private IGImageService _igImageService;
}