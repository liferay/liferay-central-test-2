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

package com.liferay.portalweb.plugins.wsrp.helloworld.addproducerhw;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddProducerHWTest extends BaseTestCase {
	public void testAddProducerHW() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
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
		selenium.clickAt("link=WSRP", RuntimeVariables.replace("WSRP"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Producers", RuntimeVariables.replace("Producers"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//input[@value='Add Producer']",
			RuntimeVariables.replace("Add Producer"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("_1_WAR_wsrpportlet_name",
			RuntimeVariables.replace("Hello World Producer Name"));
		selenium.addSelection("_1_WAR_wsrpportlet_availablePortletIds",
			RuntimeVariables.replace("label=Hello World"));
		selenium.clickAt("//button[2]",
			RuntimeVariables.replace("Move Left Arrow"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Hello World")
										.equals(selenium.getText(
								"_1_WAR_wsrpportlet_currentPortletIds"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Hello World"),
			selenium.getText("_1_WAR_wsrpportlet_currentPortletIds"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Hello World Producer Name"),
			selenium.getText("//td[1]/a"));
	}
}