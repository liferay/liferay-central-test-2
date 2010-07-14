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
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		if (!context.addPrimaryKey(
				BookmarksPortletDataHandlerImpl.class, "deleteData")) {

			BookmarksFolderLocalServiceUtil.deleteFolders(
				context.getScopeGroupId());

			BookmarksEntryLocalServiceUtil.deleteEntries(
				context.getScopeGroupId(),
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.bookmarks", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("bookmarks-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		Element foldersElement = rootElement.addElement("folders");
		Element entriesElement = rootElement.addElement("entries");

		List<BookmarksFolder> folders = BookmarksFolderUtil.findByGroupId(
			context.getScopeGroupId());

		for (BookmarksFolder folder : folders) {
			exportFolder(context, foldersElement, entriesElement, folder);
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			context.getScopeGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (BookmarksEntry entry : entries) {
			exportEntry(context, null, entriesElement, entry);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.bookmarks", context.getSourceGroupId(),
			context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		for (Element folderElement : foldersElement.elements("folder")) {
			String path = folderElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			BookmarksFolder folder =
				(BookmarksFolder)context.getZipEntryAsObject(path);

			importFolder(context, folder);
		}

		Element entriesElement = rootElement.element("entries");

		for (Element entryElement : entriesElement.elements("entry")) {
			String path = entryElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			BookmarksEntry entry = (BookmarksEntry)context.getZipEntryAsObject(
				path);

			importEntry(context, entry);
		}

		return null;
	}

	protected void exportFolder(
			PortletDataContext context, Element foldersElement,
			Element entriesElement, BookmarksFolder folder)
		throws Exception {

		if (context.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				context, foldersElement, folder.getParentFolderId());

			String path = getFolderPath(context, folder);

			if (context.isPathNotProcessed(path)) {
				Element folderElement = foldersElement.addElement("folder");

				folderElement.addAttribute("path", path);

				folder.setUserUuid(folder.getUserUuid());

				context.addPermissions(
					BookmarksFolder.class, folder.getFolderId());

				context.addZipEntry(path, folder);
			}
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (BookmarksEntry entry : entries) {
			exportEntry(context, foldersElement, entriesElement, entry);
		}
	}

	protected void exportEntry(
			PortletDataContext context, Element foldersElement,
			Element entriesElement, BookmarksEntry entry)
		throws Exception {

		if (!context.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		if (foldersElement != null) {
			exportParentFolder(context, foldersElement, entry.getFolderId());
		}

		String path = getEntryPath(context, entry);

		if (context.isPathNotProcessed(path)) {
			Element entryElement = entriesElement.addElement("entry");

			entryElement.addAttribute("path", path);

			context.addPermissions(BookmarksEntry.class, entry.getEntryId());

			if (context.getBooleanParameter(_NAMESPACE, "categories")) {
				context.addAssetCategories(
					BookmarksEntry.class, entry.getEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.addRatingsEntries(
					BookmarksEntry.class, entry.getEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(BookmarksEntry.class, entry.getEntryId());
			}

			entry.setUserUuid(entry.getUserUuid());

			context.addZipEntry(path, entry);
		}
	}

	protected void exportParentFolder(
			PortletDataContext context, Element foldersElement, long folderId)
		throws Exception {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(context, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(context, folder);

		if (context.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			folderElement.addAttribute("path", path);

			folder.setUserUuid(folder.getUserUuid());

			context.addPermissions(BookmarksFolder.class, folder.getFolderId());

			context.addZipEntry(path, folder);
		}
	}

	protected String getEntryPath(
		PortletDataContext context, BookmarksEntry entry) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.BOOKMARKS));
		sb.append("/entries/");
		sb.append(entry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getFolderPath(
		PortletDataContext context, BookmarksFolder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.BOOKMARKS));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getImportFolderPath(
		PortletDataContext context, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getSourcePortletPath(PortletKeys.BOOKMARKS));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected void importEntry(PortletDataContext context, BookmarksEntry entry)
		throws Exception {

		long userId = context.getUserId(entry.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, entry.getFolderId(), entry.getFolderId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				BookmarksEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
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

			String path = getImportFolderPath(context, folderId);

			BookmarksFolder folder =
				(BookmarksFolder)context.getZipEntryAsObject(path);

			importFolder(context, folder);

			folderId = MapUtil.getLong(
				folderPKs, entry.getFolderId(), entry.getFolderId());
		}

		BookmarksEntry importedEntry = null;

		if (context.isDataStrategyMirror()) {
			BookmarksEntry existingEntry = BookmarksEntryUtil.fetchByUUID_G(
				entry.getUuid(), context.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
					userId, context.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getComments(),
					serviceContext);
			}
			else {
				importedEntry = BookmarksEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(),
					context.getScopeGroupId(), folderId, entry.getName(),
					entry.getUrl(), entry.getComments(), serviceContext);
			}
		}
		else {
			importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
				userId, context.getScopeGroupId(), folderId, entry.getName(),
				entry.getUrl(), entry.getComments(), serviceContext);
		}

		context.importPermissions(
			BookmarksEntry.class, entry.getEntryId(),
			importedEntry.getEntryId());

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				BookmarksEntry.class, entry.getEntryId(),
				importedEntry.getEntryId());
		}
	}

	protected void importFolder(
			PortletDataContext context, BookmarksFolder folder)
		throws Exception {

		long userId = context.getUserId(folder.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(folder.getCreateDate());
		serviceContext.setModifiedDate(folder.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		if ((parentFolderId !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(context, parentFolderId);

			BookmarksFolder parentFolder =
				(BookmarksFolder)context.getZipEntryAsObject(path);

			importFolder(context, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		BookmarksFolder importedFolder = null;

		if (context.isDataStrategyMirror()) {
			BookmarksFolder existingFolder = BookmarksFolderUtil.fetchByUUID_G(
				folder.getUuid(), context.getScopeGroupId());

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

		context.importPermissions(
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