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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrary.addshortcut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AddShortcutTest extends BaseTestCase {
	public void testMember_AddShortcut() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Documents and Media",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Shortcut')]");
		assertEquals(RuntimeVariables.replace("Shortcut"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Shortcut')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Shortcut')]",
			RuntimeVariables.replace("Shortcut"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("xPath=(//input[@value='Select'])[1]",
			RuntimeVariables.replace("Select Site"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Documents and Media");
		selenium.waitForVisible("link=Global");
		selenium.clickAt("link=Global", RuntimeVariables.replace("Global"));
		selenium.selectWindow("null");
		selenium.waitForText("//span[@id='_20_toGroupName']", "Global");
		assertEquals(RuntimeVariables.replace("Global"),
			selenium.getText("//span[@id='_20_toGroupName']"));
		selenium.clickAt("xPath=(//input[@value='Select'])[2]",
			RuntimeVariables.replace("Select Site"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Documents and Media");
		selenium.waitForVisible("//tr[3]/td/a");
		selenium.clickAt("//tr[3]/td/a",
			RuntimeVariables.replace("Document_1.txt"));
		selenium.selectWindow("null");
		selenium.waitForText("//span[@id='_20_toFileEntryTitle']",
			"Document_1.txt");
		assertEquals(RuntimeVariables.replace("Document_1.txt"),
			selenium.getText("//span[@id='_20_toFileEntryTitle']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertTrue(selenium.isVisible("//img[@class='shortcut-icon']"));
		assertEquals(RuntimeVariables.replace("Document_1.txt"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
	}
}