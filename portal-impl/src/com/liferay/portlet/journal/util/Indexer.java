/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.io.StringReader;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 *
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.JOURNAL;

	public static void addArticle(
			long companyId, long groupId, String articleId, double version,
			String title, String description, String content, String type,
			Date displayDate, String[] tagsEntries)
		throws SearchException {

		Document doc = getArticleDocument(
			companyId, groupId, articleId, version, title, description, content,
			type, displayDate, tagsEntries);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void deleteArticle(long companyId, String articleId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getArticleUID(articleId));
	}

	public static Document getArticleDocument(
		long companyId, long groupId, String articleId, double version,
		String title, String description, String content, String type,
		Date displayDate, String[] tagsEntries) {

		if ((content != null) &&
			((content.indexOf("<dynamic-content>") != -1) ||
			 (content.indexOf("<static-content") != -1))) {

			content = _getIndexableContent(content);

			content = StringUtil.replace(
				content, "<![CDATA[", StringPool.BLANK);
			content = StringUtil.replace(content, "]]>", StringPool.BLANK);
		}

		content = StringUtil.replace(content, "&amp;", "&");
		content = StringUtil.replace(content, "&lt;", "<");
		content = StringUtil.replace(content, "&gt;", ">");

		content = HtmlUtil.extractText(content);

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, articleId);

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.GROUP_ID, groupId);

		doc.addText(Field.TITLE, title);
		doc.addText(Field.CONTENT, content);
		doc.addText(Field.DESCRIPTION, description);

		doc.addModifiedDate();

		doc.addKeyword(Field.ENTRY_CLASS_PK, articleId);
		doc.addKeyword("version", version);
		doc.addKeyword("type", type);
		doc.addDate("displayDate", displayDate);

		doc.addKeyword(Field.TAGS_ENTRIES, tagsEntries);

		return doc;
	}

	public static String getArticleUID(String articleId) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, articleId);

		return doc.get(Field.UID);
	}

	public static void updateArticle(
			long companyId, long groupId, String articleId, double version,
			String title, String description, String content, String type,
			Date displayDate, String[] tagsEntries)
		throws SearchException {

		Document doc = getArticleDocument(
			companyId, groupId, articleId, version, title, description, content,
			type, displayDate, tagsEntries);

		SearchEngineUtil.updateDocument(companyId, doc.get(Field.UID), doc);
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Title

		String title = doc.get(Field.TITLE);

		// Content

		String content = doc.get(Field.CONTENT);

		content = StringUtil.shorten(content, 200);

		// Portlet URL

		String groupId = doc.get("groupId");
		String articleId = doc.get(Field.ENTRY_CLASS_PK);
		String version = doc.get("version");

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter("groupId", groupId);
		portletURL.setParameter("articleId", articleId);
		portletURL.setParameter("version", version);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			JournalArticleLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static String _getIndexableContent(String content) {
		try {
			StringMaker sm = new StringMaker();

			SAXReader reader = new SAXReader();

			org.dom4j.Document doc = reader.read(new StringReader(content));

			Element root = doc.getRootElement();

			_getIndexableContent(sm, root);

			return sm.toString();
		}
		catch (Exception e) {
			e.printStackTrace();

			return content;
		}
	}

	private static void _getIndexableContent(StringMaker sm, Element root)
		throws Exception {

		for (Element el : (List<Element>)root.elements()) {
			String elType = el.attributeValue("type", StringPool.BLANK);

			if (elType.equals("text") || elType.equals("text_box") ||
				elType.equals("text_area")) {

				Element dynamicContent = el.element("dynamic-content");

				String text = dynamicContent.getText();

				sm.append(text);
				sm.append(StringPool.BLANK);
			}
			else if (el.getName().equals("static-content")) {
				String text = el.getText();

				sm.append(text);
				sm.append(StringPool.BLANK);
			}

			_getIndexableContent(sm, el);
		}
	}

}