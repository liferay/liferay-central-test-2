/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.util.Html;
import com.liferay.util.License;

import java.io.IOException;

import java.util.Date;
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
 * <a href="PluginPackageIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class PluginPackageIndexer implements Indexer {

	public static final String PORTLET_ID = "PluginPackageIndexer";

	public static void addPluginPackage(
			String moduleId, String name, String version, Date modifiedDate,
			String author, List types, List tags, List licenses,
			List liferayVersions, String shortDescription,
			String longDescription, String changeLog, String pageURL,
			String repositoryURL, String status, String installedVersion)
		throws IOException {

		ModuleId moduleIdObj = ModuleId.getInstance(moduleId);

		shortDescription = Html.stripHtml(shortDescription);
		longDescription = Html.stripHtml(longDescription);

		String content =
			name + " " + author + " " + shortDescription + " " +
				longDescription;

		String pluginId = repositoryURL + StringPool.SLASH + moduleId;

		Document doc = new Document();

		doc.add(
			LuceneFields.getKeyword(
				LuceneFields.UID, LuceneFields.getUID(PORTLET_ID, pluginId)));

		doc.add(LuceneFields.getKeyword(LuceneFields.PORTLET_ID, PORTLET_ID));

		doc.add(LuceneFields.getText(LuceneFields.TITLE, name));
		doc.add(LuceneFields.getText(LuceneFields.CONTENT, content));

		doc.add(LuceneFields.getDate(LuceneFields.MODIFIED));

		doc.add(LuceneFields.getKeyword("moduleId", moduleId));
		doc.add(LuceneFields.getKeyword("groupId", moduleIdObj.getGroupId()));
		doc.add(
			LuceneFields.getKeyword("artifactId", moduleIdObj.getArtifactId()));
		doc.add(LuceneFields.getKeyword("version", version));
		doc.add(LuceneFields.getDate("modified-date", modifiedDate));
		doc.add(LuceneFields.getKeyword("shortDescription", shortDescription));
		doc.add(LuceneFields.getKeyword("changeLog", changeLog));
		doc.add(LuceneFields.getKeyword("repositoryURL", repositoryURL));

		StringMaker sm = new StringMaker();

		Iterator itr = types.iterator();

		while (itr.hasNext()) {
			String type = (String)itr.next();

			doc.add(LuceneFields.getKeyword("type", type));

			sm.append(type);

			if (itr.hasNext()) {
				sm.append(StringPool.COMMA);
				sm.append(StringPool.SPACE);
			}
		}

		doc.add(LuceneFields.getKeyword("types", sm.toString()));

		sm = new StringMaker();

		itr = tags.iterator();

		while (itr.hasNext()) {
			String tag = (String)itr.next();

			doc.add(LuceneFields.getKeyword("tag", tag));

			sm.append(tag);

			if (itr.hasNext()) {
				sm.append(StringPool.COMMA);
				sm.append(StringPool.SPACE);
			}
		}

		doc.add(LuceneFields.getKeyword("tags", sm.toString()));

		boolean osiLicense = false;

		itr = licenses.iterator();

		while (itr.hasNext()) {
			License license = (License)itr.next();

			doc.add(LuceneFields.getKeyword("license", license.getName()));

			if (license.isOsiApproved()) {
				osiLicense = true;
			}
		}

		doc.add(
			LuceneFields.getKeyword(
				"osi-approved-license", String.valueOf(osiLicense)));

		doc.add(LuceneFields.getKeyword("status", status));

		if (installedVersion != null) {
			doc.add(
				LuceneFields.getKeyword("installedVersion", installedVersion));
		}

		synchronized (PluginPackageIndexer.class) {
			IndexWriter writer = null;

			try {
				writer = _getWriter();

				writer.addDocument(doc);
			}
			finally {
				if (writer != null) {
					LuceneUtil.write(writer);
				}
			}
		}
	}

	public static void cleanIndex() throws IOException{
		synchronized (PluginPackageIndexer.class) {
			IndexReader reader = null;

			try {
				reader = LuceneUtil.getReader(CompanyImpl.SYSTEM);

				reader.deleteDocuments(
					new Term(
						LuceneFields.PORTLET_ID,
						PluginPackageIndexer.PORTLET_ID));
			}
			finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
	}

	public static void removePluginPackage(
			String moduleId, String repositoryURL)
		throws IOException {

		String pluginId = repositoryURL + StringPool.SLASH + moduleId;

		synchronized (PluginPackageIndexer.class) {
			IndexReader reader = null;

			try {
				reader = LuceneUtil.getReader(CompanyImpl.SYSTEM);

				reader.deleteDocuments(
					new Term(
						LuceneFields.UID,
						LuceneFields.getUID(PORTLET_ID, pluginId)));
			}
			finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
	}

	public static void updatePluginPackage(
			String moduleId, String name, String version, Date modifiedDate,
			String author, List types, List tags, List licenses,
			List liferayVersions, String shortDescription,
			String longDescription, String changeLog, String pageURL,
			String repositoryURL, String status, String installedVersion)
		throws IOException {

		try {
			removePluginPackage(moduleId, repositoryURL);
		}
		catch (IOException ioe) {
		}

		addPluginPackage(
			moduleId, name, version, modifiedDate, author, types, tags,
			licenses, liferayVersions, shortDescription, longDescription,
			changeLog, pageURL, repositoryURL, status, installedVersion);
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
			PluginPackageUtil.reIndex();
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

	private static Log _log = LogFactory.getLog(PluginPackageIndexer.class);

}