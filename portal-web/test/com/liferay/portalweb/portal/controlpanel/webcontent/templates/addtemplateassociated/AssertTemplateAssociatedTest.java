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

package com.liferay.portalweb.portal.controlpanel.webcontent.templates.addtemplateassociated;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertTemplateAssociatedTest extends BaseTestCase {
	public void testAssertTemplateAssociated() throws Exception {
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

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_15_changeStructureBtn")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click("_15_changeStructureBtn");
				assertTrue(selenium.getConfirmation()
								   .matches("^Selecting a new structure will change the available input fields and available templates[\\s\\S] Do you want to proceed[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.waitForPopUp("ChangeStructure",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=ChangeStructure");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				boolean templatePresentA = selenium.isElementPresent(
						"link=STRUCTUREID");

				if (templatePresentA) {
					label = 2;

					continue;
				}

				selenium.close();

			case 2:

				boolean templatePresentB = selenium.isElementPresent(
						"link=STRUCTUREID");

				if (!templatePresentB) {
					label = 3;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=STRUCTUREID")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click("link=STRUCTUREID");

			case 3:
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace(
						"Web Content Structure Name (Use Default)"),
					selenium.getText("_15_structureNameLabel"));
				assertEquals(RuntimeVariables.replace(
						"Web Content Template Name Associated"),
					selenium.getText("//label/a"));
				assertFalse(selenium.isElementPresent(
						"link=Web Content Template Name"));

			case 100:
				label = -1;
			}
		}
	}
}