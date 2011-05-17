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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Hugo Huijser
 */
public class JournalIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {JournalArticle.class.getName()};

	public static final String PORTLET_ID = PortletKeys.JOURNAL;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		String type = (String)searchContext.getAttribute("type");

		if (Validator.isNotNull(type)) {
			contextQuery.addRequiredTerm("type", type);
		}

		Long classNameId = (Long)searchContext.getAttribute(
			Field.CLASS_NAME_ID);

		if (Validator.isNotNull(classNameId)) {
			contextQuery.addRequiredTerm("classNameId", classNameId.toString());
		}

		String structureId = (String)searchContext.getAttribute("structureId");

		if (Validator.isNotNull(structureId)) {
			contextQuery.addRequiredTerm("structureId", structureId);
		}

		String templateId = (String)searchContext.getAttribute("templateId");

		if (Validator.isNotNull(templateId)) {
			contextQuery.addRequiredTerm("templateId", templateId);
		}
	}

	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.CLASS_PK, false);
		addLocalizedSearchTerm(searchQuery, searchContext, Field.CONTENT, true);
		addLocalizedSearchTerm(
			searchQuery, searchContext, Field.DESCRIPTION, true);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, true);

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_ANY);

		if (status != WorkflowConstants.STATUS_ANY) {
			addSearchTerm(searchQuery, searchContext, Field.STATUS, false);
		}

		addLocalizedSearchTerm(searchQuery, searchContext, Field.TITLE, true);
		addSearchTerm(searchQuery, searchContext, Field.TYPE, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, true);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		String expandoAttributes = (String)params.get("expandoAttributes");

		if (Validator.isNotNull(expandoAttributes)) {
			addSearchExpando(searchQuery, searchContext, expandoAttributes);
		}
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
		String userName = PortalUtil.getUserName(userId, article.getUserName());
		Date modifiedDate = article.getModifiedDate();
		long resourcePrimKey = article.getResourcePrimKey();
		long classNameId = article.getClassNameId();
		long classPK = article.getClassPK();
		String articleId = article.getArticleId();
		double version = article.getVersion();
		Map<Locale, String> titleMap = article.getTitleMap();
		Map<Locale, String> descriptionMap = article.getDescriptionMap();
		String type = article.getType();

		String structureId = article.getStructureId();

		JournalStructure structure = null;

		if (Validator.isNotNull(structureId)) {
			try {
				structure = JournalStructureLocalServiceUtil.getStructure(
					groupId, structureId);
			}
			catch (NoSuchStructureException nsse1) {
				Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

				try {
					structure = JournalStructureLocalServiceUtil.getStructure(
						group.getGroupId(), structureId);
				}
				catch (NoSuchStructureException nsse2) {
				}
			}
		}

		String templateId = article.getTemplateId();
		Date displayDate = article.getDisplayDate();
		long status = article.getStatus();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			JournalArticle.class.getName(), resourcePrimKey);
		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				JournalArticle.class.getName(), resourcePrimKey);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			JournalArticle.class.getName(), resourcePrimKey);

		ExpandoBridge expandoBridge = article.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, groupId, articleId);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		document.addKeyword(Field.USER_NAME, userName, true);

		Locale defaultLocale = LocaleUtil.getDefault();

		document.addLocalizedText(Field.TITLE, titleMap);

		processStructure(structure, document, article.getContent());

		String defaultLangaugeId = LocaleUtil.toLanguageId(defaultLocale);

		String[] languageIds = LocalizationUtil.getAvailableLocales(
			article.getContent());

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLangaugeId};
		}

		for (String languageId : languageIds) {
			String content = extractContent(
				article.getContentByLocale(languageId));

			if (languageId.equals(defaultLangaugeId)) {
				document.addText(Field.CONTENT, content);
			}

			document.addText(
				Field.CONTENT.concat(StringPool.UNDERLINE).concat(languageId),
				content);
		}

		document.addLocalizedText(Field.DESCRIPTION, descriptionMap);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_CATEGORY_NAMES, assetCategoryNames);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(
			Field.ENTRY_CLASS_NAME, JournalArticle.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, articleId);
		document.addKeyword(Field.ROOT_ENTRY_CLASS_PK, resourcePrimKey);
		document.addKeyword(Field.CLASS_NAME_ID, classNameId);
		document.addKeyword(Field.CLASS_PK, classPK);
		document.addKeyword(Field.VERSION, version);
		document.addKeyword(Field.TYPE, type);
		document.addKeyword(Field.STATUS, status);

		document.addKeyword("structureId", structureId);
		document.addKeyword("templateId", templateId);
		document.addDate("displayDate", displayDate);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("display-date")) {
			return "displayDate";
		}
		else if (orderByCol.equals("id")) {
			return Field.ENTRY_CLASS_PK;
		}
		else if (orderByCol.equals("modified-date")) {
			return Field.MODIFIED;
		}
		else if (orderByCol.equals("title")) {
			return Field.TITLE;
		}
		else {
			return orderByCol;
		}
	}

	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String title = document.get(locale, Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(
				document.get(locale, Field.CONTENT), 200);
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

	protected void doReindex(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		Document document = getDocument(article);

		if (!article.isApproved() || !article.isIndexable()) {
			SearchEngineUtil.deleteDocument(
				article.getCompanyId(), document.get(Field.UID));

			return;
		}

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

	protected String extractContent(String content) {
		content = StringUtil.replace(content, "<![CDATA[", StringPool.BLANK);
		content = StringUtil.replace(content, "]]>", StringPool.BLANK);
		content = StringUtil.replace(content, "&amp;", "&");
		content = StringUtil.replace(content, "&lt;", "<");
		content = StringUtil.replace(content, "&gt;", ">");

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void indexField(
		Document document, Element element, String elType, String elIndexType) {

		if (Validator.isNull(elIndexType)) {
			return;
		}

		com.liferay.portal.kernel.xml.Document structureDocument =
			element.getDocument();

		Element rootElement = structureDocument.getRootElement();

		String defaultLocale = GetterUtil.getString(
			rootElement.attributeValue("default-locale"));

		String name = encodeFieldName(element.attributeValue("name"));

		List<Element> dynamicContentElements = element.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			String contentLocale = GetterUtil.getString(
				dynamicContentElement.attributeValue("language-id"));

			String[] value = new String[] {dynamicContentElement.getText()};

			if (elType.equals("multi-list")) {
				List<Element> optionElements = dynamicContentElement.elements(
					"option");

				value = new String[optionElements.size()];

				for (int i = 0; i < optionElements.size(); i++) {
					value[i] = optionElements.get(i).getText();
				}
			}

			if (elIndexType.equals("keyword")) {
				if (Validator.isNull(contentLocale)) {
					document.addKeyword(name, value);
				}
				else {
					if (defaultLocale.equals(contentLocale)) {
						document.addKeyword(name, value);
					}

					document.addKeyword(
						name.concat(StringPool.UNDERLINE).concat(contentLocale),
						value);
				}
			}
			else if (elIndexType.equals("text")) {
				if (Validator.isNull(contentLocale)) {
					document.addText(
						name, StringUtil.merge(value, StringPool.SPACE));
				}
				else {
					if (defaultLocale.equals(contentLocale)) {
						document.addText(
							name, StringUtil.merge(value, StringPool.SPACE));
					}

					document.addText(
						name.concat(StringPool.UNDERLINE).concat(contentLocale),
						StringUtil.merge(value, StringPool.SPACE));
				}
			}
		}
	}

	protected void processStructure(
			com.liferay.portal.kernel.xml.Document structureDocument,
			Document document, Element rootElement)
		throws Exception {

		LinkedList<Element> queue = new LinkedList<Element>(
			rootElement.elements());

		Element element = null;

		while ((element = queue.poll()) != null) {
			String elName = element.attributeValue("name", StringPool.BLANK);
			String elType = element.attributeValue("type", StringPool.BLANK);
			String elIndexType = element.attributeValue(
				"index-type", StringPool.BLANK);

			if (structureDocument != null) {
				String path = element.getPath().concat(
					"[@name='").concat(elName).concat("']");

				Node structureNode = structureDocument.selectSingleNode(path);

				if (structureNode != null) {
					Element structureElement = (Element)structureNode;

					elType = structureElement.attributeValue(
						"type", StringPool.BLANK);
					elIndexType = structureElement.attributeValue(
						"index-type", StringPool.BLANK);
				}
			}

			if (Validator.isNotNull(elType)) {
				indexField(document, element, elType, elIndexType);
			}

			queue.addAll(element.elements());
		}
	}

	protected void processStructure(
		JournalStructure structure, Document document, String content) {

		try {
			com.liferay.portal.kernel.xml.Document structureDocument = null;

			if (structure != null) {
				structureDocument = SAXReaderUtil.read(structure.getXsd());
			}

			com.liferay.portal.kernel.xml.Document contentDocument =
				SAXReaderUtil.read(content);

			Element rootElement = contentDocument.getRootElement();

			processStructure(structureDocument, document, rootElement);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
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
			if (!article.isIndexable()) {
				continue;
			}

			Document document = getDocument(article);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	private static final String _FIELD_NAMESPACE = "web_content";

	private static Log _log = LogFactoryUtil.getLog(JournalIndexer.class);

}