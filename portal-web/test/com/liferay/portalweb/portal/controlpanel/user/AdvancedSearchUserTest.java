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

package com.liferay.portalweb.portal.controlpanel.user;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AdvancedSearchUserTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchUserTest extends BaseTestCase {
	public void testAdvancedSearchUser() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Users")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
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
						if (selenium.isVisible("_125_firstName")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_125_firstName",
					RuntimeVariables.replace("Selen"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("selenium01"));
				assertTrue(selenium.isTextPresent("selenium02"));
				selenium.type("_125_firstName",
					RuntimeVariables.replace("Selen1"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("selenium01"));
				assertFalse(selenium.isTextPresent("selenium02"));
				selenium.type("_125_firstName", RuntimeVariables.replace(""));
				selenium.type("_125_middleName",
					RuntimeVariables.replace("lenn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("selenium01"));
				assertTrue(selenium.isTextPresent("selenium02"));
				selenium.type("_125_middleName",
					RuntimeVariables.replace("lenn1"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("selenium01"));
				assertFalse(selenium.isTextPresent("selenium02"));
				selenium.type("_125_middleName", RuntimeVariables.replace(""));
				selenium.type("_125_lastName", RuntimeVariables.replace("nium"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("selenium01"));
				assertTrue(selenium.isTextPresent("selenium02"));
				selenium.type("_125_lastName", RuntimeVariables.replace("nium1"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("selenium01"));
				assertFalse(selenium.isTextPresent("selenium02"));
				selenium.type("_125_lastName", RuntimeVariables.replace(""));
				selenium.type("_125_screenName",
					RuntimeVariables.replace("selenium"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("selenium01"));
				assertTrue(selenium.isTextPresent("selenium02"));
				selenium.type("_125_screenName",
					RuntimeVariables.replace("selenium1"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("selenium01"));
				assertFalse(selenium.isTextPresent("selenium02"));
				selenium.type("_125_screenName", RuntimeVariables.replace(""));
				selenium.type("_125_emailAddress",
					RuntimeVariables.replace("selenium.com"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("selenium01"));
				assertTrue(selenium.isTextPresent("selenium02"));
				selenium.type("_125_emailAddress",
					RuntimeVariables.replace("selenium.com1"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("selenium01"));
				assertFalse(selenium.isTextPresent("selenium02"));
				selenium.type("_125_emailAddress", RuntimeVariables.replace(""));

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