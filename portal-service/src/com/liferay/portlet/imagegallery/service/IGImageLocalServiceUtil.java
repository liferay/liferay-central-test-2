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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="IGImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link IGImageLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImageLocalService
 * @generated
 */
public class IGImageLocalServiceUtil {
	public static com.liferay.portlet.imagegallery.model.IGImage addIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.SystemException {
		return getService().addIGImage(igImage);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage createIGImage(
		long imageId) {
		return getService().createIGImage(imageId);
	}

	public static void deleteIGImage(long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteIGImage(imageId);
	}

	public static void deleteIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.SystemException {
		getService().deleteIGImage(igImage);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getIGImage(
		long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getIGImage(imageId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getIGImages(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getIGImages(start, end);
	}

	public static int getIGImagesCount()
		throws com.liferay.portal.SystemException {
		return getService().getIGImagesCount();
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.SystemException {
		return getService().updateIGImage(igImage);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateIGImage(igImage, merge);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String uuid, long userId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addImage(uuid, userId, groupId, folderId, name,
			description, file, contentType, serviceContext);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String uuid, long userId, long groupId, long folderId,
		java.lang.String name, java.lang.String description,
		java.lang.String fileName, byte[] bytes, java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addImage(uuid, userId, groupId, folderId, name,
			description, fileName, bytes, contentType, serviceContext);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String uuid, long userId, long groupId, long folderId,
		java.lang.String name, java.lang.String description,
		java.lang.String fileName, java.io.InputStream is,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addImage(uuid, userId, groupId, folderId, name,
			description, fileName, is, contentType, serviceContext);
	}

	public static void addImageResources(
		com.liferay.portlet.imagegallery.model.IGImage image,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addImageResources(image, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addImageResources(
		com.liferay.portlet.imagegallery.model.IGImage image,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addImageResources(image, communityPermissions, guestPermissions);
	}

	public static void addImageResources(long imageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addImageResources(imageId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addImageResources(long imageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addImageResources(imageId, communityPermissions, guestPermissions);
	}

	public static void deleteImage(
		com.liferay.portlet.imagegallery.model.IGImage image)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteImage(image);
	}

	public static void deleteImage(long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteImage(imageId);
	}

	public static void deleteImages(long groupId, long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteImages(groupId, folderId);
	}

	public static int getFoldersImagesCount(long groupId,
		java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		return getService().getFoldersImagesCount(groupId, folderIds);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getGroupImages(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupImages(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getGroupImages(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupImages(groupId, userId, start, end);
	}

	public static int getGroupImagesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupImagesCount(groupId);
	}

	public static int getGroupImagesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupImagesCount(groupId, userId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImage(
		long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getImage(imageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageByCustom1ImageId(
		long custom1ImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getImageByCustom1ImageId(custom1ImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageByCustom2ImageId(
		long custom2ImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getImageByCustom2ImageId(custom2ImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageByFolderIdAndNameWithExtension(
		long groupId, long folderId, java.lang.String nameWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getImageByFolderIdAndNameWithExtension(groupId, folderId,
			nameWithExtension);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getImageByLargeImageId(largeImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getImageBySmallImageId(smallImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getImageByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId) throws com.liferay.portal.SystemException {
		return getService().getImages(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getImages(groupId, folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getImages(groupId, folderId, start, end, obc);
	}
	
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> 
		getImagesByPermission(
		com.liferay.portal.security.permission.PermissionChecker permissionChecker, 
		long groupId, long folderId, String actionId) 
		throws com.liferay.portal.PortalException, com.liferay.portal.SystemException {
		return getService().getImagesByPermission(permissionChecker, groupId, 
			folderId, actionId);
	} 

	public static int getImagesCount(long groupId, long folderId)
		throws com.liferay.portal.SystemException {
		return getService().getImagesCount(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getNoAssetImages()
		throws com.liferay.portal.SystemException {
		return getService().getNoAssetImages();
	}

	public static void reIndex(
		com.liferay.portlet.imagegallery.model.IGImage image)
		throws com.liferay.portal.SystemException {
		getService().reIndex(image);
	}

	public static void reIndex(long imageId)
		throws com.liferay.portal.SystemException {
		getService().reIndex(imageId);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.imagegallery.model.IGImage image,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		java.lang.String contentType)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.updateAsset(userId, image, assetCategoryIds, assetTagNames,
			contentType);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, byte[] bytes,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateImage(userId, imageId, groupId, folderId, name,
			description, bytes, contentType, serviceContext);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateImage(userId, imageId, groupId, folderId, name,
			description, file, contentType, serviceContext);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description,
		java.io.InputStream is, java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateImage(userId, imageId, groupId, folderId, name,
			description, is, contentType, serviceContext);
	}

	public static void updateSmallImage(long smallImageId, long largeImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().updateSmallImage(smallImageId, largeImageId);
	}

	public static IGImageLocalService getService() {
		if (_service == null) {
			_service = (IGImageLocalService)PortalBeanLocatorUtil.locate(IGImageLocalService.class.getName());
		}

		return _service;
	}

	public void setService(IGImageLocalService service) {
		_service = service;
	}

	private static IGImageLocalService _service;
}