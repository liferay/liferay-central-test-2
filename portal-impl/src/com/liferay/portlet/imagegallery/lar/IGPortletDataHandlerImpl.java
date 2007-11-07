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

package com.liferay.portlet.imagegallery.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="IGPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class IGPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_enableExport};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {_enableImport};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(parameterMap, _EXPORT_IG_DATA);

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("image-gallery");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Folders

			List folders = IGFolderUtil.findByGroupId(context.getGroupId());

			List igImages = new ArrayList();

			Iterator itr = folders.iterator();

			while (itr.hasNext()) {
				IGFolder folder = (IGFolder)itr.next();

				if (context.addPrimaryKey(
						IGFolder.class, folder.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					List folderIGImages = IGImageUtil.findByFolderId(
						folder.getFolderId());

					igImages.addAll(folderIGImages);
				}
			}

			String xml = xStream.toXML(folders);

			Element el = root.addElement("ig-folders");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// IGImages

			List images = new ArrayList();

			itr = igImages.iterator();

			while (itr.hasNext()) {
				IGImage igImage = (IGImage)itr.next();

				if (context.addPrimaryKey(
						IGImage.class, igImage.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					Image largeImage = ImageUtil.fetchByPrimaryKey(
						igImage.getLargeImageId());

					images.add(largeImage);

					context.addTagsEntries(
						IGImage.class, igImage.getPrimaryKeyObj());
				}
			}

			xml = xStream.toXML(igImages);

			el = root.addElement("ig-images");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Images

			itr = images.iterator();

			while (itr.hasNext()) {
				Image image = (Image)itr.next();

				if (context.addPrimaryKey(
						Image.class, image.getPrimaryKeyObj())) {

					itr.remove();
				}
			}

			xml = xStream.toXML(images);

			el = root.addElement("images");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean importData = MapUtil.getBoolean(parameterMap, _IMPORT_IG_DATA);

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData) {
			return null;
		}

		boolean mergeData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.MERGE_DATA);

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = reader.read(new StringReader(data));

			Element root = doc.getRootElement();

			// Folders

			Element el = root.element("ig-folders").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map folderPKs = CollectionFactory.getHashMap();

			List folders = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = folders.iterator();

			while (itr.hasNext()) {
				IGFolder folder = (IGFolder)itr.next();

				importFolder(context, mergeData, folderPKs, folder);
			}

			// Images

			el = root.element("images").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map imagesPKs = CollectionFactory.getHashMap();

			List images = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = images.iterator();

			while (itr.hasNext()) {
				Image image = (Image)itr.next();

				imagesPKs.put(image.getPrimaryKeyObj(), image);
			}

			// IGImages

			el = root.element("ig-images").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List igImages = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = igImages.iterator();

			while (itr.hasNext()) {
				IGImage igImage = (IGImage)itr.next();

				importIGImage(
					context, mergeData, folderPKs, imagesPKs, igImage);
			}

			// No special modification to the incoming portlet preferences
			// needed

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importFolder(
			PortletDataContext context, boolean mergeData, Map folderPKs,
			IGFolder folder)
		throws Exception {

		Long parentFolderId = (Long)folderPKs.get(
			new Long(folder.getParentFolderId()));

		if (parentFolderId == null) {
			parentFolderId = new Long(folder.getParentFolderId());
		}

		long plid = context.getPlid();

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		IGFolder existingFolder = null;

		try {
			if (parentFolderId.longValue() !=
					IGFolderImpl.DEFAULT_PARENT_FOLDER_ID) {

				IGFolderUtil.findByPrimaryKey(parentFolderId.longValue());
			}

			if (mergeData) {
				existingFolder = IGFolderUtil.fetchByUUID_G(
					folder.getUuid(), context.getGroupId());

				if (existingFolder == null) {
					existingFolder = IGFolderLocalServiceUtil.addFolder(
						folder.getUuid(), folder.getUserId(), plid,
						parentFolderId.longValue(), folder.getName(),
						folder.getDescription(), addCommunityPermissions,
						addGuestPermissions);
				}
				else {
					existingFolder = IGFolderLocalServiceUtil.updateFolder(
						existingFolder.getFolderId(),
						parentFolderId.longValue(), folder.getName(),
						folder.getDescription(), false);
				}
			}
			else {
				existingFolder = IGFolderLocalServiceUtil.addFolder(
					folder.getUserId(), plid, parentFolderId.longValue(),
					folder.getName(), folder.getDescription(),
					addCommunityPermissions, addGuestPermissions);
			}

			folderPKs.put(
				folder.getPrimaryKeyObj(), existingFolder.getPrimaryKeyObj());
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for folder " +
					folder.getFolderId());
		}
	}

	protected void importIGImage(
			PortletDataContext context, boolean mergeData, Map folderPKs,
			Map imagesPKs, IGImage igImage)
		throws Exception {

		Long folderId = (Long)folderPKs.get(new Long(igImage.getFolderId()));

		if (folderId == null) {
			folderId = new Long(igImage.getFolderId());
		}

		Image image = (Image)imagesPKs.get(new Long(igImage.getLargeImageId()));

		File imageFile = null;

		if (image == null) {
			_log.error(
				"Could not find image for IG image " + igImage.getImageId());

			return;
		}
		else {
			imageFile = new File(
				igImage.getDescription() + "." + image.getType());

			FileUtil.write(imageFile, image.getTextObj());
		}

		String[] tagsEntries = context.getTagsEntries(
			IGImage.class, igImage.getPrimaryKeyObj());

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		IGImage existingImage = null;

		try {
			IGFolderUtil.findByPrimaryKey(folderId.longValue());

			if (mergeData) {
				existingImage = IGImageFinderUtil.findByUuid_G(
					igImage.getUuid(), context.getGroupId());

				if (existingImage == null) {
					IGImageLocalServiceUtil.addImage(
						igImage.getUuid(), igImage.getUserId(),
						folderId.longValue(), igImage.getDescription(),
						imageFile, image.getType(), tagsEntries,
						addCommunityPermissions, addGuestPermissions);
				}
				else {
					IGImageLocalServiceUtil.updateImage(
						igImage.getUserId(), existingImage.getImageId(),
						folderId.longValue(), igImage.getDescription(),
						imageFile, image.getType(), tagsEntries);
				}
			}
			else {
				IGImageLocalServiceUtil.addImage(
					igImage.getUserId(), folderId.longValue(),
					igImage.getDescription(), imageFile, image.getType(),
					tagsEntries, addCommunityPermissions, addGuestPermissions);
			}
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for IG image " +
					igImage.getImageId());
		}
	}

	private static final String _EXPORT_IG_DATA =
		"export-" + PortletKeys.IMAGE_GALLERY + "-data";

	private static final String _IMPORT_IG_DATA =
		"import-" + PortletKeys.IMAGE_GALLERY + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_IG_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_IG_DATA, true, null);

	private static Log _log = LogFactory.getLog(IGPortletDataHandlerImpl.class);

}