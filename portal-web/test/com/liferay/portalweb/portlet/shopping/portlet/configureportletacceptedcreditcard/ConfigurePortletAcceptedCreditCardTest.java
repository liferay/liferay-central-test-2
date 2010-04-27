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

package com.liferay.portalweb.portlet.shopping.portlet.configureportletacceptedcreditcard;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ConfigurePortletAcceptedCreditCardTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAcceptedCreditCardTest extends BaseTestCase {
	public void testConfigurePortletAcceptedCreditCard()
		throws Exception {
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

		selenium.clickAt("link=Shopping Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Cart", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//img[@alt='visa']"));
		assertTrue(selenium.isElementPresent("//img[@alt='discover']"));
		assertTrue(selenium.isElementPresent("//img[@alt='mastercard']"));
		assertTrue(selenium.isElementPresent("//img[@alt='amex']"));
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

		selenium.clickAt("link=Shopping Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Configuration", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.addSelection("_86_current_cc_types",
			RuntimeVariables.replace("label=MasterCard"));
		selenium.clickAt("//div/table/tbody/tr/td[2]/a[1]/img",
			RuntimeVariables.replace(""));
		selenium.addSelection("_86_current_cc_types",
			RuntimeVariables.replace("label=American Express"));
		selenium.clickAt("//div/table/tbody/tr/td[2]/a[1]/img",
			RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@id='p_p_id_86_']/div/div"));
		assertEquals(RuntimeVariables.replace("Visa Discover"),
			selenium.getText("_86_current_cc_types"));
		assertEquals(RuntimeVariables.replace("MasterCard American Express"),
			selenium.getText("_86_available_cc_types"));
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

		selenium.clickAt("link=Shopping Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Cart", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//img[@alt='visa']"));
		assertTrue(selenium.isElementPresent("//img[@alt='discover']"));
		assertFalse(selenium.isElementPresent("//img[@alt='mastercard']"));
		assertFalse(selenium.isElementPresent("//img[@alt='amex']"));
	}
}