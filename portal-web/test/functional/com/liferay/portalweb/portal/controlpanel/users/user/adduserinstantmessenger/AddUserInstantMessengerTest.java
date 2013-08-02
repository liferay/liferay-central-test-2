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

package com.liferay.portalweb.portal.controlpanel.users.user.adduserinstantmessenger;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserInstantMessengerTest extends BaseTestCase {
	public void testAddUserInstantMessenger() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("usersn"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("userfn"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_125_instantMessengerLink']");
		assertTrue(selenium.isPartialText(
				"//a[@id='_125_instantMessengerLink']", "Instant Messenger"));
		selenium.clickAt("//a[@id='_125_instantMessengerLink']",
			RuntimeVariables.replace("Instant Messenger"));
		selenium.waitForVisible("//input[@id='_125_aimSn']");
		selenium.type("//input[@id='_125_aimSn']",
			RuntimeVariables.replace("selenium01"));
		selenium.type("//input[@id='_125_jabberSn']",
			RuntimeVariables.replace("selenium01"));
		selenium.type("//input[@id='_125_msnSn']",
			RuntimeVariables.replace("selenium01"));
		selenium.type("//input[@id='_125_skypeSn']",
			RuntimeVariables.replace("selenium01"));
		selenium.type("//input[@id='_125_ymSn']",
			RuntimeVariables.replace("selenium01"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("selenium01",
			selenium.getValue("//input[@id='_125_aimSn']"));
		assertEquals("selenium01",
			selenium.getValue("//input[@id='_125_jabberSn']"));
		assertEquals("selenium01",
			selenium.getValue("//input[@id='_125_msnSn']"));
		assertEquals("selenium01",
			selenium.getValue("//input[@id='_125_skypeSn']"));
		assertEquals("selenium01", selenium.getValue("//input[@id='_125_ymSn']"));
	}
}