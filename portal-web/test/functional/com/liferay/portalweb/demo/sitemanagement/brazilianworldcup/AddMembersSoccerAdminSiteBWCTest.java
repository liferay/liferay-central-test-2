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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMembersSoccerAdminSiteBWCTest extends BaseTestCase {
	public void testAddMembersSoccerAdminSiteBWC() throws Exception {
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
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_134_name']",
			RuntimeVariables.replace("World Cup"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("World Cup - Brazil 2014"),
			selenium.getText("//td/a"));
		selenium.clickAt("//td/a",
			RuntimeVariables.replace("World Cup - Brazil 2014"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Site Memberships",
			RuntimeVariables.replace("Site Memberships"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Members"),
			selenium.getText("//span[contains(.,'Add Members')]/ul/li/strong/a"));
		selenium.clickAt("//span[contains(.,'Add Members')]/ul/li/strong/a",
			RuntimeVariables.replace("Add Members"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'User')]/a");
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'User')]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'User')]/a",
			RuntimeVariables.replace("User"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@name='_174_keywords']"));
		selenium.type("//input[@name='_174_keywords']",
			RuntimeVariables.replace("socceradmin"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bruno Admin"),
			selenium.getText("//tr[3]/td[2]"));
		assertFalse(selenium.isChecked("//td/input"));
		selenium.clickAt("//td/input",
			RuntimeVariables.replace("Bruno Admin checkbox"));
		assertTrue(selenium.isChecked("//td/input"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}