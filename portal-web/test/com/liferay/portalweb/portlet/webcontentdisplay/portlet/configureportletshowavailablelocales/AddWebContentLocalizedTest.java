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

package com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletshowavailablelocales;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddWebContentLocalizedTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddWebContentLocalizedTest extends BaseTestCase {
	public void testAddWebContentLocalized() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Web Content']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.click("_15_selectTemplateBtn");
				assertTrue(selenium.getConfirmation()
								   .matches("^Selecting a template will change the structure, available input fields, and available templates[\\s\\S] Do you want to proceed[\\s\\S]$"));
				selenium.waitForPopUp("template",
					RuntimeVariables.replace("30000"));
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
				assertTrue(selenium.isTextPresent("Test Localized Structure"));
				assertTrue(selenium.isElementPresent(
						"link=Test Localized Template"));
				selenium.click("//input[@value='Edit']");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Stop Editing']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(
					"//ul[@id='_15_structureTree']/li[1]/span[2]/div/div[4]/input");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("page-name")
												.equals(selenium.getValue(
										"_15_fieldLabel"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_15_localizedCheckbox")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean localized1Checked = selenium.isChecked(
						"_15_localizedCheckbox");

				if (localized1Checked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_15_localizedCheckbox",
					RuntimeVariables.replace(""));

			case 2:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				selenium.click(
					"//ul[@id='_15_structureTree']/li[2]/span[2]/div/div[4]/input[1]");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("page-description")
												.equals(selenium.getValue(
										"_15_fieldLabel"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_15_localizedCheckbox")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean localized2Checked = selenium.isChecked(
						"_15_localizedCheckbox");

				if (localized2Checked) {
					label = 3;

					continue;
				}

				selenium.clickAt("_15_localizedCheckbox",
					RuntimeVariables.replace(""));

			case 3:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				selenium.click("//input[@value='Stop Editing']");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Edit']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				Thread.sleep(5000);
				selenium.type("_15_text",
					RuntimeVariables.replace("Hello World Page Name"));
				selenium.type("//li[2]/span[2]/div/div[2]/span/span/span/input",
					RuntimeVariables.replace("Hello World Page Description"));
				selenium.type("_15_title",
					RuntimeVariables.replace("Hello World Localized Article"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value=\"Save and Continue\"]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value=\"Save and Continue\"]",
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
				Thread.sleep(5000);
				selenium.select("_15_languageId",
					RuntimeVariables.replace("label=Chinese (China)"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to switch the language view[\\s\\S] Changes made to this language will not be saved.$"));
				Thread.sleep(5000);
				assertEquals("Chinese (China)",
					selenium.getSelectedLabel("_15_languageId"));
				Thread.sleep(5000);
				selenium.type("_15_text",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Name"));
				selenium.type("//li[2]/span[2]/div/div[2]/span/span/span/input",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Description"));
				selenium.clickAt("//input[@value=\"Save and Approve\"]",
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

			case 100:
				label = -1;
			}
		}
	}
}