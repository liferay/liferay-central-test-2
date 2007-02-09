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

package com.liferay.portlet.admin.util;

import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.plugin.PluginUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.Html;
import com.liferay.util.License;
import com.liferay.util.StringUtil;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class Indexer
	implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.ADMIN;

	public static void addPlugin(
			String moduleId, String name, String version,
			String author, String type, List tags, List licenses,
			List liferayVersions, String shortDescription,
			String longDescription, String pageURL,	String repositoryURL)
		throws IOException {

		synchronized (IndexWriter.class) {
			shortDescription = Html.stripHtml(shortDescription);
			longDescription = Html.stripHtml(longDescription);

			String content =
				name + " " + author + " " + shortDescription + " " +
					longDescription;

			String pluginId = repositoryURL + StringPool.SLASH + moduleId;

			IndexWriter writer = _getWriter();

			Document doc = new Document();

			doc.add(
				LuceneFields.getKeyword(
					LuceneFields.UID,
					LuceneFields.getUID(PORTLET_ID, pluginId)));

			doc.add(
				LuceneFields.getKeyword(LuceneFields.PORTLET_ID, PORTLET_ID));

			doc.add(LuceneFields.getText(LuceneFields.TITLE, name));
			doc.add(LuceneFields.getText(LuceneFields.CONTENT, content));

			doc.add(LuceneFields.getDate(LuceneFields.MODIFIED));

			doc.add(LuceneFields.getKeyword("moduleId", moduleId));
			doc.add(LuceneFields.getKeyword("type", type));
			doc.add(LuceneFields.getKeyword("repositoryURL", repositoryURL));
			doc.add(LuceneFields.getKeyword("version", version));
			doc.add(LuceneFields.getKeyword(
				"shortDescription", shortDescription));

			StringBuffer sb = new StringBuffer();

			Iterator iterator = tags.iterator();

			while (iterator.hasNext()) {
				String tag = (String) iterator.next();
				doc.add(LuceneFields.getKeyword("tag", tag));

				sb.append(tag);

					if (iterator.hasNext()) {
					sb.append(StringPool.COMMA + StringPool.SPACE);
				}
			}

			doc.add(LuceneFields.getKeyword("tags", sb.toString()));

			boolean osiLicense = false;

			iterator = licenses.iterator();

			while (iterator.hasNext()) {
				License license = (License) iterator.next();
				doc.add(LuceneFields.getKeyword("license", license.getName()));
				if (license.isOsiApproved()) {
					osiLicense = true;
				}
			}

			doc.add(LuceneFields.getKeyword(
				"osi-approved-license", Boolean.toString(osiLicense)));

			writer.addDocument(doc);

			LuceneUtil.write(writer);
		}
	}

	public static void deletePlugin(String moduleId, String repositoryURL)
		throws IOException {

		String pluginId = repositoryURL + StringPool.SLASH + moduleId;

		synchronized (IndexWriter.class) {
			IndexReader reader = LuceneUtil.getReader(CompanyImpl.SYSTEM);

			reader.deleteDocuments(
				new Term(
					LuceneFields.UID,
					LuceneFields.getUID(PORTLET_ID, pluginId)));

			reader.close();
		}
	}

	public static void updatePlugin(
			String moduleId, String name, String version,
			String author, String type, List tags, List licenses,
			List liferayVersions, String shortDescription,
			String longDescription, String pageURL,	String repositoryURL)
		throws IOException {

		try {
			deletePlugin(moduleId, repositoryURL);
		}
		catch (IOException ioe) {
		}

		addPlugin(
			moduleId, name, version, author, type, tags, licenses,
			liferayVersions, shortDescription, longDescription, pageURL,
			repositoryURL);
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Title

		String title = doc.get(LuceneFields.TITLE);

		// Content

		String content = doc.get(LuceneFields.CONTENT);

		content = StringUtil.shorten(content, 200);

		// URL

		String moduleId = doc.get("moduleId");
		String repositoryURL = doc.get("repositoryURL");

		portletURL.setParameter(
			"struts_action", "/admin/view");
		portletURL.setParameter("tabs2", "repositories");
		portletURL.setParameter("moduleId", moduleId);
		portletURL.setParameter("repositoryURL", repositoryURL);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			PluginUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static IndexWriter _getWriter() {
		IndexWriter writer = null;
		Directory luceneDir = LuceneUtil.getLuceneDir(CompanyImpl.SYSTEM);

		try {
			if (luceneDir.fileExists("segments")) {
				writer = new IndexWriter(
					luceneDir, LuceneUtil.getAnalyzer(), false);
			}
			else {
				writer = new IndexWriter(
					luceneDir, LuceneUtil.getAnalyzer(), true);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe);
		}
		finally {
			if (writer != null) {
				try {
					writer.close();
				}
				catch (IOException ioe) {
					_log.error(ioe);
				}
			}
		}
		return writer;
	}

	private static Log _log = LogFactory.getLog(Indexer.class);

}