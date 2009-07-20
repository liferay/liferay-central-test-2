/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletURL;

public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.WIKI;

	public static void addPage(
			long companyId, long groupId, long resourcePrimKey, long nodeId,
			String title, String content, Date modifiedDate,
			long[] assetCategoryIds, String[] assetTagNames,
			ExpandoBridge expandoBridge)
		throws SearchException {

		try {
			deletePage(companyId, nodeId, title);
		}
		catch (SearchException se) {
		}

		Document doc = getPageDocument(
			companyId, groupId, resourcePrimKey, nodeId, title, content,
			modifiedDate, assetCategoryIds, assetTagNames, expandoBridge);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void deletePage(long companyId, long nodeId, String title)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getPageUID(nodeId, title));
	}

	public static void deletePages(long companyId, long nodeId)
		throws SearchException {

		BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create();

		booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

		booleanQuery.addRequiredTerm("nodeId", nodeId);

		Hits hits = SearchEngineUtil.search(
			companyId, booleanQuery, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (int i = 0; i < hits.getLength(); i++) {
			Document doc = hits.doc(i);

			SearchEngineUtil.deleteDocument(companyId, doc.get(Field.UID));
		}
	}

	public static Document getPageDocument(
		long companyId, long groupId, long resourcePrimKey, long nodeId,
		String title, String content, Date modifiedDate,
		long[] assetCategoryIds, String[] assetTagNames,
		ExpandoBridge expandoBridge) {

		content = HtmlUtil.extractText(content);

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, nodeId, title);

		doc.addModifiedDate(modifiedDate);

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.GROUP_ID, groupId);

		doc.addText(Field.TITLE, title);
		doc.addText(Field.CONTENT, content);
		doc.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		doc.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		doc.addKeyword(Field.ENTRY_CLASS_NAME, WikiPage.class.getName());
		doc.addKeyword(Field.ENTRY_CLASS_PK, resourcePrimKey);
		doc.addKeyword("nodeId", nodeId);

		ExpandoBridgeIndexerUtil.addAttributes(doc, expandoBridge);

		return doc;
	}

	public static String getPageUID(long nodeId, String title) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, nodeId, title);

		return doc.get(Field.UID);
	}

	public static void updatePage(
			long companyId, long groupId, long resourcePrimKey, long nodeId,
			String title, String content, Date modifiedDate,
			long[] assetCategoryIds, String[] assetTagNames,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document doc = getPageDocument(
			companyId, groupId, resourcePrimKey, nodeId, title, content,
			modifiedDate, assetCategoryIds, assetTagNames, expandoBridge);

		SearchEngineUtil.updateDocument(companyId, doc.get(Field.UID), doc);
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public DocumentSummary getDocumentSummary(
		Document doc, String snippet, PortletURL portletURL) {

		// Title

		String title = doc.get(Field.TITLE);

		// Content

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(doc.get(Field.CONTENT), 200);
		}

		// Portlet URL

		String nodeId = doc.get("nodeId");

		portletURL.setParameter("struts_action", "/wiki/view");
		portletURL.setParameter("nodeId", nodeId);
		portletURL.setParameter("title", title);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String className, long classPK) throws SearchException {
		try {
			WikiPageLocalServiceUtil.reIndex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			WikiNodeLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final String[] _CLASS_NAMES = new String[] {
		WikiPage.class.getName()
	};

}