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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.BasePortletDataHandler;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.NoSuchEntryException;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
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
 * <a href="BookmarksPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class BookmarksPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					BookmarksPortletDataHandlerImpl.class, "deleteData")) {

				BookmarksFolderLocalServiceUtil.deleteFolders(
					context.getGroupId());
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

			Element root = doc.addElement("bookmarks-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element foldersEl = root.addElement("folders");
			Element entriesEl = root.addElement("entries");

			List<BookmarksFolder> folders = BookmarksFolderUtil.findByGroupId(
				context.getGroupId());

			for (BookmarksFolder folder : folders) {
				exportFolder(context, foldersEl, entriesEl, folder);
			}

			List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
				context.getGroupId(),
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			for (BookmarksEntry entry : entries) {
				exportEntry(context, null, entriesEl, entry);
			}

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_foldersAndEntries, _tags};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_foldersAndEntries, _tags};
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
				(Map<Long, Long>)context.getNewPrimaryKeysMap(
					BookmarksFolder.class);

			for (Element folderEl : folderEls) {
				String path = folderEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				BookmarksFolder folder =
					(BookmarksFolder)context.getZipEntryAsObject(path);

				importFolder(context, folderPKs, folder);
			}

			List<Element> entryEls = root.element("entries").elements("entry");

			for (Element entryEl : entryEls) {
				String path = entryEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				BookmarksEntry entry =
					(BookmarksEntry)context.getZipEntryAsObject(path);

				importEntry(context, folderPKs, entry);
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void exportFolder(
			PortletDataContext context, Element foldersEl, Element entriesEl,
			BookmarksFolder folder)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(context, foldersEl, folder.getParentFolderId());

			String path = getFolderPath(context, folder);

			if (context.isPathNotProcessed(path)) {
				Element folderEl = foldersEl.addElement("folder");

				folderEl.addAttribute("path", path);

				folder.setUserUuid(folder.getUserUuid());

				context.addPermissions(
					BookmarksFolder.class, folder.getFolderId());

				context.addZipEntry(path, folder);
			}
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (BookmarksEntry entry : entries) {
			exportEntry(context, foldersEl, entriesEl, entry);
		}
	}

	protected void exportEntry(
			PortletDataContext context, Element foldersEl, Element entriesEl,
			BookmarksEntry entry)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		if (foldersEl != null) {
			exportParentFolder(context, foldersEl, entry.getFolderId());
		}

		String path = getEntryPath(context, entry);

		if (context.isPathNotProcessed(path)) {
			Element entryEl = entriesEl.addElement("entry");

			entryEl.addAttribute("path", path);

			context.addPermissions(BookmarksEntry.class, entry.getEntryId());

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(BookmarksEntry.class, entry.getEntryId());
			}

			entry.setUserUuid(entry.getUserUuid());

			context.addZipEntry(path, entry);
		}
	}

	protected void exportParentFolder(
			PortletDataContext context, Element foldersEl, long folderId)
		throws PortalException, SystemException {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(context, foldersEl, folder.getParentFolderId());

		String path = getFolderPath(context, folder);

		if (context.isPathNotProcessed(path)) {
			Element folderEl = foldersEl.addElement("folder");

			folderEl.addAttribute("path", path);

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

	protected void importEntry(
			PortletDataContext context, Map<Long, Long> folderPKs,
			BookmarksEntry entry)
		throws Exception {

		long userId = context.getUserId(entry.getUserUuid());
		long groupId = context.getGroupId();
		long folderId = MapUtil.getLong(
			folderPKs, entry.getFolderId(), entry.getFolderId());

		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				BookmarksEntry.class, entry.getEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(entry.getCreateDate());
		serviceContext.setModifiedDate(entry.getModifiedDate());

		if ((folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == entry.getFolderId())) {

			String path = getImportFolderPath(context, folderId);

			BookmarksFolder folder =
				(BookmarksFolder)context.getZipEntryAsObject(path);

			importFolder(context, folderPKs, folder);

			folderId = MapUtil.getLong(
				folderPKs, entry.getFolderId(), entry.getFolderId());
		}

		BookmarksEntry existingEntry = null;

		try {
			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					existingEntry = BookmarksEntryUtil.findByUUID_G(
						entry.getUuid(), groupId);

					existingEntry = BookmarksEntryLocalServiceUtil.updateEntry(
						userId, existingEntry.getEntryId(), groupId, folderId,
						entry.getName(), entry.getUrl(), entry.getComments(),
						serviceContext);
				}
				catch (NoSuchEntryException nsee) {
					existingEntry = BookmarksEntryLocalServiceUtil.addEntry(
						entry.getUuid(), userId, groupId, folderId,
						entry.getName(), entry.getUrl(), entry.getComments(),
						serviceContext);
				}
			}
			else {
				existingEntry = BookmarksEntryLocalServiceUtil.addEntry(
					null, userId, groupId, folderId, entry.getName(),
					entry.getUrl(), entry.getComments(), serviceContext);
			}

			context.importPermissions(
				BookmarksEntry.class, entry.getEntryId(),
				existingEntry.getEntryId());
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for entry " +
					entry.getEntryId());
		}
	}

	protected void importFolder(
			PortletDataContext context, Map<Long, Long> folderPKs,
			BookmarksFolder folder)
		throws Exception {

		long userId = context.getUserId(folder.getUserUuid());
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

			importFolder(context, folderPKs, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		BookmarksFolder existingFolder = null;

		try {
			if (parentFolderId !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				BookmarksFolderUtil.findByPrimaryKey(parentFolderId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {
				existingFolder = BookmarksFolderUtil.fetchByUUID_G(
					folder.getUuid(), context.getGroupId());

				if (existingFolder == null) {
					existingFolder = BookmarksFolderLocalServiceUtil.addFolder(
						folder.getUuid(), userId, parentFolderId,
						folder.getName(), folder.getDescription(),
						serviceContext);
				}
				else {
					existingFolder =
						BookmarksFolderLocalServiceUtil.updateFolder(
							existingFolder.getFolderId(), parentFolderId,
							folder.getName(), folder.getDescription(), false,
							serviceContext);
				}
			}
			else {
				existingFolder = BookmarksFolderLocalServiceUtil.addFolder(
					null, userId, parentFolderId, folder.getName(),
					folder.getDescription(), serviceContext);
			}

			folderPKs.put(folder.getFolderId(), existingFolder.getFolderId());

			context.importPermissions(
				BookmarksFolder.class, folder.getFolderId(),
				existingFolder.getFolderId());
		}
		catch (NoSuchFolderException nsfe) {
			_log.error(
				"Could not find the parent folder for folder " +
					folder.getFolderId());
		}
	}

	private static final String _NAMESPACE = "bookmarks";

	private static final PortletDataHandlerBoolean _foldersAndEntries =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-entries", true, true);

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log = LogFactoryUtil.getLog(
		BookmarksPortletDataHandlerImpl.class);

}