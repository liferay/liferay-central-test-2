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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewwcdwebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
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

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Add Web Content']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				selenium.click("_15_selectTemplateBtn");
				assertTrue(selenium.getConfirmation()
								   .matches("^Selecting a template will change the structure, available input fields, and available templates[\\s\\S] Do you want to proceed[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.waitForPopUp("template",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("template");
				selenium.saveScreenShotAndSource();
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

				selenium.saveScreenShotAndSource();
				selenium.click("link=LOCALIZED");
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();
				Thread.sleep(10000);
				assertTrue(selenium.isTextPresent("Test Localized Structure"));
				assertTrue(selenium.isElementPresent(
						"link=Test Localized Template"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("")
												.equals(selenium.getValue(
										"page-name"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//span[2]/div/span/span/span/input[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean localized1Checked = selenium.isChecked(
						"//span[2]/div/span/span/span/input[2]");

				if (localized1Checked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[2]/div/span/span/span/input[2]",
					RuntimeVariables.replace(""));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("")
												.equals(selenium.getValue(
										"page-description"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//li[2]/span[2]/div/span/span/span/input[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean localized2Checked = selenium.isChecked(
						"//li[2]/span[2]/div/span/span/span/input[2]");

				if (localized2Checked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li[2]/span[2]/div/span/span/span/input[2]",
					RuntimeVariables.replace(""));

			case 3:
				selenium.type("page-name",
					RuntimeVariables.replace("Hello World Page Name"));
				selenium.saveScreenShotAndSource();
				selenium.type("page-description",
					RuntimeVariables.replace("Hello World Page Description"));
				selenium.saveScreenShotAndSource();
				selenium.type("_15_title",
					RuntimeVariables.replace("Hello World Localized Article"));
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@value='Save as Draft']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save as Draft']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//section/div/div/div/div"));
				selenium.select("_15_languageId",
					RuntimeVariables.replace("label=Chinese (China)"));
				Thread.sleep(5000);
				assertEquals("Chinese (China)",
					selenium.getSelectedLabel("_15_languageId"));
				Thread.sleep(5000);
				selenium.type("page-name",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Name"));
				selenium.saveScreenShotAndSource();
				selenium.type("page-description",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Description"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isTextPresent(
									"Your request completed successfully.")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isTextPresent(
						"Your request completed successfully."));

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

				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isElementPresent(
						"link=Hello World Localized Article"));

			case 100:
				label = -1;
			}
		}
	}
}