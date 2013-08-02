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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.updatediscussion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveInlinePermissionsTest extends BaseTestCase {
	public void testRemoveInlinePermissions() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//tr[contains(.,'Web Content Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Web Content Name')]/td[8]/span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//tr[contains(.,'Web Content Name')]/td[8]/span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Permissions')]/a");
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Permissions')]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Permissions')]/a",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.uncheck("//input[@id='guest_ACTION_ADD_DISCUSSION']");
		selenium.uncheck("//input[@id='guest_ACTION_DELETE']");
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_DELETE_DISCUSSION']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_EXPIRE']"));
		selenium.uncheck("//input[@id='guest_ACTION_PERMISSIONS']");
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_UPDATE']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_UPDATE_DISCUSSION']"));
		selenium.uncheck("//input[@id='guest_ACTION_VIEW']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_ADD_DISCUSSION']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_DELETE']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_DELETE_DISCUSSION']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_EXPIRE']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_PERMISSIONS']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_UPDATE']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_UPDATE_DISCUSSION']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_VIEW']"));
	}
}