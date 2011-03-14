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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

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
public class WikiIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {WikiPage.class.getName()};

	public static final String PORTLET_ID = PortletKeys.WIKI;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected Summary doGetSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String nodeId = document.get("nodeId");

		portletURL.setParameter("struts_action", "/wiki/view");
		portletURL.setParameter("nodeId", nodeId);
		portletURL.setParameter("title", title);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		if (obj instanceof Object[]) {
			Object[] array = (Object[])obj;

			long companyId = (Long)array[0];
			long nodeId = (Long)array[1];
			String title = (String)array[2];

			Document document = new DocumentImpl();

			document.addUID(PORTLET_ID, nodeId, title);

			SearchEngineUtil.deleteDocument(companyId, document.get(Field.UID));

		}
		else if (obj instanceof WikiNode) {
			WikiNode node = (WikiNode)obj;

			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create();

			booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

			booleanQuery.addRequiredTerm("nodeId", node.getNodeId());

			Hits hits = SearchEngineUtil.search(
				node.getCompanyId(), booleanQuery, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (int i = 0; i < hits.getLength(); i++) {
				Document document = hits.doc(i);

				SearchEngineUtil.deleteDocument(
					node.getCompanyId(), document.get(Field.UID));
			}
		}
		else if (obj instanceof WikiPage) {
			WikiPage page = (WikiPage)obj;

			Document document = new DocumentImpl();

			document.addUID(PORTLET_ID, page.getNodeId(), page.getTitle());

			SearchEngineUtil.deleteDocument(
				page.getCompanyId(), document.get(Field.UID));
		}
	}

	protected void doReindex(Object obj) throws Exception {
		WikiPage page = (WikiPage)obj;

		if (Validator.isNotNull(page.getRedirectTitle())) {
			return;
		}

		Document document = getDocument(page);

		SearchEngineUtil.updateDocument(page.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		doReindex(page);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexNodes(companyId);
	}

	protected Document doGetDocument(Object obj) throws Exception {
		WikiPage page = (WikiPage)obj;

		long companyId = page.getCompanyId();
		long groupId = getParentGroupId(page.getGroupId());
		long scopeGroupId = page.getGroupId();
		long userId = page.getUserId();
		long resourcePrimKey = page.getResourcePrimKey();
		long nodeId = page.getNodeId();
		String title = page.getTitle();
		String content = HtmlUtil.extractText(page.getContent());
		Date modifiedDate = page.getModifiedDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			WikiPage.class.getName(), resourcePrimKey);
		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				WikiPage.class.getName(), resourcePrimKey);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			WikiPage.class.getName(), resourcePrimKey);

		ExpandoBridge expandoBridge = page.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, nodeId, title);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_CATEGORY_NAMES, assetCategoryNames);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.NODE_ID, nodeId);
		document.addKeyword(Field.ENTRY_CLASS_NAME, WikiPage.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, resourcePrimKey);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void checkSearchNodeId(
			long nodeId, SearchContext searchContext)
		throws Exception {

		WikiNodeServiceUtil.getNode(nodeId);
	}

	protected void reindexNodes(long companyId) throws Exception {
		int nodeCount = WikiNodeLocalServiceUtil.getCompanyNodesCount(
			companyId);

		int nodePages = nodeCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= nodePages; i++) {
			int nodeStart = (i * Indexer.DEFAULT_INTERVAL);
			int nodeEnd = nodeStart + Indexer.DEFAULT_INTERVAL;

			reindexNodes(companyId, nodeStart, nodeEnd);
		}
	}

	protected void reindexNodes(long companyId, int nodeStart, int nodeEnd)
		throws Exception {

		List<WikiNode> nodes = WikiNodeLocalServiceUtil.getCompanyNodes(
			companyId, nodeStart, nodeEnd);

		for (WikiNode node : nodes) {
			long nodeId = node.getNodeId();

			int pageCount = WikiPageLocalServiceUtil.getPagesCount(
				nodeId, true);

			int pagePages = pageCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= pagePages; i++) {
				int pageStart = (i * Indexer.DEFAULT_INTERVAL);
				int pageEnd = pageStart + Indexer.DEFAULT_INTERVAL;

				reindexPages(companyId, nodeId, pageStart, pageEnd);
			}
		}
	}

	protected void reindexPages(
			long companyId, long nodeId, int pageStart, int pageEnd)
		throws Exception {

		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			nodeId, true, pageStart, pageEnd);

		if (pages.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (WikiPage page : pages) {
			Document document = getDocument(page);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

}