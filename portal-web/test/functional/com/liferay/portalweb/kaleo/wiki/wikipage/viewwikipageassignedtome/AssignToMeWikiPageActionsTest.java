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

package com.liferay.portalweb.kaleo.wiki.wikipage.viewwikipageassignedtome;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignToMeWikiPageActionsTest extends BaseTestCase {
	public void testAssignToMeWikiPageActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		selenium.waitForVisible("//li[@id='_145_mySites']/a/span");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=My Workflow Tasks"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to you."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Wiki Page Title')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Wiki Page Title')]/td[6]/div/a[@title='Actions']"));
		selenium.clickAt("//tr[contains(.,'Wiki Page Title')]/td[6]/div/a[@title='Actions']",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//ul[@class='dropdown-menu lfr-menu-list direction-left']/li/a[contains(.,'Assign to Me')]");
		assertEquals(RuntimeVariables.replace("Assign to Me"),
			selenium.getText(
				"//ul[@class='dropdown-menu lfr-menu-list direction-left']/li/a[contains(.,'Assign to Me')]"));
		selenium.clickAt("//ul[@class='dropdown-menu lfr-menu-list direction-left']/li/a[contains(.,'Assign to Me')]",
			RuntimeVariables.replace("Assign to Me"));
		selenium.waitForVisible("//div[3]/div/button");
		assertEquals(RuntimeVariables.replace("OK"),
			selenium.getText("//div[3]/div/button"));
		selenium.clickAt("//div[3]/div/button", RuntimeVariables.replace("OK"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='alert alert-success']"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Wiki Page Title')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to your roles."),
			selenium.getText("//div[@class='alert alert-info']"));
	}
}