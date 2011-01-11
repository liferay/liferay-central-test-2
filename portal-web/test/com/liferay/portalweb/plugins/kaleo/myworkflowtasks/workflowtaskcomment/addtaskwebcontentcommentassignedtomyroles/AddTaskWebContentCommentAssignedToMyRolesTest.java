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

package com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtaskcomment.addtaskwebcontentcommentassignedtomyroles;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTaskWebContentCommentAssignedToMyRolesTest extends BaseTestCase {
	public void testAddTaskWebContentCommentAssignedToMyRoles()
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
		selenium.clickAt("link=My Workflow Tasks", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Pending", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Web Content Name"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText(
				"//div[@class='lfr-panel-container task-panel-container']/div[3]/div/div/span"));
		selenium.clickAt("link=Be the first.", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_153_postReplyBody0")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("_153_postReplyBody0",
			RuntimeVariables.replace("My Workflow Tasks Workflow Task Comment"));
		selenium.saveScreenShotAndSource();
		selenium.keyPress("_153_postReplyBody0",
			RuntimeVariables.replace("\\48"));
		selenium.keyPress("_153_postReplyBody0", RuntimeVariables.replace("\\8"));
		selenium.clickAt("//input[@value='Reply']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success'][1]"));
		assertEquals(RuntimeVariables.replace(
				"My Workflow Tasks Workflow Task Comment"),
			selenium.getText("//form/div/div/div/div/div[3]/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[2]/a"));
	}
}