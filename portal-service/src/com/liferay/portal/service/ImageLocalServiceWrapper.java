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

package com.liferay.portal.service;


/**
 * <a href="ImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ImageLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ImageLocalService
 * @generated
 */
public class ImageLocalServiceWrapper implements ImageLocalService {
	public ImageLocalServiceWrapper(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	public com.liferay.portal.model.Image addImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.addImage(image);
	}

	public com.liferay.portal.model.Image createImage(long imageId) {
		return _imageLocalService.createImage(imageId);
	}

	public void deleteImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_imageLocalService.deleteImage(imageId);
	}

	public void deleteImage(com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		_imageLocalService.deleteImage(image);
	}

	public java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Image getImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImage(imageId);
	}

	public java.util.List<com.liferay.portal.model.Image> getImages(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImages(start, end);
	}

	public int getImagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImagesCount();
	}

	public com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(image);
	}

	public com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(image, merge);
	}

	public com.liferay.portal.model.Image getCompanyLogo(long imageId) {
		return _imageLocalService.getCompanyLogo(imageId);
	}

	public com.liferay.portal.model.Image getDefaultCompanyLogo() {
		return _imageLocalService.getDefaultCompanyLogo();
	}

	public com.liferay.portal.model.Image getDefaultOrganizationLogo() {
		return _imageLocalService.getDefaultOrganizationLogo();
	}

	public com.liferay.portal.model.Image getDefaultSpacer() {
		return _imageLocalService.getDefaultSpacer();
	}

	public com.liferay.portal.model.Image getDefaultUserFemalePortrait() {
		return _imageLocalService.getDefaultUserFemalePortrait();
	}

	public com.liferay.portal.model.Image getDefaultUserMalePortrait() {
		return _imageLocalService.getDefaultUserMalePortrait();
	}

	public com.liferay.portal.model.Image getImage(byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImage(bytes);
	}

	public com.liferay.portal.model.Image getImage(java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImage(file);
	}

	public com.liferay.portal.model.Image getImage(java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImage(is);
	}

	public com.liferay.portal.model.Image getImageOrDefault(long imageId) {
		return _imageLocalService.getImageOrDefault(imageId);
	}

	public java.util.List<com.liferay.portal.model.Image> getImages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImages();
	}

	public java.util.List<com.liferay.portal.model.Image> getImagesBySize(
		int size) throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImagesBySize(size);
	}

	public boolean isNullOrDefaultSpacer(byte[] bytes) {
		return _imageLocalService.isNullOrDefaultSpacer(bytes);
	}

	public com.liferay.portal.model.Image updateImage(long imageId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(imageId, bytes);
	}

	public com.liferay.portal.model.Image updateImage(long imageId,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(imageId, file);
	}

	public com.liferay.portal.model.Image updateImage(long imageId,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(imageId, is);
	}

	public com.liferay.portal.model.Image updateImage(long imageId,
		byte[] bytes, java.lang.String type, int height, int width, int size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(imageId, bytes, type, height,
			width, size);
	}

	public ImageLocalService getWrappedImageLocalService() {
		return _imageLocalService;
	}

	private ImageLocalService _imageLocalService;
}