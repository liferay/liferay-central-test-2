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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.service.spring.BlogsEntryLocalServiceUtil;
import com.liferay.util.Html;
import com.liferay.util.StringUtil;
import com.liferay.util.lucene.DocumentSummary;
import com.liferay.util.lucene.IndexerException;

import java.io.IOException;

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
public class Indexer implements com.liferay.util.lucene.Indexer {

	public static final String PORTLET_ID = PortletKeys.BLOGS;

	public static void addEntry(
			String companyId, String groupId, String userId, String categoryId,
			String entryId, String title, String content)
		throws IOException {

		synchronized (IndexWriter.class) {
			content = Html.stripHtml(content);

			IndexWriter writer = LuceneUtil.getWriter(companyId);

			Document doc = new Document();

			doc.add(
				LuceneFields.getKeyword(
					LuceneFields.UID,
					LuceneFields.getUID(PORTLET_ID, entryId)));

			doc.add(
				LuceneFields.getKeyword(LuceneFields.COMPANY_ID, companyId));
			doc.add(
				LuceneFields.getKeyword(LuceneFields.PORTLET_ID, PORTLET_ID));
			doc.add(LuceneFields.getKeyword(LuceneFields.GROUP_ID, groupId));
			doc.add(LuceneFields.getKeyword(LuceneFields.USER_ID, userId));

			doc.add(LuceneFields.getText(LuceneFields.TITLE, title));
			doc.add(LuceneFields.getText(LuceneFields.CONTENT, content));

			doc.add(LuceneFields.getDate(LuceneFields.MODIFIED));

			doc.add(LuceneFields.getKeyword("categoryId", categoryId));
			doc.add(LuceneFields.getKeyword("entryId", entryId));

			writer.addDocument(doc);

			LuceneUtil.write(writer);
		}
	}

	public static void deleteEntry(
			String companyId, String entryId)
		throws IOException {

		synchronized (IndexWriter.class) {
			IndexReader reader = LuceneUtil.getReader(companyId);

			reader.deleteDocuments(
				new Term(
					LuceneFields.UID,
					LuceneFields.getUID(PORTLET_ID, entryId)));

			reader.close();
		}
	}

	public static void updateEntry(
			String companyId, String groupId, String userId, String categoryId,
			String entryId, String title, String content)
		throws IOException {

		try {
			deleteEntry(companyId, entryId);
		}
		catch (IOException ioe) {
		}

		addEntry(
			companyId, groupId, userId, categoryId, entryId, title, content);
	}

	public DocumentSummary getDocumentSummary(
		Document doc, PortletURL portletURL) {

		// Title

		String title = doc.get(LuceneFields.TITLE);

		// Content

		String content = doc.get(LuceneFields.CONTENT);

		content = StringUtil.shorten(content, 200);

		// URL

		String entryId = doc.get("entryId");

		portletURL.setParameter("struts_action", "/blogs/view_entry");
		portletURL.setParameter("entryId", entryId);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String[] ids) throws IndexerException {
		try {
			BlogsEntryLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new IndexerException(e);
		}
	}

}