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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinderUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutFinderUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;

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

/**
 * <a href="DLPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class DLPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_enableExport, _enableShortcutsExport, _enableRanksExport
		};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_enableImport, _enableShortcutsImport, _enableRanksImport
		};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(parameterMap, _EXPORT_DL_DATA);
		boolean staging = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.STAGING);

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData && !staging) {
			return null;
		}

		boolean exportShortcuts = MapUtil.getBoolean(
			parameterMap, _EXPORT_DL_SHORTCUTS);

		boolean exportRanks = MapUtil.getBoolean(
			parameterMap, _EXPORT_DL_RANKS);

		try {
			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("documentlibrary-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Folders

			List folders = DLFolderUtil.findByGroupId(
				context.getGroupId());

			List entries = new ArrayList();

			List shortcuts = new ArrayList();

			Iterator itr = folders.iterator();

			while (itr.hasNext()) {
				DLFolder folder = (DLFolder)itr.next();

				if (context.addPrimaryKey(
						DLFolder.class, folder.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					folder.setUserUuid(folder.getUserUuid());

					List folderEntries = DLFileEntryUtil.findByFolderId(
						folder.getFolderId());

					entries.addAll(folderEntries);

					if (exportShortcuts) {
						List folderShortcuts =
							DLFileShortcutUtil.findByFolderId(
								folder.getFolderId());

						shortcuts.addAll(folderShortcuts);
					}
				}
			}

			String xml = xStream.toXML(folders);

			Element el = root.addElement("documentlibrary-folders");

			Document tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			// Entries

			List ranks = new ArrayList();

			itr = entries.iterator();

			while (itr.hasNext()) {
				DLFileEntry entry = (DLFileEntry)itr.next();

				if (context.addPrimaryKey(
						DLFileEntry.class, entry.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					entry.setUserUuid(entry.getUserUuid());

					context.addComments(
						DLFileEntry.class, entry.getPrimaryKeyObj());

					context.addRatingsEntries(
						DLFileEntry.class, entry.getPrimaryKeyObj());

					context.addTagsEntries(
						DLFileEntry.class, entry.getPrimaryKeyObj());

					InputStream in =
						DLFileEntryLocalServiceUtil.getFileAsStream(
							entry.getCompanyId(), entry.getUserId(),
							entry.getFolderId(), entry.getName());

					context.getZipWriter().addEntry(
						_ZIP_FOLDER + entry.getName(), FileUtil.getBytes(in));

					if (exportRanks) {
						List entryRanks = DLFileRankUtil.findByF_N(
							entry.getFolderId(), entry.getName());

						ranks.addAll(entryRanks);
					}
				}
			}

			xml = xStream.toXML(entries);

			el = root.addElement("documentlibrary-entries");

			tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			// Shortcuts

			itr = shortcuts.iterator();

			while (itr.hasNext()) {
				DLFileShortcut shortcut = (DLFileShortcut)itr.next();

				if (context.addPrimaryKey(
						DLFileShortcut.class, shortcut.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					shortcut.setUserUuid(shortcut.getUserUuid());
				}
			}

			xml = xStream.toXML(shortcuts);

			el = root.addElement("documentlibrary-shortcuts");

			tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			// Ranks

			itr = ranks.iterator();

			while (itr.hasNext()) {
				DLFileRank rank = (DLFileRank)itr.next();

				if (context.addPrimaryKey(
						DLFileRank.class, rank.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					rank.setUserUuid(rank.getUserUuid());
				}
			}

			xml = xStream.toXML(ranks);

			el = root.addElement("documentlibrary-ranks");

			tempDoc = PortalUtil.readDocumentFromXML(xml);

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

		boolean importData = MapUtil.getBoolean(parameterMap, _IMPORT_DL_DATA);
		boolean staging = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.STAGING);

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData && !staging) {
			return null;
		}

		boolean mergeData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.MERGE_DATA);

		boolean importShortcuts = MapUtil.getBoolean(
			parameterMap, _IMPORT_DL_SHORTCUTS);

		boolean importRanks = MapUtil.getBoolean(
			parameterMap, _IMPORT_DL_RANKS);

		try {
			XStream xStream = new XStream();

			Document doc = PortalUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			// Folders

			Element el = root.element(
				"documentlibrary-folders").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map folderPKs = CollectionFactory.getHashMap();

			List folders = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = folders.iterator();

			while (itr.hasNext()) {
				DLFolder folder = (DLFolder)itr.next();

				importFolder(context, mergeData, folderPKs, folder);
			}

			// Entries

			el = root.element("documentlibrary-entries").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map entryNames = CollectionFactory.getHashMap();

			List entries = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = entries.iterator();

			while (itr.hasNext()) {
				DLFileEntry entry = (DLFileEntry)itr.next();

				importEntry(context, mergeData, folderPKs, entryNames, entry);
			}

			// Shortcuts

			if (importShortcuts) {
				el = root.element("documentlibrary-shortcuts").element("list");

				tempDoc = DocumentHelper.createDocument();

				tempDoc.content().add(el.createCopy());

				List shortcuts = (List)xStream.fromXML(
					XMLFormatter.toString(tempDoc));

				itr = shortcuts.iterator();

				while (itr.hasNext()) {
					DLFileShortcut shortcut = (DLFileShortcut)itr.next();

					importShortcut(
						context, mergeData, folderPKs, entryNames, shortcut);
				}
			}

			// Ranks

			if (importRanks) {
				el = root.element("documentlibrary-ranks").element("list");

				tempDoc = DocumentHelper.createDocument();

				tempDoc.content().add(el.createCopy());

				List ranks = (List)xStream.fromXML(
					XMLFormatter.toString(tempDoc));

				itr = ranks.iterator();

				while (itr.hasNext()) {
					DLFileRank rank = (DLFileRank)itr.next();

					importRank(context, folderPKs, entryNames, rank);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importEntry(
			PortletDataContext context, boolean mergeData, Map folderPKs,
			Map entryNames, DLFileEntry entry)
		throws Exception {

		long userId = context.getUserId(entry.getUserUuid());
		long folderId = MapUtil.getLong(
			folderPKs, entry.getFolderId(), entry.getFolderId());

		String[] tagsEntries = context.getTagsEntries(
			DLFileEntry.class, entry.getPrimaryKeyObj());

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		byte[] byteArray = context.getZipReader().getEntryAsByteArray(
			_ZIP_FOLDER + entry.getName());

		DLFileEntry existingEntry = null;

		try {
			DLFolderUtil.findByPrimaryKey(folderId);

			if (mergeData) {
				existingEntry = DLFileEntryFinderUtil.findByUuid_G(
					entry.getUuid(), context.getGroupId());

				if (existingEntry == null) {
					existingEntry = DLFileEntryLocalServiceUtil.addFileEntry(
						entry.getUuid(), userId, folderId, entry.getName(),
						entry.getTitle(), entry.getDescription(),
						tagsEntries, entry.getExtraSettings(), byteArray,
						addCommunityPermissions, addGuestPermissions);
				}
				else {
					existingEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
						userId, existingEntry.getFolderId(), folderId,
						existingEntry.getName(), entry.getName(),
						entry.getTitle(), entry.getDescription(), tagsEntries,
						entry.getExtraSettings(), byteArray);
				}
			}
			else {
				existingEntry = DLFileEntryLocalServiceUtil.addFileEntry(
					userId, folderId, entry.getName(), entry.getTitle(),
					entry.getDescription(), tagsEntries,
					entry.getExtraSettings(), byteArray,
					addCommunityPermissions, addGuestPermissions);
			}

			entryNames.put(entry.getName(), existingEntry.getName());

			context.importComments(
				DLFileEntry.class, entry.getPrimaryKeyObj(),
				existingEntry.getPrimaryKeyObj(), context.getGroupId());

			context.importRatingsEntries(
				DLFileEntry.class, entry.getPrimaryKeyObj(),
				existingEntry.getPrimaryKeyObj());
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for entry " +
					entry.getFileEntryId());
		}
	}

	protected void importFolder(
			PortletDataContext context, boolean mergeData, Map folderPKs,
			DLFolder folder)
		throws Exception {

		long userId = context.getUserId(folder.getUserUuid());
		long plid = context.getPlid();
		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		DLFolder existingFolder = null;

		try {
			if (parentFolderId != DLFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
				DLFolderUtil.findByPrimaryKey(parentFolderId);
			}

			if (mergeData) {
				existingFolder = DLFolderUtil.fetchByUUID_G(
					folder.getUuid(), context.getGroupId());

				if (existingFolder == null) {
					existingFolder = DLFolderLocalServiceUtil.addFolder(
						folder.getUuid(), userId, plid, parentFolderId,
						folder.getName(), folder.getDescription(),
						addCommunityPermissions, addGuestPermissions);
				}
				else {
					existingFolder = DLFolderLocalServiceUtil.updateFolder(
						existingFolder.getFolderId(), parentFolderId,
						folder.getName(), folder.getDescription());
				}
			}
			else {
				existingFolder = DLFolderLocalServiceUtil.addFolder(
					userId, plid, parentFolderId, folder.getName(),
					folder.getDescription(), addCommunityPermissions,
					addGuestPermissions);
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

	protected void importRank(
			PortletDataContext context, Map folderPKs, Map entryNames,
			DLFileRank rank)
		throws Exception {

		long userId = context.getUserId(rank.getUserUuid());
		long folderId = MapUtil.getLong(
			folderPKs, rank.getFolderId(), rank.getFolderId());

		String name = (String)folderPKs.get(rank.getName());

		if (name == null) {
			name = rank.getName();
		}

		try {
			DLFolderUtil.findByPrimaryKey(folderId);

			DLFileRankLocalServiceUtil.updateFileRank(
				context.getGroupId(), context.getCompanyId(), userId, folderId,
				name);
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the folder for rank " + rank.getFileRankId());
		}
	}

	protected void importShortcut(
			PortletDataContext context, boolean mergeData, Map folderPKs,
			Map entryNames, DLFileShortcut shortcut)
		throws Exception {

		long userId = context.getUserId(shortcut.getUserUuid());
		long folderId = MapUtil.getLong(
			folderPKs, shortcut.getFolderId(), shortcut.getFolderId());
		long toFolderId = MapUtil.getLong(
			folderPKs, shortcut.getToFolderId(), shortcut.getToFolderId());
		String toName = MapUtil.getString(
			entryNames, shortcut.getToName(), shortcut.getToName());

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		try {
			DLFolderUtil.findByPrimaryKey(folderId);
			DLFolderUtil.findByPrimaryKey(toFolderId);

			if (mergeData) {
				DLFileShortcut existingShortcut =
					DLFileShortcutFinderUtil.findByUuid_G(
						shortcut.getUuid(), context.getGroupId());

				if (existingShortcut == null) {
					DLFileShortcutLocalServiceUtil.addFileShortcut(
						shortcut.getUuid(), userId, folderId, toFolderId,
						toName, addCommunityPermissions, addGuestPermissions);
				}
				else {
					DLFileShortcutLocalServiceUtil.updateFileShortcut(
						userId, existingShortcut.getFileShortcutId(), folderId,
						toFolderId, toName);
				}
			}
			else {
				DLFileShortcutLocalServiceUtil.addFileShortcut(
					userId, folderId, toFolderId, toName,
					addCommunityPermissions, addGuestPermissions);
			}
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the folder for shortcut " +
					shortcut.getFileShortcutId());
		}
	}

	private static final String _EXPORT_DL_DATA =
		"export-" + PortletKeys.DOCUMENT_LIBRARY + "-data";

	private static final String _IMPORT_DL_DATA =
		"import-" + PortletKeys.DOCUMENT_LIBRARY + "-data";

	private static final String _EXPORT_DL_SHORTCUTS =
		"export-" + PortletKeys.DOCUMENT_LIBRARY + "-shortcuts";

	private static final String _IMPORT_DL_SHORTCUTS =
		"import-" + PortletKeys.DOCUMENT_LIBRARY + "-shortcuts";

	private static final String _EXPORT_DL_RANKS =
		"export-" + PortletKeys.DOCUMENT_LIBRARY + "-ranks";

	private static final String _IMPORT_DL_RANKS =
		"import-" + PortletKeys.DOCUMENT_LIBRARY + "-ranks";

	private static final String _ZIP_FOLDER = "document-library/";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_DL_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_DL_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableShortcutsExport =
		new PortletDataHandlerBoolean(_EXPORT_DL_SHORTCUTS, true, null);

	private static final PortletDataHandlerBoolean _enableShortcutsImport =
		new PortletDataHandlerBoolean(_IMPORT_DL_SHORTCUTS, true, null);

	private static final PortletDataHandlerBoolean _enableRanksExport =
		new PortletDataHandlerBoolean(_EXPORT_DL_RANKS, true, null);

	private static final PortletDataHandlerBoolean _enableRanksImport =
		new PortletDataHandlerBoolean(_IMPORT_DL_RANKS, true, null);

	private static Log _log =
		LogFactory.getLog(DLPortletDataHandlerImpl.class);

}