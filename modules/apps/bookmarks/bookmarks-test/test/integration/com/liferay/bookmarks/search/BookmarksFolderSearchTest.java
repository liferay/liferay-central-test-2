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

package com.liferay.bookmarks.search;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.bookmarks.util.BookmarksTestUtil;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationTestRule;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
@Sync
public class BookmarksFolderSearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Ignore()
	@Override
	@Test
	public void testLocalizedSearch() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchAttachments() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchByDDMStructureField() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchComments() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchExpireAllVersions() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchExpireLatestVersion() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchMyEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchRecentEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchStatus() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchVersions() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchWithinDDMStructure() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		BookmarksFolder parentFolder = (BookmarksFolder)parentBaseModel;

		long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (parentFolder != null) {
			folderId = parentFolder.getFolderId();
		}

		return BookmarksTestUtil.addFolder(folderId, keywords, serviceContext);
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		BookmarksFolderServiceUtil.deleteFolder(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BookmarksFolder.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		return BookmarksTestUtil.addFolder(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return BookmarksTestUtil.addFolder(
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		BookmarksFolder folder = (BookmarksFolder)baseModel;

		folder.setName(keywords);

		return BookmarksFolderLocalServiceUtil.updateBookmarksFolder(folder);
	}

}