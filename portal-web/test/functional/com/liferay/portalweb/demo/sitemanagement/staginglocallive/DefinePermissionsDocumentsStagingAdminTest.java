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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefinePermissionsDocumentsStagingAdminTest extends BaseTestCase {
	public void testDefinePermissionsDocumentsStagingAdmin()
		throws Exception {
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
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Staging"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Staging Admin"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace("Staging Admin"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForText("//h3[2]", "Documents and Media");
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText("//h3[2]"));
		assertFalse(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[2]"));
		selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[2]",
			RuntimeVariables.replace("Documents and Media"));
		assertTrue(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//div[3]/div/div/table/tbody/tr[4]/td[2]"));
		assertFalse(selenium.isChecked(
				"//div[3]/div/div/table/tbody/tr[4]/td/input"));
		selenium.clickAt("//div[3]/div/div/table/tbody/tr[4]/td/input",
			RuntimeVariables.replace("Delete"));
		assertTrue(selenium.isChecked(
				"//div[3]/div/div/table/tbody/tr[4]/td/input"));
		assertEquals(RuntimeVariables.replace("Update"),
			selenium.getText("//div[3]/div/div/table/tbody/tr[7]/td[2]"));
		assertFalse(selenium.isChecked(
				"//div[3]/div/div/table/tbody/tr[7]/td/input"));
		selenium.clickAt("//div[3]/div/div/table/tbody/tr[7]/td/input",
			RuntimeVariables.replace("Update"));
		assertTrue(selenium.isChecked(
				"//div[3]/div/div/table/tbody/tr[7]/td/input"));
		assertEquals(RuntimeVariables.replace("Update Discussion"),
			selenium.getText("//div[3]/div/div/table/tbody/tr[8]/td[2]"));
		assertFalse(selenium.isChecked(
				"//div[3]/div/div/table/tbody/tr[8]/td[1]/input"));
		selenium.clickAt("//div[3]/div/div/table/tbody/tr[8]/td[1]/input",
			RuntimeVariables.replace("Update Discussion"));
		assertTrue(selenium.isChecked(
				"//div[3]/div/div/table/tbody/tr[8]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText("//div[3]/div/div/table/tbody/tr[9]/td[2]"));
		assertFalse(selenium.isChecked(
				"//div[3]/div/div/table/tbody/tr[9]/td[1]/input"));
		selenium.clickAt("//div[3]/div/div/table/tbody/tr[9]/td[1]/input",
			RuntimeVariables.replace("View"));
		assertTrue(selenium.isChecked(
				"//div[3]/div/div/table/tbody/tr[9]/td[1]/input"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}