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

package com.liferay.portalweb.portal.controlpanel.sites.siteteam.assignmemberssiteteamuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersSiteTeamUserTest extends BaseTestCase {
	public void testAssignMembersSiteTeamUser() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a[contains(@id,'groupSelectorButton')]/span",
			RuntimeVariables.replace("Site Selector"));
		selenium.waitForVisible("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Site Memberships",
			RuntimeVariables.replace("Site Memberships"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.clickAt("link=View Teams",
			RuntimeVariables.replace("View Teams"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name: Manage Memberships"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.type("//input[@id='_174_name']",
			RuntimeVariables.replace("Site Team Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Team Name"),
			selenium.getText("//tr[contains(.,'Site Team Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Site Team Description"),
			selenium.getText("//tr[contains(.,'Site Team Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Site Team Name')]/td[3]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Site Team Name')]/td[3]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]");
		assertEquals(RuntimeVariables.replace("Assign Members"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]",
			RuntimeVariables.replace("Assign Members"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Team Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("No users were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=Available", RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_174_keywords']",
			RuntimeVariables.replace("usersn"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText("//tr[contains(.,'userfn userln')]/td[2]"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText("//tr[contains(.,'userfn userln')]/td[3]"));
		assertFalse(selenium.isChecked("//input[@name='_174_allRowIds']"));
		selenium.clickAt("//input[@name='_174_allRowIds']",
			RuntimeVariables.replace("Assign usersn"));
		assertTrue(selenium.isChecked("//input[@name='_174_allRowIds']"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("link=Current", RuntimeVariables.replace("Current"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText("//tr[contains(.,'userfn userln')]/td[2]"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText("//tr[contains(.,'userfn userln')]/td[3]"));
	}
}