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
 * <a href="AdvancedSearchOrganizationTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchOrganizationTest extends BaseTestCase {
	public void testAdvancedSearchOrganization() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Organizations")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Advanced \u00bb");
		selenium.typeKeys("_126_name", RuntimeVariables.replace("Selenium"));
		selenium.type("_126_name", RuntimeVariables.replace("Selenium"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Diamond Bar"));
		selenium.type("_126_name", RuntimeVariables.replace("Selenium1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Diamond Bar"));
		selenium.type("_126_name", RuntimeVariables.replace(""));
		selenium.type("_126_street",
			RuntimeVariables.replace("12345 Selenium St"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Diamond Bar"));
		selenium.type("_126_street",
			RuntimeVariables.replace("12345 Selenium Street"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Diamond Bar"));
		selenium.type("_126_street", RuntimeVariables.replace(""));
		selenium.type("_126_city", RuntimeVariables.replace("Diamond Bar"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Diamond Bar"));
		selenium.type("_126_city", RuntimeVariables.replace("Diamond Bars"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Diamond Bar"));
		selenium.type("_126_city", RuntimeVariables.replace(""));
		selenium.type("_126_zip", RuntimeVariables.replace("41111"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Diamond Bar"));
		selenium.type("_126_zip", RuntimeVariables.replace("411111"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Diamond Bar"));
		selenium.type("_126_zip", RuntimeVariables.replace(""));
		selenium.select("_126_type",
			RuntimeVariables.replace("label=Regular Organization"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Diamond Bar"));
		selenium.select("_126_type", RuntimeVariables.replace("label=Location"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Diamond Bar"));
		selenium.select("_126_type", RuntimeVariables.replace("label=Any"));
		selenium.select("_126_countryId",
			RuntimeVariables.replace("label=United States"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Diamond Bar"));
		selenium.select("_126_countryId",
			RuntimeVariables.replace("label=Turkey"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Diamond Bar"));
		selenium.select("_126_countryId",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_126_regionId",
			RuntimeVariables.replace("label=California"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Diamond Bar"));
		selenium.select("_126_regionId",
			RuntimeVariables.replace("label=Wyoming"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Diamond Bar"));
		selenium.select("_126_countryId", RuntimeVariables.replace("label="));
		selenium.click("link=\u00ab Basic");
	}
}