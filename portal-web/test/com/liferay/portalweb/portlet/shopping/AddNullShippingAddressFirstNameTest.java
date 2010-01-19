/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portalweb.portlet.shopping;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddNullShippingAddressFirstNameTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddNullShippingAddressFirstNameTest extends BaseTestCase {
	public void testAddNullShippingAddressFirstName() throws Exception {
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
		selenium.type("_34_shippingFirstName", RuntimeVariables.replace(""));
		selenium.type("_34_shippingStreet",
			RuntimeVariables.replace("1234 Sesame Street"));
		selenium.type("_34_shippingCity",
			RuntimeVariables.replace("Gotham City"));
		selenium.select("_34_shippingStateSel",
			RuntimeVariables.replace("label=California"));
		selenium.type("_34_shippingZip", RuntimeVariables.replace("90028"));
		selenium.type("_34_shippingCountry", RuntimeVariables.replace("USA"));
		selenium.type("_34_shippingPhone",
			RuntimeVariables.replace("626-589-1453"));
		selenium.select("_34_ccType", RuntimeVariables.replace("label=Visa"));
		selenium.type("_34_ccNumber",
			RuntimeVariables.replace("4111111111111111"));
		selenium.select("_34_ccExpYear", RuntimeVariables.replace("label=2011"));
		selenium.type("_34_comments",
			RuntimeVariables.replace("Please take care of my order."));
		selenium.clickAt("//input[@value='Continue']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have entered invalid data. Please try again."));
		assertTrue(selenium.isTextPresent("Please enter a valid first name."));
	}
}