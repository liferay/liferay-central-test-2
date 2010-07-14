/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.imagegallery.DuplicateImageNameException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.util.PwdGenerator;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class IGPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportImage(
			PortletDataContext context, Element foldersElement,
			Element imagesElement, IGImage image)
		throws Exception {

		if (!context.isWithinDateRange(image.getModifiedDate())) {
			return;
		}

		if (foldersElement != null) {
			exportParentFolder(context, foldersElement, image.getFolderId());
		}

		String path = getImagePath(context, image);

		if (context.isPathNotProcessed(path)) {
			Element imageElement = imagesElement.addElement("image");

			imageElement.addAttribute("path", path);
			imageElement.addAttribute(
				"bin-path", getImageBinPath(context, image));

			if (context.getBooleanParameter(_NAMESPACE, "categories")) {
				context.addAssetCategories(IGImage.class, image.getImageId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.addRatingsEntries(IGImage.class, image.getImageId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(IGImage.class, image.getImageId());
			}

			image.setUserUuid(image.getUserUuid());

			Image largeImage = ImageUtil.findByPrimaryKey(
				image.getLargeImageId());

			image.setImageType(largeImage.getType());

			context.addPermissions(IGImage.class, image.getImageId());

			context.addZipEntry(
				getImageBinPath(context, image), largeImage.getTextObj());

			context.addZipEntry(path, image);
		}
	}

	public static String getImagePath(
		PortletDataContext context, IGImage image) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.IMAGE_GALLERY));
		sb.append("/images/");
		sb.append(image.getImageId());
		sb.append(".xml");

		return sb.toString();
	}

	public static void importFolder(
			PortletDataContext context, Element folderElement)
		throws Exception {

		String path = folderElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		IGFolder folder = (IGFolder)context.getZipEntryAsObject(path);

		importFolder(context, folder);
	}

	public static void importImage(
			PortletDataContext context, Element imageElement)
		throws Exception {

		String path = imageElement.attributeValue("path");

		String binPath = imageElement.attributeValue("bin-path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		IGImage image = (IGImage)context.getZipEntryAsObject(path);

		importImage(context, image, binPath);
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndImages, _categories, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndImages, _categories, _ratings, _tags
		};
	}

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	public boolean isPublishToLiveByDefault() {
		return PropsValues.DL_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected static void importFolder(
			PortletDataContext context, IGFolder folder)
		throws Exception {

		long userId = context.getUserId(folder.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(IGFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(folder.getCreateDate());
		serviceContext.setModifiedDate(folder.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		if ((parentFolderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(context, parentFolderId);

			IGFolder parentFolder = (IGFolder)context.getZipEntryAsObject(path);

			importFolder(context, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		IGFolder importedFolder = null;

		if (context.isDataStrategyMirror()) {
			IGFolder existingFolder = IGFolderUtil.fetchByUUID_G(
				folder.getUuid(), context.getScopeGroupId());

			if (existingFolder == null) {
				String name = getFolderName(
					context.getCompanyId(), context.getScopeGroupId(),
					parentFolderId, folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = IGFolderLocalServiceUtil.addFolder(
					userId, parentFolderId, name, folder.getDescription(),
					serviceContext);
			}
			else {
				importedFolder = IGFolderLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId,
					folder.getName(), folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			String name = getFolderName(
				context.getCompanyId(), context.getScopeGroupId(),
				parentFolderId, folder.getName(), 2);

			importedFolder = IGFolderLocalServiceUtil.addFolder(
				userId, parentFolderId, name, folder.getDescription(),
				serviceContext);
		}

		folderPKs.put(folder.getFolderId(), importedFolder.getFolderId());

		context.importPermissions(
			IGFolder.class, folder.getFolderId(), importedFolder.getFolderId());
	}

	protected static void importImage(
			PortletDataContext context, IGImage image, String binPath)
		throws Exception {

		long userId = context.getUserId(image.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(IGFolder.class);

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

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				IGImage.class, image.getImageId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				IGImage.class, image.getImageId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(image.getCreateDate());
		serviceContext.setModifiedDate(image.getModifiedDate());

		if ((folderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == image.getFolderId())) {

			String path = getImportFolderPath(context, folderId);

			IGFolder folder = (IGFolder)context.getZipEntryAsObject(path);

			importFolder(context, folder);

			folderId = MapUtil.getLong(
				folderPKs, image.getFolderId(), image.getFolderId());
		}

		IGImage importedImage = null;

		if (context.isDataStrategyMirror()) {
			IGImage existingImage = IGImageUtil.fetchByUUID_G(
				image.getUuid(), context.getScopeGroupId());

			if (existingImage == null) {
				serviceContext.setUuid(image.getUuid());

				importedImage = IGImageLocalServiceUtil.addImage(
					userId, context.getScopeGroupId(), folderId,
					image.getName(), image.getDescription(), imageFile,
					image.getImageType(), serviceContext);
			}
			else {
				importedImage = IGImageLocalServiceUtil.updateImage(
					userId, existingImage.getImageId(),
					context.getScopeGroupId(), folderId, image.getName(),
					image.getDescription(), imageFile, image.getImageType(),
					serviceContext);
			}
		}
		else {
			String name = image.getName();

			try {
				importedImage = IGImageLocalServiceUtil.addImage(
					userId, context.getScopeGroupId(), folderId, name,
					image.getDescription(), imageFile, image.getImageType(),
					serviceContext);
			}
			catch (DuplicateImageNameException dine) {
				String[] nameParts = name.split("\\.", 2);

				name = nameParts[0] + PwdGenerator.getPassword();

				if (nameParts.length > 1) {
					name += StringPool.PERIOD + nameParts[1];
				}

				importedImage = IGImageLocalServiceUtil.addImage(
					userId, context.getScopeGroupId(), folderId, name,
					image.getDescription(), imageFile, image.getImageType(),
					serviceContext);
			}
		}

		context.importPermissions(
			IGImage.class, image.getImageId(), importedImage.getImageId());

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				IGImage.class, image.getImageId(), importedImage.getImageId());
		}

		Map<Long, Long> igImagePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(IGImage.class);

		igImagePKs.put(image.getImageId(), importedImage.getImageId());
	}

	protected static void exportFolder(
			PortletDataContext context, Element foldersElement,
			Element imagesElement, IGFolder folder)
		throws Exception {

		if (context.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				context, foldersElement, folder.getParentFolderId());

			String path = getFolderPath(context, folder.getFolderId());

			if (context.isPathNotProcessed(path)) {
				Element folderElement = foldersElement.addElement("folder");

				folderElement.addAttribute("path", path);

				folder.setUserUuid(folder.getUserUuid());

				context.addPermissions(IGFolder.class, folder.getFolderId());

				context.addZipEntry(path, folder);
			}
		}

		List<IGImage> images = IGImageUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (IGImage image : images) {
			exportImage(context, foldersElement, imagesElement, image);
		}
	}

	protected static void exportParentFolder(
			PortletDataContext context, Element foldersElement, long folderId)
		throws Exception {

		if (folderId == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(context, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(context, folder.getFolderId());

		if (context.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			folderElement.addAttribute("path", path);

			folder.setUserUuid(folder.getUserUuid());

			context.addPermissions(IGFolder.class, folder.getFolderId());

			context.addZipEntry(path, folder);
		}
	}

	protected static String getFolderName(
			long companyId, long groupId, long parentFolderId, String name,
			int count)
		throws Exception {

		IGFolder folder = IGFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Pattern.matches(".* \\(\\d+\\)", name)) {
			int pos = name.lastIndexOf(" (");

			name = name.substring(0, pos);
		}

		StringBundler sb = new StringBundler(5);

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

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.IMAGE_GALLERY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImageBinPath(
		PortletDataContext context, IGImage image) {

		StringBundler sb = new StringBundler(5);

		sb.append(context.getPortletPath(PortletKeys.IMAGE_GALLERY));
		sb.append("/bin/");
		sb.append(image.getImageId());
		sb.append(StringPool.PERIOD);
		sb.append(image.getImageType());

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext context, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getSourcePortletPath(PortletKeys.IMAGE_GALLERY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		if (!context.addPrimaryKey(
				IGPortletDataHandlerImpl.class, "deleteData")) {

			IGFolderLocalServiceUtil.deleteFolders(context.getScopeGroupId());

			IGImageLocalServiceUtil.deleteImages(
				context.getScopeGroupId(),
				IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.imagegallery", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("image-gallery");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		Element foldersElement = rootElement.addElement("folders");
		Element imagesElement = rootElement.addElement("images");

		List<IGFolder> folders = IGFolderUtil.findByGroupId(
			context.getScopeGroupId());

		for (IGFolder folder : folders) {
			exportFolder(context, foldersElement, imagesElement, folder);
		}

		List<IGImage> images = IGImageUtil.findByG_F(
			context.getScopeGroupId(),
			IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (IGImage image : images) {
			exportImage(context, null, imagesElement, image);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.imagegallery",
			context.getSourceGroupId(), context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		List<Element> folderElements = foldersElement.elements("folder");

		for (Element folderElement : folderElements) {
			String path = folderElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			IGFolder folder = (IGFolder)context.getZipEntryAsObject(path);

			importFolder(context, folder);
		}

		Element imagesElement = rootElement.element("images");

		List<Element> imageElements = imagesElement.elements("image");

		for (Element imageElement : imageElements) {
			String path = imageElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			IGImage image = (IGImage)context.getZipEntryAsObject(path);

			String binPath = imageElement.attributeValue("bin-path");

			importImage(context, image, binPath);
		}

		return null;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "image_gallery";

	private static Log _log = LogFactoryUtil.getLog(
		IGPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _foldersAndImages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-images", true, true);

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}