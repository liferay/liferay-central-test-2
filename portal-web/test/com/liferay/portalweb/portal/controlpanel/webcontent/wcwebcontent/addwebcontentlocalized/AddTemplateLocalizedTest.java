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
public class AddTemplateLocalizedTest extends BaseTestCase {
	public void testAddTemplateLocalized() throws Exception {
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
				selenium.clickAt("link=Templates", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Add Template']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.type("_15_newTemplateId",
					RuntimeVariables.replace("LOCALIZED"));
				selenium.saveScreenShotAndSource();
				selenium.type("_15_name",
					RuntimeVariables.replace("Test Localized Template"));
				selenium.saveScreenShotAndSource();
				selenium.type("_15_description",
					RuntimeVariables.replace(
						"This is a test localized template."));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace(""));
				selenium.waitForPopUp("structure",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("structure");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				boolean localizedPresentA = selenium.isElementPresent(
						"link=LOCALIZED");

				if (localizedPresentA) {
					label = 2;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();

			case 2:

				boolean localizedPresentB = selenium.isElementPresent(
						"link=LOCALIZED");

				if (!localizedPresentB) {
					label = 3;

					continue;
				}

				selenium.click("link=LOCALIZED");
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();

			case 3:
				Thread.sleep(5000);
				assertTrue(selenium.isElementPresent(
						"link=Test Localized Structure"));
				selenium.type("_15_xsl",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\controlpanel\\webcontent\\wcwebcontent\\addwebcontentlocalized\\dependencies\\LocalizedTemplate.htm"));
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isTextPresent(
						"Your request completed successfully."));
				assertTrue(selenium.isElementPresent("link=LOCALIZED"));
				assertTrue(selenium.isTextPresent("Test Localized Template"));

			case 100:
				label = -1;
			}
		}
	}
}