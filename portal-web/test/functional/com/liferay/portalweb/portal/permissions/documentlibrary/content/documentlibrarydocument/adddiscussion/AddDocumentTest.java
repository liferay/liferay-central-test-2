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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.adddiscussion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDocumentTest extends BaseTestCase {
	public void testAddDocument() throws Exception {
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
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Basic Document')]");
				assertEquals(RuntimeVariables.replace("Basic Document"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Basic Document')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Basic Document')]",
					RuntimeVariables.replace("Basic Document"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				selenium.waitForVisible("//input[@id='_20_file']");
				selenium.uploadCommonFile("//input[@id='_20_file']",
					RuntimeVariables.replace("Document_1.txt"));
				selenium.type("//input[@id='_20_title']",
					RuntimeVariables.replace("Document_1.txt"));
				selenium.select("//select[@name='_20_inputPermissionsViewRole']",
					RuntimeVariables.replace("Owner"));
				assertEquals(RuntimeVariables.replace("More Options \u00bb"),
					selenium.getText(
						"//span[contains(@id,'inputPermissionsShowOptionsLink')]/a"));
				selenium.clickAt("//span[contains(@id,'inputPermissionsShowOptionsLink')]/a",
					RuntimeVariables.replace("More Options \u00bb"));
				selenium.waitForVisible(
					"//input[@id='_20_guestPermissions_ADD_DISCUSSION']");

				boolean guestAddDiscussion = selenium.isChecked(
						"//input[@id='_20_guestPermissions_ADD_DISCUSSION']");

				if (!guestAddDiscussion) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_20_guestPermissions_ADD_DISCUSSION']",
					RuntimeVariables.replace("Guest Add Discussion"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='_20_guestPermissions_ADD_DISCUSSION']"));

				boolean siteMemberAddDiscussion = selenium.isChecked(
						"//input[@id='_20_groupPermissions_ADD_DISCUSSION']");

				if (!siteMemberAddDiscussion) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_20_groupPermissions_ADD_DISCUSSION']",
					RuntimeVariables.replace("Group Add Discussion"));

			case 3:
				assertFalse(selenium.isChecked(
						"//input[@id='_20_groupPermissions_ADD_DISCUSSION']"));
				Thread.sleep(1000);
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.waitForVisible(
					"//a[contains(@class,'document-link')]/span[@class='entry-title']");
				assertEquals(RuntimeVariables.replace("Document_1.txt"),
					selenium.getText(
						"//a[contains(@class,'document-link')]/span[@class='entry-title']"));

			case 100:
				label = -1;
			}
		}
	}
}