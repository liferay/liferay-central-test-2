/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewlocalizewebcontenttemplatelanguagewcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizeWCWebContentTemplateWCDTest extends BaseTestCase {
	public void testLocalizeWCWebContentTemplateWCD() throws Exception {
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
						if (selenium.isElementPresent(
									"link=Web Content Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//img[@alt='Edit Web Content']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//img[@alt='Edit Web Content']",
					RuntimeVariables.replace("Edit Web Content"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Web Content Name"),
					selenium.getText("//h1[@class='header-title']"));
				assertTrue(selenium.isPartialText(
						"//fieldset/div/div/div/div/span",
						"Test Localized Structure"));
				assertTrue(selenium.isElementPresent(
						"//span[2]/div/span/span/span/input[2]"));

				boolean localizedPageNameChecked = selenium.isChecked(
						"//span[2]/div/span/span/span/input[2]");

				if (localizedPageNameChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[2]/div/span/span/span/input[2]",
					RuntimeVariables.replace(""));

			case 2:
				assertTrue(selenium.isElementPresent(
						"//li[2]/span[2]/div/span/span/span/input[2]"));

				boolean localizedPageDescriptionChecked = selenium.isChecked(
						"//li[2]/span[2]/div/span/span/span/input[2]");

				if (localizedPageDescriptionChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li[2]/span[2]/div/span/span/span/input[2]",
					RuntimeVariables.replace(""));

			case 3:
				assertTrue(selenium.isChecked(
						"//span[2]/div/span/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"//li[2]/span[2]/div/span/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save as Draft']",
					RuntimeVariables.replace("Save as Draft"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Draft"),
					selenium.getText("//span[3]/strong"));
				assertEquals("Web Content Page Name",
					selenium.getValue("//input[@id='page-name']"));
				assertEquals("Web Content Page Description",
					selenium.getValue("//input[@id='page-description']"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//select[@id='_15_languageIdSelect']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("//select[@id='_15_languageIdSelect']",
					RuntimeVariables.replace("label=Chinese (China)"));
				Thread.sleep(5000);
				selenium.type("//input[@id='page-name']",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Name"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='page-description']",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Description"));
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Web Content Page Name"),
					selenium.getText("//td[1]"));
				assertEquals(RuntimeVariables.replace(
						"Web Content Page Description"),
					selenium.getText("//td[2]"));

			case 100:
				label = -1;
			}
		}
	}
}