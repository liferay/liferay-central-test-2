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

package com.liferay.bookmarks.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.test.WhenIsIndexableBaseModel;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class BookmarksFolderTrashHandlerTest
	extends BaseTrashHandlerTestCase implements WhenIsIndexableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		_testMode = PortalRunMode.isTestMode();

		PortalRunMode.setTestMode(true);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		PortalRunMode.setTestMode(_testMode);
	}

	@Ignore
	@Override
	@Test
	public void testAddBaseModelWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testAddBaseModelWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testAddBaseModelWithDraftStatusIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatusIsNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusIsNotVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreStatus()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreUniqueTitle()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashDuplicate() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashMyBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashRecentBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndDelete() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndDeleteIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestore() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestoreIndexable()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestoreIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelIsNotVisible() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		BookmarksFolder parentFolder = (BookmarksFolder)parentBaseModel;

		String name = getSearchKeywords();

		return BookmarksFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), parentFolder.getFolderId(), name,
			StringPool.BLANK, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		String name = getSearchKeywords();

		return BookmarksFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID, name,
			StringPool.BLANK, serviceContext);
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		BookmarksFolder folder = (BookmarksFolder)parentBaseModel;

		BookmarksFolderServiceUtil.deleteFolder(folder.getFolderId(), false);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return BookmarksFolderLocalServiceUtil.getFolder(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BookmarksFolder.class;
	}

	@Override
	protected String getBaseModelName(ClassedModel classedModel) {
		BookmarksFolder folder = (BookmarksFolder)classedModel;

		return folder.getName();
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		BookmarksFolder parentFolder = (BookmarksFolder)parentBaseModel;

		return BookmarksFolderLocalServiceUtil.getFoldersCount(
			parentFolder.getGroupId(), parentFolder.getFolderId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		return BookmarksFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), parentBaseModelId,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(
			group, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		BookmarksFolderServiceUtil.moveFolderFromTrash(
			(Long)classedModel.getPrimaryKeyObj(),
			(Long)parentBaseModel.getPrimaryKeyObj());

		return parentBaseModel;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		BookmarksFolderServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		BookmarksFolderServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
			primaryKey);

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			folder = BookmarksFolderLocalServiceUtil.updateStatus(
				TestPropsValues.getUserId(), folder,
				WorkflowConstants.STATUS_DRAFT);
		}

		return folder;
	}

	private boolean _testMode;

}