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

package com.liferay.portlet.bookmarks.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;
import com.liferay.portlet.deletion.model.DeletionEntry;
import com.liferay.portlet.deletion.service.DeletionEntryLocalServiceUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class BookmarksPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndEntries, _categories, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndEntries, _categories, _ratings, _tags
		};
	}

	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				BookmarksPortletDataHandlerImpl.class, "deleteData")) {

			BookmarksFolderLocalServiceUtil.deleteFolders(
				portletDataContext.getScopeGroupId());

			BookmarksEntryLocalServiceUtil.deleteEntries(
				portletDataContext.getScopeGroupId(),
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.bookmarks",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("bookmarks-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element foldersElement = rootElement.addElement("folders");
		Element entriesElement = rootElement.addElement("entries");

		List<BookmarksFolder> folders = BookmarksFolderUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (BookmarksFolder folder : folders) {
			exportFolder(
				portletDataContext, foldersElement, entriesElement, folder);
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			portletDataContext.getScopeGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (BookmarksEntry entry : entries) {
			exportEntry(portletDataContext, null, entriesElement, entry);
		}

		return document.formattedString();
	}

	protected void doExportDeletions(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Date startDate = portletDataContext.getStartDate();

		portletDataContext.addDeletionEntries(
			DeletionEntryLocalServiceUtil.getEntries(
				portletDataContext.getScopeGroupId(),
				startDate, BookmarksFolder.class.getName()));

		portletDataContext.addDeletionEntries(
			DeletionEntryLocalServiceUtil.getEntries(
				portletDataContext.getScopeGroupId(),
				startDate, BookmarksEntry.class.getName()));
	}

	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.bookmarks",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		for (Element folderElement : foldersElement.elements("folder")) {
			String path = folderElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			BookmarksFolder folder =
				(BookmarksFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, folder);
		}

		Element entriesElement = rootElement.element("entries");

		for (Element entryElement : entriesElement.elements("entry")) {
			String path = entryElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			BookmarksEntry entry =
				(BookmarksEntry)portletDataContext.getZipEntryAsObject(path);

			importEntry(portletDataContext, entry);
		}

		return null;
	}

	protected void doImportDeletions(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		List<String> paths = portletDataContext.getDeletionEntries(
			BookmarksFolder.class.getName());

		for (String path : paths) {
			if (portletDataContext.isPathNotProcessed(path)) {
				DeletionEntry deletionEntry =
					(DeletionEntry)portletDataContext.getZipEntryAsObject(path);

				BookmarksFolder bookmarksFolder =
					BookmarksFolderUtil.fetchByUUID_G(
						deletionEntry.getClassUuid(),
						portletDataContext.getScopeGroupId());

				if (bookmarksFolder != null) {
					BookmarksFolderLocalServiceUtil.deleteFolder(
						bookmarksFolder.getFolderId());
				}
			}
		}

		paths = portletDataContext.getDeletionEntries(
			BookmarksEntry.class.getName());

		for (String path : paths) {
			if (portletDataContext.isPathNotProcessed(path)) {
				DeletionEntry deletionEntry =
					(DeletionEntry)portletDataContext.getZipEntryAsObject(path);

				BookmarksEntry bookmarksEntry =
					BookmarksEntryUtil.fetchByUUID_G(
						deletionEntry.getClassUuid(),
						portletDataContext.getScopeGroupId());

				if (bookmarksEntry != null) {
					BookmarksEntryLocalServiceUtil.deleteEntry(
						bookmarksEntry.getEntryId());
				}
			}
		}
	}

	protected void exportFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			Element entriesElement, BookmarksFolder folder)
		throws Exception {

		if (portletDataContext.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				portletDataContext, foldersElement, folder.getParentFolderId());

			String path = getFolderPath(portletDataContext, folder);

			if (portletDataContext.isPathNotProcessed(path)) {
				Element folderElement = foldersElement.addElement("folder");

				folderElement.addAttribute("path", path);

				folder.setUserUuid(folder.getUserUuid());

				portletDataContext.addPermissions(
					BookmarksFolder.class, folder.getFolderId());

				portletDataContext.addZipEntry(path, folder);
			}
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (BookmarksEntry entry : entries) {
			exportEntry(
				portletDataContext, foldersElement, entriesElement, entry);
		}
	}

	protected void exportEntry(
			PortletDataContext portletDataContext, Element foldersElement,
			Element entriesElement, BookmarksEntry entry)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		if (foldersElement != null) {
			exportParentFolder(
				portletDataContext, foldersElement, entry.getFolderId());
		}

		String path = getEntryPath(portletDataContext, entry);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element entryElement = entriesElement.addElement("entry");

			entryElement.addAttribute("path", path);

			portletDataContext.addPermissions(
				BookmarksEntry.class, entry.getEntryId());

			if (portletDataContext.getBooleanParameter(
				_NAMESPACE, "categories")) {

				portletDataContext.addAssetCategories(
					BookmarksEntry.class, entry.getEntryId());
			}

			if (portletDataContext.getBooleanParameter(_NAMESPACE, "ratings")) {
				portletDataContext.addRatingsEntries(
					BookmarksEntry.class, entry.getEntryId());
			}

			if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
				portletDataContext.addAssetTags(
					BookmarksEntry.class, entry.getEntryId());
			}

			entry.setUserUuid(entry.getUserUuid());

			portletDataContext.addZipEntry(path, entry);
		}
	}

	protected void exportParentFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			long folderId)
		throws Exception {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(
			portletDataContext, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			folderElement.addAttribute("path", path);

			folder.setUserUuid(folder.getUserUuid());

			portletDataContext.addPermissions(
				BookmarksFolder.class, folder.getFolderId());

			portletDataContext.addZipEntry(path, folder);
		}
	}

	protected String getEntryPath(
		PortletDataContext portletDataContext, BookmarksEntry entry) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.BOOKMARKS));
		sb.append("/entries/");
		sb.append(entry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getFolderPath(
		PortletDataContext portletDataContext, BookmarksFolder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.BOOKMARKS));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getImportFolderPath(
		PortletDataContext portletDataContext, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getSourcePortletPath(PortletKeys.BOOKMARKS));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected void importEntry(
			PortletDataContext portletDataContext, BookmarksEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, entry.getFolderId(), entry.getFolderId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = portletDataContext.getAssetCategoryIds(
				BookmarksEntry.class, entry.getEntryId());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = portletDataContext.getAssetTagNames(
				BookmarksEntry.class, entry.getEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(entry.getCreateDate());
		serviceContext.setModifiedDate(entry.getModifiedDate());

		if ((folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == entry.getFolderId())) {

			String path = getImportFolderPath(portletDataContext, folderId);

			BookmarksFolder folder =
				(BookmarksFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, folder);

			folderId = MapUtil.getLong(
				folderPKs, entry.getFolderId(), entry.getFolderId());
		}

		BookmarksEntry importedEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			BookmarksEntry existingEntry = BookmarksEntryUtil.fetchByUUID_G(
				entry.getUuid(), portletDataContext.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
			else {
				importedEntry = BookmarksEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(),
					portletDataContext.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
		}
		else {
			importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
				userId, portletDataContext.getScopeGroupId(), folderId,
				entry.getName(), entry.getUrl(), entry.getDescription(),
				serviceContext);
		}

		portletDataContext.importPermissions(
			BookmarksEntry.class, entry.getEntryId(),
			importedEntry.getEntryId());

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ratings")) {
			portletDataContext.importRatingsEntries(
				BookmarksEntry.class, entry.getEntryId(),
				importedEntry.getEntryId());
		}
	}

	protected void importFolder(
			PortletDataContext portletDataContext, BookmarksFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(folder.getCreateDate());
		serviceContext.setModifiedDate(folder.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		if ((parentFolderId !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(
				portletDataContext, parentFolderId);

			BookmarksFolder parentFolder =
				(BookmarksFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		BookmarksFolder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			BookmarksFolder existingFolder = BookmarksFolderUtil.fetchByUUID_G(
				folder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFolder == null) {
				serviceContext.setUuid(folder.getUuid());

				importedFolder = BookmarksFolderLocalServiceUtil.addFolder(
					userId, parentFolderId, folder.getName(),
					folder.getDescription(), serviceContext);
			}
			else {
				importedFolder = BookmarksFolderLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId,
					folder.getName(), folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			importedFolder = BookmarksFolderLocalServiceUtil.addFolder(
				userId, parentFolderId, folder.getName(),
				folder.getDescription(), serviceContext);
		}

		folderPKs.put(folder.getFolderId(), importedFolder.getFolderId());

		portletDataContext.importPermissions(
			BookmarksFolder.class, folder.getFolderId(),
			importedFolder.getFolderId());
	}

	private static final String _NAMESPACE = "bookmarks";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _foldersAndEntries =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-entries", true, true);

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}