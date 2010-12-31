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

package com.liferay.portlet.bookmarks.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class BookmarksIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {BookmarksEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.BOOKMARKS;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String url = document.get(Field.URL);

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/bookmarks/view_entry");
		portletURL.setParameter("entryId", entryId);

		return new Summary(title, url, portletURL);
	}

	protected void checkSearchFolderId(
			long folderId, SearchContext searchContext)
		throws Exception {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		BookmarksFolderServiceUtil.getFolder(folderId);
	}

	protected void doDelete(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entry.getEntryId());

		SearchEngineUtil.deleteDocument(
			entry.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		long folderId = entry.getFolderId();
		long entryId = entry.getEntryId();
		String name = entry.getName();
		String url = entry.getUrl();
		String description = entry.getDescription();
		Date modifiedDate = entry.getModifiedDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			BookmarksEntry.class.getName(), entryId);
		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				BookmarksEntry.class.getName(), entryId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			BookmarksEntry.class.getName(), entryId);

		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entryId);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);

		document.addText(Field.TITLE, name);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_CATEGORY_NAMES, assetCategoryNames);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.FOLDER_ID, folderId);
		document.addKeyword(
			Field.ENTRY_CLASS_NAME, BookmarksEntry.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);
		document.addText(Field.URL, url);
		document.addText(Field.DESCRIPTION, description);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		Document document = getDocument(entry);

		SearchEngineUtil.updateDocument(entry.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		doReindex(entry);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
		reindexRoot(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexEntries(
			long companyId, long groupId, long folderId, int entryStart,
			int entryEnd)
		throws Exception {

		List<BookmarksEntry> entries =
			BookmarksEntryLocalServiceUtil.getEntries(
				groupId, folderId, entryStart, entryEnd);

		if (entries.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (BookmarksEntry entry : entries) {
			Document document = getDocument(entry);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	protected void reindexFolders(long companyId) throws Exception {
		int folderCount =
			BookmarksFolderLocalServiceUtil.getCompanyFoldersCount(companyId);

		int folderPages = folderCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= folderPages; i++) {
			int folderStart = (i * Indexer.DEFAULT_INTERVAL);
			int folderEnd = folderStart + Indexer.DEFAULT_INTERVAL;

			reindexFolders(companyId, folderStart, folderEnd);
		}
	}

	protected void reindexFolders(
			long companyId, int folderStart, int folderEnd)
		throws Exception {

		List<BookmarksFolder> folders =
			BookmarksFolderLocalServiceUtil.getCompanyFolders(
				companyId, folderStart, folderEnd);

		for (BookmarksFolder folder : folders) {
			long groupId = folder.getGroupId();
			long folderId = folder.getFolderId();

			int entryCount = BookmarksEntryLocalServiceUtil.getEntriesCount(
				groupId, folderId);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= entryPages; i++) {
				int entryStart = (i * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reindexEntries(
					companyId, groupId, folderId, entryStart, entryEnd);
			}
		}
	}

	protected void reindexRoot(long companyId) throws Exception {
		int groupCount = GroupLocalServiceUtil.getCompanyGroupsCount(companyId);

		int groupPages = groupCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= groupPages; i++) {
			int groupStart = (i * Indexer.DEFAULT_INTERVAL);
			int groupEnd = groupStart + Indexer.DEFAULT_INTERVAL;

			reindexRoot(companyId, groupStart, groupEnd);
		}
	}

	protected void reindexRoot(long companyId, int groupStart, int groupEnd)
		throws Exception {

		List<Group> groups = GroupLocalServiceUtil.getCompanyGroups(
			companyId, groupStart, groupEnd);

		for (Group group : groups) {
			long groupId = group.getGroupId();
			long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			int entryCount = BookmarksEntryLocalServiceUtil.getEntriesCount(
				groupId, folderId);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= entryPages; i++) {
				int entryStart = (i * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reindexEntries(
					companyId, groupId, folderId, entryStart, entryEnd);
			}
		}
	}

}