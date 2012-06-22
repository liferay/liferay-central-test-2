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
public class AddUserAddress2Test extends BaseTestCase {
	public void testAddUserAddress2() throws Exception {
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
				if (selenium.isVisible("//div[2]/div/span/span/button[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[2]/div/span/span/button[1]",
			RuntimeVariables.replace("Add Row"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_125_addressStreet1_2']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_125_addressStreet1_2']",
			RuntimeVariables.replace("123 Lets"));
		selenium.select("//select[@id='_125_addressTypeId2']",
			RuntimeVariables.replace("label=Other"));
		selenium.type("//input[@id='_125_addressStreet2_2']",
			RuntimeVariables.replace("897 Hope"));
		selenium.type("//input[@id='_125_addressZip2']",
			RuntimeVariables.replace("00000"));
		selenium.type("//input[@id='_125_addressStreet3_2']",
			RuntimeVariables.replace("7896 This"));
		selenium.type("//input[@id='_125_addressCity2']",
			RuntimeVariables.replace("Works"));
		selenium.select("//select[@id='_125_addressCountryId2']",
			RuntimeVariables.replace("label=Canada"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//select[@id='_125_addressRegionId2']", "Ontario")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("//select[@id='_125_addressRegionId2']",
			RuntimeVariables.replace("label=Ontario"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("123 Lets",
			selenium.getValue("//input[@id='_125_addressStreet1_1']"));
		assertEquals("Other",
			selenium.getSelectedLabel("//select[@id='_125_addressTypeId1']"));
		assertEquals("897 Hope",
			selenium.getValue("//input[@id='_125_addressStreet2_1']"));
		assertEquals("00000",
			selenium.getValue("//input[@id='_125_addressZip1']"));
		assertEquals("7896 This",
			selenium.getValue("//input[@id='_125_addressStreet3_1']"));
		assertEquals("Works",
			selenium.getValue("//input[@id='_125_addressCity1']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if ("Canada".equals(selenium.getSelectedLabel(
								"//select[@id='_125_addressCountryId1']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("Canada",
			selenium.getSelectedLabel("//select[@id='_125_addressCountryId1']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if ("Ontario".equals(selenium.getSelectedLabel(
								"//select[@id='_125_addressRegionId1']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("Ontario",
			selenium.getSelectedLabel("//select[@id='_125_addressRegionId1']"));
	}
}