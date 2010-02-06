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

import com.liferay.portal.model.Layout;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;

/**
 * <a href="BaseBookmarksServiceTestCase.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class BaseBookmarksServiceTestCase extends BaseServiceTestCase {

	protected BookmarksEntry addEntry() throws Exception {
		BookmarksFolder folder = addFolder();

		String name = "Test Entry";
		String url = "http://www.liferay.com";
		String comments = "This is a test entry.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return BookmarksEntryServiceUtil.addEntry(
			folder.getGroupId(), folder.getFolderId(), name, url, comments,
			serviceContext);
	}

	protected BookmarksFolder addFolder() throws Exception {
		long parentFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		return addFolder(parentFolderId);
	}

	protected BookmarksFolder addFolder(long parentFolderId) throws Exception {
		String name = "Test Folder";
		String description = "This is a test folder.";

		Layout layout = LayoutLocalServiceUtil.getLayout(
			TestPropsValues.LAYOUT_PLID);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(layout.getGroupId());

		return BookmarksFolderServiceUtil.addFolder(
			parentFolderId, name, description, serviceContext);
	}

}