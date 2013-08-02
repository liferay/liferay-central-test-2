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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopeorganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownOrganizationWebContentTest extends BaseTestCase {
	public void testTearDownOrganizationWebContent() throws Exception {
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
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/a/span",
					RuntimeVariables.replace("Scope Selector"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Organization Name')]");
				assertEquals(RuntimeVariables.replace("Organization Name"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Organization Name')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Organization Name')]",
					RuntimeVariables.replace("Organization Name"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForText("//strong/a/span", "Organization Name");
				assertEquals(RuntimeVariables.replace("Organization Name"),
					selenium.getText("//strong/a/span"));
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");

				boolean webContent1Present = selenium.isElementPresent(
						"//input[@name='_15_rowIds']");

				if (!webContent1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete the selected web content?");

			case 2:

				boolean webContent2Present = selenium.isElementPresent(
						"//input[@name='_15_rowIds']");

				if (!webContent2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete the selected web content?");

			case 3:

				boolean webContent3Present = selenium.isElementPresent(
						"//input[@name='_15_rowIds']");

				if (!webContent3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete the selected web content?");

			case 4:

				boolean webContent4Present = selenium.isElementPresent(
						"//input[@name='_15_rowIds']");

				if (!webContent4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete the selected web content?");

			case 5:

				boolean webContent5Present = selenium.isElementPresent(
						"//input[@name='_15_rowIds']");

				if (!webContent5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete the selected web content?");

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}