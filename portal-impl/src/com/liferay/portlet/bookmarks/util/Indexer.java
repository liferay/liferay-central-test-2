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

package com.liferay.portlet.bookmarks.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.util.search.DocumentImpl;

import javax.portlet.PortletURL;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.BOOKMARKS;

	public static void addEntry(
			long companyId, long groupId, long folderId, long entryId,
			String name, String url, String comments, String[] tagsEntries)
		throws SearchException {

		Document doc = getEntryDocument(
			companyId, groupId, folderId, entryId, name, url, comments,
			tagsEntries);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void deleteEntry(long companyId, long entryId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getEntryUID(entryId));
	}

	public static Document getEntryDocument(
		long companyId, long groupId, long folderId, long entryId, String name,
		String url, String comments, String[] tagsEntries) {

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, entryId);

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.GROUP_ID, groupId);

		doc.addText(Field.NAME, name);
		doc.addText(Field.URL, url);
		doc.addText(Field.COMMENTS, comments);

		doc.addModifiedDate();

		doc.addKeyword("folderId", folderId);
		doc.addKeyword("entryId", entryId);

		doc.addKeyword(Field.TAGS_ENTRIES, tagsEntries);

		return doc;
	}

	public static String getEntryUID(long entryId) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, entryId);

		return doc.get(Field.UID);
	}

	public static void updateEntry(
			long companyId, long groupId, long folderId, long entryId,
			String name, String url, String comments, String[] tagsEntries)
		throws SearchException {

		Document doc = getEntryDocument(
			companyId, groupId, folderId, entryId, name, url, comments,
			tagsEntries);

		SearchEngineUtil.updateDocument(companyId, doc.get(Field.UID), doc);
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Name

		String name = doc.get(Field.NAME);

		// URL

		String url = doc.get(Field.URL);

		// Portlet URL

		String entryId = doc.get("entryId");

		portletURL.setParameter("struts_action", "/bookmarks/edit_entry");
		portletURL.setParameter("entryId", entryId);

		return new DocumentSummary(name, url, portletURL);
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			BookmarksFolderLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

}