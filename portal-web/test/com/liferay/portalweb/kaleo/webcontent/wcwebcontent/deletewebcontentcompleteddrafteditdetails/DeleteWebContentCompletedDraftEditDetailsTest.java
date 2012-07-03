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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentcompleteddrafteditdetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteWebContentCompletedDraftEditDetailsTest extends BaseTestCase {
	public void testDeleteWebContentCompletedDraftEditDetails()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Web Content Name Edited"),
			selenium.getText("//td[3]/a"));
		assertTrue(selenium.isElementPresent("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isElementPresent("//td[6]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[7]/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("Web Content Name Edited"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		assertTrue(selenium.isElementPresent(
				"//input[@value='Submit for Publication']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Submit for Publication' and @disabled='']"));
		assertTrue(selenium.isElementPresent("//button[2]"));
		selenium.clickAt("//button[2]",
			RuntimeVariables.replace("Expire this Version"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("Web Content Name Edited"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent("//button[2]"));
		selenium.clickAt("//button[2]",
			RuntimeVariables.replace("Delete this Version"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this article version[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//td[3]/a"));
		assertTrue(selenium.isElementPresent("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isElementPresent("//td[6]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[7]/a"));
	}
}