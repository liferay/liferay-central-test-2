/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service;

/**
 * <p>
 * This class is a wrapper for {@link IGImageLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImageLocalService
 * @generated
 */
public class IGImageLocalServiceWrapper implements IGImageLocalService {
	public IGImageLocalServiceWrapper(IGImageLocalService igImageLocalService) {
		_igImageLocalService = igImageLocalService;
	}

	/**
	* Adds the i g image to the database. Also notifies the appropriate model listeners.
	*
	* @param igImage the i g image to add
	* @return the i g image that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGImage addIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.addIGImage(igImage);
	}

	/**
	* Creates a new i g image with the primary key. Does not add the i g image to the database.
	*
	* @param imageId the primary key for the new i g image
	* @return the new i g image
	*/
	public com.liferay.portlet.imagegallery.model.IGImage createIGImage(
		long imageId) {
		return _igImageLocalService.createIGImage(imageId);
	}

	/**
	* Deletes the i g image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param imageId the primary key of the i g image to delete
	* @throws PortalException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteIGImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteIGImage(imageId);
	}

	/**
	* Deletes the i g image from the database. Also notifies the appropriate model listeners.
	*
	* @param igImage the i g image to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteIGImage(igImage);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the i g image with the primary key.
	*
	* @param imageId the primary key of the i g image to get
	* @return the i g image
	* @throws PortalException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGImage getIGImage(
		long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getIGImage(imageId);
	}

	/**
	* Gets the i g image with the UUID and group id.
	*
	* @param uuid the UUID of i g image to get
	* @param groupId the group id of the i g image to get
	* @return the i g image
	* @throws PortalException if a i g image with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGImage getIGImageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getIGImageByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the i g images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of i g images to return
	* @param end the upper bound of the range of i g images to return (not inclusive)
	* @return the range of i g images
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getIGImages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getIGImages(start, end);
	}

	/**
	* Gets the number of i g images.
	*
	* @return the number of i g images
	* @throws SystemException if a system exception occurred
	*/
	public int getIGImagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getIGImagesCount();
	}

	/**
	* Updates the i g image in the database. Also notifies the appropriate model listeners.
	*
	* @param igImage the i g image to update
	* @return the i g image that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGImage updateIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateIGImage(igImage);
	}

	/**
	* Updates the i g image in the database. Also notifies the appropriate model listeners.
	*
	* @param igImage the i g image to update
	* @param merge whether to merge the i g image with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the i g image that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGImage updateIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateIGImage(igImage, merge);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.addImage(userId, groupId, folderId, name,
			description, file, contentType, serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String description, java.lang.String fileName, byte[] bytes,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.addImage(userId, groupId, folderId, name,
			description, fileName, bytes, contentType, serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		long userId, long groupId, long folderId, java.lang.String name,
		java.lang.String description, java.lang.String fileName,
		java.io.InputStream is, java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.addImage(userId, groupId, folderId, name,
			description, fileName, is, contentType, serviceContext);
	}

	public void addImageResources(
		com.liferay.portlet.imagegallery.model.IGImage image,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.addImageResources(image, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addImageResources(
		com.liferay.portlet.imagegallery.model.IGImage image,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.addImageResources(image, communityPermissions,
			guestPermissions);
	}

	public void addImageResources(long imageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.addImageResources(imageId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(long imageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.addImageResources(imageId, communityPermissions,
			guestPermissions);
	}

	public void deleteImage(
		com.liferay.portlet.imagegallery.model.IGImage image)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteImage(image);
	}

	public void deleteImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteImage(imageId);
	}

	public void deleteImages(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteImages(groupId, folderId);
	}

	public int getFoldersImagesCount(long groupId,
		java.util.List<java.lang.Long> folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getFoldersImagesCount(groupId, folderIds);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getGroupImages(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getGroupImages(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getGroupImages(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getGroupImages(groupId, userId, start, end);
	}

	public int getGroupImagesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getGroupImagesCount(groupId);
	}

	public int getGroupImagesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getGroupImagesCount(groupId, userId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImage(imageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByCustom1ImageId(
		long custom1ImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByCustom1ImageId(custom1ImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByCustom2ImageId(
		long custom2ImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByCustom2ImageId(custom2ImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByFolderIdAndNameWithExtension(
		long groupId, long folderId, java.lang.String nameWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByFolderIdAndNameWithExtension(groupId,
			folderId, nameWithExtension);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByLargeImageId(largeImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageBySmallImageId(smallImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByUuidAndGroupId(uuid, groupId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImages(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImages(groupId, folderId, start, end);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImages(groupId, folderId, start, end, obc);
	}

	public int getImagesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImagesCount(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getNoAssetImages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getNoAssetImages();
	}

	public void updateAsset(long userId,
		com.liferay.portlet.imagegallery.model.IGImage image,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		java.lang.String contentType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.updateAsset(userId, image, assetCategoryIds,
			assetTagNames, contentType);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, byte[] bytes,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateImage(userId, imageId, groupId,
			folderId, name, description, bytes, contentType, serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateImage(userId, imageId, groupId,
			folderId, name, description, file, contentType, serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description,
		java.io.InputStream is, java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateImage(userId, imageId, groupId,
			folderId, name, description, is, contentType, serviceContext);
	}

	public void updateSmallImage(long smallImageId, long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.updateSmallImage(smallImageId, largeImageId);
	}

	public IGImageLocalService getWrappedIGImageLocalService() {
		return _igImageLocalService;
	}

	public void setWrappedIGImageLocalService(
		IGImageLocalService igImageLocalService) {
		_igImageLocalService = igImageLocalService;
	}

	private IGImageLocalService _igImageLocalService;
}