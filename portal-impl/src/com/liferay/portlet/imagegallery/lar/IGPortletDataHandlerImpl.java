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

package com.liferay.portlet.imagegallery.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="IGPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class IGPortletDataHandlerImpl implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {

			// Folders

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
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("image-gallery");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element foldersEl = root.addElement("folders");

			Element imagesEl = root.addElement("images");

			List<IGFolder> folders = IGFolderUtil.findByGroupId(
				context.getGroupId());

			for (IGFolder igFolder : folders) {
				exportFolder(context, foldersEl, imagesEl, igFolder);
			}

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_foldersAndImages, _tags};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_foldersAndImages, _tags};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		try {
			Document doc = DocumentUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			// Folders

			List<Element> folderEls =
				root.element("folders").elements("folder");

			Map<Long, Long> folderPKs = context.getNewPrimaryKeysMap(
				IGFolder.class);

			for (Element el : folderEls) {
				String path = el.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					IGFolder folder =
						(IGFolder)context.getZipEntryAsObject(path);

					importFolder(context, folderPKs, folder);
				}
			}

			// IGImages

			List<Element> entryEls = root.element("images").elements("image");

			for (Element el : entryEls) {
				String path = el.attributeValue("path");
				String binPath = el.attributeValue("bin-path");

				if (context.isPathNotProcessed(path)) {
					IGImage igImage =
						(IGImage)context.getZipEntryAsObject(path);

					importImage(context, folderPKs, igImage, binPath);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return false;
	}

	public static void exportFolder(
			PortletDataContext context, Element foldersEl, Element imagesEl,
			IGFolder igFolder)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(igFolder.getModifiedDate())) {
			String path = getFolderPath(context, igFolder);

			foldersEl.addElement("folder").addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				igFolder.setUserUuid(igFolder.getUserUuid());

				context.addZipEntry(path, igFolder);
			}

			exportParentFolder(
				context, foldersEl, igFolder.getParentFolderId());
		}

		List<IGImage> igImages =
			IGImageUtil.findByFolderId(igFolder.getFolderId());

		for (IGImage igImage : igImages) {
			exportImage(context, foldersEl, imagesEl, igImage);
		}
	}

	public static void exportImage(
			PortletDataContext context, Element foldersEl, Element imagesEl,
			IGImage image)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(image.getModifiedDate())) {
			String path = getImagePath(context, image);

			Element imageEl = imagesEl.addElement("image");
			imageEl.addAttribute("path", path);
			imageEl.addAttribute("bin-path", getImageBinPath(context, image));

			if (context.isPathNotProcessed(path)) {
				if (context.getBooleanParameter(_NAMESPACE, "tags")) {
					context.addTagsEntries(
						IGImage.class, image.getPrimaryKeyObj());
				}

				image.setUserUuid(image.getUserUuid());

				Image largeImage = ImageUtil.findByPrimaryKey(
					image.getLargeImageId());

				image.setImageType(largeImage.getType());

				context.addZipEntry(
					getImageBinPath(context, image), largeImage.getTextObj());

				context.addZipEntry(path, image);
			}

			exportParentFolder(context, foldersEl, image.getFolderId());
		}
	}

	protected static void exportParentFolder(
			PortletDataContext context, Element foldersEl,
			long folderId)
		throws PortalException, SystemException {

		if (context.hasDateRange() &&
				(folderId != IGFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {
			IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

			String path = getFolderPath(context, folder);

			foldersEl.addElement("folder").addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				folder.setUserUuid(folder.getUserUuid());

				context.addZipEntry(path, folder);
			}

			exportParentFolder(context, foldersEl, folder.getParentFolderId());
		}
	}

	protected void importFolder(
			PortletDataContext context, Map<Long, Long> folderPKs,
			IGFolder igFolder)
		throws Exception {

		long userId = context.getUserId(igFolder.getUserUuid());
		long plid = context.getPlid();
		long parentFolderId = MapUtil.getLong(
			folderPKs, igFolder.getParentFolderId(), igFolder.getParentFolderId());

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		IGFolder existingFolder = null;

		try {
			if (parentFolderId != IGFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
				IGFolderUtil.findByPrimaryKey(parentFolderId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				existingFolder = IGFolderUtil.fetchByUUID_G(
					igFolder.getUuid(), context.getGroupId());

				if (existingFolder == null) {
					String name = getFolderName(
						context.getCompanyId(), context.getGroupId(),
						parentFolderId, igFolder.getName(), 2);

					existingFolder = IGFolderLocalServiceUtil.addFolder(
						igFolder.getUuid(), userId, plid, parentFolderId,
						name, igFolder.getDescription(), addCommunityPermissions,
						addGuestPermissions);
				}
				else {
					existingFolder =
						IGFolderLocalServiceUtil.updateFolder(
						existingFolder.getFolderId(), parentFolderId,
						igFolder.getName(), igFolder.getDescription(),
						false);
				}
			}
			else {
				String name = getFolderName(
					context.getCompanyId(), context.getGroupId(),
					parentFolderId, igFolder.getName(), 2);

				existingFolder = IGFolderLocalServiceUtil.addFolder(
					userId, plid, parentFolderId, name, igFolder.getDescription(),
					addCommunityPermissions, addGuestPermissions);
			}

			folderPKs.put(igFolder.getFolderId(), existingFolder.getFolderId());
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for folder " +
					igFolder.getFolderId());
		}
	}

	protected void importImage(
			PortletDataContext context, Map<Long, Long> folderPKs,
			IGImage igImage, String binPath)
		throws Exception {

		long userId = context.getUserId(igImage.getUserUuid());
		long folderId = MapUtil.getLong(
			folderPKs, igImage.getFolderId(), igImage.getFolderId());

		File imageFile = null;

		byte[] byteArray = context.getZipEntryAsByteArray(binPath);

		if (byteArray == null) {
			_log.error(
				"Could not find image for IG image " + igImage.getImageId());

			return;
		}
		else {
			imageFile = File.createTempFile(
				String.valueOf(igImage.getPrimaryKey()),
				StringPool.PERIOD + igImage.getImageType());

			FileUtil.write(imageFile, byteArray);
		}

		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
				IGImage.class, igImage.getPrimaryKeyObj());
		}

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		IGImage existingImage = null;

		try {
			IGFolderUtil.findByPrimaryKey(folderId);

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					existingImage = IGImageFinderUtil.findByUuid_G(
						igImage.getUuid(), context.getGroupId());

					IGImageLocalServiceUtil.updateImage(
						userId, existingImage.getImageId(), folderId,
						igImage.getName(), igImage.getDescription(), imageFile,
						igImage.getImageType(), tagsEntries);
				}
				catch (NoSuchImageException nsie) {
					IGImageLocalServiceUtil.addImage(
						igImage.getUuid(), userId, folderId,
						igImage.getName(), igImage.getDescription(), imageFile,
						igImage.getImageType(), tagsEntries,
						addCommunityPermissions, addGuestPermissions);
				}
			}
			else {
				IGImageLocalServiceUtil.addImage(
					userId, folderId, igImage.getName(),
					igImage.getDescription(), imageFile, igImage.getImageType(),
					tagsEntries, addCommunityPermissions, addGuestPermissions);
			}
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for IG image " +
					igImage.getImageId());
		}
	}

	protected String getFolderName(
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

		StringMaker sm = new StringMaker();

		sm.append(name);
		sm.append(StringPool.SPACE);
		sm.append(StringPool.OPEN_PARENTHESIS);
		sm.append(count);
		sm.append(StringPool.CLOSE_PARENTHESIS);

		name = sm.toString();

		return getFolderName(companyId, groupId, parentFolderId, name, ++count);
	}

	protected static String getFolderPath(
			PortletDataContext context, IGFolder folder) {
		return context.getPortletPath(PortletKeys.IMAGE_GALLERY) + "/folders/" +
			folder.getFolderId() + ".xml";
	}

	protected static String getImageBinPath(
			PortletDataContext context, IGImage image) {
		return context.getPortletPath(PortletKeys.IMAGE_GALLERY) +
			"/bin/" + image.getImageId() + CharPool.PERIOD +
				image.getImageType();
	}

	protected static String getImagePath(
			PortletDataContext context, IGImage image) {
		return context.getPortletPath(PortletKeys.IMAGE_GALLERY) + "/images/" +
			image.getImageId() + ".xml";
	}

	private static final String _NAMESPACE = "image_gallery";

	private static final PortletDataHandlerBoolean _foldersAndImages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-images", true, true);

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log = LogFactory.getLog(IGPortletDataHandlerImpl.class);

}