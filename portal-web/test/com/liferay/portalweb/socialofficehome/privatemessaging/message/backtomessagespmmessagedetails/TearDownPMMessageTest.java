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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.backtomessagespmmessagedetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPMMessageTest extends BaseTestCase {
	public void testTearDownPMMessage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//nav/ul/li[contains(.,'Messages')]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//nav/ul/li[contains(.,'Messages')]/a/span",
					RuntimeVariables.replace("Messages"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Private Messaging"),
					selenium.getText("//span[@class='portlet-title-default']"));

				boolean message1Present = selenium.isElementPresent(
						"//td[1]/span/span/span/input[2]");

				if (!message1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Checkbox"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 2:

				boolean message2Present = selenium.isElementPresent(
						"//td[1]/span/span/span/input[2]");

				if (!message2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Checkbox"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 3:

				boolean message3Present = selenium.isElementPresent(
						"//td[1]/span/span/span/input[2]");

				if (!message3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Checkbox"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 4:

				boolean message4Present = selenium.isElementPresent(
						"//td[1]/span/span/span/input[2]");

				if (!message4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Checkbox"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 5:

				boolean message5Present = selenium.isElementPresent(
						"//td[1]/span/span/span/input[2]");

				if (!message5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Checkbox"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}