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

package com.liferay.portlet.blogs.service;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.io.InputStream;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 * @author Manuel de la Peña
 */
@Sync
public class BlogsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_statusAnyQueryDefinition = new QueryDefinition<BlogsEntry>(
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
		_statusApprovedQueryDefinition = new QueryDefinition<BlogsEntry>(
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
		_statusInTrashQueryDefinition = new QueryDefinition<BlogsEntry>(
			WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
		_user = TestPropsValues.getUser();
	}

	@Test
	public void testAddEntryWithoutSmallImage() throws Exception {
		BlogsEntry expectedEntry = testAddEntry(false);

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			expectedEntry.getEntryId());

		BlogsTestUtil.assertEquals(expectedEntry, actualEntry);
	}

	@Test
	public void testAddEntryWithSmallImage() throws Exception {
		BlogsEntry expectedEntry = testAddEntry(true);

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			expectedEntry.getEntryId());

		BlogsTestUtil.assertEquals(expectedEntry, actualEntry);
	}

	@Test
	public void testGetCompanyEntriesCountInTrash() throws Exception {
		testGetCompanyEntriesCount(true);
	}

	@Test
	public void testGetCompanyEntriesCountNotInTrash() throws Exception {
		testGetCompanyEntriesCount(false);
	}

	@Test
	public void testGetCompanyEntriesInTrash() throws Exception {
		testGetCompanyEntries(true);
	}

	@Test
	public void testGetCompanyEntriesNotInTrash() throws Exception {
		testGetCompanyEntries(false);
	}

	@Test
	public void testGetDiscussionMessageDisplay() throws Exception {
		BlogsEntry entry = addEntry(false);

		try {
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				TestPropsValues.getUserId(), _group.getGroupId(),
				BlogsEntry.class.getName(), entry.getEntryId(),
				WorkflowConstants.STATUS_ANY);
		}
		catch (Exception e) {
			Assert.fail(
				"The initial discussion does not exist for entry " +
					entry.getEntryId());
		}
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToCurrentEntry()
		throws Exception {

		BlogsEntry previousEntry = addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		BlogsEntry nextEntry = addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			currentEntry.getEntryId());

		Assert.assertNotNull(
			"The previous entry relative to entry " +
				currentEntry.getEntryId() + " should be " +
					previousEntry.getEntryId() + " but is null",
			entries[0]);
		Assert.assertNotNull(
			"The current entry relative to entry " + currentEntry.getEntryId() +
				" should be " + currentEntry.getEntryId() + " but is null",
			entries[1]);
		Assert.assertNotNull(
			"The next entry relative to entry " + currentEntry.getEntryId() +
				" should be " + nextEntry.getEntryId() + " but is null",
			entries[2]);
		Assert.assertEquals(
			"The previous entry relative to entry" +
				currentEntry.getEntryId() + " should be " +
					previousEntry.getEntryId(),
			entries[0].getEntryId(), previousEntry.getEntryId());
		Assert.assertEquals(
			"The current entry relative to entry " + currentEntry.getEntryId() +
				" should be " + currentEntry.getEntryId(),
			entries[1].getEntryId(), currentEntry.getEntryId());
		Assert.assertEquals(
			"The next entry relative to entry " + currentEntry.getEntryId() +
				" should be " + nextEntry.getEntryId(),
			entries[2].getEntryId(), nextEntry.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToNextEntry()
		throws Exception {

		addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		BlogsEntry nextEntry = addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			nextEntry.getEntryId());

		Assert.assertNull(
			"The next entry relative to entry " + nextEntry.getEntryId() +
				" should be null",
			entries[2]);
		Assert.assertNotNull(
			"The current entry relative to entry " + nextEntry.getEntryId() +
				" should be " + nextEntry.getEntryId() + " but is null",
			entries[1]);
		Assert.assertNotNull(
			"The previous entry relative to entry " + nextEntry.getEntryId() +
				" should be " + currentEntry.getEntryId() + " but is null",
			entries[0]);
		Assert.assertEquals(
			"The previous entry relative to entry " + nextEntry.getEntryId() +
				" should be " + currentEntry.getEntryId(),
			entries[0].getEntryId(), currentEntry.getEntryId());
		Assert.assertEquals(
			"The current entry relative to entry" + nextEntry.getEntryId() +
				" should be " + nextEntry.getEntryId(),
			entries[1].getEntryId(), nextEntry.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToPreviousEntry()
		throws Exception {

		BlogsEntry previousEntry = addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			previousEntry.getEntryId());

		Assert.assertNull(
			"The previous entry relative to entry " +
				previousEntry.getEntryId() + " should be null",
			entries[0]);
		Assert.assertNotNull(
			"The current entry relative to entry " +
				previousEntry.getEntryId() + " should be " +
					previousEntry.getEntryId() + " but is null",
			entries[1]);
		Assert.assertNotNull(
			"The next entry relative to entry " + previousEntry.getEntryId() +
				" should be " + currentEntry.getEntryId() + " but is null",
			entries[2]);
		Assert.assertEquals(
			"The current entry relative to entry " +
				previousEntry.getEntryId() + " should be " +
					previousEntry.getEntryId(),
			entries[1].getEntryId(), previousEntry.getEntryId());
		Assert.assertEquals(
			"The next entry relative to entry " + previousEntry.getEntryId() +
				" should be " + currentEntry.getEntryId(),
			entries[2].getEntryId(), currentEntry.getEntryId());
	}

	@Test
	public void testGetEntryByGroupAndUrlTitle() throws Exception {
		BlogsEntry expectedEntry = addEntry(false);

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getEntry(
			expectedEntry.getGroupId(), expectedEntry.getUrlTitle());

		BlogsTestUtil.assertEquals(expectedEntry, actualEntry);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, true);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, false);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, true);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, false);
	}

	@Test
	public void testGetGroupEntriesInTrashWithDisplayDate() throws Exception {
		testGetGroupEntries(true, true);
	}

	@Test
	public void testGetGroupEntriesInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntries(true, false);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntries(false, true);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntries(false, false);
	}

	@Test
	public void testGetGroupsEntries() throws Exception {
		List<BlogsEntry> groupsEntries =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_statusInTrashQueryDefinition);

		int initialCount = groupsEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> groupsEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_statusInTrashQueryDefinition);

		Assert.assertEquals(initialCount + 1, groupsEntriesInTrash.size());

		for (BlogsEntry groupsEntry : groupsEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != groupsEntry.getStatus()) {
				Assert.fail(
					"Entry " + groupsEntry.getEntryId() + " is not in trash");
			}

			if (groupsEntry.getCompanyId() != _user.getCompanyId()) {
				Assert.fail(
					"Entry belongs to company " + groupsEntry.getCompanyId() +
						" but should belong to company " +
							_user.getCompanyId());
			}
		}
	}

	@Test
	public void testGetGroupUserEntriesCountInTrash() throws Exception {
		testGetGroupUserEntriesCount(true);
	}

	@Test
	public void testGetGroupUserEntriesCountNotInTrash() throws Exception {
		testGetGroupUserEntriesCount(false);
	}

	@Test
	public void testGetGroupUserEntriesInTrash() throws Exception {
		testGetGroupUserEntries(true);
	}

	@Test
	public void testGetGroupUserEntriesNotInTrash() throws Exception {
		testGetGroupUserEntries(false);
	}

	@Test
	public void testGetNoAssetEntries() throws Exception {
		BlogsEntry entry = addEntry(false);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<BlogsEntry> entries =
			BlogsEntryLocalServiceUtil.getNoAssetEntries();

		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(entry, entries.get(0));
	}

	@Test
	public void testGetOrganizationEntriesCountInTrash() throws Exception {
		testGetOrganizationEntriesCount(true);
	}

	@Test
	public void testGetOrganizationEntriesCountNotInTrash() throws Exception {
		testGetOrganizationEntriesCount(false);
	}

	@Test
	public void testGetOrganizationEntriesInTrash() throws Exception {
		testGetOrganizationEntries(true);
	}

	@Test
	public void testGetOrganizationEntriesNotInTrash() throws Exception {
		testGetOrganizationEntries(false);
	}

	@Test
	public void testSubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			TestPropsValues.getUserId(), _group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testUnsubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			TestPropsValues.getUserId(), _group.getGroupId());

		BlogsEntryLocalServiceUtil.unsubscribe(
			TestPropsValues.getUserId(), _group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		Assert.assertEquals(initialCount, actualCount);
	}

	@Test
	public void testUpdateEntryResources() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.updateEntryResources(
			entry, new String[] {ActionKeys.ADD_DISCUSSION}, null);
	}

	protected BlogsEntry addEntry(boolean statusInTrash) throws Exception {
		return addEntry(TestPropsValues.getUserId(), statusInTrash);
	}

	protected BlogsEntry addEntry(long userId, boolean statusInTrash)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), userId);

		Calendar displayDateCalendar = CalendarFactoryUtil.getCalendar(
			2012, 1, 1);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), displayDateCalendar.getTime(),
			serviceContext);

		if (statusInTrash) {
			entry = BlogsEntryLocalServiceUtil.moveEntryToTrash(userId, entry);
		}

		return entry;
	}

	protected BlogsEntry addEntryWithSmallImage(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), userId);

		ClassLoader classLoader = getClass().getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/portal/util/dependencies/test.jpg");

		FileEntry fileEntry = null;

		try {
			fileEntry = TempFileEntryUtil.getTempFileEntry(
				serviceContext.getScopeGroupId(), userId,
				BlogsEntry.class.getName(), "image.jpg");
		}
		catch (Exception e) {
			fileEntry = TempFileEntryUtil.addTempFileEntry(
				serviceContext.getScopeGroupId(), userId,
				BlogsEntry.class.getName(), "image.jpg", inputStream,
				MimeTypesUtil.getContentType("image.jpg"));
		}

		ImageSelector coverImageSelector = null;
		ImageSelector smallImageSelector = new ImageSelector(
			fileEntry.getFileEntryId(), StringPool.BLANK, null);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(2012, 1, 1);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), displayCalendar.getTime(), true,
			true, new String[0], StringPool.BLANK, coverImageSelector,
			smallImageSelector, serviceContext);

		return entry;
	}

	protected void assertBlogsEntriesStatus(
		List<BlogsEntry> entries, boolean statusInTrash) {

		for (BlogsEntry entry : entries) {
			if (statusInTrash &&
				(WorkflowConstants.STATUS_IN_TRASH != entry.getStatus())) {

				Assert.fail(
					"The entry " + entry.getEntryId() + " should be in trash");
			}
			else if (!statusInTrash &&
					 (WorkflowConstants.STATUS_IN_TRASH == entry.getStatus())) {

				Assert.fail(
					"Entry " + entry.getEntryId() + " should not be in trash");
			}
		}
	}

	protected BlogsEntry testAddEntry(boolean smallImage) throws Exception {
		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusApprovedQueryDefinition);

		BlogsEntry entry = null;

		if (smallImage) {
			entry = addEntryWithSmallImage(TestPropsValues.getUserId());
		}
		else {
			entry = addEntry(false);
		}

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusApprovedQueryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);

		if (smallImage) {
			Assert.assertTrue(entry.isSmallImage());
		}
		else {
			Assert.assertFalse(entry.isSmallImage());
		}

		return entry;
	}

	protected void testGetCompanyEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetCompanyEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			_user.getCompanyId(), new Date(), queryDefinition);

		addEntry(false);
		addEntry(true);

		int actualCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetGroupEntries(
			boolean statusInTrash, boolean displayDate)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries = null;

		if (displayDate) {
			initialEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			initialEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries = null;

		if (displayDate) {
			actualEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			actualEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetGroupEntriesCount(
			boolean statusInTrash, boolean displayDate)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = 0;

		if (displayDate) {
			initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), queryDefinition);
		}

		addEntry(false);
		addEntry(true);

		int actualCount = 0;

		if (displayDate) {
			actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetGroupUserEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetGroupUserEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
			_group.getGroupId(), _user.getUserId(), new Date(),
			queryDefinition);

		addEntry(false);
		addEntry(true);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
			_group.getGroupId(), _user.getUserId(), new Date(),
			queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetOrganizationEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(), queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(user.getUserId(), false);
		addEntry(user.getUserId(), true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetOrganizationEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		int initialCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(), queryDefinition);

		addEntry(user.getUserId(), false);
		addEntry(user.getUserId(), true);

		int actualCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@DeleteAfterTestRun
	private Group _group;

	private QueryDefinition<BlogsEntry> _statusAnyQueryDefinition;
	private QueryDefinition<BlogsEntry> _statusApprovedQueryDefinition;
	private QueryDefinition<BlogsEntry> _statusInTrashQueryDefinition;
	private User _user;

}