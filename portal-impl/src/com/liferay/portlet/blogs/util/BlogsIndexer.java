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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class BlogsIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {BlogsEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.BLOGS;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected Summary doGetSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/blogs/view_entry");
		portletURL.setParameter("entryId", entryId);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		BlogsEntry entry = (BlogsEntry)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entry.getEntryId());

		SearchEngineUtil.deleteDocument(
			entry.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		BlogsEntry entry = (BlogsEntry)obj;

		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		String userName = PortalUtil.getUserName(userId, entry.getUserName());
		long entryId = entry.getEntryId();
		String title = entry.getTitle();
		String content = HtmlUtil.extractText(entry.getContent());
		Date displayDate = entry.getDisplayDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			BlogsEntry.class.getName(), entryId);
		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				BlogsEntry.class.getName(), entryId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			BlogsEntry.class.getName(), entryId);

		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entryId);

		document.addModifiedDate(displayDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		document.addText(Field.USER_NAME, userName);

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_CATEGORY_NAMES, assetCategoryNames);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.ENTRY_CLASS_NAME, BlogsEntry.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		BlogsEntry entry = (BlogsEntry)obj;

		if (!entry.isApproved()) {
			return;
		}

		Document document = getDocument(entry);

		SearchEngineUtil.updateDocument(entry.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

		doReindex(entry);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexEntries(long companyId) throws Exception {
		int count = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			companyId, WorkflowConstants.STATUS_APPROVED);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reindexEntries(companyId, start, end);
		}
	}

	protected void reindexEntries(long companyId, int start, int end)
		throws Exception {

		List<BlogsEntry> entries = BlogsEntryLocalServiceUtil.getCompanyEntries(
			companyId, WorkflowConstants.STATUS_APPROVED, start, end);

		if (entries.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (BlogsEntry entry : entries) {
			Document document = getDocument(entry);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

}