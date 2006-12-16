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

package com.liferay.portlet.softwarerepository.util;

import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarerepository.service.SRProductEntryLocalServiceUtil;
import com.liferay.util.Html;
import com.liferay.util.StringUtil;

import java.io.IOException;

import javax.portlet.PortletURL;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Indexer
	implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.SOFTWARE_REPOSITORY;

	public static void addProductEntry(
		String companyId, long productEntryId, String userName, String groupId,
		String repoArtifactId, String repoGroupId, String name, String type,
		String shortDescription, String longDescription, String pageURL)
		throws IOException {

		synchronized (IndexWriter.class) {
			shortDescription = Html.stripHtml(shortDescription);
			longDescription = Html.stripHtml(longDescription);

			IndexWriter writer = LuceneUtil.getWriter(companyId);

			Document doc = new Document();

			doc.add(
				LuceneFields.getKeyword(
					LuceneFields.UID,
					LuceneFields.getUID(
						PORTLET_ID, Long.toString(productEntryId))));

			doc.add(
				LuceneFields.getKeyword(LuceneFields.COMPANY_ID, companyId));
			doc.add(
				LuceneFields.getKeyword(LuceneFields.PORTLET_ID, PORTLET_ID));
			doc.add(LuceneFields.getKeyword(LuceneFields.GROUP_ID, groupId));

			doc.add(LuceneFields.getText(LuceneFields.TITLE, name));

			String content = repoArtifactId + " " + repoGroupId + " " + type +
				" " + shortDescription + " " + longDescription + " " + pageURL +
				" " + userName;
			doc.add(LuceneFields.getText(LuceneFields.CONTENT, content));

			doc.add(LuceneFields.getDate(LuceneFields.MODIFIED));

			doc.add(LuceneFields.getKeyword("repoArtifactId", repoArtifactId));
			doc.add(LuceneFields.getKeyword("repoGroupId", repoGroupId));
			doc.add(LuceneFields.getKeyword("userName", userName));
			doc.add(LuceneFields.getKeyword("type", type));
			doc.add(LuceneFields.getKeyword(
				"productEntryId", Long.toString(productEntryId)));

			writer.addDocument(doc);

			LuceneUtil.write(writer);
		}
	}

	public static void deleteProductEntry(String companyId, long productEntryId)
		throws IOException {

		synchronized (IndexWriter.class) {
			IndexReader reader = LuceneUtil.getReader(companyId);

			reader.deleteDocuments(
				new Term(
					LuceneFields.UID,
					LuceneFields.getUID(PORTLET_ID,
						Long.toString(productEntryId))));

			reader.close();
		}
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Title

		String title = doc.get(LuceneFields.TITLE);

		// Content

		String content = doc.get(LuceneFields.CONTENT);

		content = StringUtil.shorten(content, 200);

		// URL

		String productEntryId = doc.get("productEntryId");

		portletURL.setParameter("struts_action", "/software_repository/view_product_entry");
		portletURL.setParameter("productEntryId", productEntryId);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			SRProductEntryLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public static void updateProductEntry(
		String companyId, long productEntryId, String userName, String groupId,
		String repoArtifactId, String repoGroupId,
		String name, String type, String shortDescription,
		String longDescription, String pageURL)
		throws IOException {

		try {
			deleteProductEntry(companyId, productEntryId);
		}
		catch (IOException ioe) {
		}

		addProductEntry(
			companyId, productEntryId, userName, groupId, repoArtifactId,
			repoGroupId, name, type, shortDescription, longDescription,
			pageURL);
	}

}