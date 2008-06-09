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
 * <a href="ImageLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portal.service.impl.ImageLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ImageLocalServiceFactory
 * @see com.liferay.portal.service.ImageLocalServiceUtil
 *
 */
public interface ImageLocalService {
	public com.liferay.portal.model.Image addImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException;

	public void deleteImage(long imageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteImage(com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image getImage(long imageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image getCompanyLogo(long imageId);

	public com.liferay.portal.model.Image getDefaultCompanyLogo();

	public com.liferay.portal.model.Image getDefaultSpacer();

	public com.liferay.portal.model.Image getDefaultUserFemalePortrait();

	public com.liferay.portal.model.Image getDefaultUserMalePortrait();

	public com.liferay.portal.model.Image getImage(byte[] bytes)
		throws java.io.IOException;

	public com.liferay.portal.model.Image getImage(java.io.File file)
		throws java.io.IOException;

	public com.liferay.portal.model.Image getImage(java.io.InputStream is)
		throws java.io.IOException;

	public com.liferay.portal.model.Image getImageOrDefault(long imageId);

	public java.util.List<com.liferay.portal.model.Image> getImages()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> getImages(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Image> getImagesBySize(
		int size) throws com.liferay.portal.SystemException;

	public boolean isNullOrDefaultSpacer(byte[] bytes);

	public com.liferay.portal.model.Image updateImage(long imageId, byte[] bytes)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image updateImage(long imageId,
		java.io.File file) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image updateImage(long imageId,
		java.io.InputStream is) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Image updateImage(long imageId,
		byte[] bytes, java.lang.String type, int height, int width, int size)
		throws com.liferay.portal.SystemException;
}