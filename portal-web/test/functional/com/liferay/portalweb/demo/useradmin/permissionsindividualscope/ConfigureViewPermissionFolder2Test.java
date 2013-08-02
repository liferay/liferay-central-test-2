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

package com.liferay.portalweb.demo.useradmin.permissionsindividualscope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureViewPermissionFolder2Test extends BaseTestCase {
	public void testConfigureViewPermissionFolder2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.clickAt("//div[3]/span[2]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class=\"lfr-component lfr-menu-list\"]/ul/li[3]/a");
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class=\"lfr-component lfr-menu-list\"]/ul/li[3]/a"));
		selenium.clickAt("//div[@class=\"lfr-component lfr-menu-list\"]/ul/li[3]/a",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isChecked("//input[@name='16_ACTION_VIEW']"));
		selenium.clickAt("//input[@name='16_ACTION_VIEW']",
			RuntimeVariables.replace("Guest View"));
		assertFalse(selenium.isChecked("//input[@name='16_ACTION_VIEW']"));
		assertFalse(selenium.isChecked("//input[@name='18_ACTION_VIEW']"));
		selenium.clickAt("//input[@name='18_ACTION_VIEW']",
			RuntimeVariables.replace("User View"));
		assertTrue(selenium.isChecked("//input[@name='18_ACTION_VIEW']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}