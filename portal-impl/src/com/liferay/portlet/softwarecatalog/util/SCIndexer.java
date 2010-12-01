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

package com.liferay.portlet.softwarecatalog.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class SCIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {SCProductEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.SOFTWARE_CATALOG;

	public SCIndexer() {
		super();

		setStagingAware(false);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String productEntryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/software_catalog/view_product_entry");
		portletURL.setParameter("productEntryId", productEntryId);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		SCProductEntry productEntry = (SCProductEntry)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, productEntry.getProductEntryId());

		SearchEngineUtil.deleteDocument(
			productEntry.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		SCProductEntry productEntry = (SCProductEntry)obj;

		long companyId = productEntry.getCompanyId();
		long groupId = getParentGroupId(productEntry.getGroupId());
		long scopeGroupId = productEntry.getGroupId();
		long userId = productEntry.getUserId();
		String userName = PortalUtil.getUserName(
			userId, productEntry.getUserName());
		long productEntryId = productEntry.getProductEntryId();
		String name = productEntry.getName();
		Date modifiedDate = productEntry.getModifiedDate();

		String version = StringPool.BLANK;

		SCProductVersion latestProductVersion = productEntry.getLatestVersion();

		if (latestProductVersion != null) {
			version = latestProductVersion.getVersion();
		}

		String type = productEntry.getType();
		String shortDescription = HtmlUtil.extractText(
			productEntry.getShortDescription());
		String longDescription = HtmlUtil.extractText(
			productEntry.getLongDescription());
		String pageURL = productEntry.getPageURL();
		String repoGroupId = productEntry.getRepoGroupId();
		String repoArtifactId = productEntry.getRepoArtifactId();

		ExpandoBridge expandoBridge = productEntry.getExpandoBridge();

		StringBundler sb = new StringBundler(15);

		sb.append(userId);
		sb.append(StringPool.SPACE);
		sb.append(userName);
		sb.append(StringPool.SPACE);
		sb.append(type);
		sb.append(StringPool.SPACE);
		sb.append(shortDescription);
		sb.append(StringPool.SPACE);
		sb.append(longDescription);
		sb.append(StringPool.SPACE);
		sb.append(pageURL);
		sb.append(StringPool.SPACE);
		sb.append(repoGroupId);
		sb.append(StringPool.SPACE);
		sb.append(repoArtifactId);

		String content = sb.toString();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, productEntryId);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		document.addText(Field.USER_NAME, userName);

		document.addText(Field.TITLE, name);
		document.addText(Field.CONTENT, content);

		document.addKeyword(
			Field.ENTRY_CLASS_NAME, SCProductEntry.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, productEntryId);

		document.addKeyword("version", version);
		document.addKeyword("type", type);
		document.addText("shortDescription", shortDescription);
		document.addText("longDescription", longDescription);
		document.addText("pageURL", pageURL);
		document.addKeyword("repoGroupId", repoGroupId);
		document.addKeyword("repoArtifactId", repoArtifactId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		SCProductEntry productEntry = (SCProductEntry)obj;

		Document document = getDocument(productEntry);

		SearchEngineUtil.updateDocument(productEntry.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		SCProductEntry productEntry =
			SCProductEntryLocalServiceUtil.getProductEntry(classPK);

		doReindex(productEntry);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexProductEntries(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {

		String type = (String)searchContext.getAttribute("type");

		if (Validator.isNotNull(type)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			searchQuery.addRequiredTerm("type", type);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void reindexProductEntries(long companyId) throws Exception {
		int count =
			SCProductEntryLocalServiceUtil.getCompanyProductEntriesCount(
				companyId);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reindexProductEntries(companyId, start, end);
		}
	}

	protected void reindexProductEntries(long companyId, int start, int end)
		throws Exception {

		List<SCProductEntry> productEntries =
			SCProductEntryLocalServiceUtil.getCompanyProductEntries(
				companyId, start, end);

		if (productEntries.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (SCProductEntry productEntry : productEntries) {
			Document document = getDocument(productEntry);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

}