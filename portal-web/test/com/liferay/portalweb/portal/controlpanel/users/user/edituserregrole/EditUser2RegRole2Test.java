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

package com.liferay.portalweb.portal.controlpanel.users.user.edituserregrole;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditUser2RegRole2Test extends BaseTestCase {
	public void testEditUser2RegRole2() throws Exception {
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
		selenium.clickAt("link=Search All Users",
			RuntimeVariables.replace("Search All Users"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("usersn2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("userfn2"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-first-name_row-usersn2']/a"));
		assertEquals(RuntimeVariables.replace("userln2"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-last-name_row-usersn2']/a"));
		assertEquals(RuntimeVariables.replace("usersn2"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-screen-name_row-usersn2']/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//span[@title='Actions']/ul[@id='_125_usersSearchContainer_usersn2_menu']/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul[@id='_125_usersSearchContainer_usersn2_menu']/li/strong/a",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']", "Roles"));
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//span[contains(.,'Select')]/a[contains(@href,'openRegularRoleSelector')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//span[contains(.,'Select')]/a[contains(@href,'openRegularRoleSelector')]"));
		selenium.clickAt("//span[contains(.,'Select')]/a[contains(@href,'openRegularRoleSelector')]",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.waitForPopUp("role", RuntimeVariables.replace("30000"));
		selenium.selectWindow("title=Users and Organizations");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//tr[contains(.,'regrole2')]/td[@headers='_125_rolesSearchContainer_col-title']/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("regrole2"),
			selenium.getText(
				"//tr[contains(.,'regrole2')]/td[@headers='_125_rolesSearchContainer_col-title']/a"));
		selenium.clickAt("//tr[contains(.,'regrole2')]/td[@headers='_125_rolesSearchContainer_col-title']/a",
			RuntimeVariables.replace("regrole2"));
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//div[@id='_125_rolesSearchContainer']", "regrole2")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isPartialText(
				"//div[@id='_125_rolesSearchContainer']", "regrole2"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("regrole2"),
			selenium.getText(
				"//tr[contains(.,'regrole')]/td[@headers='_125_rolesSearchContainer_col-title']"));
	}
}