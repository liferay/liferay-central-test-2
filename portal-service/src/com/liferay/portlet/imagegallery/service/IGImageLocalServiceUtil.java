/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="IGImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.imagegallery.service.IGImageLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.imagegallery.service.IGImageLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.imagegallery.service.IGImageLocalService
 * @see com.liferay.portlet.imagegallery.service.IGImageLocalServiceFactory
 *
 */
public class IGImageLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage addImage(
		long userId, java.lang.String folderId, java.lang.String description,
		java.io.File file, java.lang.String contentType,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.addImage(userId, folderId, description,
			file, contentType, tagsEntries, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage addImage(
		long userId, java.lang.String folderId, java.lang.String description,
		java.io.File file, java.lang.String contentType,
		java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.addImage(userId, folderId, description,
			file, contentType, tagsEntries, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage addImage(
		long userId, java.lang.String folderId, java.lang.String description,
		java.io.File file, java.lang.String contentType,
		java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.addImage(userId, folderId, description,
			file, contentType, tagsEntries, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addImageResources(java.lang.String folderId,
		java.lang.String imageId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
		igImageLocalService.addImageResources(folderId, imageId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addImageResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		com.liferay.portlet.imagegallery.model.IGImage image,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
		igImageLocalService.addImageResources(folder, image,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addImageResources(java.lang.String folderId,
		java.lang.String imageId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
		igImageLocalService.addImageResources(folderId, imageId,
			communityPermissions, guestPermissions);
	}

	public static void addImageResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		com.liferay.portlet.imagegallery.model.IGImage image,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
		igImageLocalService.addImageResources(folder, image,
			communityPermissions, guestPermissions);
	}

	public static void deleteImage(long companyId, java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
		igImageLocalService.deleteImage(companyId, imageId);
	}

	public static void deleteImage(
		com.liferay.portlet.imagegallery.model.IGImage image)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
		igImageLocalService.deleteImage(image);
	}

	public static void deleteImages(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
		igImageLocalService.deleteImages(folderId);
	}

	public static int getFoldersImagesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getFoldersImagesCount(folderIds);
	}

	public static java.util.List getGroupImages(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getGroupImages(groupId, begin, end);
	}

	public static java.util.List getGroupImages(long groupId, long userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getGroupImages(groupId, userId, begin, end);
	}

	public static int getGroupImagesCount(long groupId)
		throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getGroupImagesCount(groupId);
	}

	public static int getGroupImagesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getGroupImagesCount(groupId, userId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImage(
		long companyId, java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getImage(companyId, imageId);
	}

	public static java.util.List getImages(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getImages(folderId);
	}

	public static java.util.List getImages(java.lang.String folderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getImages(folderId, begin, end);
	}

	public static java.util.List getImages(java.lang.String folderId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getImages(folderId, begin, end, obc);
	}

	public static int getImagesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.getImagesCount(folderId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long companyId, java.lang.String imageId, java.lang.String folderId,
		java.lang.String description, java.io.File file,
		java.lang.String contentType, java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

		return igImageLocalService.updateImage(companyId, imageId, folderId,
			description, file, contentType, tagsEntries);
	}
}