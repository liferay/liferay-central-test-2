/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.util.Html;
import com.liferay.util.StringUtil;

import java.io.IOException;

import java.util.Date;

import javax.portlet.PortletURL;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.JOURNAL;

	public static void addArticle(
			String companyId, long groupId, String articleId, double version,
			String title, String description, String content, String type,
			Date displayDate)
		throws IOException {

		synchronized (IndexWriter.class) {
			if ((content != null) && (content.indexOf("<root>") != -1) &&
				(content.indexOf("<dynamic-content>") != -1)) {

				content = StringUtil.replace(
					content, "<![CDATA[", StringPool.BLANK);
				content = StringUtil.replace(content, "]]>", StringPool.BLANK);
			}

			content = Html.stripHtml(content);

			IndexWriter writer = LuceneUtil.getWriter(companyId);

			Document doc = new Document();

			doc.add(
				LuceneFields.getKeyword(
					LuceneFields.UID,
					LuceneFields.getUID(PORTLET_ID, articleId)));

			doc.add(
				LuceneFields.getKeyword(LuceneFields.COMPANY_ID, companyId));
			doc.add(
				LuceneFields.getKeyword(LuceneFields.PORTLET_ID, PORTLET_ID));
			doc.add(LuceneFields.getKeyword(LuceneFields.GROUP_ID, groupId));

			doc.add(LuceneFields.getText(LuceneFields.TITLE, title));
			doc.add(LuceneFields.getText(LuceneFields.CONTENT, content));
			doc.add(LuceneFields.getText(
				LuceneFields.DESCRIPTION, description));

			doc.add(LuceneFields.getDate(LuceneFields.MODIFIED));

			doc.add(LuceneFields.getKeyword("articleId", articleId));
			doc.add(
				LuceneFields.getKeyword("version", Double.toString(version)));
			doc.add(LuceneFields.getKeyword("type", type));
			doc.add(LuceneFields.getDate("displayDate", displayDate));

			writer.addDocument(doc);

			LuceneUtil.write(writer);
		}
	}

	public static void deleteArticle(String companyId, String articleId)
		throws IOException {

		synchronized (IndexWriter.class) {
			IndexReader reader = LuceneUtil.getReader(companyId);

			reader.deleteDocuments(
				new Term(
					LuceneFields.UID,
					LuceneFields.getUID(PORTLET_ID, articleId)));

			reader.close();
		}
	}

	public static void updateArticle(
			String companyId, long groupId, String articleId, double version,
			String title, String description, String content, String type,
			Date displayDate)
		throws IOException {

		try {
			deleteArticle(companyId, articleId);
		}
		catch (IOException ioe) {
		}

		addArticle(
			companyId, groupId, articleId, version, title, description, content,
			type, displayDate);
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Title

		String title = doc.get(LuceneFields.TITLE);

		// Content

		String content = doc.get(LuceneFields.CONTENT);

		content = StringUtil.shorten(content, 200);

		// URL

		String groupId = doc.get("groupId");
		String articleId = doc.get("articleId");
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

}