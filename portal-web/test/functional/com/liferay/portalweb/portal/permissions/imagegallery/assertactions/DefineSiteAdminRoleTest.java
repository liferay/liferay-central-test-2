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
public class DefineSiteAdminRoleTest extends BaseTestCase {
	public void testDefineSiteAdminRole() throws Exception {
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
				selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("SiteAdmin"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("SiteAdmin"),
					selenium.getText("//tr[contains(.,'SiteAdmin')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'SiteAdmin')]/td[1]/a",
					RuntimeVariables.replace("SiteAdmin"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("label=Media Gallery"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Documents and Media"),
					selenium.getText("//h3[contains(.,'Documents and Media')]"));

				boolean addDocumentCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT']");

				if (addDocumentCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT']",
					RuntimeVariables.replace(
						"Documents and Media Add Document Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT']"));

				boolean addDocumentTypeCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']");

				if (addDocumentTypeCheckbox) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']",
					RuntimeVariables.replace(
						"Documents and Media Add Document Type Checkbox"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']"));

				boolean addFolderCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_FOLDER']");

				if (addFolderCheckbox) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_FOLDER']",
					RuntimeVariables.replace(
						"Documents and Media Add Folder Checkbox"));

			case 4:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_FOLDER']"));

				boolean addRepositoryCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_REPOSITORY']");

				if (addRepositoryCheckbox) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_REPOSITORY']",
					RuntimeVariables.replace(
						"Documents and Media Add Repository Checkbox"));

			case 5:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_REPOSITORY']"));

				boolean addShortcutCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_SHORTCUT']");

				if (addShortcutCheckbox) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_SHORTCUT']",
					RuntimeVariables.replace(
						"Documents and Media Add Shortcut Checkbox"));

			case 6:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_SHORTCUT']"));

				boolean addStructureCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_STRUCTURE']");

				if (addStructureCheckbox) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_STRUCTURE']",
					RuntimeVariables.replace(
						"Documents and Media Add Structure Checkbox"));

			case 7:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_STRUCTURE']"));

				boolean permissionsCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryPERMISSIONS']");

				if (permissionsCheckbox) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryPERMISSIONS']",
					RuntimeVariables.replace(
						"Documents and Media Permissions Checkbox"));

			case 8:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryPERMISSIONS']"));

				boolean updateCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryUPDATE']");

				if (updateCheckbox) {
					label = 9;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryUPDATE']",
					RuntimeVariables.replace(
						"Documents and Media Update Checkbox"));

			case 9:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryUPDATE']"));

				boolean viewCheckbox = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryVIEW']");

				if (viewCheckbox) {
					label = 10;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryVIEW']",
					RuntimeVariables.replace(
						"Documents and Media View Checkbox"));

			case 10:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryVIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace(
						"value=regexp:.*portletResource=31&.*showModelResources=0"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Media Gallery"),
					selenium.getText("//h3[contains(.,'Media Gallery')]"));

				boolean addToPageCheckbox = selenium.isChecked(
						"//input[@value='31ADD_TO_PAGE']");

				if (addToPageCheckbox) {
					label = 11;

					continue;
				}

				selenium.clickAt("//input[@value='31ADD_TO_PAGE']",
					RuntimeVariables.replace(
						"Media Gallery Add to Page Checkbox"));

			case 11:
				assertTrue(selenium.isChecked("//input[@value='31ADD_TO_PAGE']"));

				boolean configurationCheckbox = selenium.isChecked(
						"//input[@value='31CONFIGURATION']");

				if (configurationCheckbox) {
					label = 12;

					continue;
				}

				selenium.clickAt("//input[@value='31CONFIGURATION']",
					RuntimeVariables.replace(
						"Media Gallery Configuration Checkbox"));

			case 12:
				assertTrue(selenium.isChecked(
						"//input[@value='31CONFIGURATION']"));

				boolean permissions1Checkbox = selenium.isChecked(
						"//input[@value='31PERMISSIONS']");

				if (permissions1Checkbox) {
					label = 13;

					continue;
				}

				selenium.clickAt("//input[@value='31PERMISSIONS']",
					RuntimeVariables.replace(
						"Media Gallery Permissions Checkbox"));

			case 13:
				assertTrue(selenium.isChecked("//input[@value='31PERMISSIONS']"));

				boolean view1Checkbox = selenium.isChecked(
						"//input[@value='31VIEW']");

				if (view1Checkbox) {
					label = 14;

					continue;
				}

				selenium.clickAt("//input[@value='31VIEW']",
					RuntimeVariables.replace("Media Gallery View Checkbox"));

			case 14:
				assertTrue(selenium.isChecked("//input[@value='31VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}