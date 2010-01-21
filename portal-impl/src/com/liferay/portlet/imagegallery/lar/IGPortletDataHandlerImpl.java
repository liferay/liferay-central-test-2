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

package com.liferay.portlet.imagegallery.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.BasePortletDataHandler;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * <a href="IGPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class IGPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportImage(
			PortletDataContext context, Element foldersEl, Element imagesEl,
			IGImage image)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(image.getModifiedDate())) {
			return;
		}

		if (foldersEl != null) {
			exportParentFolder(context, foldersEl, image.getFolderId());
		}

		String path = getImagePath(context, image);

		if (context.isPathNotProcessed(path)) {
			Element imageEl = imagesEl.addElement("image");

			imageEl.addAttribute("path", path);
			imageEl.addAttribute("bin-path", getImageBinPath(context, image));

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(IGImage.class, image.getImageId());
			}

			image.setUserUuid(image.getUserUuid());

			Image largeImage = ImageUtil.findByPrimaryKey(
				image.getLargeImageId());

			image.setImageType(largeImage.getType());

			context.addZipEntry(
				getImageBinPath(context, image), largeImage.getTextObj());

			context.addZipEntry(path, image);
		}
	}

	public static void importFolder(
			PortletDataContext context, Map<Long, Long> folderPKs,
			IGFolder folder)
		throws Exception {

		long userId = context.getUserId(folder.getUserUuid());
		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(context.getGroupId());

		if ((parentFolderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(context, parentFolderId);

			IGFolder parentFolder = (IGFolder)context.getZipEntryAsObject(path);

			importFolder(context, folderPKs, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		IGFolder existingFolder = null;

		try {
			if (parentFolderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				IGFolderUtil.findByPrimaryKey(parentFolderId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				existingFolder = IGFolderUtil.fetchByUUID_G(
					folder.getUuid(), context.getGroupId());

				if (existingFolder == null) {
					String name = getFolderName(
						context.getCompanyId(), context.getGroupId(),
						parentFolderId, folder.getName(), 2);

					existingFolder = IGFolderLocalServiceUtil.addFolder(
						folder.getUuid(), userId, parentFolderId, name,
						folder.getDescription(), serviceContext);
				}
				else {
					existingFolder = IGFolderLocalServiceUtil.updateFolder(
						existingFolder.getFolderId(), parentFolderId,
						folder.getName(), folder.getDescription(), false,
						serviceContext);
				}
			}
			else {
				String name = getFolderName(
					context.getCompanyId(), context.getGroupId(),
					parentFolderId, folder.getName(), 2);

				existingFolder = IGFolderLocalServiceUtil.addFolder(
					null, userId, parentFolderId, name, folder.getDescription(),
					serviceContext);
			}

			folderPKs.put(folder.getFolderId(), existingFolder.getFolderId());
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for folder " +
					folder.getFolderId());
		}
	}

	public static void importImage(
			PortletDataContext context, Map<Long, Long> folderPKs,
			IGImage image, String binPath)
		throws Exception {

		long userId = context.getUserId(image.getUserUuid());
		long groupId = context.getGroupId();
		long folderId = MapUtil.getLong(
			folderPKs, image.getFolderId(), image.getFolderId());

		File imageFile = null;

		byte[] bytes = context.getZipEntryAsByteArray(binPath);

		if (bytes == null) {
			_log.error(
				"Could not find image file for image " + image.getImageId());

			return;
		}
		else {
			imageFile = File.createTempFile(
				String.valueOf(image.getPrimaryKey()),
				StringPool.PERIOD + image.getImageType());

			FileUtil.write(imageFile, bytes);
		}

		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				IGImage.class, image.getImageId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetTagNames(assetTagNames);

		if ((folderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == image.getFolderId())) {

			String path = getImportFolderPath(context, folderId);

			IGFolder folder = (IGFolder)context.getZipEntryAsObject(path);

			importFolder(context, folderPKs, folder);

			folderId = MapUtil.getLong(
				folderPKs, image.getFolderId(), image.getFolderId());
		}

		IGImage existingImage = null;

		try {
			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					existingImage = IGImageUtil.findByUUID_G(
						image.getUuid(), groupId);

					IGImageLocalServiceUtil.updateImage(
						userId, existingImage.getImageId(), groupId, folderId,
						image.getName(), image.getDescription(), imageFile,
						image.getImageType(), serviceContext);
				}
				catch (NoSuchImageException nsie) {
					IGImageLocalServiceUtil.addImage(
						image.getUuid(), userId, groupId, folderId,
						image.getName(), image.getDescription(), imageFile,
						image.getImageType(), serviceContext);
				}
			}
			else {
				IGImageLocalServiceUtil.addImage(
					null, userId, groupId, folderId, image.getName(),
					image.getDescription(), imageFile, image.getImageType(),
					serviceContext);
			}
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for image " +
					image.getImageId());
		}
	}

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					IGPortletDataHandlerImpl.class, "deleteData")) {

				IGFolderLocalServiceUtil.deleteFolders(context.getGroupId());
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("image-gallery");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element foldersEl = root.addElement("folders");
			Element imagesEl = root.addElement("images");

			List<IGFolder> folders = IGFolderUtil.findByGroupId(
				context.getGroupId());

			for (IGFolder folder : folders) {
				exportFolder(context, foldersEl, imagesEl, folder);
			}

			List<IGImage> images = IGImageUtil.findByG_F(
				context.getGroupId(),
				IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			for (IGImage image : images) {
				exportImage(context, null, imagesEl, image);
			}

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_foldersAndImages, _tags};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_foldersAndImages, _tags};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> folderEls = root.element("folders").elements(
				"folder");

			Map<Long, Long> folderPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(IGFolder.class);

			for (Element folderEl : folderEls) {
				String path = folderEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				IGFolder folder = (IGFolder)context.getZipEntryAsObject(path);

				importFolder(context, folderPKs, folder);
			}

			List<Element> imageEls = root.element("images").elements("image");

			for (Element imageEl : imageEls) {
				String path = imageEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				IGImage image = (IGImage)context.getZipEntryAsObject(path);

				String binPath = imageEl.attributeValue("bin-path");

				importImage(context, folderPKs, image, binPath);
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected static void exportFolder(
			PortletDataContext context, Element foldersEl, Element imagesEl,
			IGFolder folder)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(context, foldersEl, folder.getParentFolderId());

			String path = getFolderPath(context, folder.getFolderId());

			if (context.isPathNotProcessed(path)) {
				Element folderEl = foldersEl.addElement("folder");

				folderEl.addAttribute("path", path);

				folder.setUserUuid(folder.getUserUuid());

				context.addZipEntry(path, folder);
			}
		}

		List<IGImage> images = IGImageUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (IGImage image : images) {
			exportImage(context, foldersEl, imagesEl, image);
		}
	}

	protected static void exportParentFolder(
			PortletDataContext context, Element foldersEl, long folderId)
		throws PortalException, SystemException {

		if (folderId == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(context, foldersEl, folder.getParentFolderId());

		String path = getFolderPath(context, folder.getFolderId());

		if (context.isPathNotProcessed(path)) {
			Element folderEl = foldersEl.addElement("folder");

			folderEl.addAttribute("path", path);

			folder.setUserUuid(folder.getUserUuid());

			context.addZipEntry(path, folder);
		}
	}

	protected static String getFolderName(
			long companyId, long groupId, long parentFolderId, String name,
			int count)
		throws SystemException {

		IGFolder folder = IGFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Pattern.matches(".* \\(\\d+\\)", name)) {
			int pos = name.lastIndexOf(" (");

			name = name.substring(0, pos);
		}

		StringBuilder sb = new StringBuilder();

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(count);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();

		return getFolderName(companyId, groupId, parentFolderId, name, ++count);
	}

	protected static String getFolderPath(
		PortletDataContext context, long folderId) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.IMAGE_GALLERY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImageBinPath(
		PortletDataContext context, IGImage image) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.IMAGE_GALLERY));
		sb.append("/bin/");
		sb.append(image.getImageId());
		sb.append(StringPool.PERIOD);
		sb.append(image.getImageType());

		return sb.toString();
	}

	protected static String getImagePath(
		PortletDataContext context, IGImage image) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.IMAGE_GALLERY));
		sb.append("/images/");
		sb.append(image.getImageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext context, long folderId) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getSourcePortletPath(PortletKeys.IMAGE_GALLERY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	private static final String _NAMESPACE = "image_gallery";

	private static final PortletDataHandlerBoolean _foldersAndImages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-images", true, true);

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log =
		LogFactoryUtil.getLog(IGPortletDataHandlerImpl.class);

}