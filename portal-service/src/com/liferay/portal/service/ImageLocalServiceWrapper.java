/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQuery(dynamicQuery, start, end);
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