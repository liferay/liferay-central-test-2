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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut.view;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddShortcutTest extends BaseTestCase {
	public void testAddShortcut() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.clickAt("link=Documents and Media",
					RuntimeVariables.replace("Documents and Media"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//span[@title='Add']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Add']/ul/li/strong/a",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Shortcut')]");
				assertEquals(RuntimeVariables.replace("Shortcut"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Shortcut')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Shortcut')]"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("xPath=(//input[@value='Select'])[1]",
					RuntimeVariables.replace("Select Site"));
				Thread.sleep(5000);
				selenium.selectWindow("title=Documents and Media");
				selenium.waitForVisible(
					"//tr[contains(.,'Liferay') and contains(.,'Open')]/td[1]/a");
				selenium.clickAt("//tr[contains(.,'Liferay') and contains(.,'Open')]/td[1]/a",
					RuntimeVariables.replace("Liferay"));
				selenium.selectWindow("null");
				selenium.waitForText("//span[@id='_20_toGroupName']", "Liferay");
				assertEquals(RuntimeVariables.replace("Liferay"),
					selenium.getText("//span[@id='_20_toGroupName']"));
				selenium.clickAt("xPath=(//input[@value='Select'])[2]",
					RuntimeVariables.replace("Select Site"));
				Thread.sleep(5000);
				selenium.selectWindow("title=Documents and Media");
				selenium.waitForVisible(
					"//tr[contains(.,'Document_1.txt')]/td/a");
				selenium.clickAt("//tr[contains(.,'Document_1.txt')]/td/a",
					RuntimeVariables.replace("Document_1.txt"));
				selenium.selectWindow("null");
				selenium.waitForText("//span[@id='_20_toFileEntryTitle']",
					"Document_1.txt");
				assertEquals(RuntimeVariables.replace("Liferay"),
					selenium.getText("//span[@id='_20_toGroupName']"));
				assertEquals(RuntimeVariables.replace("Document_1.txt"),
					selenium.getText("//span[@id='_20_toFileEntryTitle']"));
				selenium.select("//select[@id='_20_inputPermissionsViewRole']",
					RuntimeVariables.replace("Owner"));
				selenium.clickAt("link=More Options \u00bb",
					RuntimeVariables.replace("More Options \u00bb"));
				selenium.waitForVisible(
					"//input[@id='_20_guestPermissions_ADD_DISCUSSION']");

				boolean addGroupDiscussion = selenium.isChecked(
						"//input[@id='_20_guestPermissions_ADD_DISCUSSION']");

				if (!addGroupDiscussion) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_20_guestPermissions_ADD_DISCUSSION']",
					RuntimeVariables.replace("Add Group Discussion"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='_20_guestPermissions_ADD_DISCUSSION']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Document_1.txt"),
					selenium.getText(
						"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
				assertEquals(RuntimeVariables.replace("Document_1.txt"),
					selenium.getText(
						"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
				assertTrue(selenium.isVisible(
						"//div[@data-title='Document_1.txt']/a/span/img[@class='shortcut-icon']"));

			case 100:
				label = -1;
			}
		}
	}
}