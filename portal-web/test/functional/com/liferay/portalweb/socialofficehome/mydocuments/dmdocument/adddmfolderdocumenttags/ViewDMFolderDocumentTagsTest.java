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

package com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocumenttags;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderDocumentTagsTest extends BaseTestCase {
	public void testViewDMFolderDocumentTags() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("My Documents"),
			selenium.getText("//nav/ul/li[contains(.,'My Documents')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'My Documents')]/a/span",
			RuntimeVariables.replace("My Documents"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[@class='folder selected']/a",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[@class='folder selected']/a"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li/span/a"));
		assertEquals(RuntimeVariables.replace(
				"Access these files offline using Liferay Sync."),
			selenium.getText("//div[@id='_20_syncNotificationContent']/a"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Download')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Checkout')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Edit')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Move')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Permissions')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Delete')]/span[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//span[@class='document-title']"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertTrue(selenium.isPartialText(
				"//span[@class='user-date']/span/span", "Uploaded by Joe Bloggs"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-ratings']"));
		assertEquals(RuntimeVariables.replace("Version History"),
			selenium.getText(
				"//div[@class='lfr-document-library-versions']/div/div/div"));
		assertEquals(RuntimeVariables.replace("tag1"),
			selenium.getText("xPath=(//span[@class='tag'])[1]"));
		assertEquals(RuntimeVariables.replace("tag2"),
			selenium.getText("xPath=(//span[@class='tag'])[2]"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//span[@class='version-number']"));
		assertEquals(RuntimeVariables.replace("By: Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertTrue(selenium.isVisible("//span[@class='modified-date']"));
		assertEquals(RuntimeVariables.replace("Size: 21.5k"),
			selenium.getText("//span[@class='size']"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div/a"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText("//span[@class='subscribe-link']/a/span"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[contains(@class,'version')]"));
		assertEquals(RuntimeVariables.replace("Last Updated by Joe Bloggs"),
			selenium.getText("//div[@class='lfr-asset-icon lfr-asset-author']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-asset-icon lfr-asset-date']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (21.5k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
	}
}