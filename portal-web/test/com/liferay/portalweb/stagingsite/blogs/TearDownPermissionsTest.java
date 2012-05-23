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

package com.liferay.portalweb.stagingsite.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPermissionsTest extends BaseTestCase {
	public void testTearDownPermissions() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

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
			RuntimeVariables.replace("Power"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Power User"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace("Power User"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Blogs"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Add Entry")
										.equals(selenium.getText(
								"//tr[3]/td[2]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add Entry"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText("//span/a/span"));
		selenium.clickAt("//span/a/span",
			RuntimeVariables.replace("Limit Scope"));
		Thread.sleep(5000);
		selenium.selectWindow("name=site");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//tr[4]/td/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[4]/td/a"));
		selenium.clickAt("//tr[4]/td/a",
			RuntimeVariables.replace("User Personal Site"));
		Thread.sleep(5000);
		selenium.selectWindow("null");
		assertTrue(selenium.isPartialText(
				"//span[@id='_128_groupHTMLcom.liferay.portlet.blogsADD_ENTRY']",
				"User Personal Site"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Sites"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Manage Pages")
										.equals(selenium.getText(
								"//tr[14]/td[2]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Manage Pages"),
			selenium.getText("//tr[14]/td[2]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Portal")
										.equals(selenium.getText(
								"//span[@id='_128_groupHTMLcom.liferay.portal.model.GroupMANAGE_LAYOUTS']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText("//tr[14]/td[4]/span/a/span"));
		selenium.clickAt("//tr[14]/td[4]/span/a/span",
			RuntimeVariables.replace("Limit Scope"));
		Thread.sleep(5000);
		selenium.selectWindow("name=site");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//tr[4]/td/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[4]/td/a"));
		selenium.clickAt("//tr[4]/td/a",
			RuntimeVariables.replace("User Personal Site"));
		Thread.sleep(5000);
		selenium.selectWindow("null");
		assertTrue(selenium.isPartialText(
				"//span[@id='_128_groupHTMLcom.liferay.portal.model.GroupMANAGE_LAYOUTS']",
				"User Personal Site"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}