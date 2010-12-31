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

package com.liferay.portalweb.portlet.shopping.order.checkoutorderbillingstateinvalid;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CheckoutOrderBillingStateInvalidTest extends BaseTestCase {
	public void testCheckoutOrderBillingStateInvalid()
		throws Exception {
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

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Shopping Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Cart", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Checkout']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.type("_34_billingStreet",
					RuntimeVariables.replace("1234 Sesame Street"));
				selenium.saveScreenShotAndSource();
				selenium.type("_34_billingCity",
					RuntimeVariables.replace("Gotham City"));
				selenium.saveScreenShotAndSource();
				selenium.select("_34_billingStateSel",
					RuntimeVariables.replace("label=Outside US"));
				selenium.type("_34_billingZip",
					RuntimeVariables.replace("90028"));
				selenium.saveScreenShotAndSource();
				selenium.type("_34_billingCountry",
					RuntimeVariables.replace("USA"));
				selenium.saveScreenShotAndSource();
				selenium.type("_34_billingPhone",
					RuntimeVariables.replace("626-589-1453"));
				selenium.saveScreenShotAndSource();

				boolean sameAsBillingChecked = selenium.isChecked(
						"_34_shipToBillingCheckbox");

				if (sameAsBillingChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_34_shipToBillingCheckbox",
					RuntimeVariables.replace(""));

			case 2:
				selenium.select("_34_ccType",
					RuntimeVariables.replace("label=Visa"));
				selenium.type("_34_ccNumber",
					RuntimeVariables.replace("4111111111111111"));
				selenium.saveScreenShotAndSource();
				selenium.select("_34_ccExpYear",
					RuntimeVariables.replace("label=2011"));
				selenium.type("_34_comments",
					RuntimeVariables.replace("Please take care of my order."));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Continue']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("outside-us"),
					selenium.getText("//tr[7]/td[2]"));
				assertEquals(RuntimeVariables.replace("outside-us"),
					selenium.getText("//td[2]/table/tbody/tr[7]/td[2]"));

			case 100:
				label = -1;
			}
		}
	}
}