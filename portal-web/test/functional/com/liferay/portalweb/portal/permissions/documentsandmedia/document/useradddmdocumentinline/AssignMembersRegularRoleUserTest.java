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

package com.liferay.portalweb.portal.permissions.documentsandmedia.document.useradddmdocumentinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersRegularRoleUserTest extends BaseTestCase {
	public void testAssignMembersRegularRoleUser() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
					RuntimeVariables.replace("Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Roles Regrole Name"),
					selenium.getText(
						"//tr[contains(.,'Roles Regrole Name')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Regular"),
					selenium.getText(
						"//tr[contains(.,'Roles Regrole Name')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText(
						"//tr[contains(.,'Roles Regrole Name')]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Roles Regrole Name')]/td[4]/span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//tr[contains(.,'Roles Regrole Name')]/td[4]/span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Available",
					RuntimeVariables.replace("Available"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));
				selenium.waitForVisible("//input[@name='_128_keywords']");

			case 2:
				selenium.type("//input[@name='_128_keywords']",
					RuntimeVariables.replace("usersn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("usersn"),
					selenium.getText("//tr[3]/td[3]"));
				assertFalse(selenium.isChecked("//input[@name='_128_rowIds']"));
				selenium.clickAt("//input[@name='_128_rowIds']",
					RuntimeVariables.replace("Checkbox"));
				assertTrue(selenium.isChecked("//input[@name='_128_rowIds']"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}