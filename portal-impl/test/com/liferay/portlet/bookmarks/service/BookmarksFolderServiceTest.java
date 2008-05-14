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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.messaging.DefaultMessageBus;
import com.liferay.portal.kernel.messaging.DefaultMessageSender;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.lucene.LuceneSearchEngineImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.util.search.IndexWriterMessageSender;

import java.util.List;

/**
 * <a href="BookmarksFolderServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BookmarksFolderServiceTest extends BaseBookmarksServiceTestCase {

	public void testAddFolder() throws Exception {
		addFolder();
	}

	public void testAddSubfolder() throws Exception {
		BookmarksFolder folder = addFolder();

		addFolder(folder.getFolderId());
	}

	public void testDeleteFolder() throws Exception {
		BookmarksFolder folder = addFolder();

		BookmarksFolderServiceUtil.deleteFolder(folder.getFolderId());
	}

	public void testGetFolder() throws Exception {
		BookmarksFolder folder = addFolder();

		BookmarksFolderServiceUtil.getFolder(folder.getFolderId());
	}

	public void testSearch() throws Exception {
		MessageBusUtil.init(
			new DefaultMessageBus(), new DefaultMessageSender());

		SearchEngineUtil.init(
			new LuceneSearchEngineImpl(), new IndexWriterMessageSender());

		BookmarksEntry entry = addEntry();

		Thread.sleep(1000);

		long groupId = PortalUtil.getPortletGroupId(
			TestPropsValues.LAYOUT_PLID);

		String keywords = "test";

		Hits hits = BookmarksFolderLocalServiceUtil.search(
			entry.getCompanyId(), groupId, new long[] {entry.getFolderId()},
			keywords, SearchEngineUtil.ALL_POS, SearchEngineUtil.ALL_POS);

		assertEquals(1, hits.getLength());

		List<Document> results =  hits.toList();

		for (Document document : results) {
			assertEquals(
				entry.getCompanyId(),
				Long.parseLong(document.get(Field.COMPANY_ID)));

			assertEquals(
				groupId, Long.parseLong(document.get(Field.GROUP_ID)));

			assertEquals(entry.getName(), document.get(Field.NAME));
			assertEquals(entry.getUrl(), document.get(Field.URL));
			assertEquals(entry.getComments(), document.get(Field.COMMENTS));

			assertEquals(
				entry.getFolderId(), Long.parseLong(document.get("folderId")));
			assertEquals(
				entry.getEntryId(), Long.parseLong(document.get("entryId")));
		}

		BookmarksFolderLocalServiceUtil.deleteFolder(entry.getFolderId());

		Thread.sleep(1000);

		hits = BookmarksFolderLocalServiceUtil.search(
			entry.getCompanyId(), groupId, new long[] {entry.getFolderId()},
			keywords, SearchEngineUtil.ALL_POS, SearchEngineUtil.ALL_POS);

		assertEquals(0, hits.getLength());

		addEntry();
		addEntry();
		addEntry();
		entry = addEntry();

		Thread.sleep(1000);

		hits = BookmarksFolderLocalServiceUtil.search(
			entry.getCompanyId(), groupId, null, keywords, 1, 3);

		assertEquals(4, hits.getLength());
		assertEquals(2, hits.getDocs().length);
	}

}