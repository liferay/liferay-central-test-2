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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.test.DefaultWhenIsIndexableBaseModel;
import com.liferay.portlet.trash.test.WhenHasMyBaseModel;
import com.liferay.portlet.trash.test.WhenHasRecentBaseModelCount;
import com.liferay.portlet.trash.test.WhenIsAssetableBaseModel;
import com.liferay.portlet.trash.test.WhenIsIndexableBaseModel;
import com.liferay.portlet.trash.test.WhenIsMoveableFromTrashBaseModel;
import com.liferay.portlet.trash.test.WhenIsUpdatableBaseModel;

import java.io.InputStream;

import java.util.Calendar;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 * @author Eduardo Garcia
 */
@Sync
public class MBThreadTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenHasMyBaseModel, WhenHasRecentBaseModelCount,
			   WhenIsAssetableBaseModel, WhenIsIndexableBaseModel,
			   WhenIsMoveableFromTrashBaseModel, WhenIsUpdatableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public int getMineBaseModelsCount(long groupId, long userId)
		throws Exception {

		return MBThreadServiceUtil.getGroupThreadsCount(
			groupId, userId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getRecentBaseModelsCount(long groupId) throws Exception {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.HOUR, -1);

		return MBThreadServiceUtil.getGroupThreadsCount(
			groupId, 0, calendar.getTime(), WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getSearchKeywords() {
		return _SUBJECT;
	}

	@Override
	public BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		MBThreadServiceUtil.moveThreadFromTrash(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			(Long)classedModel.getPrimaryKeyObj());

		return parentBaseModel;
	}

	@Override
	public int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		return _whenIsIndexableBaseModel.searchBaseModelsCount(
			MBMessage.class, groupId);
	}

	@Override
	public int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		return _whenIsIndexableBaseModel.searchTrashEntriesCount(
			keywords, serviceContext);
	}

	@Before
	public void setUp() throws Exception {
		_whenIsIndexableBaseModel = new DefaultWhenIsIndexableBaseModel();

		super.setUp();
	}

	@Test
	public void testCategoryMessageCount() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getMessageCount(
			(Long)parentBaseModel.getPrimaryKeyObj());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getMessageCount((Long)parentBaseModel.getPrimaryKeyObj()));

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getMessageCount((Long)parentBaseModel.getPrimaryKeyObj()));

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getMessageCount((Long)parentBaseModel.getPrimaryKeyObj()));

		replyMessage(baseModel);

		Assert.assertEquals(
			initialBaseModelsCount + 2,
			getMessageCount((Long)parentBaseModel.getPrimaryKeyObj()));
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
	public void testTrashVersionBaseModelAndDeleteIsNotFound()
		throws Exception {
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
	public void testTrashVersionParentBaseModelAndRestoreIsNotInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsVisible()
		throws Exception {
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
	public BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		MBThread thread = MBThreadLocalServiceUtil.getThread(primaryKey);

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			thread = MBThreadLocalServiceUtil.updateStatus(
				TestPropsValues.getUserId(), primaryKey,
				WorkflowConstants.STATUS_DRAFT);
		}

		return thread;
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		MBCategory category = (MBCategory)parentBaseModel;

		MBMessage message = MBTestUtil.addMessageWithWorkflow(
			serviceContext.getUserId(), category.getGroupId(),
			category.getCategoryId(), getSearchKeywords(), getSearchKeywords(),
			approved, serviceContext);

		return message.getThread();
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			serviceContext.getScopeGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		return message.getThread();
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		MBCategory parentCategory = (MBCategory)parentBaseModel;

		MBCategoryLocalServiceUtil.deleteCategory(parentCategory, false);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return MBThreadLocalServiceUtil.getThread(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return MBThread.class;
	}

	protected int getMessageCount(long categoryId) throws Exception {
		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		return category.getMessageCount();
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		MBCategory category = (MBCategory)parentBaseModel;

		return MBThreadLocalServiceUtil.getCategoryThreadsCount(
			category.getGroupId(), category.getCategoryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		return MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentBaseModelId,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@Override
	protected Class<?> getParentBaseModelClass() {
		return MBCategory.class;
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected boolean isAssetEntryVisible(ClassedModel classedModel)
		throws Exception {

		MBMessage rootMessage = MBMessageLocalServiceUtil.getMBMessage(
			((MBThread)classedModel).getRootMessageId());

		return super.isAssetEntryVisible(rootMessage);
	}

	@Override
	protected boolean isBaseModelContainerModel() {
		return false;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		MBThreadServiceUtil.moveThreadToTrash(primaryKey);
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		MBCategoryServiceUtil.moveCategoryToTrash(primaryKey);
	}

	protected void replyMessage(BaseModel<?> baseModel) throws Exception {
		MBThread thread = (MBThread)baseModel;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				thread.getGroupId(), TestPropsValues.getUserId());

		MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			thread.getGroupId(), thread.getCategoryId(), thread.getThreadId(),
			thread.getRootMessageId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), MBMessageConstants.DEFAULT_FORMAT,
			Collections.<ObjectValuePair<String, InputStream>>emptyList(),
			false, 0.0, false, serviceContext);
	}

	private static final String _SUBJECT = "Subject";

	private WhenIsIndexableBaseModel _whenIsIndexableBaseModel;

}