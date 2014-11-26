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

package com.liferay.portlet.blogs.subscriptions;

import com.dumbster.smtp.MailMessage;

import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.MailServiceTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.BlogsConstants;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
@Sync
public class BlogsSubscriptionEmailEntryUpdatedCommentTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER);
	}

	@Test
	public void testEmailEntryUpdatedNotSentIfNotSpecified() throws Exception {
		BlogsEntry entry = BlogsTestUtil.addEntry(_group, true);

		BlogsEntryLocalServiceUtil.subscribe(
			_user.getUserId(), _group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(entry.getGroupId());

		serviceContext.setAttribute(
			"emailEntryUpdatedComment", "This entry was updated.");

		BlogsTestUtil.updateEntry(
			entry, StringUtil.randomString(), true, serviceContext);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testEmailEntryUpdatedSentWithEmailEntryUpdatedComment()
		throws Exception {

		setUpBlogsSettings();

		BlogsEntry entry = BlogsTestUtil.addEntry(_group, true);

		BlogsEntryLocalServiceUtil.subscribe(
			_user.getUserId(), _group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(entry.getGroupId());

		serviceContext.setAttribute(
			"emailEntryUpdatedComment", "This entry was updated.");
		serviceContext.setAttribute(
			"sendEmailEntryUpdated", Boolean.TRUE.toString());

		BlogsTestUtil.updateEntry(
			entry, StringUtil.randomString(), true, serviceContext);

		MailMessage message = MailServiceTestUtil.getLastMailMessage();

		Assert.assertEquals(message.getBody(), "This entry was updated.");
	}

	@Test
	public void testEmailEntryUpdatedSentWithEmptyEmailEntryUpdatedComment()
		throws Exception {

		setUpBlogsSettings();

		BlogsEntry entry = BlogsTestUtil.addEntry(_group, true);

		BlogsEntryLocalServiceUtil.subscribe(
			_user.getUserId(), _group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(entry.getGroupId());

		serviceContext.setAttribute(
			"sendEmailEntryUpdated", Boolean.TRUE.toString());

		BlogsTestUtil.updateEntry(
			entry, StringUtil.randomString(), true, serviceContext);

		MailMessage message = MailServiceTestUtil.getLastMailMessage();

		Assert.assertEquals(message.getBody(), StringPool.NEW_LINE);
	}

	protected void setUpBlogsSettings() throws Exception {
		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			_group.getGroupId(), BlogsConstants.SERVICE_NAME);

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String subscriptionBodyPreferencesKey =
			LocalizationUtil.getLocalizedName(
				"emailEntryUpdatedBody",
				LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

		modifiableSettings.setValue(
			subscriptionBodyPreferencesKey, "[$BLOGS_ENTRY_UPDATE_COMMENT$]");

		modifiableSettings.store();
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}