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

package com.liferay.portalweb.portal.controlpanel.users.user.edituserregrole;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditUser3RegRole3Test extends BaseTestCase {
	public void testEditUser3RegRole3() throws Exception {
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
		selenium.clickAt("link=Search All Users",
			RuntimeVariables.replace("Search All Users"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("usersn3"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn3"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-first-name_row-usersn3']/a"));
		assertEquals(RuntimeVariables.replace("userln3"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-last-name_row-usersn3']/a"));
		assertEquals(RuntimeVariables.replace("usersn3"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-screen-name_row-usersn3']/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//span[@title='Actions']/ul[@id='_125_usersSearchContainer_usersn3_menu']/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul[@id='_125_usersSearchContainer_usersn3_menu']/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']", "Roles"));
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));
		selenium.waitForVisible(
			"//span[contains(.,'Select')]/a[contains(@href,'openRegularRoleSelector')]");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//span[contains(.,'Select')]/a[contains(@href,'openRegularRoleSelector')]"));
		selenium.clickAt("//span[contains(.,'Select')]/a[contains(@href,'openRegularRoleSelector')]",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Users and Organizations");
		selenium.waitForVisible(
			"//tr[contains(.,'Roles Regrole3 Name')]/td[@headers='_125_rolesSearchContainer_col-title']/a");
		assertEquals(RuntimeVariables.replace("Roles Regrole3 Name"),
			selenium.getText(
				"//tr[contains(.,'Roles Regrole3 Name')]/td[@headers='_125_rolesSearchContainer_col-title']/a"));
		selenium.clickAt("//tr[contains(.,'Roles Regrole3 Name')]/td[@headers='_125_rolesSearchContainer_col-title']/a",
			RuntimeVariables.replace("Roles Regrole3 Name"));
		selenium.selectWindow("null");
		selenium.waitForPartialText("//div[@id='_125_rolesSearchContainer']",
			"Roles Regrole3 Name");
		assertTrue(selenium.isPartialText(
				"//div[@id='_125_rolesSearchContainer']", "Roles Regrole3 Name"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Roles Regrole3 Name"),
			selenium.getText(
				"//tr[contains(.,'Roles Regrole3 Name')]/td[@headers='_125_rolesSearchContainer_col-title']"));
	}
}