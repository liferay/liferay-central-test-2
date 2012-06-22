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

package com.liferay.portalweb.portal.controlpanel.users.useraddress.adduseraddressmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserAddress3Test extends BaseTestCase {
	public void testAddUserAddress3() throws Exception {
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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("selen01"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("User Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_125_addressesLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isPartialText("//a[@id='_125_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_125_addressesLink']",
			RuntimeVariables.replace("Addresses"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[2]/span/span/button[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[2]/span/span/button[1]",
			RuntimeVariables.replace("Add Row"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_125_addressStreet1_3']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_125_addressStreet1_3']",
			RuntimeVariables.replace("1220 Brea Canyon Rd"));
		selenium.select("//select[@id='_125_addressTypeId3']",
			RuntimeVariables.replace("label=Business"));
		selenium.type("//input[@id='_125_addressStreet2_3']",
			RuntimeVariables.replace("Ste 12"));
		selenium.type("//input[@id='_125_addressZip3']",
			RuntimeVariables.replace("91789"));
		selenium.type("//input[@id='_125_addressStreet3_3']",
			RuntimeVariables.replace("Walnut"));
		selenium.type("//input[@id='_125_addressCity3']",
			RuntimeVariables.replace("Los Angeles"));
		selenium.select("//select[@id='_125_addressCountryId3']",
			RuntimeVariables.replace("label=United States"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//select[@id='_125_addressRegionId3']",
							"California")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("//select[@id='_125_addressRegionId3']",
			RuntimeVariables.replace("label=California"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("1220 Brea Canyon Rd",
			selenium.getValue("//input[@id='_125_addressStreet1_2']"));
		assertEquals("Business",
			selenium.getSelectedLabel("//select[@id='_125_addressTypeId2']"));
		assertEquals("Ste 12",
			selenium.getValue("//input[@id='_125_addressStreet2_2']"));
		assertEquals("91789",
			selenium.getValue("//input[@id='_125_addressZip2']"));
		assertEquals("Walnut",
			selenium.getValue("//input[@id='_125_addressStreet3_2']"));
		assertEquals("Los Angeles",
			selenium.getValue("//input[@id='_125_addressCity2']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if ("United States".equals(selenium.getSelectedLabel(
								"//select[@id='_125_addressCountryId2']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_125_addressCountryId2']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if ("California".equals(selenium.getSelectedLabel(
								"//select[@id='_125_addressRegionId2']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_125_addressRegionId2']"));
	}
}