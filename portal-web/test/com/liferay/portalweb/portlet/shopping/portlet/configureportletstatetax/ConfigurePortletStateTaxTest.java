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

package com.liferay.portalweb.portlet.shopping.portlet.configureportletstatetax;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ConfigurePortletStateTaxTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletStateTaxTest extends BaseTestCase {
	public void testConfigurePortletStateTax() throws Exception {
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

			case 2:
				selenium.clickAt("link=Configuration",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_86_taxRate", RuntimeVariables.replace("7.750%"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@id='p_p_id_86_']/div/div"));
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
				selenium.clickAt("link=Cart", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Checkout']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_34_billingStreet",
					RuntimeVariables.replace("1234 Sesame Street"));
				selenium.type("_34_billingCity",
					RuntimeVariables.replace("Gotham City"));
				selenium.select("_34_billingStateSel",
					RuntimeVariables.replace("label=California"));
				selenium.type("_34_billingZip",
					RuntimeVariables.replace("90028"));
				selenium.type("_34_billingCountry",
					RuntimeVariables.replace("USA"));
				selenium.type("_34_billingPhone",
					RuntimeVariables.replace("626-589-1453"));

				boolean sameAsBillingChecked = selenium.isChecked(
						"_34_shipToBillingCheckbox");

				if (sameAsBillingChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("_34_shipToBillingCheckbox",
					RuntimeVariables.replace(""));

			case 3:
				assertTrue(selenium.isChecked("_34_shipToBillingCheckbox"));
				selenium.select("_34_ccType",
					RuntimeVariables.replace("label=Visa"));
				selenium.type("_34_ccNumber",
					RuntimeVariables.replace("4111111111111111"));
				selenium.select("_34_ccExpYear",
					RuntimeVariables.replace("label=2011"));
				selenium.type("_34_ccVerNumber", RuntimeVariables.replace("526"));
				selenium.clickAt("//input[@value='Continue']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("$0.77"),
					selenium.getText("//table[3]/tbody/tr[2]/td[2]"));
				assertEquals(RuntimeVariables.replace("$10.76"),
					selenium.getText("//table[3]/tbody/tr[4]/td[2]"));

			case 100:
				label = -1;
			}
		}
	}
}