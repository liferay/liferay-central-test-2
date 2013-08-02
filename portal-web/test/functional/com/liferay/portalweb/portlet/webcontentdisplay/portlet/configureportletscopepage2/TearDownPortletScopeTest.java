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

package com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopepage2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPortletScopeTest extends BaseTestCase {
	public void testTearDownPortletScope() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Welcome",
					RuntimeVariables.replace("Welcome"));
				selenium.waitForPageToLoad("30000");

				boolean pageDefaultPresent = selenium.isElementPresent(
						"link=Web Content Display Test Page");

				if (!pageDefaultPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("link=Scope");
				selenium.clickAt("link=Scope", RuntimeVariables.replace("Scope"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_86_scopeType']",
					RuntimeVariables.replace("Default"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals("Default",
					selenium.getSelectedLabel("//select[@id='_86_scopeType']"));
				selenium.selectFrame("relative=top");

			case 2:

				boolean page1Present = selenium.isElementPresent(
						"link=Web Content Display Test Page1");

				if (!page1Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=Web Content Display Test Page1",
					RuntimeVariables.replace("Web Content Display Test Page1"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("link=Scope");
				selenium.clickAt("link=Scope", RuntimeVariables.replace("Scope"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_86_scopeType']",
					RuntimeVariables.replace("Default"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals("Default",
					selenium.getSelectedLabel("//select[@id='_86_scopeType']"));
				selenium.selectFrame("relative=top");

			case 3:

				boolean page2Present = selenium.isElementPresent(
						"link=Web Content Display Test Page2");

				if (!page2Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("link=Web Content Display Test Page2",
					RuntimeVariables.replace("Web Content Display Test Page2"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("link=Scope");
				selenium.clickAt("link=Scope", RuntimeVariables.replace("Scope"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_86_scopeType']",
					RuntimeVariables.replace("Default"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals("Default",
					selenium.getSelectedLabel("//select[@id='_86_scopeType']"));
				selenium.selectFrame("relative=top");

			case 4:

				boolean page3Present = selenium.isElementPresent(
						"link=Web Content Display Test Page3");

				if (!page3Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("link=Web Content Display Test Page3",
					RuntimeVariables.replace("Web Content Display Test Page3"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("link=Scope");
				selenium.clickAt("link=Scope", RuntimeVariables.replace("Scope"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_86_scopeType']",
					RuntimeVariables.replace("Default"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals("Default",
					selenium.getSelectedLabel("//select[@id='_86_scopeType']"));
				selenium.selectFrame("relative=top");

			case 5:
			case 100:
				label = -1;
			}
		}
	}
}