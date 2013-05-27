/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.webdav.methods.Method;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Vilmos Papp
 * @author Akos Thurzo
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortalImplURLTest {

	@Test
	public void testChildLayoutFriendlyURL() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		UserGroup userGroup = UserGroupLocalServiceUtil.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			"Test " + ServiceTestUtil.nextInt(), StringPool.BLANK,
			serviceContext);

		Group group = userGroup.getGroup();

		Layout homeLayout = LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), group.getGroupId(), true,
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

		UserGroupLocalServiceUtil.deleteUserGroup(userGroup);
	}

	@Test
	public void testGetLayoutURL() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		String virtualHostname = "test.com";

		company.setVirtualHostname(virtualHostname);

		Group group = GroupTestUtil.addGroup();

		long siteGroupId = PortalUtil.getSiteGroupId(group.getGroupId());

		User user = TestPropsValues.getUser();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);
		themeDisplay.setI18nLanguageId(StringPool.BLANK);
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setSecure(false);
		themeDisplay.setServerName(virtualHostname);
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(siteGroupId);
		themeDisplay.setUser(user);
		themeDisplay.setWidget(false);

		String friendlyURLUsingVirtualHost = PortalUtil.getLayoutURL(
			layout, themeDisplay, false);

		themeDisplay.setServerName("localhost");

		String friendlyURLUsingLocalhost = PortalUtil.getLayoutURL(
			layout, themeDisplay, false);

		Assert.assertEquals(
			friendlyURLUsingLocalhost, friendlyURLUsingVirtualHost);

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			company.getCompanyId());

		Layout controlPanelLayout = LayoutLocalServiceUtil.getLayout(
			controlPanelPlid);

		themeDisplay.setLayout(controlPanelLayout);
		themeDisplay.setLayoutSet(controlPanelLayout.getLayoutSet());

		String friendlyURLFromControlPanelUsingLocalhost =
			PortalUtil.getLayoutURL(layout, themeDisplay, false);

		themeDisplay.setServerName(virtualHostname);

		String friendlyURLFromControlPanelUsingVirtualHost =
			PortalUtil.getLayoutURL(layout, themeDisplay, false);

		Assert.assertEquals(
			friendlyURLUsingVirtualHost,
			friendlyURLFromControlPanelUsingVirtualHost);

		Assert.assertEquals(
			friendlyURLFromControlPanelUsingLocalhost,
			friendlyURLFromControlPanelUsingVirtualHost);
	}

	@Test
	public void testJournalArticleFriendlyURL() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		Group group = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			StringPool.BLANK, 0, GroupConstants.DEFAULT_LIVE_GROUP_ID,
			"Test " + ServiceTestUtil.nextInt(), StringPool.BLANK,
			GroupConstants.TYPE_SITE_OPEN, StringPool.BLANK, true, true,
			serviceContext);

		LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		Layout layout = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			"Test " + ServiceTestUtil.nextInt(), StringPool.BLANK,
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

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(Locale.US, "Test Journal Article");

		StringBundler sb = new StringBundler(6);

		sb.append("<?xml version=\"1.0\"?><root available-locales=");
		sb.append("\"en_US\" default-locale=\"en_US\">");
		sb.append("<static-content language-id=\"en_US\"><![CDATA[<p>");
		sb.append("This test content is in English.");
		sb.append("</p>]]>");
		sb.append("</static-content></root>");

		JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), group.getGroupId(), 0, 0, 0,
			StringPool.BLANK, true, 1, titleMap, new HashMap<Locale, String>(),
			sb.toString(), "general", null, null, layout.getUuid(), 1, 1, 1965,
			0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, false, false, null,
			null, null, StringPool.BLANK, serviceContext);

		String actualURL = PortalUtil.getActualURL(
			group.getGroupId(), false, Portal.PATH_MAIN,
			"/-/test-journal-article", new HashMap<String, String[]>(),
			getRequestContext());

		Assert.assertNotNull(actualURL);

		try {
			PortalUtil.getActualURL(
				group.getGroupId(), false, Portal.PATH_MAIN,
				"/-/non-existing-test-journal-article",
				new HashMap<String, String[]>(), getRequestContext());

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}

		GroupLocalServiceUtil.deleteGroup(group);
	}

	protected Map<String, Object> getRequestContext() {
		Map<String, Object> requestContext = new HashMap<String, Object>();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		requestContext.put("request", mockHttpServletRequest);

		return requestContext;
	}

}