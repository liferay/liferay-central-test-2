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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_MoveImageTest extends BaseTestCase {
	public void testMember_MoveImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Subfolder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Subfolder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Subfolder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image 3 Test"),
			selenium.getText("//a[@title='Permissions Image 3 Test - ']"));
		selenium.clickAt("//a[@title='Permissions Image 3 Test - ']",
			RuntimeVariables.replace("Permissions Image 3 Test"));
		selenium.waitForVisible("//img[@title='View']");
		selenium.click("//img[@title='View']");
		selenium.waitForVisible("//button[contains(.,'Move')]");
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText("//button[contains(.,'Move')]"));
		selenium.clickAt("//button[contains(.,'Move')]",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Media Gallery");
		selenium.waitForVisible("link=Home");
		selenium.clickAt("link=Home", RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[contains(.,'Media Gallery')]");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText("//a[contains(.,'Media Gallery')]"));
		selenium.clickAt("//a[contains(.,'Media Gallery')]",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//li[@class='last']");
		selenium.click("//input[@value='Choose This Folder']");
		selenium.selectWindow("null");
		selenium.waitForText("//a[@id='_31_folderName']",
			"Media Gallery Permissions Test Folder");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText("//a[@id='_31_folderName']"));
		selenium.waitForVisible("//input[@value='Move']");
		selenium.clickAt("//input[@value='Move']",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image 3 Test"),
			selenium.getText("//a[@title='Permissions Image 3 Test - ']"));
	}
}