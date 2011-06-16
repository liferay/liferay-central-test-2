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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontenttemplatewcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentTemplateWCDTest extends BaseTestCase {
	public void testAddWCWebContentTemplateWCD() throws Exception {
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
		selenium.clickAt("//img[@alt='Add Web Content']",
			RuntimeVariables.replace("Add Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_15_title']",
			RuntimeVariables.replace("Web Content Name"));
		selenium.saveScreenShotAndSource();
		selenium.click("//fieldset/div/div/span/span/input");
		assertTrue(selenium.getConfirmation()
						   .matches("^Selecting a template will change the structure, available input fields, and available templates[\\s\\S] Do you want to proceed[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		selenium.waitForPopUp("template", RuntimeVariables.replace("30000"));
		selenium.selectWindow("template");
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//td[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.click("//td[1]/a");
		selenium.selectWindow("null");
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		selenium.type("//input[@id='page-name']",
			RuntimeVariables.replace("Web Content Page Name"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='page-description']",
			RuntimeVariables.replace("Web Content Page Description"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText("//fieldset/div/div/div/div/span",
				"Test Localized Structure"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Web Content Page Name"),
			selenium.getText("//td[1]"));
		assertEquals(RuntimeVariables.replace("Web Content Page Description"),
			selenium.getText("//td[2]"));
	}
}