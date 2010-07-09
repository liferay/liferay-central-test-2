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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class JournalIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {JournalArticle.class.getName()};

	public static final String PORTLET_ID = PortletKeys.JOURNAL;

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

		String groupId = document.get("groupId");
		String articleId = document.get(Field.ENTRY_CLASS_PK);
		String version = document.get("version");

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter("groupId", groupId);
		portletURL.setParameter("articleId", articleId);
		portletURL.setParameter("version", version);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		Document document = new DocumentImpl();

		document.addUID(
			PORTLET_ID, article.getGroupId(), article.getArticleId());

		SearchEngineUtil.deleteDocument(
			article.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		long companyId = article.getCompanyId();
		long groupId = getParentGroupId(article.getGroupId());
		long scopeGroupId = article.getGroupId();
		long userId = article.getUserId();
		long resourcePrimKey = article.getResourcePrimKey();
		String articleId = article.getArticleId();
		double version = article.getVersion();
		String title = article.getTitle();
		String description = article.getDescription();
		String content = article.getContent();
		String type = article.getType();
		Date displayDate = article.getDisplayDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			JournalArticle.class.getName(), resourcePrimKey);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			JournalArticle.class.getName(), resourcePrimKey);

		ExpandoBridge expandoBridge = article.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, groupId, articleId);

		document.addModifiedDate(displayDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, processContent(document, content));
		document.addText(Field.DESCRIPTION, description);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(
			Field.ENTRY_CLASS_NAME, JournalArticle.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, articleId);
		document.addKeyword(Field.ROOT_ENTRY_CLASS_PK, resourcePrimKey);
		document.addKeyword(Field.VERSION, version);
		document.addKeyword(Field.TYPE, type);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		if (!article.isApproved() || !article.isIndexable()) {
			return;
		}

		Document document = getDocument(article);

		SearchEngineUtil.updateDocument(article.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				classPK, WorkflowConstants.STATUS_APPROVED);

		doReindex(article);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexArticles(companyId);
	}

	protected String encodeFieldName(String name) {
		return _FIELD_NAMESPACE.concat(StringPool.FORWARD_SLASH).concat(name);
	}

	protected String getIndexableContent(Document document, Element rootElement)
		throws Exception {

		StringBundler sb = new StringBundler();

		LinkedList<Element> queue = new LinkedList<Element>(
			rootElement.elements());

		Element element = null;

		while ((element = queue.poll()) != null) {
			String elType = element.attributeValue("type", StringPool.BLANK);
			String elIndexType = element.attributeValue(
				"index-type", StringPool.BLANK);

			indexField(document, element, elType, elIndexType);

			if (elType.equals("text") || elType.equals("text_box") ||
				elType.equals("text_area")) {

				for (Element dynamicContentElement :
						element.elements("dynamic-content")) {

					String text = dynamicContentElement.getText();

					sb.append(text);
					sb.append(StringPool.SPACE);
				}
			}
			else if (element.getName().equals("static-content")) {
				String text = element.getText();

				sb.append(text);
				sb.append(StringPool.SPACE);
			}

			queue.addAll(element.elements());
		}

		return sb.toString();
	}

	protected String getIndexableContent(Document document, String content) {
		try {
			com.liferay.portal.kernel.xml.Document contentDocument =
				SAXReaderUtil.read(content);

			Element rootElement = contentDocument.getRootElement();

			return getIndexableContent(document, rootElement);
		}
		catch (Exception e) {
			_log.error(e, e);

			return content;
		}
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void indexField(
		Document document, Element element, String elType, String elIndexType) {

		if (Validator.isNull(elIndexType)) {
			return;
		}

		Element dynamicContentElement = element.element("dynamic-content");

		String fieldName = encodeFieldName(
			element.attributeValue("name", StringPool.BLANK));
		String[] value = new String[] {dynamicContentElement.getText()};

		if (elType.equals("multi-list")) {
			List<Element> optionElements = dynamicContentElement.elements();

			value = new String[optionElements.size()];

			for (int i = 0; i < optionElements.size(); i++) {
				value[i] = optionElements.get(i).getText();
			}
		}

		if (elIndexType.equals("keyword")) {
			document.addKeyword(fieldName, value);
		}
		else if (elIndexType.equals("text")) {
			document.addText(
				fieldName, StringUtil.merge(value, StringPool.SPACE));
		}
	}

	protected String processContent(Document document, String content) {
		if ((content != null) &&
			((content.indexOf("<dynamic-content") != -1) ||
			 (content.indexOf("<static-content") != -1))) {

			content = getIndexableContent(document, content);

			content = StringUtil.replace(
				content, "<![CDATA[", StringPool.BLANK);
			content = StringUtil.replace(content, "]]>", StringPool.BLANK);
		}

		content = StringUtil.replace(content, "&amp;", "&");
		content = StringUtil.replace(content, "&lt;", "<");
		content = StringUtil.replace(content, "&gt;", ">");

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected void reindexArticles(long companyId) throws Exception {
		int count = JournalArticleLocalServiceUtil.getCompanyArticlesCount(
			companyId, WorkflowConstants.STATUS_APPROVED);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reindexArticles(companyId, start, end);
		}
	}

	protected void reindexArticles(long companyId, int start, int end)
		throws Exception {

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getCompanyArticles(
				companyId, WorkflowConstants.STATUS_APPROVED, start, end);

		if (articles.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (JournalArticle article : articles) {
			Document document = getDocument(article);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	protected static final String _FIELD_NAMESPACE = "web_content";

	private static Log _log = LogFactoryUtil.getLog(JournalIndexer.class);

}