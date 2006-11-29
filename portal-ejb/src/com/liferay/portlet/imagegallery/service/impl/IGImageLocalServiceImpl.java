/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.portlet.imagegallery.service.IGImageLocalService;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinder;
import com.liferay.portlet.imagegallery.service.persistence.IGImagePK;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ImageUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * <a href="IGImageLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IGImageLocalServiceImpl implements IGImageLocalService {

	public IGImage addImage(
			String userId, String folderId, String description, File file,
			String contentType, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addImage(
			userId, folderId, description, file, contentType,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public IGImage addImage(
			String userId, String folderId, String description, File file,
			String contentType, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addImage(
			userId, folderId, description, file, contentType, null, null,
			communityPermissions, guestPermissions);
	}

	public IGImage addImage(
			String userId, String folderId, String description, File file,
			String contentType, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		try {

			// Image

			User user = UserUtil.findByPrimaryKey(userId);
			IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);
			BufferedImage bufferedImage = ImageIO.read(file);
			byte[] bytes = FileUtil.getBytes(file);
			Date now = new Date();

			validate(file, bytes);

			String imageId = Long.toString(CounterLocalServiceUtil.increment(
				IGImage.class.getName() + "." + user.getCompanyId()));

			IGImagePK pk = new IGImagePK(user.getCompanyId(), imageId);

			IGImage image = IGImageUtil.create(pk);

			image.setCompanyId(pk.companyId);
			image.setUserId(user.getUserId());
			image.setCreateDate(now);
			image.setModifiedDate(now);
			image.setFolderId(folderId);
			image.setDescription(description);
			image.setHeight(bufferedImage.getHeight());
			image.setWidth(bufferedImage.getWidth());
			image.setSize(bytes.length);

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

			return image;
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	public void addImageResources(
			String folderId, String imageId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);
		IGImage image = IGImageUtil.findByPrimaryKey(
			new IGImagePK(folder.getCompanyId(), imageId));

		addImageResources(
			folder, image, addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
			IGFolder folder, IGImage image, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			image.getCompanyId(), folder.getGroupId(), image.getUserId(),
			IGImage.class.getName(), image.getPrimaryKey().toString(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
			String folderId, String imageId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);
		IGImage image = IGImageUtil.findByPrimaryKey(
			new IGImagePK(folder.getCompanyId(), imageId));

		addImageResources(
			folder, image, communityPermissions, guestPermissions);
	}

	public void addImageResources(
			IGFolder folder, IGImage image, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			image.getCompanyId(), folder.getGroupId(), image.getUserId(),
			IGImage.class.getName(), image.getPrimaryKey().toString(),
			communityPermissions, guestPermissions);
	}

	public void deleteImage(String companyId, String imageId)
		throws PortalException, SystemException {

		IGImage image = IGImageUtil.findByPrimaryKey(
			new IGImagePK(companyId, imageId));

		deleteImage(image);
	}

	public void deleteImage(IGImage image)
		throws PortalException, SystemException {

		// Images

		ImageLocalUtil.remove(image.getLargeImageId());
		ImageLocalUtil.remove(image.getSmallImageId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			image.getCompanyId(), IGImage.class.getName(),
			ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
			image.getPrimaryKey().toString());

		// Image

		IGImageUtil.remove(image.getPrimaryKey());
	}

	public void deleteImages(String folderId)
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

	public List getGroupImages(String groupId, int begin, int end)
		throws SystemException {

		return IGImageFinder.findByGroupId(groupId, begin, end);
	}

	public List getGroupImages(
			String groupId, String userId, int begin, int end)
		throws SystemException {

		if (Validator.isNull(userId)) {
			return IGImageFinder.findByGroupId(groupId, begin, end);
		}
		else {
			return IGImageFinder.findByG_U(groupId, userId, begin, end);
		}
	}

	public int getGroupImagesCount(String groupId) throws SystemException {
		return IGImageFinder.countByGroupId(groupId);
	}

	public int getGroupImagesCount(String groupId, String userId)
		throws SystemException {

		if (Validator.isNull(userId)) {
			return IGImageFinder.countByGroupId(groupId);
		}
		else {
			return IGImageFinder.countByG_U(groupId, userId);
		}
	}

	public IGImage getImage(String companyId, String imageId)
		throws PortalException, SystemException {

		return IGImageUtil.findByPrimaryKey(
			new IGImagePK(companyId, imageId));
	}

	public List getImages(String folderId) throws SystemException {
		return IGImageUtil.findByFolderId(folderId);
	}

	public List getImages(String folderId, int begin, int end)
		throws SystemException {

		return IGImageUtil.findByFolderId(folderId, begin, end);
	}

	public List getImages(
			String folderId, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return IGImageUtil.findByFolderId(folderId, begin, end, obc);
	}

	public int getImagesCount(String folderId) throws SystemException {
		return IGImageUtil.countByFolderId(folderId);
	}

	public IGImage updateImage(
			String companyId, String imageId, String folderId,
			String description, File file, String contentType)
		throws PortalException, SystemException {

		try {

			// Image

			IGImage image = IGImageUtil.findByPrimaryKey(
				new IGImagePK(companyId, imageId));

			IGFolder folder = getFolder(image, folderId);
			BufferedImage bufferedImage = ImageIO.read(file);
			byte[] bytes = FileUtil.getBytes(file);

			validate(file, bytes);

			image.setModifiedDate(new Date());
			image.setFolderId(folder.getFolderId());
			image.setDescription(description);
			image.setHeight(bufferedImage.getHeight());
			image.setWidth(bufferedImage.getWidth());
			image.setSize(bytes.length);

			IGImageUtil.update(image);

			// Images

			saveImages(
				image.getLargeImageId(), bufferedImage, image.getSmallImageId(),
				file, bytes, contentType);

			return image;
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	protected IGFolder getFolder(IGImage image, String folderId)
		throws PortalException, SystemException {

		if (!image.getFolderId().equals(folderId)) {
			IGFolder oldFolder = IGFolderUtil.findByPrimaryKey(
				image.getFolderId());

			IGFolder newFolder = IGFolderUtil.fetchByPrimaryKey(folderId);

			if ((newFolder == null) ||
				(!oldFolder.getGroupId().equals(newFolder.getGroupId()))) {

				folderId = image.getFolderId();
			}
		}

		return IGFolderUtil.findByPrimaryKey(folderId);
	}

	protected void saveImages(
			String imageKey, BufferedImage bufferedImage, String thumbnailKey,
			File file, byte[] bytes, String contentType)
		throws SystemException {

		try {

			// Image

			ImageLocalUtil.put(imageKey, bytes);

			// Thumbnail

			int thumbnailMaxHeight = GetterUtil.getInteger(
				PropsUtil.get(PropsUtil.IG_IMAGE_THUMBNAIL_MAX_HEIGHT));

			int thumbnailMaxWidth = GetterUtil.getInteger(
				PropsUtil.get(PropsUtil.IG_IMAGE_THUMBNAIL_MAX_WIDTH));

			BufferedImage thumbnail = ImageUtil.scale(
				bufferedImage, thumbnailMaxHeight, thumbnailMaxWidth);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			if (contentType.indexOf("gif") != -1) {
				ImageUtil.encodeGIF(thumbnail, baos);
			}
			else if (contentType.indexOf("jpg") != -1 ||
					 contentType.indexOf("jpeg") != -1) {

				ImageIO.write(thumbnail, "jpeg", baos);
			}
			else if (contentType.indexOf("png") != -1) {
				ImageIO.write(thumbnail, "png", baos);
			}

			ImageLocalUtil.put(thumbnailKey, baos.toByteArray());
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