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

package com.liferay.portalweb.portal.dbupgrade.sampledata600.shopping.order;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CheckOutOrderTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CheckOutOrderTest extends BaseTestCase {
	public void testCheckOutOrder() throws Exception {
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

		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_134_name",
			RuntimeVariables.replace("Shopping Order Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Shopping Order Community",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Cart", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Checkout']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_34_billingStreet",
			RuntimeVariables.replace("1234 Sesame Street"));
		selenium.type("_34_billingCity", RuntimeVariables.replace("Gotham City"));
		selenium.select("_34_billingStateSel",
			RuntimeVariables.replace("label=California"));
		selenium.type("_34_billingZip", RuntimeVariables.replace("90028"));
		selenium.type("_34_billingCountry", RuntimeVariables.replace("USA"));
		selenium.type("_34_billingPhone",
			RuntimeVariables.replace("626-589-1453"));
		selenium.click("_34_shipToBillingCheckbox");
		selenium.select("_34_ccType", RuntimeVariables.replace("label=Visa"));
		selenium.type("_34_ccNumber",
			RuntimeVariables.replace("4111111111111111"));
		selenium.select("_34_ccExpYear", RuntimeVariables.replace("label=2011"));
		selenium.type("_34_comments",
			RuntimeVariables.replace("Please take care of my order."));
		selenium.clickAt("//input[@value='Continue']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1234 Sesame Street"),
			selenium.getText("//tr[5]/td[2]"));
		selenium.click(RuntimeVariables.replace("//input[@value='Finished']"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Thank you for your purchase."),
			selenium.getText("//div[2]/div/div/div"));
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

		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_134_name",
			RuntimeVariables.replace("Shopping Order Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Shopping Order Community",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Orders", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Order Number"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//form",
				"Please take care of my order."));
	}
}