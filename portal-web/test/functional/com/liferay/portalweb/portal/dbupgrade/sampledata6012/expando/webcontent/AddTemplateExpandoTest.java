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

package com.liferay.portalweb.portal.dbupgrade.sampledata6012.expando.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTemplateExpandoTest extends BaseTestCase {
	public void testAddTemplateExpando() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/expando-web-content-community/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText("//li[@id='_145_manageContent']/a/span"));
				selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Templates",
					RuntimeVariables.replace("Templates"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Template']",
					RuntimeVariables.replace("Add Template"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_15_name']",
					RuntimeVariables.replace("Expando Template Test"));
				selenium.type("//textarea[@id='_15_description']",
					RuntimeVariables.replace(
						"This is an expando template test."));

				boolean cacheableChecked = selenium.isChecked(
						"//input[@id='_15_cacheableCheckbox']");

				if (!cacheableChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_15_cacheableCheckbox']",
					RuntimeVariables.replace("Cacheable"));

			case 2:
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				selenium.selectWindow("title=Web Content");
				selenium.waitForVisible("//td[2]/a");
				assertTrue(selenium.isPartialText("//td[2]/a",
						"Expando Structure Test"));
				selenium.click("//td[2]/a");
				selenium.selectWindow("null");
				selenium.waitForText("//a[@id='_15_structureName']",
					"Expando Structure Test");
				assertEquals(RuntimeVariables.replace("Expando Structure Test"),
					selenium.getText("//a[@id='_15_structureName']"));
				selenium.uploadFile("//input[@id='_15_xsl']",
					RuntimeVariables.replace(
						"L:\\private\\ee-6.1.x-portal\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata6012\\expando\\webcontent\\dependencies\\Expando.htm"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isVisible("//td[2]/a"));
				assertTrue(selenium.isPartialText("//td[3]/a",
						"Expando Template Test"));
				assertTrue(selenium.isPartialText("//td[3]/a",
						"This is an expando template test."));

			case 100:
				label = -1;
			}
		}
	}
}