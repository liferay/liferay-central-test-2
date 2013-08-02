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
public class EditDataDefinitionFieldComponentTest extends BaseTestCase {
	public void testEditDataDefinitionFieldComponent()
		throws Exception {
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
		selenium.clickAt("link=Dynamic Data Lists",
			RuntimeVariables.replace("Dynamic Data Lists"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_167_manageDDMStructuresLink']");
		assertEquals(RuntimeVariables.replace("Manage Data Definitions"),
			selenium.getText("//a[@id='_167_manageDDMStructuresLink']"));
		selenium.clickAt("//a[@id='_167_manageDDMStructuresLink']",
			RuntimeVariables.replace("Manage Data Definitions"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/search_container.js')]");
		selenium.waitForVisible(
			"//input[@id='_166_toggle_id_ddm_structure_searchkeywords']");
		selenium.type("//input[@id='_166_toggle_id_ddm_structure_searchkeywords']",
			RuntimeVariables.replace("Ticket Definition"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(5000);
		selenium.waitForVisible("//span[@title='Actions']/ul/li/strong/a/span");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit')]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit')]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit')]/a",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//div[@class='aui-diagram-builder-canvas']");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-canvas']/div/div[2]/div/label"));
		selenium.clickAt("//div[@class='aui-diagram-builder-canvas']/div/div[2]",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("xPath=(//button[@id='editEvent'])[2]");
		selenium.clickAt("xPath=(//button[@id='editEvent'])[2]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//tbody[@class='yui3-datatable-data']");
		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[2]/td[1]",
			RuntimeVariables.replace("Field Label"));
		selenium.waitForVisible("//div[@class='yui3-widget-bd']/input");
		selenium.type("//div[@class='yui3-widget-bd']/input",
			RuntimeVariables.replace("Component"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[1]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible("//div[@class='yui3-widget-bd']/input");
		selenium.type("//div[@class='yui3-widget-bd']/input",
			RuntimeVariables.replace("component_name"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[8]/td[1]",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='yui3-widget-bd']/div/div[2]/input[1]");
		assertEquals(RuntimeVariables.replace("Add option"),
			selenium.getText(
				"//a[@class='aui-celleditor-edit-link aui-celleditor-edit-add-option']"));
		selenium.clickAt("//a[@class='aui-celleditor-edit-link aui-celleditor-edit-add-option']",
			RuntimeVariables.replace("Add option"));
		selenium.clickAt("//a[@class='aui-celleditor-edit-link aui-celleditor-edit-add-option']",
			RuntimeVariables.replace("Add option"));
		selenium.clickAt("//a[@class='aui-celleditor-edit-link aui-celleditor-edit-add-option']",
			RuntimeVariables.replace("Add option"));
		assertTrue(selenium.isVisible(
				"//div[@class='yui3-widget-bd']/div[2]/div[7]/input"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[2]/input[1]",
			RuntimeVariables.replace("Blog"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[2]/input[2]",
			RuntimeVariables.replace("Blog"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[3]/input[1]",
			RuntimeVariables.replace("Bookmarks"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[3]/input[2]",
			RuntimeVariables.replace("Bookmarks"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[4]/input[1]",
			RuntimeVariables.replace("Documents and Media"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[4]/input[2]",
			RuntimeVariables.replace("Documents and Media"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[5]/input[1]",
			RuntimeVariables.replace("Kaleo"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[5]/input[2]",
			RuntimeVariables.replace("Kaleo"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[6]/input[1]",
			RuntimeVariables.replace("DDL"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[6]/input[2]",
			RuntimeVariables.replace("DDL"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[7]/input[1]",
			RuntimeVariables.replace("Javascript"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[7]/input[2]",
			RuntimeVariables.replace("Javascript"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("select"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Component"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("component_name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"Blog, Bookmarks, Documents and Media, Kaleo, DDL, Javascript"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[2]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[9]/td[2]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}