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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

import java.util.List;

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
		long userId = 0;
		long folderId = entry.getFolderId();
		String keywords = "test";

		Hits hits = BookmarksFolderLocalServiceUtil.search(
			companyId, groupId, userId, new long[] {folderId}, keywords,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

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

		hits = BookmarksFolderLocalServiceUtil.search(
			companyId, groupId, userId, new long[] {folderId}, keywords,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		assertEquals(0, hits.getLength());

		addEntry();
		addEntry();
		addEntry();
		addEntry();

		Thread.sleep(1000);

		hits = BookmarksFolderLocalServiceUtil.search(
			companyId, groupId, userId, null, keywords, 1, 3);

		assertEquals(4, hits.getLength());
		assertEquals(2, hits.getDocs().length);
	}

}