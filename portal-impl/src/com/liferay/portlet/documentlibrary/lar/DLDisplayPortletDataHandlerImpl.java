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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 */
public class DLDisplayPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _comments, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _comments, _ratings, _tags
		};
	}

	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		preferences.setValue("rootFolderId", StringPool.BLANK);
		preferences.setValue("showBreadcrumbs", StringPool.BLANK);
		preferences.setValue("showFoldersSearch", StringPool.BLANK);
		preferences.setValue("showSubfolders", StringPool.BLANK);
		preferences.setValue("foldersPerPage", StringPool.BLANK);
		preferences.setValue("folderColumns", StringPool.BLANK);
		preferences.setValue("showFileEntriesSearch", StringPool.BLANK);
		preferences.setValue("fileEntriesPerPage", StringPool.BLANK);
		preferences.setValue("fileEntryColumns", StringPool.BLANK);
		preferences.setValue("enable-comment-ratings", StringPool.BLANK);

		return preferences;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.documentlibrary", context.getScopeGroupId());

		long rootFolderId = GetterUtil.getLong(
			preferences.getValue("rootFolderId", null));

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement(
			"documentlibrary-display-data");

		Element foldersElement = rootElement.addElement("folders");
		Element fileEntriesElement = rootElement.addElement("file-entries");
		Element fileShortcutsElement = rootElement.addElement("file-shortcuts");
		Element fileRanksElement = rootElement.addElement("file-ranks");

		if (rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			List<DLFolder> folders = DLFolderUtil.findByGroupId(
				context.getScopeGroupId());

			for (DLFolder folder : folders) {
				DLPortletDataHandlerImpl.exportFolder(
					context, foldersElement, fileEntriesElement,
					fileShortcutsElement, fileRanksElement, folder);
			}
		}
		else {
			DLFolder folder = DLFolderUtil.findByPrimaryKey(rootFolderId);

			rootElement.addAttribute(
				"root-folder-id", String.valueOf(folder.getFolderId()));

			DLPortletDataHandlerImpl.exportFolder(
				context, foldersElement, fileEntriesElement,
				fileShortcutsElement, fileRanksElement, folder);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.documentlibrary",
			context.getSourceGroupId(), context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		List<Element> folderElements = foldersElement.elements("folder");

		for (Element folderElement : folderElements) {
			DLPortletDataHandlerImpl.importFolder(context, folderElement);
		}

		Element fileEntriesElement = rootElement.element("file-entries");

		List<Element> fileEntryElements = fileEntriesElement.elements(
			"file-entry");

		for (Element fileEntryElement : fileEntryElements) {
			DLPortletDataHandlerImpl.importFileEntry(context, fileEntryElement);
		}

		if (context.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<Element> fileShortcutElements = rootElement.element(
				"file-shortcuts").elements("file-shortcut");

			for (Element fileShortcutElement : fileShortcutElements) {
				DLPortletDataHandlerImpl.importFileShortcut(
					context, fileShortcutElement);
			}
		}

		if (context.getBooleanParameter(_NAMESPACE, "ranks")) {
			Element fileRanksElement = rootElement.element("file-ranks");

			List<Element> fileRankElements = fileRanksElement.elements(
				"file-rank");

			for (Element fileRankElement : fileRankElements) {
				DLPortletDataHandlerImpl.importFileRank(
					context, fileRankElement);
			}
		}

		long rootFolderId = GetterUtil.getLong(
			rootElement.attributeValue("root-folder-id"));

		if (rootFolderId > 0) {
			Map<Long, Long> folderPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFolder.class);

			rootFolderId = MapUtil.getLong(
				folderPKs, rootFolderId, rootFolderId);

			preferences.setValue("rootFolderId", String.valueOf(rootFolderId));
		}

		return preferences;
	}

	private static final String _NAMESPACE = "document_library";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _foldersAndDocuments =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-documents", true, true);

	private static PortletDataHandlerBoolean _ranks =
		new PortletDataHandlerBoolean(_NAMESPACE, "ranks");

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _shortcuts=
		new PortletDataHandlerBoolean(_NAMESPACE, "shortcuts");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}