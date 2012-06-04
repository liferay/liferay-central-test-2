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

package com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersorgroleuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersOrgRoleUserTest extends BaseTestCase {
	public void testAssignMembersOrgRoleUser() throws Exception {
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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("Organization Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a[2]/strong"));
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText(
				"//td[@id='_125_organizationsSearchContainer_col-type_row-1']/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//span[@title='Actions']/ul[@id='_125_organizationsSearchContainer_1_menu']/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul[@id='_125_organizationsSearchContainer_1_menu']/li/strong/a/span",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Organization Role')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Assign Organization Roles"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Organization Role')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Organization Role')]",
			RuntimeVariables.replace("Assign Organization Roles"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Orgrole Name"),
			selenium.getText(
				"//tr[contains(.,'Orgrole Name')]/td[@headers='_125_ocerSearchContainer_col-name']/a"));
		assertEquals(RuntimeVariables.replace("Organization"),
			selenium.getText(
				"//tr[contains(.,'Organization')]/td[@headers='_125_ocerSearchContainer_col-type']/a"));
		selenium.clickAt("//tr[contains(.,'Orgrole Name')]/td[@headers='_125_ocerSearchContainer_col-name']/a",
			RuntimeVariables.replace("Orgrole Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Available", RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("usersn"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-name_row-usersn']"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-screen-name_row-usersn']"));
		assertFalse(selenium.isChecked(
				"//td[@id='_125_usersSearchContainer_col-rowChecker_row-usersn']/input"));
		selenium.check(
			"//td[@id='_125_usersSearchContainer_col-rowChecker_row-usersn']/input");
		assertTrue(selenium.isChecked(
				"//td[@id='_125_usersSearchContainer_col-rowChecker_row-usersn']/input"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked(
				"//td[@id='_125_usersSearchContainer_col-rowChecker_row-usersn']/input"));
	}
}