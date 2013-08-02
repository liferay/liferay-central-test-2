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

package com.liferay.portalweb.portal.controlpanel.sites.site.joinsiterestricted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ApproveUserJoinSitesTest extends BaseTestCase {
	public void testApproveUserJoinSites() throws Exception {
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
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Site Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Restricted"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("1 User"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[4]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[5]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'Site Name')]/td[6]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Site Name')]/td[7]/span/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Site Name')]/td[7]/span/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Memberships')]");
		assertEquals(RuntimeVariables.replace("Manage Memberships"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Memberships')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Memberships')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are 1 membership requests pending."),
			selenium.getText("//span[3]/a/span"));
		selenium.clickAt("//span[3]/a/span",
			RuntimeVariables.replace("There are 1 membership requests pending."));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText("//td/span/a/span"));
		selenium.clickAt("//td/span/a/span", RuntimeVariables.replace("Reply"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//textarea[@id='_174_replyComments']",
			RuntimeVariables.replace("You are welcome"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("xPath=(//div[@class='portlet-msg-success'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Your reply will be sent to the user by email."),
			selenium.getText("xPath=(//div[@class='portlet-msg-success'])[2]"));
		selenium.clickAt("link=Approved", RuntimeVariables.replace("Approved"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace(
				"userfn userln (userea@liferay.com)"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"I want to join this really cool community please."),
			selenium.getText("//tr[3]/td[3]"));
		assertTrue(selenium.isElementPresent("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[5]"));
		assertEquals(RuntimeVariables.replace("You are welcome"),
			selenium.getText("//tr[3]/td[6]"));
	}
}