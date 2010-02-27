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
 * <a href="AddOrganizationServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationServiceTest extends BaseTestCase {
	public void testAddOrganizationService() throws Exception {
		selenium.clickAt("link=Organizations", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Organizations", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_126_keywords", RuntimeVariables.replace("selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Selenium")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Selenium", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("servicesLink")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("servicesLink", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_126_orgLaborTypeId0")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_126_orgLaborTypeId0",
			RuntimeVariables.replace("label=Training"));
		selenium.select("_126_sunOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_sunClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_monOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_monClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_tueOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_tueClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_wedOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_wedClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_thuOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_thuClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_friOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_friClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_satOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_satClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}