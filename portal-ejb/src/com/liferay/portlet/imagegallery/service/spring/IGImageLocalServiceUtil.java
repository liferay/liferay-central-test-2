/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.imagegallery.service.spring;

/**
 * <a href="IGImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IGImageLocalServiceUtil {
	public static com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String description, java.io.File file,
		java.lang.String contentType, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

			return igImageLocalService.addImage(userId, folderId, description,
				file, contentType, addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addImageResources(java.lang.String folderId,
		java.lang.String imageId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
			igImageLocalService.addImageResources(folderId, imageId,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addImageResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		com.liferay.portlet.imagegallery.model.IGImage image,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
			igImageLocalService.addImageResources(folder, image,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteImage(java.lang.String companyId,
		java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
			igImageLocalService.deleteImage(companyId, imageId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteImage(
		com.liferay.portlet.imagegallery.model.IGImage image)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
			igImageLocalService.deleteImage(image);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteImages(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();
			igImageLocalService.deleteImages(folderId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getFoldersImagesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

			return igImageLocalService.getFoldersImagesCount(folderIds);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImage getImage(
		java.lang.String companyId, java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

			return igImageLocalService.getImage(companyId, imageId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getImages(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

			return igImageLocalService.getImages(folderId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getImages(java.lang.String folderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

			return igImageLocalService.getImages(folderId, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getImages(java.lang.String folderId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

			return igImageLocalService.getImages(folderId, begin, end, obc);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getImagesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

			return igImageLocalService.getImagesCount(folderId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateImage(
		java.lang.String companyId, java.lang.String imageId,
		java.lang.String folderId, java.lang.String description,
		java.io.File file, java.lang.String contentType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			IGImageLocalService igImageLocalService = IGImageLocalServiceFactory.getService();

			return igImageLocalService.updateImage(companyId, imageId,
				folderId, description, file, contentType);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}