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
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ImageLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ImageLocalService
 *
 */
public class ImageLocalServiceUtil {
	public static com.liferay.portal.model.Image addImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException {
		return _service.addImage(image);
	}

	public static void deleteImage(long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteImage(imageId);
	}

	public static void deleteImage(com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException {
		_service.deleteImage(image);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Image getImage(long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getImage(imageId);
	}

	public static java.util.List<com.liferay.portal.model.Image> getImages(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getImages(start, end);
	}

	public static int getImagesCount()
		throws com.liferay.portal.SystemException {
		return _service.getImagesCount();
	}

	public static com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException {
		return _service.updateImage(image);
	}

	public static com.liferay.portal.model.Image getCompanyLogo(long imageId) {
		return _service.getCompanyLogo(imageId);
	}

	public static com.liferay.portal.model.Image getDefaultCompanyLogo() {
		return _service.getDefaultCompanyLogo();
	}

	public static com.liferay.portal.model.Image getDefaultSpacer() {
		return _service.getDefaultSpacer();
	}

	public static com.liferay.portal.model.Image getDefaultUserFemalePortrait() {
		return _service.getDefaultUserFemalePortrait();
	}

	public static com.liferay.portal.model.Image getDefaultUserMalePortrait() {
		return _service.getDefaultUserMalePortrait();
	}

	public static com.liferay.portal.model.Image getImage(byte[] bytes)
		throws java.io.IOException {
		return _service.getImage(bytes);
	}

	public static com.liferay.portal.model.Image getImage(java.io.File file)
		throws java.io.IOException {
		return _service.getImage(file);
	}

	public static com.liferay.portal.model.Image getImage(
		java.io.InputStream is) throws java.io.IOException {
		return _service.getImage(is);
	}

	public static com.liferay.portal.model.Image getImageOrDefault(long imageId) {
		return _service.getImageOrDefault(imageId);
	}

	public static java.util.List<com.liferay.portal.model.Image> getImages()
		throws com.liferay.portal.SystemException {
		return _service.getImages();
	}

	public static java.util.List<com.liferay.portal.model.Image> getImagesBySize(
		int size) throws com.liferay.portal.SystemException {
		return _service.getImagesBySize(size);
	}

	public static boolean isNullOrDefaultSpacer(byte[] bytes) {
		return _service.isNullOrDefaultSpacer(bytes);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		byte[] bytes) throws com.liferay.portal.SystemException {
		return _service.updateImage(imageId, bytes);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		java.io.File file) throws com.liferay.portal.SystemException {
		return _service.updateImage(imageId, file);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		java.io.InputStream is) throws com.liferay.portal.SystemException {
		return _service.updateImage(imageId, is);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		byte[] bytes, java.lang.String type, int height, int width, int size)
		throws com.liferay.portal.SystemException {
		return _service.updateImage(imageId, bytes, type, height, width, size);
	}

	public static ImageLocalService getService() {
		return _service;
	}

	public void setService(ImageLocalService service) {
		_service = service;
	}

	private static ImageLocalService _service;
}