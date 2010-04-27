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

package com.liferay.portalweb.portlet.shopping.portlet.configureportletinsurancepercentage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="TearDownConfigurationTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TearDownConfigurationTest extends BaseTestCase {
	public void testTearDownConfiguration() throws Exception {
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
						if (selenium.isElementPresent("link=Shopping Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Shopping Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Configuration",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Payment Settings",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_86_current_cc_types")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean amexAvailable = selenium.isPartialText("_86_available_cc_types",
						"American Express");

				if (!amexAvailable) {
					label = 2;

					continue;
				}

				selenium.addSelection("_86_available_cc_types",
					RuntimeVariables.replace("label=American Express"));
				selenium.clickAt("//div/table/tbody/tr/td[2]/a[2]/img",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("_86_current_cc_types",
									"American Express")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_86_current_cc_types")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean discoverAvailable = selenium.isPartialText("_86_available_cc_types",
						"Discover");

				if (!discoverAvailable) {
					label = 3;

					continue;
				}

				selenium.addSelection("_86_available_cc_types",
					RuntimeVariables.replace("label=Discover"));
				selenium.clickAt("//div/table/tbody/tr/td[2]/a[2]/img",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("_86_current_cc_types",
									"Discover")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 3:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_86_current_cc_types")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean masterCardAvailable = selenium.isPartialText("_86_available_cc_types",
						"MasterCard");

				if (!masterCardAvailable) {
					label = 4;

					continue;
				}

				selenium.addSelection("_86_available_cc_types",
					RuntimeVariables.replace("label=MasterCard"));
				selenium.clickAt("//div/table/tbody/tr/td[2]/a[2]/img",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("_86_current_cc_types",
									"MasterCard")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 4:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_86_current_cc_types")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean visaAvailable = selenium.isPartialText("_86_available_cc_types",
						"Visa");

				if (!visaAvailable) {
					label = 5;

					continue;
				}

				selenium.addSelection("_86_available_cc_types",
					RuntimeVariables.replace("label=Visa"));
				selenium.clickAt("//div/table/tbody/tr/td[2]/a[2]/img",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("_86_current_cc_types",
									"Visa")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 5:
				selenium.select("_86_currencyId",
					RuntimeVariables.replace("label=USD"));
				selenium.select("_86_taxState",
					RuntimeVariables.replace("label=California"));
				selenium.type("_86_taxRate", RuntimeVariables.replace(""));
				selenium.type("_86_minOrder", RuntimeVariables.replace(""));
				selenium.clickAt("//div[3]/span[1]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Shipping Calculation",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.select("_86_shippingFormula",
					RuntimeVariables.replace("label=Flat Amount"));
				selenium.type("_86_shipping0", RuntimeVariables.replace(""));
				selenium.type("_86_shipping1", RuntimeVariables.replace(""));
				selenium.type("_86_shipping2", RuntimeVariables.replace(""));
				selenium.type("_86_shipping3", RuntimeVariables.replace(""));
				selenium.type("_86_shipping4", RuntimeVariables.replace(""));
				selenium.clickAt("//div[2]/span[1]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Insurance Calculation",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.select("_86_insuranceFormula",
					RuntimeVariables.replace("label=Flat Amount"));
				selenium.type("_86_insurance0", RuntimeVariables.replace(""));
				selenium.type("_86_insurance1", RuntimeVariables.replace(""));
				selenium.type("_86_insurance2", RuntimeVariables.replace(""));
				selenium.type("_86_insurance3", RuntimeVariables.replace(""));
				selenium.type("_86_insurance4", RuntimeVariables.replace(""));
				selenium.clickAt("//div[2]/span[1]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}