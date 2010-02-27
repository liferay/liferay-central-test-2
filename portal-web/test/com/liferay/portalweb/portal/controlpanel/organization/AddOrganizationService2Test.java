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
 * <a href="AddOrganizationService2Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationService2Test extends BaseTestCase {
	public void testAddOrganizationService2() throws Exception {
		selenium.clickAt("link=Organizations", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Organizations", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_126_keywords", RuntimeVariables.replace("selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Selenium", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("servicesLink", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@id='services']/fieldset/div[2]/div/span/a[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[@id='services']/fieldset/div[2]/div/span/a[1]",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_126_orgLaborTypeId2")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_126_orgLaborTypeId2",
			RuntimeVariables.replace("label=Administrative"));
		selenium.select("_126_sunOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_sunClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_monOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_monClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_tueOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_tueClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_wedOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_wedClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_thuOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_thuClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_friOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_friClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_satOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_satClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}