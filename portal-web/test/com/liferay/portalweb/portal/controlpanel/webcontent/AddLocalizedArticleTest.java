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

package com.liferay.portalweb.portal.controlpanel.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddLocalizedArticleTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddLocalizedArticleTest extends BaseTestCase {
	public void testAddLocalizedArticle() throws Exception {
		selenium.clickAt("link=Web Content", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Web Content']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[2]/input[5]");
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
		Thread.sleep(5000);
		selenium.selectWindow("null");
		assertTrue(selenium.isElementPresent("link=Test Localized Structure"));
		assertTrue(selenium.isElementPresent("link=Test Localized Template"));
		selenium.type("_15_structure_el0_content",
			RuntimeVariables.replace("Hello World Page Name"));
		selenium.clickAt("//input[@type='checkbox']",
			RuntimeVariables.replace(""));
		selenium.type("_15_structure_el1_content",
			RuntimeVariables.replace("Hello World Page Description"));
		selenium.clickAt("//tr[2]/td/table/tbody/tr/td/fieldset/table[2]/tbody/tr/td[1]/input",
			RuntimeVariables.replace(""));
		selenium.type("_15_title",
			RuntimeVariables.replace("Hello World Localized Article"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value=\"Save\"]")) {
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

		selenium.select("_15_languageId",
			RuntimeVariables.replace("label=Chinese (China)"));
		Thread.sleep(5000);
		selenium.type("_15_structure_el0_content",
			RuntimeVariables.replace("???? Page Name"));
		selenium.type("_15_structure_el1_content",
			RuntimeVariables.replace("???? Page Description"));
		selenium.clickAt("//input[@value=\"Save and Approve\"]",
			RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertTrue(selenium.isElementPresent(
				"link=Hello World Localized Article"));
	}
}