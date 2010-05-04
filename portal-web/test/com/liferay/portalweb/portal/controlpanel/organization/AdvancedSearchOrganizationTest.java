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

package com.liferay.portalweb.portal.controlpanel.organization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AdvancedSearchOrganizationTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchOrganizationTest extends BaseTestCase {
	public void testAdvancedSearchOrganization() throws Exception {
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
				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean AvancedPresent = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!AvancedPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_126_name")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_126_name", RuntimeVariables.replace("Selenium"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Selenium"));
				selenium.type("_126_name", RuntimeVariables.replace("Selenium1"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Selenium"));
				selenium.type("_126_name", RuntimeVariables.replace(""));
				selenium.type("_126_street",
					RuntimeVariables.replace("12345 Selenium St"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Selenium"));
				selenium.type("_126_street",
					RuntimeVariables.replace("12345 Selenium Street"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Selenium"));
				selenium.type("_126_street", RuntimeVariables.replace(""));
				selenium.type("_126_city",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Selenium"));
				selenium.type("_126_city",
					RuntimeVariables.replace("Diamond Bars"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Selenium"));
				selenium.type("_126_city", RuntimeVariables.replace(""));
				selenium.type("_126_zip", RuntimeVariables.replace("41111"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Selenium"));
				selenium.type("_126_zip", RuntimeVariables.replace("411111"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Selenium"));
				selenium.type("_126_zip", RuntimeVariables.replace(""));
				selenium.select("_126_type",
					RuntimeVariables.replace("label=Regular Organization"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Selenium"));
				selenium.select("_126_type",
					RuntimeVariables.replace("label=Location"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Selenium"));
				selenium.select("_126_type",
					RuntimeVariables.replace("label=Any"));
				Thread.sleep(5000);
				selenium.select("_126_countryId",
					RuntimeVariables.replace("label=United States"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Selenium"));
				Thread.sleep(5000);
				selenium.select("_126_countryId",
					RuntimeVariables.replace("label=Turkey"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Selenium"));
				Thread.sleep(5000);
				selenium.select("_126_countryId",
					RuntimeVariables.replace("label=United States"));
				Thread.sleep(5000);
				selenium.select("_126_regionId",
					RuntimeVariables.replace("label=California"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Selenium"));
				Thread.sleep(5000);
				selenium.select("_126_regionId",
					RuntimeVariables.replace("label=Wyoming"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Diamond Bar"));
				Thread.sleep(5000);
				selenium.select("_126_countryId",
					RuntimeVariables.replace("label="));

				boolean BasicPresent = selenium.isVisible("link=\u00ab Basic");

				if (!BasicPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 3:
			case 100:
				label = -1;
			}
		}
	}
}