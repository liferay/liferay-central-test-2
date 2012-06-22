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

package com.liferay.portalweb.portal.controlpanel.settings.address.deletesettingsaddress;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsAddressTest extends BaseTestCase {
	public void testAddSettingsAddress() throws Exception {
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
		selenium.clickAt("link=Portal Settings",
			RuntimeVariables.replace("Portal Settings"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_130_addressesLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[@id='_130_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.type("//input[@id='_130_addressStreet1_0']",
			RuntimeVariables.replace("123. Liferay Ln."));
		selenium.type("//input[@id='_130_addressCity0']",
			RuntimeVariables.replace("Rays of Light"));
		selenium.type("//input[@id='_130_addressZip0']",
			RuntimeVariables.replace("12345"));
		selenium.select("//select[@id='_130_addressCountryId0']",
			RuntimeVariables.replace("label=United States"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//select[@id='_130_addressRegionId0']",
							"California")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("//select[@id='_130_addressRegionId0']",
			RuntimeVariables.replace("label=California"));
		selenium.select("//select[@id='_130_addressTypeId0']",
			RuntimeVariables.replace("label=Billing"));
		selenium.clickAt("//input[@id='_130_addressMailing0Checkbox']",
			RuntimeVariables.replace("Mailing Checkbox"));
		selenium.clickAt("//input[@id='_130_addressPrimary0']",
			RuntimeVariables.replace("Primary Button"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_130_addressStreet1_0']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("123. Liferay Ln.",
			selenium.getValue("//input[@id='_130_addressStreet1_0']"));
		assertEquals("Rays of Light",
			selenium.getValue("//input[@id='_130_addressCity0']"));
		assertEquals("12345",
			selenium.getValue("//input[@id='_130_addressZip0']"));
		assertEquals("Billing",
			selenium.getSelectedLabel("//select[@id='_130_addressTypeId0']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_130_addressCountryId0']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_130_addressRegionId0']"));
	}
}