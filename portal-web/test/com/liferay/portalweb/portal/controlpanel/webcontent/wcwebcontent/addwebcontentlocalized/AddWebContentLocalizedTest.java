/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWebContentLocalizedTest extends BaseTestCase {
	public void testAddWebContentLocalized() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Web Content']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.click("//fieldset/div/div/span/span/input");
		assertTrue(selenium.getConfirmation()
						   .matches("^Selecting a template will change the structure, available input fields, and available templates[\\s\\S] Do you want to proceed[\\s\\S]$"));
		selenium.waitForPopUp("template", RuntimeVariables.replace("30000"));
		selenium.selectWindow("template");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=LOCALIZED")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("link=LOCALIZED");
		selenium.selectWindow("null");
		Thread.sleep(5000);
		assertTrue(selenium.isPartialText("//fieldset/div/div/div/div/span",
				"Test Localized Structure"));
		assertTrue(selenium.isElementPresent("link=Test Localized Template"));
		selenium.clickAt("_15_editStructureBtn",
			RuntimeVariables.replace("Edit"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[4]/span[2]/span/input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[4]/span[2]/span/input",
			RuntimeVariables.replace("Edit Options"));
		assertFalse(selenium.isChecked("_15_localizedCheckbox"));
		selenium.clickAt("_15_localizedCheckbox", RuntimeVariables.replace(""));
		assertTrue(selenium.isChecked("_15_localizedCheckbox"));
		selenium.clickAt("_15_save", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Your request processed successfully.")
										.equals(selenium.getText(
								"_15_journalMessage"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("_15_journalMessage"));
		selenium.clickAt("_15_close", RuntimeVariables.replace("Close"));
		Thread.sleep(5000);
		selenium.clickAt("//li[2]/span[2]/div/div[4]/span[2]/span/input",
			RuntimeVariables.replace("Edit Options"));
		assertFalse(selenium.isChecked("_15_localizedCheckbox"));
		selenium.clickAt("_15_localizedCheckbox", RuntimeVariables.replace(""));
		assertTrue(selenium.isChecked("_15_localizedCheckbox"));
		selenium.clickAt("_15_save", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Your request processed successfully.")
										.equals(selenium.getText(
								"_15_journalMessage"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("_15_journalMessage"));
		selenium.clickAt("_15_close", RuntimeVariables.replace("Close"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_15_saveStructureBtn")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("_15_saveStructureBtn",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/button[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div/button[1]", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Your request processed successfully.")
										.equals(selenium.getText(
								"_15_saveStructureMessage"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("_15_saveStructureMessage"));
		selenium.clickAt("closethick", RuntimeVariables.replace(""));
		selenium.clickAt("_15_editStructureBtn",
			RuntimeVariables.replace("Stop Editing"));
		Thread.sleep(5000);
		selenium.type("page-name",
			RuntimeVariables.replace("Hello World Page Name"));
		selenium.type("page-description",
			RuntimeVariables.replace("Hello World Page Description"));
		selenium.type("_15_title",
			RuntimeVariables.replace("Hello World Localized Article"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save as Draft']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isTextPresent(
							"Your request processed successfully.")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_languageId")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_15_languageId",
			RuntimeVariables.replace("label=Chinese (China)"));
		Thread.sleep(5000);
		selenium.type("page-name",
			RuntimeVariables.replace("\u4e16\u754c\u60a8\u597d Page Name"));
		selenium.type("page-description",
			RuntimeVariables.replace(
				"\u4e16\u754c\u60a8\u597d Page Description"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isTextPresent(
							"Your request processed successfully.")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Hello World Localized Article")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isElementPresent(
				"link=Hello World Localized Article"));
	}
}