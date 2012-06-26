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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertActionsTest extends BaseTestCase {
	public void testMember_AssertActions() throws Exception {
		selenium.open("/web/site-name/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Message Boards Permissions Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Message Boards Permissions Page",
			RuntimeVariables.replace("Message Boards Permissions Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("RSS"),
			selenium.getText("//div[2]/div/span/a/span[1]"));
		Thread.sleep(5000);
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Subscribe")
										.equals(selenium.getText(
								"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		assertFalse(selenium.isElementPresent("link=Edit"));
		assertFalse(selenium.isElementPresent("link=Permissions"));
		assertFalse(selenium.isElementPresent("link=Move Thread"));
		assertFalse(selenium.isElementPresent("link=Delete"));
		assertFalse(selenium.isElementPresent("link=Add Category"));
		assertFalse(selenium.isElementPresent("link=Banned Users"));
		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong", RuntimeVariables.replace("Category Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Thread Subject"),
			selenium.getText("//tr[3]/td/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		assertFalse(selenium.isElementPresent("link=Edit"));
		assertFalse(selenium.isElementPresent("link=Move"));
		assertFalse(selenium.isElementPresent("link=Delete"));
		assertTrue(selenium.isVisible("//input[@value='Post New Thread']"));
		selenium.clickAt("//tr[3]/td/a",
			RuntimeVariables.replace("Thread Subject"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Reply with Quote"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[3]/span/a"));
		assertFalse(selenium.isElementPresent("link=Delete"));
		assertFalse(selenium.isElementPresent("link=Edit"));
		assertFalse(selenium.isElementPresent("link=Permissions"));
	}
}