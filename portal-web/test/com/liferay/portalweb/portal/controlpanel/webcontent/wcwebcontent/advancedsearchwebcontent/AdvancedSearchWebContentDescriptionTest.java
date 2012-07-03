/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.advancedsearchwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchWebContentDescriptionTest extends BaseTestCase {
	public void testAdvancedSearchWebContentDescription()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean advancedVisible = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace("Advanced \u00bb"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='_15_description']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_15_description']",
					RuntimeVariables.replace("web content description"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.type("//input[@id='_15_description']",
					RuntimeVariables.replace(""));
				selenium.type("//input[@id='_15_description']",
					RuntimeVariables.replace("web1 content1 description1"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.type("//input[@id='_15_description']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.isElementNotPresent("link=Web Content Name"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));

			case 100:
				label = -1;
			}
		}
	}
}