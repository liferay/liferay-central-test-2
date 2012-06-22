/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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

		Long classNameId = (Long)searchContext.getAttribute(
			Field.CLASS_NAME_ID);

		if (classNameId != null) {
			contextQuery.addRequiredTerm("classNameId", classNameId.toString());
		}

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		String articleType = (String)searchContext.getAttribute("articleType");

		if (Validator.isNotNull(articleType)) {
			contextQuery.addRequiredTerm(Field.TYPE, articleType);
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

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.CLASS_PK, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchTerm(searchQuery, searchContext, Field.TYPE, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId, double version, int status) {

		addReindexCriteria(dynamicQuery, companyId, status);

		Property property = PropertyFactoryUtil.forName("version");

		dynamicQuery.add(property.eq(version));
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId, int status) {

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(status));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		deleteDocument(
			article.getCompanyId(), article.getGroupId(),
			article.getArticleId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		Document document = getBaseModelDocument(PORTLET_ID, article);

		document.addUID(
			PORTLET_ID, article.getGroupId(), article.getArticleId());

		Locale defaultLocale = LocaleUtil.getDefault();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String[] languageIds = getLanguageIds(
			defaultLanguageId, article.getContent());

		for (String languageId : languageIds) {
			String content = extractContent(article, languageId);

			if (languageId.equals(defaultLanguageId)) {
				document.addText(Field.CONTENT, content);
			}

			document.addText(
				Field.CONTENT.concat(StringPool.UNDERLINE).concat(languageId),
				content);
		}

		document.addLocalizedText(
			Field.DESCRIPTION, article.getDescriptionMap());
		document.addLocalizedText(Field.TITLE, article.getTitleMap());
		document.addKeyword(Field.TYPE, article.getType());
		document.addKeyword(Field.VERSION, article.getVersion());

		document.addKeyword("articleId", article.getArticleId());
		document.addDate("displayDate", article.getDisplayDate());
		document.addKeyword("layoutUuid", article.getLayoutUuid());
		document.addKeyword("structureId", article.getStructureId());
		document.addKeyword("templateId", article.getTemplateId());

		JournalStructure structure = null;

		if (Validator.isNotNull(article.getStructureId())) {
			try {
				structure = JournalStructureLocalServiceUtil.getStructure(
					article.getGroupId(), article.getStructureId(), true);
			}
			catch (NoSuchStructureException nsse) {
			}
		}

		processStructure(structure, document, article.getContent());

		return document;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("display-date")) {
			return "displayDate";
		}
		else if (orderByCol.equals("id")) {
			return Field.ENTRY_CLASS_PK;
		}
		else if (orderByCol.equals("modified-date")) {
			return Field.MODIFIED_DATE;
		}
		else if (orderByCol.equals("title")) {
			return Field.TITLE;
		}
		else {
			return orderByCol;
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String title = document.get(locale, Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(
				document.get(locale, Field.CONTENT), 200);
		}

		String groupId = document.get(Field.GROUP_ID);
		String articleId = document.get("articleId");
		String version = document.get(Field.VERSION);

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter("groupId", groupId);
		portletURL.setParameter("articleId", articleId);
		portletURL.setParameter("version", version);

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		Document document = getDocument(article);

		if (!article.isIndexable() ||
			(!article.isApproved() &&
			 (article.getVersion() !=
				  JournalArticleConstants.VERSION_DEFAULT))) {

			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), article.getCompanyId(),
				document.get(Field.UID));

			return;
		}

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), article.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				classPK, WorkflowConstants.STATUS_APPROVED);

		doReindex(article);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexArticles(companyId);
	}

	protected String encodeFieldName(String name) {
		return _FIELD_NAMESPACE.concat(StringPool.FORWARD_SLASH).concat(name);
	}

	protected String extractContent(JournalArticle article, String languageId) {
		String content = article.getContentByLocale(languageId);

		if (Validator.isNotNull(article.getStructureId())) {
			content = extractDynamicContent(content);
		}
		else {
			content = extractStaticContent(content);
		}

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected String extractDynamicContent(Element rootElement) {
		StringBundler sb = new StringBundler();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			String type = dynamicElementElement.attributeValue(
				"type", StringPool.BLANK);

			if (!type.equals("boolean") && !type.equals("document_library") &&
				!type.equals("image") && !type.equals("list") &&
				!type.equals("link_to_layout") && !type.equals("multi-list") &&
				!type.equals("selection_break")) {

				Element dynamicContentElement = dynamicElementElement.element(
					"dynamic-content");

				if (dynamicContentElement != null) {
					String dynamicContent = dynamicContentElement.getText();

					sb.append(dynamicContent);
					sb.append(StringPool.SPACE);
				}
			}

			sb.append(extractDynamicContent(dynamicElementElement));
		}

		return sb.toString();
	}

	protected String extractDynamicContent(String content) {
		try {
			com.liferay.portal.kernel.xml.Document document =
				SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			return extractDynamicContent(rootElement);
		}
		catch (DocumentException de) {
			_log.error(de);
		}

		return StringPool.BLANK;
	}

	protected String extractStaticContent(String content) {
		content = StringUtil.replace(content, "<![CDATA[", StringPool.BLANK);
		content = StringUtil.replace(content, "]]>", StringPool.BLANK);
		content = StringUtil.replace(content, "&amp;", "&");
		content = StringUtil.replace(content, "&lt;", "<");
		content = StringUtil.replace(content, "&gt;", ">");

		return content;
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = LocalizationUtil.getAvailableLocales(content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected List<JournalArticle> getReindexApprovedArticles(
			long companyId, long startId, long endId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JournalArticle.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("id");

		dynamicQuery.add(property.ge(startId));
		dynamicQuery.add(property.lt(endId));

		addReindexCriteria(
			dynamicQuery, companyId, WorkflowConstants.STATUS_APPROVED);

		return JournalArticleLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

	protected List<JournalArticle> getReindexDraftArticles(
			long companyId, long startId, long endId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JournalArticle.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("id");

		dynamicQuery.add(property.ge(startId));
		dynamicQuery.add(property.lt(endId));

		addReindexCriteria(
			dynamicQuery, companyId, JournalArticleConstants.VERSION_DEFAULT,
			WorkflowConstants.STATUS_APPROVED);

		return JournalArticleLocalServiceUtil.dynamicQuery(dynamicQuery);
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
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JournalArticle.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minIdProjection = ProjectionFactoryUtil.min("id");
		Projection maxIdProjection = ProjectionFactoryUtil.max("id");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minIdProjection);
		projectionList.add(maxIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(
			dynamicQuery, companyId, WorkflowConstants.STATUS_APPROVED);

		List<Object[]> results = JournalArticleLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxIds = results.get(0);

		if ((minAndMaxIds[0] == null) || (minAndMaxIds[1] == null)) {
			return;
		}

		long minId = (Long)minAndMaxIds[0];
		long maxId = (Long)minAndMaxIds[1];

		long startId = minId;
		long endId = startId + DEFAULT_INTERVAL;

		while (startId <= maxId) {
			reindexArticles(companyId, startId, endId);

			startId = endId;
			endId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexArticles(long companyId, long startId, long endId)
		throws Exception {

		List<JournalArticle> articles = new ArrayList<JournalArticle>();

		articles.addAll(getReindexApprovedArticles(companyId, startId, endId));
		articles.addAll(getReindexDraftArticles(companyId, startId, endId));

		if (articles.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			articles.size());

		for (JournalArticle article : articles) {
			if (!article.isIndexable()) {
				continue;
			}

			if (article.isApproved()) {
				JournalArticle latestArticle =
					JournalArticleLocalServiceUtil.getLatestArticle(
						article.getResourcePrimKey(),
						WorkflowConstants.STATUS_APPROVED);

				if (!latestArticle.isIndexable()) {
					continue;
				}
			}

			Document document = getDocument(article);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	private static final String _FIELD_NAMESPACE = "web_content";

	private static final boolean _PERMISSION_AWARE = true;

	private static Log _log = LogFactoryUtil.getLog(JournalIndexer.class);

}