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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ImageLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ImageLocalService
 * @generated
 */
public class ImageLocalServiceUtil {
	public static com.liferay.portal.model.Image addImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addImage(image);
	}

	public static com.liferay.portal.model.Image createImage(long imageId) {
		return getService().createImage(imageId);
	}

	public static void deleteImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteImage(imageId);
	}

	public static void deleteImage(com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteImage(image);
	}

	public static java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Image getImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getImage(imageId);
	}

	public static java.util.List<com.liferay.portal.model.Image> getImages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getImages(start, end);
	}

	public static int getImagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getImagesCount();
	}

	public static com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateImage(image);
	}

	public static com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateImage(image, merge);
	}

	public static com.liferay.portal.model.Image getCompanyLogo(long imageId) {
		return getService().getCompanyLogo(imageId);
	}

	public static com.liferay.portal.model.Image getDefaultCompanyLogo() {
		return getService().getDefaultCompanyLogo();
	}

	public static com.liferay.portal.model.Image getDefaultOrganizationLogo() {
		return getService().getDefaultOrganizationLogo();
	}

	public static com.liferay.portal.model.Image getDefaultSpacer() {
		return getService().getDefaultSpacer();
	}

	public static com.liferay.portal.model.Image getDefaultUserFemalePortrait() {
		return getService().getDefaultUserFemalePortrait();
	}

	public static com.liferay.portal.model.Image getDefaultUserMalePortrait() {
		return getService().getDefaultUserMalePortrait();
	}

	public static com.liferay.portal.model.Image getImage(byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getImage(bytes);
	}

	public static com.liferay.portal.model.Image getImage(java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getImage(file);
	}

	public static com.liferay.portal.model.Image getImage(
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getImage(is);
	}

	public static com.liferay.portal.model.Image getImageOrDefault(long imageId) {
		return getService().getImageOrDefault(imageId);
	}

	public static java.util.List<com.liferay.portal.model.Image> getImages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getImages();
	}

	public static java.util.List<com.liferay.portal.model.Image> getImagesBySize(
		int size) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getImagesBySize(size);
	}

	public static boolean isNullOrDefaultSpacer(byte[] bytes) {
		return getService().isNullOrDefaultSpacer(bytes);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateImage(imageId, bytes);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateImage(imageId, file);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateImage(imageId, is);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		byte[] bytes, java.lang.String type, int height, int width, int size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateImage(imageId, bytes, type, height, width, size);
	}

	public static ImageLocalService getService() {
		if (_service == null) {
			_service = (ImageLocalService)PortalBeanLocatorUtil.locate(ImageLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ImageLocalService service) {
		_service = service;
	}

	private static ImageLocalService _service;
}