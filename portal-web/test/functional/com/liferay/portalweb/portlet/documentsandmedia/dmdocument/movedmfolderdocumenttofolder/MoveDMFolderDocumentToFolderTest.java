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

package com.liferay.portalweb.portlet.documentsandmedia.dmdocument.movedmfolderdocumenttofolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveDMFolderDocumentToFolderTest extends BaseTestCase {
	public void testMoveDMFolderDocumentToFolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder1 Name"));
		selenium.waitForText("//li[@class='folder selected']/a",
			"DM Folder1 Name");
		assertEquals(RuntimeVariables.replace("DM Folder1 Document Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_20_rowIdsFileEntryCheckbox']"));
		selenium.clickAt("//input[@id='_20_rowIdsFileEntryCheckbox']",
			RuntimeVariables.replace("Entry Check Box"));
		assertTrue(selenium.isChecked(
				"//input[@id='_20_rowIdsFileEntryCheckbox']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a");
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//a[@id='_20_folderName']"));
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Documents and Media");
		selenium.waitForVisible("xPath=(//input[@value='Choose'])[2]");
		selenium.click("xPath=(//input[@value='Choose'])[2]");
		selenium.selectWindow("null");
		selenium.waitForText("//a[@id='_20_folderName']", "DM Folder2 Name");
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText("//a[@id='_20_folderName']"));
		selenium.clickAt("//input[@value='Move']",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder1 Name"));
		selenium.waitForText("//li[@class='folder selected']/a",
			"DM Folder1 Name");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText("//li[@class='folder selected']/a"));
		assertFalse(selenium.isTextPresent("DM Folder1 Document Title"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		selenium.clickAt("xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]",
			RuntimeVariables.replace("DM Folder2 Name"));
		selenium.waitForText("//li[@class='folder selected']/a",
			"DM Folder2 Name");
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText("//li[@class='folder selected']/a"));
		assertEquals(RuntimeVariables.replace("DM Folder1 Document Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
	}
}