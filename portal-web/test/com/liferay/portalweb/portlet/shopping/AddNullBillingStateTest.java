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
 * <a href="AddNullBillingStateTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddNullBillingStateTest extends BaseTestCase {
	public void testAddNullBillingState() throws Exception {
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

		selenium.click(RuntimeVariables.replace("link=Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Cart"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Checkout']"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_34_billingFirstName",
			RuntimeVariables.replace("Joe"));
		selenium.type("_34_billingFirstName", RuntimeVariables.replace("Joe"));
		selenium.typeKeys("_34_billingStreet",
			RuntimeVariables.replace("321321"));
		selenium.type("_34_billingStreet", RuntimeVariables.replace("321321"));
		selenium.typeKeys("_34_billingCity",
			RuntimeVariables.replace("Gotham Cit"));
		selenium.type("_34_billingCity", RuntimeVariables.replace("Gotham City"));
		selenium.select("_34_billingStateSel",
			RuntimeVariables.replace("label=Outside US"));
		selenium.typeKeys("_34_billingZip", RuntimeVariables.replace("90028"));
		selenium.type("_34_billingZip", RuntimeVariables.replace("90028"));
		selenium.typeKeys("_34_billingCountry", RuntimeVariables.replace("USA"));
		selenium.type("_34_billingCountry", RuntimeVariables.replace("USA"));
		selenium.typeKeys("_34_billingPhone",
			RuntimeVariables.replace("626-589-1453"));
		selenium.type("_34_billingPhone",
			RuntimeVariables.replace("626-589-1453"));
		selenium.select("_34_ccType", RuntimeVariables.replace("label=Visa"));
		selenium.typeKeys("_34_ccNumber",
			RuntimeVariables.replace("4111111111111111"));
		selenium.type("_34_ccNumber",
			RuntimeVariables.replace("4111111111111111"));
		selenium.select("_34_ccExpYear", RuntimeVariables.replace("label=2011"));
		selenium.typeKeys("_34_comments",
			RuntimeVariables.replace("Please take care of m order."));
		selenium.type("_34_comments",
			RuntimeVariables.replace("Please take care of my order."));
		selenium.click(RuntimeVariables.replace("//input[@value='Continue']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have entered invalid data. Please try again."));
		assertTrue(selenium.isTextPresent("Please enter a valid state."));
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
	}
}