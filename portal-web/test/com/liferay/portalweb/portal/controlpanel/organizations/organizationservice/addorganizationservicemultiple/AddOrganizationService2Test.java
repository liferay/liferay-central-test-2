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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationservice.addorganizationservicemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationService2Test extends BaseTestCase {
	public void testAddOrganizationService2() throws Exception {
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

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 2:
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Selenium"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Selenium"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//td[2]/a", RuntimeVariables.replace(""));
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
						if (selenium.isElementPresent(
									"//div[8]/div/fieldset/div[2]/div/span/span/button[1]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[8]/div/fieldset/div[2]/div/span/span/button[1]",
					RuntimeVariables.replace("Add Row"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_126_orgLaborTypeId3")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_126_orgLaborTypeId3",
					RuntimeVariables.replace("label=Training"));
				selenium.select("_126_sunOpen3",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_sunClose3",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_monOpen3",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_monClose3",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_tueOpen3",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_tueClose3",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_wedOpen3",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_wedClose3",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_thuOpen3",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_thuClose3",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_friOpen3",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_friClose3",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_satOpen3",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_satClose3",
					RuntimeVariables.replace("label=05:00"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//section/div/div/div/div[1]"));
				assertEquals("Training",
					selenium.getSelectedLabel("_126_orgLaborTypeId1"));

			case 100:
				label = -1;
			}
		}
	}
}