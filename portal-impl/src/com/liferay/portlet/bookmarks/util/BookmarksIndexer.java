/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.persistence.GroupActionableDynamicQuery;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryActionableDynamicQuery;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderActionableDynamicQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class BookmarksIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {BookmarksEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.BOOKMARKS;

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean isPermissionAware() {
		return _PERMISSION_AWARE;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] folderIds = searchContext.getFolderIds();

		if ((folderIds != null) && (folderIds.length > 0)) {
			if (folderIds[0] ==
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return;
			}

			BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long folderId : folderIds) {
				try {
					BookmarksFolderServiceUtil.getFolder(folderId);
				}
				catch (Exception e) {
					continue;
				}

				folderIdsQuery.addTerm(Field.FOLDER_ID, folderId);
			}

			contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		deleteDocument(entry.getCompanyId(), entry.getEntryId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		Document document = getBaseModelDocument(PORTLET_ID, entry);

		document.addText(Field.DESCRIPTION, entry.getDescription());
		document.addKeyword(Field.FOLDER_ID, entry.getFolderId());
		document.addText(Field.TITLE, entry.getName());
		document.addText(Field.URL, entry.getUrl());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String url = document.get(Field.URL);

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/bookmarks/view_entry");
		portletURL.setParameter("entryId", entryId);

		return new Summary(title, url, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		BookmarksEntry entry = (BookmarksEntry)obj;

		Document document = getDocument(entry);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), entry.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
		reindexRoot(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexEntries(
			long companyId, final long groupId, final long folderId)
		throws PortalException, SystemException {

		final Collection<Document> documents = new ArrayList<Document>();

		ActionableDynamicQuery actionableDynamicQuery =
			new BookmarksEntryActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property property = PropertyFactoryUtil.forName("folderId");

				dynamicQuery.add(property.eq(folderId));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				BookmarksEntry entry = (BookmarksEntry)object;

				Document document = getDocument(entry);

				documents.add(document);
			}

		};

		actionableDynamicQuery.setGroupId(groupId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	protected void reindexFolders(final long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new BookmarksFolderActionableDynamicQuery() {

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				BookmarksFolder folder = (BookmarksFolder)object;

				long groupId = folder.getGroupId();
				long folderId = folder.getFolderId();

				reindexEntries(companyId, groupId, folderId);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new GroupActionableDynamicQuery() {

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				Group group = (Group)object;

				long groupId = group.getGroupId();
				long folderId =
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

				reindexEntries(companyId, groupId, folderId);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();
	}

	private static final boolean _PERMISSION_AWARE = true;

}