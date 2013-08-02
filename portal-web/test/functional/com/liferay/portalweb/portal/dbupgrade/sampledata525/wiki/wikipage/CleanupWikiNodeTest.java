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

package com.liferay.portalweb.portal.dbupgrade.sampledata525.wiki.wikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CleanupWikiNodeTest extends BaseTestCase {
	public void testCleanupWikiNode() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				assertTrue(selenium.isPartialText(
						"//h2[@class='user-greeting']/span", "Welcome"));
				selenium.mouseOver("//h2[@class='user-greeting']/span");
				selenium.clickAt("//h2[@class='user-greeting']/span",
					RuntimeVariables.replace("Welcome"));
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Communities",
					RuntimeVariables.replace("Communities"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace("Wiki Wiki Page Community"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//tr[@class='portlet-section-body results-row']/td[1]/a",
					RuntimeVariables.replace("Public Pages - Live (1)"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("link=Wiki Page Test");
				selenium.clickAt("link=Wiki Page Test",
					RuntimeVariables.replace("Wiki Page Test"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//img[@alt='Manage Wikis']",
					RuntimeVariables.replace("Manage Wikis"));
				selenium.waitForPageToLoad("30000");

				boolean wikiNodePresent = selenium.isElementPresent(
						"//tr[4]/td[4]/ul/li/strong/span");

				if (!wikiNodePresent) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//ul[@class='lfr-component lfr-actions right ']/li/strong/span"));
				selenium.clickAt("//ul[@class='lfr-component lfr-actions right ']/li/strong/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:
			case 100:
				label = -1;
			}
		}
	}
}