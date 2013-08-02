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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousdeletereplymicroblogscontentprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDeleteReplyMicroblogsContentNotificationsTest
	extends BaseTestCase {
	public void testViewDeleteReplyMicroblogsContentNotifications()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		selenium.waitForElementPresent("//li[@id='_145_notificationsMenu']");
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");
		selenium.waitForVisible("//div[@class='title']");
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 commented on your post."),
			selenium.getText("//div[@class='title']"));
		assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
			selenium.getText("//div[@class='body']"));
		selenium.clickAt("//div[@class='title']",
			RuntimeVariables.replace(
				"Social01 Office01 User01 commented on your post."));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"xPath=(//div[@class='user-name']/span)[contains(.,'Joe Bloggs')]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("//span[@class='action comment']/a"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//div[@class='user-name']/span)[contains(.,'Social01 Office01 User01')]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//div[@class='content'])[2]"));
		assertFalse(selenium.isTextPresent("1 Comment"));
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		selenium.waitForElementPresent("//li[@id='_145_notificationsMenu']");
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");
		selenium.waitForVisible("//div[@class='title']");
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 commented on your post."),
			selenium.getText("//div[@class='title']"));
		assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
			selenium.getText("//div[@class='body']"));
		assertEquals(RuntimeVariables.replace("Mark All as Read"),
			selenium.getText("//span[@class='dismiss-notifications']/a"));
		selenium.clickAt("//span[@class='dismiss-notifications']/a",
			RuntimeVariables.replace("Mark All as Read"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		selenium.waitForElementPresent("//li[@id='_145_notificationsMenu']");
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");
		assertFalse(selenium.isTextPresent(
				"Social01 Office01 User01 commented on your post."));
	}
}