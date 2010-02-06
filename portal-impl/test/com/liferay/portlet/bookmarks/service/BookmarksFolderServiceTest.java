/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

import java.util.List;

/**
 * <a href="BookmarksFolderServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
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
		BookmarksEntry entry = addEntry();

		Thread.sleep(1000);

		long companyId = entry.getCompanyId();
		long groupId = entry.getFolder().getGroupId();
		long folderId = entry.getFolderId();
		String keywords = "test";

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setFolderIds(new long[] {folderId});
		searchContext.setGroupId(groupId);
		searchContext.setKeywords(keywords);

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		assertEquals(1, hits.getLength());

		List<Document> results =  hits.toList();

		for (Document doc : results) {
			assertEquals(
				companyId,
				GetterUtil.getLong(doc.get(Field.COMPANY_ID)));

			assertEquals(groupId, GetterUtil.getLong(doc.get(Field.GROUP_ID)));

			assertEquals(entry.getName(), doc.get(Field.TITLE));
			assertEquals(entry.getUrl(), doc.get(Field.URL));
			assertEquals(entry.getComments(), doc.get(Field.COMMENTS));

			assertEquals(folderId, GetterUtil.getLong(doc.get("folderId")));
			assertEquals(
				entry.getEntryId(),
				GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK)));
		}

		BookmarksFolderLocalServiceUtil.deleteFolder(folderId);

		Thread.sleep(1000);

		hits = indexer.search(searchContext);

		assertEquals(0, hits.getLength());

		addEntry();
		addEntry();
		addEntry();
		addEntry();

		Thread.sleep(1000);

		searchContext.setEnd(3);
		searchContext.setFolderIds(null);
		searchContext.setStart(1);

		hits = indexer.search(searchContext);

		assertEquals(4, hits.getLength());
		assertEquals(2, hits.getDocs().length);
	}

}