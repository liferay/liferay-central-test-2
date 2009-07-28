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

package com.liferay.portalweb.portal.controlpanel.portal;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddUserAddress2Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddUserAddress2Test extends BaseTestCase {
	public void testAddUserAddress2() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Users")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Users"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_125_keywords", RuntimeVariables.replace("selen01"));
		selenium.type("_125_keywords", RuntimeVariables.replace("selen01"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=selen01"));
		selenium.waitForPageToLoad("30000");
		selenium.click("addressesLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Add Row")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("link=Add Row");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_125_addressStreet1_2")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_125_addressStreet1_2",
			RuntimeVariables.replace("17730 Antonio Ave"));
		selenium.type("_125_addressStreet1_2",
			RuntimeVariables.replace("17730 Antonio Ave"));
		selenium.select("_125_addressCountryId2",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_125_addressRegionId2",
			RuntimeVariables.replace("label=California"));
		selenium.select("_125_addressTypeId2",
			RuntimeVariables.replace("label=Personal"));
		selenium.typeKeys("_125_addressZip2", RuntimeVariables.replace("90703"));
		selenium.type("_125_addressZip2", RuntimeVariables.replace("90703"));
		selenium.typeKeys("_125_addressCity2",
			RuntimeVariables.replace("Cerritos"));
		selenium.type("_125_addressCity2", RuntimeVariables.replace("Cerritos"));
		selenium.click("_125_addressPrimary2");
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}