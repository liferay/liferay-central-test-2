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

package com.liferay.portal.util;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Vilmos Papp
 */
public class PortalImplActualURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testChildLayoutFriendlyURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		UserGroup userGroup = UserGroupLocalServiceUtil.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			"Test " + RandomTestUtil.nextInt(), StringPool.BLANK,
			serviceContext);

		_group = userGroup.getGroup();

		Layout homeLayout = LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			homeLayout.getLayoutId(), "Child Layout", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		String actualURL = PortalUtil.getActualURL(
			userGroup.getGroup().getGroupId(), true, Portal.PATH_MAIN,
			"/~/" + userGroup.getUserGroupId() + "/child-layout",
			new HashMap<String, String[]>(), getRequestContext());

		Assert.assertNotNull(actualURL);

		try {
			PortalUtil.getActualURL(
				userGroup.getGroup().getGroupId(), true, Portal.PATH_MAIN,
				"/~/" + userGroup.getUserGroupId() +
					"/non-existing-child-layout",
				new HashMap<String, String[]>(), getRequestContext());

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	@Test
	public void testJournalArticleFriendlyURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		_group = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			StringPool.BLANK, 0, GroupConstants.DEFAULT_LIVE_GROUP_ID,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, StringPool.BLANK,
			true, true, serviceContext);

		LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		Layout layout = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			"Test " + RandomTestUtil.nextInt(), StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String portletId = layoutTypePortlet.addPortletId(
			TestPropsValues.getUserId(), PortletKeys.ASSET_PUBLISHER,
			"column-1", 0);

		layoutTypePortlet.setTypeSettingsProperty(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
			portletId);

		layout = LayoutServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.US, "Test Journal Article");

		Map<Locale, String> contentMap = new HashMap<>();

		contentMap.put(LocaleUtil.US, "This test content is in English.");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, titleMap, titleMap,
			contentMap, layout.getUuid(), LocaleUtil.US, null, false, false,
			serviceContext);

		String actualURL = PortalUtil.getActualURL(
			_group.getGroupId(), false, Portal.PATH_MAIN,
			"/-/test-journal-article", new HashMap<String, String[]>(),
			getRequestContext());

		Assert.assertNotNull(actualURL);

		try {
			PortalUtil.getActualURL(
				_group.getGroupId(), false, Portal.PATH_MAIN,
				"/-/non-existing-test-journal-article",
				new HashMap<String, String[]>(), getRequestContext());

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	protected Map<String, Object> getRequestContext() {
		Map<String, Object> requestContext = new HashMap<>();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		requestContext.put("request", mockHttpServletRequest);

		return requestContext;
	}

	@DeleteAfterTestRun
	private Group _group;

}