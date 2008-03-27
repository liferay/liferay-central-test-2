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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.util.ByteArrayMaker;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.imagegallery.DuplicateImageNameException;
import com.liferay.portlet.imagegallery.ImageNameException;
import com.liferay.portlet.imagegallery.ImageSizeException;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.portlet.imagegallery.service.base.IGImageLocalServiceBaseImpl;
import com.liferay.portlet.imagegallery.util.Indexer;
import com.liferay.util.FileUtil;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="IGImageLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGImageLocalServiceImpl extends IGImageLocalServiceBaseImpl {

	public IGImage addImage(
			long userId, long folderId, String name, String description,
			File file, String contentType, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addImage(
			null, userId, folderId, name, description, file, contentType,
			tagsEntries, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public IGImage addImage(
			String uuid, long userId, long folderId, String name,
			String description, File file, String contentType,
			String[] tagsEntries, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addImage(
			uuid, userId, folderId, name, description, file, contentType,
			tagsEntries, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public IGImage addImage(
			long userId, long folderId, String name, String description,
			File file, String contentType, String[] tagsEntries,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addImage(
			null, userId, folderId, name, description, file, contentType,
			tagsEntries, null, null, communityPermissions, guestPermissions);
	}

	public IGImage addImage(
			String uuid, long userId, long folderId, String name,
			String description, File file, String contentType,
			String[] tagsEntries, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		try {

			// Image

			String extension = FileUtil.getExtension(file.getName());

			if (Validator.isNotNull(name) &&
				StringUtil.endsWith(name, extension)) {

				name = FileUtil.stripExtension(name);
			}

			String nameWithExtension = name + StringPool.PERIOD + extension;
			byte[] bytes = FileUtil.getBytes(file);

			validate(folderId, nameWithExtension, file, bytes);

			User user = userPersistence.findByPrimaryKey(userId);
			IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);
			RenderedImage renderedImage = ImageProcessorUtil.read(
				file).getRenderedImage();
			Date now = new Date();

			long imageId = counterLocalService.increment();

			if (Validator.isNull(name)) {
				name = String.valueOf(imageId);
			}

			IGImage image = igImagePersistence.create(imageId);

			image.setUuid(uuid);
			image.setCompanyId(user.getCompanyId());
			image.setUserId(user.getUserId());
			image.setCreateDate(now);
			image.setModifiedDate(now);
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

			igImagePersistence.update(image, false);

			// Images

			saveImages(
				image.getLargeImageId(), renderedImage, image.getSmallImageId(),
				image.getCustom1ImageId(), image.getCustom2ImageId(), file,
				bytes, contentType);

			// Resources

			if ((addCommunityPermissions != null) &&
				(addGuestPermissions != null)) {

				addImageResources(
					folder, image, addCommunityPermissions.booleanValue(),
					addGuestPermissions.booleanValue());
			}
			else {
				addImageResources(
					folder, image, communityPermissions, guestPermissions);
			}

			// Tags

			updateTagsAsset(userId, image, tagsEntries);

			// Lucene

			try {
				Indexer.addImage(
					image.getCompanyId(), folder.getGroupId(), folderId,
					imageId, name, description, tagsEntries);
			}
			catch (IOException ioe) {
				_log.error("Indexing " + imageId, ioe);
			}

			return image;
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	public void addImageResources(
			long folderId, long imageId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);
		IGImage image = igImagePersistence.findByPrimaryKey(imageId);

		addImageResources(
			folder, image, addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
			IGFolder folder, IGImage image, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			image.getCompanyId(), folder.getGroupId(), image.getUserId(),
			IGImage.class.getName(), image.getImageId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
			long folderId, long imageId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);
		IGImage image = igImagePersistence.findByPrimaryKey(imageId);

		addImageResources(
			folder, image, communityPermissions, guestPermissions);
	}

	public void addImageResources(
			IGFolder folder, IGImage image, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			image.getCompanyId(), folder.getGroupId(), image.getUserId(),
			IGImage.class.getName(), image.getImageId(), communityPermissions,
			guestPermissions);
	}

	public void deleteImage(long imageId)
		throws PortalException, SystemException {

		IGImage image = igImagePersistence.findByPrimaryKey(imageId);

		deleteImage(image);
	}

	public void deleteImage(IGImage image)
		throws PortalException, SystemException {

		// Lucene

		try {
			Indexer.deleteImage(image.getCompanyId(), image.getImageId());
		}
		catch (IOException ioe) {
			_log.error("Deleting index " + image.getImageId(), ioe);
		}

		// Tags

		tagsAssetLocalService.deleteAsset(
			IGImage.class.getName(), image.getImageId());

		// Resources

		resourceLocalService.deleteResource(
			image.getCompanyId(), IGImage.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, image.getImageId());

		// Images

		ImageLocalUtil.deleteImage(image.getSmallImageId());
		ImageLocalUtil.deleteImage(image.getLargeImageId());

		// Image

		igImagePersistence.remove(image.getPrimaryKey());
	}

	public void deleteImages(long folderId)
		throws PortalException, SystemException {

		List<IGImage> images = igImagePersistence.findByFolderId(folderId);

		for (IGImage image : images) {
			deleteImage(image);
		}
	}

	public int getFoldersImagesCount(List<Long> folderIds)
		throws SystemException {

		return igImageFinder.countByFolderIds(folderIds);
	}

	public List<IGImage> getGroupImages(long groupId, int begin, int end)
		throws SystemException {

		return igImageFinder.findByGroupId(groupId, begin, end);
	}

	public List<IGImage> getGroupImages(
			long groupId, long userId, int begin, int end)
		throws SystemException {

		if (userId <= 0) {
			return igImageFinder.findByGroupId(groupId, begin, end);
		}
		else {
			return igImageFinder.findByG_U(groupId, userId, begin, end);
		}
	}

	public int getGroupImagesCount(long groupId) throws SystemException {
		return igImageFinder.countByGroupId(groupId);
	}

	public int getGroupImagesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return igImageFinder.countByGroupId(groupId);
		}
		else {
			return igImageFinder.countByG_U(groupId, userId);
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
			long folderId, String nameWithExtension)
		throws PortalException, SystemException {

		String name = FileUtil.stripExtension(nameWithExtension);

		List<IGImage> images = igImagePersistence.findByF_N(folderId, name);

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

		return igImageFinder.findByUuid_G(uuid, groupId);
	}

	public List<IGImage> getImages(long folderId) throws SystemException {
		return igImagePersistence.findByFolderId(folderId);
	}

	public List<IGImage> getImages(long folderId, int begin, int end)
		throws SystemException {

		return igImagePersistence.findByFolderId(folderId, begin, end);
	}

	public List<IGImage> getImages(
			long folderId, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return igImagePersistence.findByFolderId(folderId, begin, end, obc);
	}

	public int getImagesCount(long folderId) throws SystemException {
		return igImagePersistence.countByFolderId(folderId);
	}

	public List<IGImage> getNoAssetImages() throws SystemException {
		return igImageFinder.findByNoAssets();
	}

	public IGImage updateImage(
			long userId, long imageId, long folderId, String name,
			String description, File file, String contentType,
			String[] tagsEntries)
		throws PortalException, SystemException {

		try {

			// Image

			IGImage image = igImagePersistence.findByPrimaryKey(imageId);

			IGFolder folder = getFolder(image, folderId);

			RenderedImage renderedImage = null;
			byte[] bytes = null;

			if ((file != null) && file.exists()) {
				renderedImage = ImageProcessorUtil.read(
					file).getRenderedImage();
				bytes = FileUtil.getBytes(file);

				validate(bytes);
			}

			if (Validator.isNotNull(name) && !name.equals(image.getName())) {
				String nameWithExtension = IGImageImpl.getNameWithExtension(
					name, image.getImageType());

				validate(folderId, nameWithExtension);
			}
			else {
				name = image.getName();
			}

			image.setModifiedDate(new Date());
			image.setFolderId(folder.getFolderId());
			image.setName(name);
			image.setDescription(description);

			igImagePersistence.update(image, false);

			// Images

			if (renderedImage != null) {
				saveImages(
					image.getLargeImageId(), renderedImage,
					image.getSmallImageId(), image.getCustom1ImageId(),
					image.getCustom2ImageId(), file, bytes, contentType);
			}

			// Tags

			updateTagsAsset(userId, image, tagsEntries);

			// Lucene

			try {
				Indexer.updateImage(
					image.getCompanyId(), folder.getGroupId(),
					folder.getFolderId(), imageId, name, description,
					tagsEntries);
			}
			catch (IOException ioe) {
				_log.error("Indexing " + imageId, ioe);
			}

			return image;
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	public void updateTagsAsset(
			long userId, IGImage image, String[] tagsEntries)
		throws PortalException, SystemException {

		Image largeImage = ImageLocalUtil.getImage(image.getLargeImageId());

		if (largeImage == null) {
			return;
		}

		tagsAssetLocalService.updateAsset(
			userId, image.getFolder().getGroupId(), IGImage.class.getName(),
			image.getImageId(), tagsEntries, null, null, null, null,
			largeImage.getType(), null, image.getDescription(), null, null,
			largeImage.getHeight(), largeImage.getWidth(), null, false);
	}

	protected IGFolder getFolder(IGImage image, long folderId)
		throws PortalException, SystemException {

		if (image.getFolderId() != folderId) {
			IGFolder oldFolder = igFolderPersistence.findByPrimaryKey(
				image.getFolderId());

			IGFolder newFolder = igFolderPersistence.fetchByPrimaryKey(
				folderId);

			if ((newFolder == null) ||
				(oldFolder.getGroupId() != newFolder.getGroupId())) {

				folderId = image.getFolderId();
			}
		}

		return igFolderPersistence.findByPrimaryKey(folderId);
	}

	protected void saveImages(
			long largeImageId, RenderedImage renderedImage, long smallImageId,
			long custom1ImageId, long custom2ImageId, File file, byte[] bytes,
			String contentType)
		throws SystemException {

		try {

			// Image

			ImageLocalUtil.updateImage(largeImageId, bytes);

			// Thumbnail and custom sizes

			saveScaledImage(
				renderedImage, smallImageId, contentType,
				PropsValues.IG_IMAGE_THUMBNAIL_MAX_DIMENSION);

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
		throws IOException, SystemException {

		RenderedImage thumbnail = ImageProcessorUtil.scale(
			renderedImage, dimension, dimension);

		ByteArrayMaker bam = new ByteArrayMaker();

		if (contentType.indexOf("bmp") != -1) {
			ImageEncoder encoder = ImageCodec.createImageEncoder(
				"BMP", bam, null);

			encoder.encode(thumbnail);
		}
		else if (contentType.indexOf("gif") != -1) {
			ImageProcessorUtil.encodeGIF(thumbnail, bam);
		}
		else if (contentType.indexOf("jpg") != -1 ||
				 contentType.indexOf("jpeg") != -1) {

			ImageIO.write(thumbnail, "jpeg", bam);
		}
		else if (contentType.indexOf("png") != -1) {
			ImageIO.write(thumbnail, "png", bam);
		}
		else if (contentType.indexOf("tif") != -1) {
			ImageEncoder encoder = ImageCodec.createImageEncoder(
				"TIFF", bam, null);

			encoder.encode(thumbnail);
		}

		ImageLocalUtil.updateImage(imageId, bam.toByteArray());
	}

	protected void validate(byte[] bytes) throws ImageSizeException {
		if ((PropsValues.IG_IMAGE_MAX_SIZE > 0) &&
			((bytes == null) ||
			 (bytes.length > PropsValues.IG_IMAGE_MAX_SIZE))) {

			throw new ImageSizeException();
		}
	}

	protected void validate(long folderId, String nameWithExtension)
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
			(nameWithExtension.indexOf("&") != -1) ||
			(nameWithExtension.indexOf("[") != -1) ||
			(nameWithExtension.indexOf("]") != -1) ||
			(nameWithExtension.indexOf("'") != -1)) {

			throw new ImageNameException();
		}

		boolean validImageExtension = false;

		String[] imageExtensions =
			PropsUtil.getArray(PropsUtil.IG_IMAGE_EXTENSIONS);

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

		List<IGImage> images = igImagePersistence.findByF_N(folderId, name);

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
			long folderId, String nameWithExtension, File file, byte[] bytes)
		throws PortalException, SystemException {

		if (file != null) {
			String fileName = file.getName();
			String extension = FileUtil.getExtension(fileName);

			if (Validator.isNull(nameWithExtension)) {
				nameWithExtension = fileName;
			}
			else if (!StringUtil.endsWith(nameWithExtension, extension)) {
				throw new ImageNameException();
			}
		}

		validate(folderId, nameWithExtension);
		validate(bytes);
	}

	private static Log _log = LogFactory.getLog(IGImageLocalServiceImpl.class);

}