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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ByteArrayMaker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.imagegallery.ImageNameException;
import com.liferay.portlet.imagegallery.ImageSizeException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.base.IGImageLocalServiceBaseImpl;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinder;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.ImageUtil;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * <a href="IGImageLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGImageLocalServiceImpl extends IGImageLocalServiceBaseImpl {

	public IGImage addImage(
			long userId, long folderId, String description, File file,
			String contentType, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addImage(
			userId, folderId, description, file, contentType, tagsEntries,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public IGImage addImage(
			long userId, long folderId, String description, File file,
			String contentType, String[] tagsEntries,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addImage(
			userId, folderId, description, file, contentType, tagsEntries,
			null, null, communityPermissions, guestPermissions);
	}

	public IGImage addImage(
			long userId, long folderId, String description, File file,
			String contentType, String[] tagsEntries,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		try {

			// Image

			User user = UserUtil.findByPrimaryKey(userId);
			IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);
			BufferedImage bufferedImage = ImageIO.read(file);
			byte[] bytes = FileUtil.getBytes(file);
			Date now = new Date();

			validate(file, bytes);

			long imageId = CounterLocalServiceUtil.increment();

			IGImage image = IGImageUtil.create(imageId);

			image.setCompanyId(user.getCompanyId());
			image.setUserId(user.getUserId());
			image.setCreateDate(now);
			image.setModifiedDate(now);
			image.setFolderId(folderId);
			image.setDescription(description);
			image.setSmallImageId(CounterLocalServiceUtil.increment());
			image.setLargeImageId(CounterLocalServiceUtil.increment());

			IGImageUtil.update(image);

			// Images

			saveImages(
				image.getLargeImageId(), bufferedImage, image.getSmallImageId(),
				file, bytes, contentType);

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

			updateTagsAsset(image, tagsEntries);

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

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);
		IGImage image = IGImageUtil.findByPrimaryKey(imageId);

		addImageResources(
			folder, image, addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
			IGFolder folder, IGImage image, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			image.getCompanyId(), folder.getGroupId(), image.getUserId(),
			IGImage.class.getName(), image.getImageId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
			long folderId, long imageId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);
		IGImage image = IGImageUtil.findByPrimaryKey(imageId);

		addImageResources(
			folder, image, communityPermissions, guestPermissions);
	}

	public void addImageResources(
			IGFolder folder, IGImage image, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			image.getCompanyId(), folder.getGroupId(), image.getUserId(),
			IGImage.class.getName(), image.getImageId(), communityPermissions,
			guestPermissions);
	}

	public void deleteImage(long imageId)
		throws PortalException, SystemException {

		IGImage image = IGImageUtil.findByPrimaryKey(imageId);

		deleteImage(image);
	}

	public void deleteImage(IGImage image)
		throws PortalException, SystemException {

		// Tags

		TagsAssetLocalServiceUtil.deleteAsset(
			IGImage.class.getName(), image.getImageId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			image.getCompanyId(), IGImage.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, image.getImageId());

		// Images

		ImageLocalUtil.deleteImage(image.getSmallImageId());
		ImageLocalUtil.deleteImage(image.getLargeImageId());

		// Image

		IGImageUtil.remove(image.getPrimaryKey());
	}

	public void deleteImages(long folderId)
		throws PortalException, SystemException {

		Iterator itr = IGImageUtil.findByFolderId(folderId).iterator();

		while (itr.hasNext()) {
			IGImage image = (IGImage)itr.next();

			deleteImage(image);
		}
	}

	public int getFoldersImagesCount(List folderIds)
		throws SystemException {

		return IGImageFinder.countByFolderIds(folderIds);
	}

	public List getGroupImages(long groupId, int begin, int end)
		throws SystemException {

		return IGImageFinder.findByGroupId(groupId, begin, end);
	}

	public List getGroupImages(long groupId, long userId, int begin, int end)
		throws SystemException {

		if (userId <= 0) {
			return IGImageFinder.findByGroupId(groupId, begin, end);
		}
		else {
			return IGImageFinder.findByG_U(groupId, userId, begin, end);
		}
	}

	public int getGroupImagesCount(long groupId) throws SystemException {
		return IGImageFinder.countByGroupId(groupId);
	}

	public int getGroupImagesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return IGImageFinder.countByGroupId(groupId);
		}
		else {
			return IGImageFinder.countByG_U(groupId, userId);
		}
	}

	public IGImage getImage(long imageId)
		throws PortalException, SystemException {

		return IGImageUtil.findByPrimaryKey(imageId);
	}

	public List getImages(long folderId) throws SystemException {
		return IGImageUtil.findByFolderId(folderId);
	}

	public List getImages(long folderId, int begin, int end)
		throws SystemException {

		return IGImageUtil.findByFolderId(folderId, begin, end);
	}

	public List getImages(
			long folderId, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return IGImageUtil.findByFolderId(folderId, begin, end, obc);
	}

	public int getImagesCount(long folderId) throws SystemException {
		return IGImageUtil.countByFolderId(folderId);
	}

	public IGImage updateImage(
			long imageId, long folderId, String description, File file,
			String contentType, String[] tagsEntries)
		throws PortalException, SystemException {

		try {

			// Image

			IGImage image = IGImageUtil.findByPrimaryKey(imageId);

			IGFolder folder = getFolder(image, folderId);

			BufferedImage bufferedImage = null;
			byte[] bytes = null;

			if (file != null) {
				if (file.exists()) {
					bufferedImage = ImageIO.read(file);
					bytes = FileUtil.getBytes(file);
				}

				validate(file, bytes);
			}

			image.setModifiedDate(new Date());
			image.setFolderId(folder.getFolderId());
			image.setDescription(description);

			IGImageUtil.update(image);

			// Images

			if (bufferedImage != null) {
				saveImages(
					image.getLargeImageId(), bufferedImage,
					image.getSmallImageId(), file, bytes, contentType);
			}

			// Tags

			updateTagsAsset(image, tagsEntries);

			return image;
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	public void updateTagsAsset(IGImage image, String[] tagsEntries)
		throws PortalException, SystemException {

		Image largeImage = ImageLocalUtil.getImage(image.getLargeImageId());

		TagsAssetLocalServiceUtil.updateAsset(
			image.getUserId(), IGImage.class.getName(), image.getImageId(),
			tagsEntries, null, null, null, null, largeImage.getType(),
			image.getDescription(), image.getDescription(),
			image.getDescription(), null, largeImage.getHeight(),
			largeImage.getWidth());
	}

	protected IGFolder getFolder(IGImage image, long folderId)
		throws PortalException, SystemException {

		if (image.getFolderId() != folderId) {
			IGFolder oldFolder = IGFolderUtil.findByPrimaryKey(
				image.getFolderId());

			IGFolder newFolder = IGFolderUtil.fetchByPrimaryKey(folderId);

			if ((newFolder == null) ||
				(oldFolder.getGroupId() != newFolder.getGroupId())) {

				folderId = image.getFolderId();
			}
		}

		return IGFolderUtil.findByPrimaryKey(folderId);
	}

	protected void saveImages(
			long largeImageId, BufferedImage bufferedImage, long smallImageId,
			File file, byte[] bytes, String contentType)
		throws SystemException {

		try {

			// Image

			ImageLocalUtil.updateImage(largeImageId, bytes);

			// Thumbnail

			int thumbnailMaxHeight = GetterUtil.getInteger(
				PropsUtil.get(PropsUtil.IG_IMAGE_THUMBNAIL_MAX_HEIGHT));

			int thumbnailMaxWidth = GetterUtil.getInteger(
				PropsUtil.get(PropsUtil.IG_IMAGE_THUMBNAIL_MAX_WIDTH));

			BufferedImage thumbnail = ImageUtil.scale(
				bufferedImage, thumbnailMaxHeight, thumbnailMaxWidth);

			ByteArrayMaker bam = new ByteArrayMaker();

			if (contentType.indexOf("gif") != -1) {
				ImageUtil.encodeGIF(thumbnail, bam);
			}
			else if (contentType.indexOf("jpg") != -1 ||
					 contentType.indexOf("jpeg") != -1) {

				ImageIO.write(thumbnail, "jpeg", bam);
			}
			else if (contentType.indexOf("png") != -1) {
				ImageIO.write(thumbnail, "png", bam);
			}

			ImageLocalUtil.updateImage(smallImageId, bam.toByteArray());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void validate(File file, byte[] bytes)
		throws PortalException, SystemException {

		String imageName = StringPool.BLANK;

		if (file != null) {
			imageName = file.getName();
		}

		boolean validImageExtension = false;

		String[] imageExtensions =
			PropsUtil.getArray(PropsUtil.IG_IMAGE_EXTENSIONS);

		for (int i = 0; i < imageExtensions.length; i++) {
			if (StringPool.STAR.equals(imageExtensions[i]) ||
				StringUtil.endsWith(imageName, imageExtensions[i])) {

				validImageExtension = true;

				break;
			}
		}

		if (!validImageExtension) {
			throw new ImageNameException(imageName);
		}

		long imageMaxSize = GetterUtil.getLong(
			PropsUtil.get(PropsUtil.IG_IMAGE_MAX_SIZE));

		if ((imageMaxSize > 0) &&
			((bytes == null) || (bytes.length > imageMaxSize))) {

			throw new ImageSizeException();
		}
	}

}