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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * <a href="JournalIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class JournalIndexer extends BaseIndexer {

	public static final String PORTLET_ID = PortletKeys.JOURNAL;

	public static void addArticle(
			long companyId, long groupId, long resourcePrimKey,
			String articleId, double version, String title, String description,
			String content, String type, Date displayDate,
			long[] assetCategoryIds, String[] assetTagNames,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document document = getArticleDocument(
			companyId, groupId, resourcePrimKey, articleId, version, title,
			description, content, type, displayDate, assetCategoryIds,
			assetTagNames, expandoBridge);

		SearchEngineUtil.addDocument(companyId, document);
	}

	public static void deleteArticle(
			long companyId, long groupId, String articleId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(
			companyId, getArticleUID(groupId, articleId));
	}

	public static Document getArticleDocument(
		long companyId, long groupId, long resourcePrimKey, String articleId,
		double version, String title, String description, String content,
		String type, Date displayDate, long[] assetCategoryIds,
		String[] assetTagNames, ExpandoBridge expandoBridge) {

		Document document = new DocumentImpl();

		if ((content != null) &&
			((content.indexOf("<dynamic-content") != -1) ||
			 (content.indexOf("<static-content") != -1))) {

			content = _getIndexableContent(document, content);

			content = StringUtil.replace(
				content, "<![CDATA[", StringPool.BLANK);
			content = StringUtil.replace(content, "]]>", StringPool.BLANK);
		}

		content = StringUtil.replace(content, "&amp;", "&");
		content = StringUtil.replace(content, "&lt;", "<");
		content = StringUtil.replace(content, "&gt;", ">");

		content = HtmlUtil.extractText(content);

		document.addUID(PORTLET_ID, groupId, articleId);

		document.addModifiedDate(displayDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
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

	public static String getArticleUID(long groupId, String articleId) {
		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, groupId, articleId);

		return document.get(Field.UID);
	}

	public static void updateArticle(
			long companyId, long groupId, long resourcePrimKey,
			String articleId, double version, String title, String description,
			String content, String type, Date displayDate,
			long[] assetCategoryIds, String[] assetTagNames,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document document = getArticleDocument(
			companyId, groupId, resourcePrimKey, articleId, version, title,
			description, content, type, displayDate, assetCategoryIds,
			assetTagNames, expandoBridge);

		SearchEngineUtil.updateDocument(
			companyId, document.get(Field.UID), document);
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		// Title

		String title = document.get(Field.TITLE);

		// Content

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		// Portlet URL

		String groupId = document.get("groupId");
		String articleId = document.get(Field.ENTRY_CLASS_PK);
		String version = document.get("version");

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter("groupId", groupId);
		portletURL.setParameter("articleId", articleId);
		portletURL.setParameter("version", version);

		return new Summary(title, content, portletURL);
	}

	public void reindex(String className, long classPK) throws SearchException {
		try {
			JournalArticleLocalServiceUtil.reindex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reindex(String[] ids) throws SearchException {
		try {
			JournalArticleLocalServiceUtil.reindex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static String _getIndexableContent(Document doc, String content) {
		try {
			StringBuilder sb = new StringBuilder();

			com.liferay.portal.kernel.xml.Document contentDoc =
				SAXReaderUtil.read(content);

			Element root = contentDoc.getRootElement();

			_getIndexableContent(sb, doc, root);

			return sb.toString();
		}
		catch (Exception e) {
			e.printStackTrace();

			return content;
		}
	}

	private static void _getIndexableContent(
			StringBuilder sb, Document doc, Element root)
		throws Exception {

		for (Element el : root.elements()) {
			String elType = el.attributeValue("type", StringPool.BLANK);
			String elIndexType = el.attributeValue(
				"index-type", StringPool.BLANK);

			_indexField(doc, el, elType, elIndexType);

			if (elType.equals("text") || elType.equals("text_box") ||
				elType.equals("text_area")) {

				for (Element dynamicContent : el.elements("dynamic-content")) {
					String text = dynamicContent.getText();

					sb.append(text);
					sb.append(StringPool.SPACE);
				}
			}
			else if (el.getName().equals("static-content")) {
				String text = el.getText();

				sb.append(text);
				sb.append(StringPool.SPACE);
			}

			_getIndexableContent(sb, doc, el);
		}
	}

	private static void _indexField(
		Document document, Element el, String elType, String elIndexType) {

		if (Validator.isNull(elIndexType)) {
			return;
		}

		Element dynamicContent = el.element("dynamic-content");

		String name = el.attributeValue("name", StringPool.BLANK);
		String[] value = new String[] {dynamicContent.getText()};

		if (elType.equals("multi-list")) {
			List<Element> options = dynamicContent.elements();

			value = new String[options.size()];

			for (int i = 0; i < options.size(); i++) {
				value[i] = options.get(i).getText();
			}
		}

		if (elIndexType.equals("keyword")) {
			document.addKeyword(name, value);
		}
		else if (elIndexType.equals("text")) {
			document.addText(name, StringUtil.merge(value, StringPool.SPACE));
		}
	}

	private static final String[] _CLASS_NAMES = new String[] {
		JournalArticle.class.getName()
	};

}