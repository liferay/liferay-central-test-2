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

package com.liferay.portalweb.plugins.drools.user.usecase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAssignMembersUser2SiteTest extends BaseTestCase {
	public void testViewAssignMembersUser2Site() throws Exception {
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
		assertTrue(selenium.isVisible(
				"xPath=(//span[@title='Actions']/ul/li/strong/a)[2]"));
		selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a)[2]",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Memberships')]");
		assertEquals(RuntimeVariables.replace("Manage Memberships"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Memberships')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Memberships')]"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_174_keywords_users']",
			RuntimeVariables.replace("user2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//div[@class='portlet-body']/div[1]/h1"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//div[@class='portlet-body']/div[1]/span"));
		assertEquals(RuntimeVariables.replace("View Members"),
			selenium.getText("//div[@class='portlet-body']/div[2]/span[1]"));
		assertTrue(selenium.isPartialText(
				"//div[@class='portlet-body']/div[2]/span[2]", "Add Members"));
		assertTrue(selenium.isPartialText(
				"//div[@class='portlet-body']/div[2]/span[3]",
				"Add Site Roles to"));
		assertEquals(RuntimeVariables.replace("View Teams"),
			selenium.getText("//div[@class='portlet-body']/div[2]/span[4]"));
		assertEquals(RuntimeVariables.replace("Summary"),
			selenium.getText("//div[@class='portlet-body']/ul/li[1]"));
		assertEquals(RuntimeVariables.replace("Users"),
			selenium.getText("//div[@class='portlet-body']/ul/li[2]"));
		assertEquals(RuntimeVariables.replace("Organizations"),
			selenium.getText("//div[@class='portlet-body']/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("User Groups"),
			selenium.getText("//div[@class='portlet-body']/ul/li[4]"));
		assertTrue(selenium.isVisible(
				"//div[@class='portlet-body']/form/div[1]/div/span/span[1]/span/span/input"));
		assertEquals("Search",
			selenium.getValue(
				"//div[@class='portlet-body']/form/div[1]/div/span/span[2]/span/input"));
		assertEquals(RuntimeVariables.replace("Advanced \u00bb"),
			selenium.getText("//div[@class='portlet-body']/form/div[1]/div/div"));
		assertTrue(selenium.isVisible(
				"//div[@class='portlet-body']/form/div[3]/div[1]/div/table"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Screen Name"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Site Roles"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("User Two"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[1]"));
		assertEquals(RuntimeVariables.replace("user2"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[4]",
				"Actions"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText(
				"//div[@class='portlet-body']/form/div[3]/div[2]/div/div"));
		assertEquals("Assign Users",
			selenium.getValue(
				"//div[@class='portlet-body']/form/div[4]/span/span/input"));
	}
}