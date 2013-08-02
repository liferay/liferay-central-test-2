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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documenttype.delete;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDocumentTypeTest extends BaseTestCase {
	public void testAddDocumentType() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Manage']/ul/li/strong/a",
					RuntimeVariables.replace("Manage"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Document Types')]");
				assertEquals(RuntimeVariables.replace("Document Types"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Document Types')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Document Types')]",
					RuntimeVariables.replace("Document Types"));
				selenium.waitForVisible(
					"//iframe[contains(@id,'openFileEntryTypeView')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'openFileEntryTypeView')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/search_container.js')]");
				selenium.waitForVisible(
					"//span[@class='lfr-toolbar-button add-button ']/a");
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText(
						"//span[@class='lfr-toolbar-button add-button ']/a"));
				selenium.clickAt("//span[@class='lfr-toolbar-button add-button ']/a",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible("//input[@id='_20_name']");
				selenium.type("//input[@id='_20_name']",
					RuntimeVariables.replace("Document Type Name"));
				selenium.waitForVisible(
					"//div[@class='aui-tabview-content aui-widget-bd']/div/ul/li[@title=\"Text Box\"]/span");
				assertEquals(RuntimeVariables.replace("Text Box"),
					selenium.getText(
						"//div[@class='aui-tabview-content aui-widget-bd']/div/ul/li[@title=\"Text Box\"]/div"));
				selenium.dragAndDropToObject("//div[@class='aui-tabview-content aui-widget-bd']/div/ul/li[@title=\"Text Box\"]/div",
					"//div[@class='aui-diagram-builder-drop-container']");
				selenium.waitForVisible(
					"//div[contains(@class,'aui-form-builder-text-field-content')]/label");
				assertEquals(RuntimeVariables.replace("Text Box"),
					selenium.getText(
						"//div[contains(@class,'aui-form-builder-text-field-content')]/label"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.waitForVisible("//input[@id='_20_keywords']");
				selenium.type("//input[@id='_20_keywords']",
					RuntimeVariables.replace("Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//tr[contains(.,'Document Type Name')]/td[1]");
				assertEquals(RuntimeVariables.replace("Document Type Name"),
					selenium.getText(
						"//tr[contains(.,'Document Type Name')]/td[1]"));
				selenium.waitForVisible(
					"//tr[contains(.,'Document Type Name')]/td[3]/span[@title='Actions']/ul/li/strong/a");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Document Type Name')]/td[3]/span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//tr[contains(.,'Document Type Name')]/td[3]/span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");

				boolean guestView = selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']");

				if (guestView) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='guest_ACTION_VIEW']",
					RuntimeVariables.replace("Guest View"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}