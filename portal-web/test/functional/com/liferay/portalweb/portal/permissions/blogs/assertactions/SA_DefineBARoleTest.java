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

package com.liferay.portalweb.portal.permissions.blogs.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_DefineBARoleTest extends BaseTestCase {
	public void testSA_DefineBARole() throws Exception {
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
					RuntimeVariables.replace("Blogs"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Blogs Administrator"),
					selenium.getText(
						"//tr/td[1]/a[contains(.,'Blogs Administrator')]"));
				selenium.clickAt("//tr/td[1]/a[contains(.,'Blogs Administrator')]",
					RuntimeVariables.replace("Blogs Administrator"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Define Permissions"),
					selenium.getText(
						"//ul[@class='aui-tabview-list']/li/span/a[contains(.,'Define Permissions')]"));
				selenium.clickAt("//ul[@class='aui-tabview-list']/li/span/a[contains(.,'Define Permissions')]",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("label=Blogs"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Blogs"),
					selenium.getText("//form/h3"));

				boolean blogsAllChecked = selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[1]");

				if (blogsAllChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[1]",
					RuntimeVariables.replace("Select All"));

			case 2:
				assertTrue(selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[1]"));

				boolean blogsEntryAllChecked = selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[2]");

				if (blogsEntryAllChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[2]",
					RuntimeVariables.replace("Select All"));

			case 3:
				assertTrue(selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[2]"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace(
						"value=regexp:.*portletResource=33&.*showModelResources=0"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Blogs"),
					selenium.getText("//form/h3"));

				boolean blogsAllChecked2 = selenium.isChecked(
						"//input[@name='_128_allRowIds']");

				if (blogsAllChecked2) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_128_allRowIds']",
					RuntimeVariables.replace("Select All"));

			case 4:
				assertTrue(selenium.isChecked("//input[@name='_128_allRowIds']"));
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