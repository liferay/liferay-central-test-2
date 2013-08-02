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

package com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationjoinprivrstrsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ConfirmNotificationJoinPrivRstrSiteTest extends BaseTestCase {
	public void testSOUs_ConfirmNotificationJoinPrivRstrSite()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible("//span[@class='notification-count']");
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//span[@class='notification-count']");
		selenium.waitForElementPresent(
			"//div[contains(@class, 'user-notification-events-container')]");
		assertTrue(selenium.isVisible(
				"//div[contains(@class, 'user-notification-events-container')]"));
		assertTrue(selenium.isPartialText(
				"//div[contains(@class, 'user-notification-event-content')]/div[2]/div",
				"Joe Bloggs invited you to join"));
		assertEquals(RuntimeVariables.replace("Private Restricted Site Name"),
			selenium.getText(
				"//div[contains(@class, 'user-notification-event-content')]/div[2]/div/a[2]"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//div[@class='notification-entry']/div[2]/span/a"));
		selenium.clickAt("//div[@class='notification-entry']/div[2]/span/a",
			RuntimeVariables.replace("Confirm"));
		selenium.waitForNotText("//span[@class='notification-count']", "1");
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_5_WAR_soportlet_tabs1']", "My Sites"));
		selenium.select("//select[@id='_5_WAR_soportlet_tabs1']",
			RuntimeVariables.replace("My Sites"));
		selenium.waitForNotPartialText("//ul[contains(@class, 'site-list')]/",
			"liferay.com");
		assertFalse(selenium.isPartialText(
				"//ul[contains(@class, 'site-list')]/", "liferay.com"));
		assertEquals(RuntimeVariables.replace("Private Restricted Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
	}
}