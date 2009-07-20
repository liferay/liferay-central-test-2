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

public class AddItemTest extends BaseTestCase {
	public void testAddItem() throws Exception {
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
		selenium.click(RuntimeVariables.replace("//td[1]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Add Item']"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_34_sku", RuntimeVariables.replace("1111"));
		selenium.type("_34_sku", RuntimeVariables.replace("1111"));
		selenium.typeKeys("_34_name",
			RuntimeVariables.replace("Jona Lons - Wheel"));
		selenium.type("_34_name", RuntimeVariables.replace("Jona Lyons - Wheel"));
		selenium.typeKeys("_34_description",
			RuntimeVariables.replace("Sounds like: The middle of a vacation."));
		selenium.type("_34_description",
			RuntimeVariables.replace("Sounds like: The middle of a vacation."));
		selenium.typeKeys("_34_stockQuantity", RuntimeVariables.replace("50"));
		selenium.type("_34_stockQuantity", RuntimeVariables.replace("50"));
		selenium.typeKeys("_34_properties",
			RuntimeVariables.replace("Limited Time Onl"));
		selenium.type("_34_properties",
			RuntimeVariables.replace("Limited Time Only"));
		assertTrue(selenium.isElementPresent("_34_requiresShippingCheckbox"));
		selenium.click("_34_requiresShippingCheckbox");
		assertTrue(selenium.isChecked("_34_requiresShippingCheckbox"));
		selenium.typeKeys("_34_price0", RuntimeVariables.replace("$9.99"));
		selenium.type("_34_price0", RuntimeVariables.replace("$9.99"));
		selenium.typeKeys("_34_minQuantity0", RuntimeVariables.replace("1"));
		selenium.type("_34_minQuantity0", RuntimeVariables.replace("1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertTrue(selenium.isTextPresent("Jona Lyons - Wheel"));
		assertTrue(selenium.isTextPresent(
				"Sounds like: The middle of a vacation."));
		assertTrue(selenium.isTextPresent("Limited: Time Only"));
	}
}