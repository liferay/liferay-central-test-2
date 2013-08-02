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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.saveasdraftwcwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSaveAsDraftWCWebContentTest extends BaseTestCase {
	public void testViewSaveAsDraftWCWebContent() throws Exception {
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

		String webcontentID = selenium.getText("//td[2]/a");
		RuntimeVariables.setValue("webcontentID", webcontentID);
		assertEquals(RuntimeVariables.replace("${webcontentID}"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//td[5]/a"));
		assertTrue(selenium.isVisible("//td[6]/a"));
		assertTrue(selenium.isVisible("//td[7]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[8]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//a[contains(@id,'SearchContainer_1_menu_edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//a[contains(@id,'SearchContainer_1_menu_edit')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//a[contains(@id,'SearchContainer_1_menu_permissions')]"));
		assertEquals(RuntimeVariables.replace("View (Opens New Window)"),
			selenium.getText("//a[contains(@id,'SearchContainer_1_menu_view')]"));
		assertEquals(RuntimeVariables.replace("Copy"),
			selenium.getText("//a[contains(@id,'SearchContainer_1_menu_copy')]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//a[contains(@id,'SearchContainer_1_menu_delete')]"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("WC WebContent ID"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertTrue(selenium.isPartialText(
				"//div[@id='cpContextPanelTemplate']",
				"Web Content can be any content you would like to add to a site, such as articles, a FAQ, or a news item. Administrators can manage content, as well as assign user roles and permissions. Users may add, edit, approve, or view content depending on their role."));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText("//span[@class='workflow-id']",
				RuntimeVariables.getValue("webcontentID")));
		assertEquals(RuntimeVariables.replace("Version: 1.0"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Draft"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//button[.='Permissions']"));
		assertEquals(RuntimeVariables.replace("View History"),
			selenium.getText("//button[.='View History']"));
		assertEquals(RuntimeVariables.replace("Default"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		assertEquals(RuntimeVariables.replace("None"),
			selenium.getText("//span[@class='template-name-label']"));
		assertEquals(RuntimeVariables.replace("English (United States)"),
			selenium.getText("//span[@id='_15_textLanguageId']"));
		assertEquals("WC WebContent Title",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		Thread.sleep(1000);
		selenium.waitForVisible("//iframe[contains(@title,'Rich text editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich text editor')]");
		selenium.waitForText("//body", "WC WebContent Content");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		assertTrue(selenium.isChecked("//input[@id='_15_indexableCheckbox']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//span[@class='article-name']"));
		assertTrue(selenium.isPartialText("//a[@id='_15_contentLink']",
				"Content"));
		assertTrue(selenium.isPartialText("//a[@id='_15_abstractLink']",
				"Abstract"));
		assertTrue(selenium.isPartialText("//a[@id='_15_categorizationLink']",
				"Categorization"));
		assertTrue(selenium.isPartialText("//a[@id='_15_scheduleLink']",
				"Schedule"));
		assertTrue(selenium.isPartialText("//a[@id='_15_displayPageLink']",
				"Display Page"));
		assertTrue(selenium.isPartialText("//a[@id='_15_relatedAssetsLink']",
				"Related Assets"));
		assertTrue(selenium.isPartialText("//a[@id='_15_customFieldsLink']",
				"Custom Fields"));
		assertTrue(selenium.isVisible("//input[@value='Save as Draft']"));
		assertTrue(selenium.isVisible("//input[@value='Publish']"));
		assertTrue(selenium.isVisible("xPath=(//input[@value='Cancel'])[2]"));
	}
}