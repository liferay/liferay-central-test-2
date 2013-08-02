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

package com.liferay.portalweb.portal.permissions.blogs.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_AssignUserRolesTest extends BaseTestCase {
	public void testSA_AssignUserRoles() throws Exception {
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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("BA"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("BA"),
			selenium.getText("//tr/td[2]/a[contains(.,'BA')]"));
		selenium.clickAt("//tr/td[2]/a[contains(.,'BA')]",
			RuntimeVariables.replace("BA"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']", "Roles"));
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));
		selenium.waitForVisible("//div[@id='_125_roles']/span/a/span");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//div[@id='_125_roles']/span/a/span"));
		selenium.clickAt("//div[@id='_125_roles']/span/a/span",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Users and Organizations");
		selenium.waitForVisible(
			"//tr[contains(.,'Blogs Administrator')]/td[1]/a");
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("Blogs"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Administrator"),
			selenium.getText("//tr[contains(.,'Blogs Administrator')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Blogs Administrator')]/td[1]/a",
			RuntimeVariables.replace("Blogs Administrator"));
		selenium.selectWindow("null");
		selenium.waitForPartialText("//div[@id='_125_rolesSearchContainer']",
			"Blogs Administrator");
		assertTrue(selenium.isPartialText(
				"//div[@id='_125_rolesSearchContainer']", "Blogs Administrator"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("Member"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Member"),
			selenium.getText("//tr/td[2]/a[contains(.,'Member')]"));
		selenium.clickAt("//tr/td[2]/a[contains(.,'Member')]",
			RuntimeVariables.replace("Member"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']", "Roles"));
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));
		selenium.waitForVisible("//div[@id='_125_roles']/span/a/span");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//div[@id='_125_roles']/span/a/span"));
		selenium.clickAt("//div[@id='_125_roles']/span/a/span",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Users and Organizations");
		selenium.waitForVisible("//tr/td[1]/a[contains(.,'Member')]");
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("Member"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Member"),
			selenium.getText("//tr/td[1]/a[contains(.,'Member')]"));
		selenium.clickAt("//tr/td[1]/a[contains(.,'Member')]",
			RuntimeVariables.replace("Member"));
		selenium.selectWindow("null");
		selenium.waitForPartialText("//div[@id='_125_rolesSearchContainer']",
			"Member");
		assertTrue(selenium.isPartialText(
				"//div[@id='_125_rolesSearchContainer']", "Member"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}