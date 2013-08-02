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

package com.liferay.portalweb.demo.dynamicdata.kaleoticketdefinitionworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignRoleJavascriptDeveloperTest extends BaseTestCase {
	public void testAssignRoleJavascriptDeveloper() throws Exception {
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
			RuntimeVariables.replace("jd@liferay.com"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//tbody/tr[3]/td[2]/a", "Javascript");
		assertEquals(RuntimeVariables.replace("Javascript"),
			selenium.getText("//tbody/tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Developer"),
			selenium.getText("//tbody/tr[3]/td[3]/a"));
		selenium.clickAt("//tbody/tr[3]/td[2]/a",
			RuntimeVariables.replace("Javascript"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']", "Roles"));
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));
		selenium.waitForVisible("//div[6]/span[1]/a/span");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//div[6]/span[1]/a/span"));
		selenium.clickAt("//div[6]/span[1]/a/span",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Users and Organizations");
		selenium.waitForVisible("link=Javascript Developer");
		selenium.clickAt("link=Javascript Developer",
			RuntimeVariables.replace("Javascript Developer"));
		selenium.selectWindow("null");
		selenium.waitForPartialText("//div[@id='_125_rolesSearchContainer']",
			"Javascript Developer");
		assertTrue(selenium.isPartialText(
				"//div[@id='_125_rolesSearchContainer']", "Javascript Developer"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}