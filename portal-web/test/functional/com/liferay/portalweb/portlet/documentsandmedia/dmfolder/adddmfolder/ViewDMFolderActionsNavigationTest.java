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

package com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderActionsNavigationTest extends BaseTestCase {
	public void testViewDMFolderActionsNavigation() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//span[@class='document-thumbnail']/img");
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//div[@class='document-container']/div/a/span[2]"));
		selenium.clickAt("//div[@class='document-container']/div/a/span[2]",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[@class='folder selected']/a/span[2]",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[@class='folder selected']/a/span[2]"));
		selenium.clickAt("//span[@class='overlay document-action']/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions Arrow Drop Down"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
		assertEquals(RuntimeVariables.replace("Add Subfolder"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
		assertEquals(RuntimeVariables.replace("Access from Desktop"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
	}
}