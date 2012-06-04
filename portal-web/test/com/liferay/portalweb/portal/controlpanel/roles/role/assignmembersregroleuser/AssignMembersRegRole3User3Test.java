/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersregroleuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersRegRole3User3Test extends BaseTestCase {
	public void testAssignMembersRegRole3User3() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Regrole3"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Roles Regrole3 Name"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-name_row-1']"));
		assertEquals(RuntimeVariables.replace("Regular"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-type_row-1']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//span[@title='Actions']/ul[@id='_128_ocerSearchContainer_1_menu']/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul[@id='_128_ocerSearchContainer_1_menu']/li/strong/a/span",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Assign Members"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]",
			RuntimeVariables.replace("Assign Members"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Roles Regrole3 Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.clickAt("link=Available", RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_128_keywords']",
			RuntimeVariables.replace("usersn3"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("userfn3 userln3"),
			selenium.getText(
				"//td[@id='_128_usersSearchContainer_col-name_row-usersn3']"));
		assertEquals(RuntimeVariables.replace("usersn3"),
			selenium.getText(
				"//td[@id='_128_usersSearchContainer_col-screen-name_row-usersn3']"));
		assertFalse(selenium.isChecked(
				"//td[@id='_128_usersSearchContainer_col-rowChecker_row-usersn3']/input"));
		selenium.check(
			"//td[@id='_128_usersSearchContainer_col-rowChecker_row-usersn3']/input");
		assertTrue(selenium.isChecked(
				"//td[@id='_128_usersSearchContainer_col-rowChecker_row-usersn3']/input"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked(
				"//td[@id='_128_usersSearchContainer_col-rowChecker_row-usersn3']/input"));
	}
}