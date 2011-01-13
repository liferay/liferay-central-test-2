/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.plugins.kaleo.webcontent.wcwebcontent.deletewebcontentcompleteddrafteditdetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteWebContentCompletedDraftEditDetailsTest extends BaseTestCase {
	public void testDeleteWebContentCompletedDraftEditDetails()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Web Content", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
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
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		assertTrue(selenium.isElementPresent(
				"//input[@value='Submit for Publication']"));
		assertFalse(selenium.isElementPresent(
				"//input[@value='Submit for Publication' and @disabled='']"));
		assertTrue(selenium.isElementPresent("//input[@value='Expire Version']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Expire Version' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//input[@value='Discard Draft' and @disabled='']"));
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Discard Draft']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to discard this draft[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
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