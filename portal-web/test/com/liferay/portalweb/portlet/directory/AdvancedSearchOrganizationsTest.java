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

package com.liferay.portalweb.portlet.directory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AdvancedSearchOrganizationsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchOrganizationsTest extends BaseTestCase {
	public void testAdvancedSearchOrganizations() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Directory Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Directory Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Advanced \u00bb");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_11_name")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_11_name", RuntimeVariables.replace("Test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Diamond Bar"));
		selenium.type("_11_name", RuntimeVariables.replace("Test1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Diamond Bar"));
		selenium.type("_11_name", RuntimeVariables.replace(""));
		selenium.type("_11_street", RuntimeVariables.replace("Test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Diamond Bar"));
		selenium.type("_11_street", RuntimeVariables.replace("Test1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Diamond Bar"));
		selenium.type("_11_street", RuntimeVariables.replace(""));
		selenium.type("_11_city", RuntimeVariables.replace("Diamond"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Diamond Bar"));
		selenium.type("_11_city", RuntimeVariables.replace("Diamond1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Diamond Bar"));
		selenium.type("_11_city", RuntimeVariables.replace(""));
		selenium.type("_11_zip", RuntimeVariables.replace("11111"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Diamond Bar"));
		selenium.type("_11_zip", RuntimeVariables.replace("111111"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Diamond Bar"));
		selenium.type("_11_zip", RuntimeVariables.replace(""));
		selenium.select("_11_type",
			RuntimeVariables.replace("label=Regular Organization"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Diamond Bar"));
		selenium.select("_11_type", RuntimeVariables.replace("label=Location"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Diamond Bar"));
		selenium.select("_11_type", RuntimeVariables.replace("label=Any"));
		selenium.select("_11_countryId",
			RuntimeVariables.replace("label=United States"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Diamond Bar"));
		selenium.select("_11_countryId",
			RuntimeVariables.replace("label=United Kingdom"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Diamond Bar"));
		selenium.select("_11_countryId",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_11_regionId",
			RuntimeVariables.replace("label=California"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Diamond Bar"));
		selenium.select("_11_regionId", RuntimeVariables.replace("label=Hawaii"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Diamond Bar"));
		selenium.select("_11_countryId", RuntimeVariables.replace("label="));
		selenium.click("link=\u00ab Basic");
	}
}