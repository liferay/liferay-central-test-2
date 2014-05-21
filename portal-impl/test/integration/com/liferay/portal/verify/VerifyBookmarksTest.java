/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.test.portal.service.ServiceTestUtil;
import com.liferay.test.portal.util.GroupTestUtil;
import com.liferay.test.portal.util.RandomTestUtil;
import com.liferay.test.portal.util.TestPropsValues;
import com.liferay.test.portlet.bookmarks.util.BookmarksTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 * @author Eudaldo Alonso
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class VerifyBookmarksTest extends BaseVerifyTestCase {

	@Test
	public void testBookmarksEntryTreePathWithBookmarksEntryInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			parentFolder.getFolderId(), true, serviceContext);

		BookmarksEntryLocalServiceUtil.moveEntryToTrash(
			TestPropsValues.getUserId(), entry.getEntryId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testBookmarksEntryTreePathWithBookmarksParentFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		BookmarksFolder grandparentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), grandparentFolder.getFolderId(),
			RandomTestUtil.randomString());

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BookmarksTestUtil.addEntry(
			parentFolder.getFolderId(), true, serviceContext);

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testBookmarksFolderTreePathWithBookmarksFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	@Transactional
	public void testBookmarksFolderTreePathWithBookmarksParentFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		BookmarksFolder grandparentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), grandparentFolder.getFolderId(),
			RandomTestUtil.randomString());

		BookmarksTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyBookmarks();
	}

}