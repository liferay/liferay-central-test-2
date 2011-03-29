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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.imagegallery.DuplicateImageNameException;
import com.liferay.portlet.imagegallery.ImageNameException;
import com.liferay.portlet.imagegallery.ImageSizeException;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.portlet.imagegallery.service.base.IGImageLocalServiceBaseImpl;
import com.liferay.portlet.imagegallery.social.IGActivityKeys;
import com.liferay.portlet.imagegallery.util.comparator.ImageModifiedDateComparator;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Alexander Chow
 */
public class IGImageLocalServiceImpl extends IGImageLocalServiceBaseImpl {

	public IGImage addImage(
			long userId, long groupId, long folderId, String name,
			String description, File file, String contentType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			String fileName = file.getName();
			byte[] bytes = FileUtil.getBytes(file);

			return addImage(
				userId, groupId, folderId, name, description, fileName,
				bytes, contentType, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public IGImage addImage(
			long userId, long groupId, long folderId, String name,
			String description, String fileName, byte[] bytes,
			String contentType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {

			// Image

			String extension = FileUtil.getExtension(fileName);

			if (Validator.isNotNull(name) &&
				StringUtil.endsWith(name, extension)) {

				name = FileUtil.stripExtension(name);
			}

			String nameWithExtension = name + StringPool.PERIOD + extension;

			validate(groupId, folderId, nameWithExtension, fileName, bytes);

			User user = userPersistence.findByPrimaryKey(userId);
			RenderedImage renderedImage = ImageProcessorUtil.read(
				bytes).getRenderedImage();
			Date now = new Date();

			long imageId = counterLocalService.increment();

			if (Validator.isNull(name)) {
				name = String.valueOf(imageId);
			}

			IGImage image = igImagePersistence.create(imageId);

			image.setUuid(serviceContext.getUuid());
			image.setGroupId(groupId);
			image.setCompanyId(user.getCompanyId());
			image.setUserId(user.getUserId());
			image.setCreateDate(serviceContext.getCreateDate(now));
			image.setModifiedDate(serviceContext.getModifiedDate(now));
			image.setFolderId(folderId);
			image.setName(name);
			image.setDescription(description);
			image.setSmallImageId(counterLocalService.increment());
			image.setLargeImageId(counterLocalService.increment());

			if (PropsValues.IG_IMAGE_CUSTOM_1_MAX_DIMENSION > 0) {
				image.setCustom1ImageId(counterLocalService.increment());
			}

			if (PropsValues.IG_IMAGE_CUSTOM_2_MAX_DIMENSION > 0) {
				image.setCustom2ImageId(counterLocalService.increment());
			}

			image.setExpandoBridgeAttributes(serviceContext);

			igImagePersistence.update(image, false);

			// Resources

			if (serviceContext.getAddCommunityPermissions() ||
				serviceContext.getAddGuestPermissions()) {

				addImageResources(
					image, serviceContext.getAddCommunityPermissions(),
					serviceContext.getAddGuestPermissions());
			}
			else {
				addImageResources(
					image, serviceContext.getCommunityPermissions(),
					serviceContext.getGuestPermissions());
			}

			// Images

			saveImages(
				image.getLargeImageId(), renderedImage, image.getSmallImageId(),
				image.getCustom1ImageId(), image.getCustom2ImageId(), bytes,
				contentType);

			// Asset

			updateAsset(
				userId, image, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), contentType);

			// Social

			socialActivityLocalService.addActivity(
				userId, image.getGroupId(), IGImage.class.getName(), imageId,
				IGActivityKeys.ADD_IMAGE, StringPool.BLANK, 0);

			// Indexer

			Indexer indexer = IndexerRegistryUtil.getIndexer(IGImage.class);

			indexer.reindex(image);

			return image;
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	public IGImage addImage(
			long userId, long groupId, long folderId, String name,
			String description, String fileName, InputStream is,
			String contentType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			byte[] bytes = FileUtil.getBytes(is);

			return addImage(
				userId, groupId, folderId, name, description, fileName, bytes,
				contentType, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addImageResources(
			IGImage image, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			image.getCompanyId(), image.getGroupId(), image.getUserId(),
			IGImage.class.getName(), image.getImageId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
			IGImage image, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			image.getCompanyId(), image.getGroupId(), image.getUserId(),
			IGImage.class.getName(), image.getImageId(), communityPermissions,
			guestPermissions);
	}

	public void addImageResources(
			long imageId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGImage image = igImagePersistence.findByPrimaryKey(imageId);

		addImageResources(image, addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
			long imageId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		IGImage image = igImagePersistence.findByPrimaryKey(imageId);

		addImageResources(image, communityPermissions, guestPermissions);
	}

	public void deleteImage(IGImage image)
		throws PortalException, SystemException {

		// Image

		igImagePersistence.remove(image);

		// Resources

		resourceLocalService.deleteResource(
			image.getCompanyId(), IGImage.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, image.getImageId());

		// Images

		imageLocalService.deleteImage(image.getSmallImageId());
		imageLocalService.deleteImage(image.getLargeImageId());
		imageLocalService.deleteImage(image.getCustom1ImageId());
		imageLocalService.deleteImage(image.getCustom2ImageId());

		// Asset

		assetEntryLocalService.deleteEntry(
			IGImage.class.getName(), image.getImageId());

		// Expando

		expandoValueLocalService.deleteValues(
			IGImage.class.getName(), image.getImageId());

		// Social

		socialActivityLocalService.deleteActivities(
			IGImage.class.getName(), image.getImageId());

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(IGImage.class);

		indexer.delete(image);
	}

	public void deleteImage(long imageId)
		throws PortalException, SystemException {

		IGImage image = igImagePersistence.findByPrimaryKey(imageId);

		deleteImage(image);
	}

	public void deleteImages(long groupId, long folderId)
		throws PortalException, SystemException {

		List<IGImage> images = igImagePersistence.findByG_F(groupId, folderId);

		for (IGImage image : images) {
			deleteImage(image);
		}
	}

	public int getFoldersImagesCount(long groupId, List<Long> folderIds)
		throws SystemException {

		return igImagePersistence.countByG_F(
			groupId,
			ArrayUtil.toArray(folderIds.toArray(new Long[folderIds.size()])));
	}

	public List<IGImage> getGroupImages(long groupId, int start, int end)
		throws SystemException {

		return igImagePersistence.findByGroupId(
			groupId, start, end, new ImageModifiedDateComparator());
	}

	public List<IGImage> getGroupImages(
			long groupId, long userId, int start, int end)
		throws SystemException {

		OrderByComparator orderByComparator = new ImageModifiedDateComparator();

		if (userId <= 0) {
			return igImagePersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}
		else {
			return igImagePersistence.findByG_U(
				groupId, userId, start, end, orderByComparator);
		}
	}

	public int getGroupImagesCount(long groupId) throws SystemException {
		return igImagePersistence.countByGroupId(groupId);
	}

	public int getGroupImagesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return igImagePersistence.countByGroupId(groupId);
		}
		else {
			return igImagePersistence.countByG_U(groupId, userId);
		}
	}

	public IGImage getImage(long imageId)
		throws PortalException, SystemException {

		return igImagePersistence.findByPrimaryKey(imageId);
	}

	public IGImage getImageByCustom1ImageId(long custom1ImageId)
		throws PortalException, SystemException {

		return igImagePersistence.findByCustom1ImageId(custom1ImageId);
	}

	public IGImage getImageByCustom2ImageId(long custom2ImageId)
		throws PortalException, SystemException {

		return igImagePersistence.findByCustom2ImageId(custom2ImageId);
	}

	public IGImage getImageByFolderIdAndNameWithExtension(
			long groupId, long folderId, String nameWithExtension)
		throws PortalException, SystemException {

		String name = FileUtil.stripExtension(nameWithExtension);

		List<IGImage> images = igImagePersistence.findByG_F_N(
			groupId, folderId, name);

		if ((images.size() <= 0) && Validator.isNumber(name)) {
			long imageId = GetterUtil.getLong(name);

			IGImage image = igImagePersistence.fetchByPrimaryKey(imageId);

			if (image != null) {
				images.add(image);
			}
		}

		for (IGImage image : images) {
			if (nameWithExtension.equals(image.getNameWithExtension())) {
				return image;
			}
		}

		throw new NoSuchImageException();
	}

	public IGImage getImageByLargeImageId(long largeImageId)
		throws PortalException, SystemException {

		return igImagePersistence.findByLargeImageId(largeImageId);
	}

	public IGImage getImageBySmallImageId(long smallImageId)
		throws PortalException, SystemException {

		return igImagePersistence.findBySmallImageId(smallImageId);
	}

	public IGImage getImageByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		return igImagePersistence.findByUUID_G(uuid, groupId);
	}

	public List<IGImage> getImages(long groupId, long folderId)
		throws SystemException {

		return igImagePersistence.findByG_F(groupId, folderId);
	}

	public List<IGImage> getImages(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return igImagePersistence.findByG_F(groupId, folderId, start, end);
	}

	public List<IGImage> getImages(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return igImagePersistence.findByG_F(groupId, folderId, start, end, obc);
	}

	public int getImagesCount(long groupId, long folderId)
		throws SystemException {

		return igImagePersistence.countByG_F(groupId, folderId);
	}

	public List<IGImage> getNoAssetImages() throws SystemException {
		return igImageFinder.findByNoAssets();
	}

	public void updateAsset(
			long userId, IGImage image, long[] assetCategoryIds,
			String[] assetTagNames, String contentType)
		throws PortalException, SystemException {

		Image largeImage = imageLocalService.getImage(image.getLargeImageId());

		if (largeImage == null) {
			return;
		}

		if (contentType == null) {
			contentType = MimeTypesUtil.getContentType(largeImage.getType());
		}

		assetEntryLocalService.updateEntry(
			userId, image.getGroupId(), IGImage.class.getName(),
			image.getImageId(), image.getUuid(), assetCategoryIds,
			assetTagNames, true, null, null, null, null, contentType,
			image.getName(), image.getDescription(), null, null, null,
			largeImage.getHeight(), largeImage.getWidth(), null, false);
	}

	public IGImage updateImage(
			long userId, long imageId, long groupId, long folderId, String name,
			String description, byte[] bytes, String contentType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {

			// Image

			IGImage image = igImagePersistence.findByPrimaryKey(imageId);

			folderId = getFolder(image, folderId);

			RenderedImage renderedImage = null;

			if (bytes != null) {
				renderedImage = ImageProcessorUtil.read(
					bytes).getRenderedImage();

				validate(bytes);
			}

			if (Validator.isNotNull(name) && !name.equals(image.getName())) {
				String nameWithExtension = IGImageImpl.getNameWithExtension(
					name, image.getImageType());

				validate(groupId, folderId, nameWithExtension);
			}
			else {
				name = image.getName();
			}

			image.setModifiedDate(serviceContext.getModifiedDate(null));
			image.setFolderId(folderId);
			image.setName(name);
			image.setDescription(description);
			image.setExpandoBridgeAttributes(serviceContext);

			igImagePersistence.update(image, false);

			// Images

			if (renderedImage != null) {
				saveImages(
					image.getLargeImageId(), renderedImage,
					image.getSmallImageId(), image.getCustom1ImageId(),
					image.getCustom2ImageId(), bytes, contentType);
			}

			// Asset

			long[] assetCategoryIds = serviceContext.getAssetCategoryIds();
			String[] assetTagNames = serviceContext.getAssetTagNames();

			updateAsset(
				userId, image, assetCategoryIds, assetTagNames, contentType);

			// Social

			socialActivityLocalService.addActivity(
				userId, image.getGroupId(), IGImage.class.getName(), imageId,
				IGActivityKeys.UPDATE_IMAGE, StringPool.BLANK, 0);

			// Indexer

			Indexer indexer = IndexerRegistryUtil.getIndexer(IGImage.class);

			indexer.reindex(image);

			return image;
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	public IGImage updateImage(
			long userId, long imageId, long groupId, long folderId, String name,
			String description, File file, String contentType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			byte[] bytes = null;

			if ((file != null) && file.exists()) {
				bytes = FileUtil.getBytes(file);
			}

			return updateImage(
				userId, imageId, groupId, folderId, name, description, bytes,
				contentType, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public IGImage updateImage(
			long userId, long imageId, long groupId, long folderId, String name,
			String description, InputStream is, String contentType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			byte[] bytes = null;

			if (is != null) {
				bytes = FileUtil.getBytes(is);
			}

			return updateImage(
				userId, imageId, groupId, folderId, name, description, bytes,
				contentType, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void updateSmallImage(long smallImageId, long largeImageId)
		throws PortalException, SystemException {

		try {
			RenderedImage renderedImage = null;

			Image largeImage = imageLocalService.getImage(largeImageId);

			byte[] bytes = largeImage.getTextObj();
			String contentType = largeImage.getType();

			if (bytes != null) {
				renderedImage = ImageProcessorUtil.read(
					bytes).getRenderedImage();

				//validate(bytes);
			}

			if (renderedImage != null) {
				saveScaledImage(
					renderedImage, smallImageId, contentType,
					PrefsPropsUtil.getInteger(
						PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION));
			}
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	protected long getFolder(IGImage image, long folderId)
		throws SystemException {

		if ((image.getFolderId() != folderId) &&
			(folderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			IGFolder newFolder = igFolderPersistence.fetchByPrimaryKey(
				folderId);

			if ((newFolder == null) ||
				(image.getGroupId() != newFolder.getGroupId())) {

				folderId = image.getFolderId();
			}
		}

		return folderId;
	}

	protected void saveImages(
			long largeImageId, RenderedImage renderedImage, long smallImageId,
			long custom1ImageId, long custom2ImageId, byte[] bytes,
			String contentType)
		throws PortalException, SystemException {

		try {

			// Image

			imageLocalService.updateImage(largeImageId, bytes);

			// Thumbnail and custom sizes

			saveScaledImage(
				renderedImage, smallImageId, contentType,
				PrefsPropsUtil.getInteger(
					PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION));

			if (custom1ImageId > 0) {
				saveScaledImage(
					renderedImage, custom1ImageId, contentType,
					PropsValues.IG_IMAGE_CUSTOM_1_MAX_DIMENSION);
			}

			if (custom2ImageId > 0) {
				saveScaledImage(
					renderedImage, custom2ImageId, contentType,
					PropsValues.IG_IMAGE_CUSTOM_2_MAX_DIMENSION);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void saveScaledImage(
			RenderedImage renderedImage, long imageId, String contentType,
			int dimension)
		throws IOException, PortalException, SystemException {

		RenderedImage thumbnail = ImageProcessorUtil.scale(
			renderedImage, dimension, dimension);

		imageLocalService.updateImage(
			imageId, ImageProcessorUtil.getBytes(thumbnail, contentType));
	}

	protected void validate(byte[] bytes)
		throws ImageSizeException, SystemException {

		if ((PrefsPropsUtil.getLong(PropsKeys.IG_IMAGE_MAX_SIZE) > 0) &&
			((bytes == null) ||
			 (bytes.length >
				 PrefsPropsUtil.getLong(PropsKeys.IG_IMAGE_MAX_SIZE)))) {

			throw new ImageSizeException();
		}
	}

	protected void validate(
			long groupId, long folderId, String nameWithExtension)
		throws PortalException, SystemException {

		if ((nameWithExtension.indexOf("\\\\") != -1) ||
			(nameWithExtension.indexOf("//") != -1) ||
			(nameWithExtension.indexOf(":") != -1) ||
			(nameWithExtension.indexOf("*") != -1) ||
			(nameWithExtension.indexOf("?") != -1) ||
			(nameWithExtension.indexOf("\"") != -1) ||
			(nameWithExtension.indexOf("<") != -1) ||
			(nameWithExtension.indexOf(">") != -1) ||
			(nameWithExtension.indexOf("|") != -1) ||
			(nameWithExtension.indexOf("[") != -1) ||
			(nameWithExtension.indexOf("]") != -1) ||
			(nameWithExtension.indexOf("'") != -1)) {

			throw new ImageNameException();
		}

		boolean validImageExtension = false;

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.IG_IMAGE_EXTENSIONS, StringPool.COMMA);

		for (int i = 0; i < imageExtensions.length; i++) {
			if (StringPool.STAR.equals(imageExtensions[i]) ||
				StringUtil.endsWith(nameWithExtension, imageExtensions[i])) {

				validImageExtension = true;

				break;
			}
		}

		if (!validImageExtension) {
			throw new ImageNameException();
		}

		String name = FileUtil.stripExtension(nameWithExtension);
		String imageType = FileUtil.getExtension(nameWithExtension);

		List<IGImage> images = igImagePersistence.findByG_F_N(
			groupId, folderId, name);

		if (imageType.equals("jpeg")) {
			imageType = ImageProcessor.TYPE_JPEG;
		}
		else if (imageType.equals("tif")) {
			imageType = ImageProcessor.TYPE_TIFF;
		}

		for (IGImage image : images) {
			if (imageType.equals(image.getImageType())) {
				throw new DuplicateImageNameException();
			}
		}
	}

	protected void validate(
			long groupId, long folderId, String nameWithExtension,
			String fileName, byte[] bytes)
		throws PortalException, SystemException {

		if (Validator.isNotNull(fileName)) {
			String extension = FileUtil.getExtension(fileName);

			if (Validator.isNull(nameWithExtension)) {
				nameWithExtension = fileName;
			}
			else if (!StringUtil.endsWith(nameWithExtension, extension)) {
				throw new ImageNameException();
			}
		}

		validate(groupId, folderId, nameWithExtension);
		validate(bytes);
	}

}