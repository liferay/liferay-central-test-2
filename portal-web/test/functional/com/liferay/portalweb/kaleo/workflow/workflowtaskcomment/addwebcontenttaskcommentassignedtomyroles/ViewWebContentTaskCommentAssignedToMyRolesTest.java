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

package com.liferay.portalweb.kaleo.workflow.workflowtaskcomment.addwebcontenttaskcommentassignedtomyroles;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentTaskCommentAssignedToMyRolesTest extends BaseTestCase {
	public void testViewWebContentTaskCommentAssignedToMyRoles()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Workflow Tasks",
			RuntimeVariables.replace("My Workflow Tasks"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to you."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC Web Content Title"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//td[3]/a"));
		assertTrue(selenium.isVisible("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//td[5]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("WC Web Content Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Workflow Task Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Submissions",
			RuntimeVariables.replace("My Submissions"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Single Approver"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC Web Content Title"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isVisible("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//td[6]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("WC Web Content Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Workflow Task Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Workflow", RuntimeVariables.replace("Workflow"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Submissions",
			RuntimeVariables.replace("Submissions"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Single Approver"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC Web Content Title"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isVisible("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//td[6]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("Single Approver"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Workflow Task Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
	}
}