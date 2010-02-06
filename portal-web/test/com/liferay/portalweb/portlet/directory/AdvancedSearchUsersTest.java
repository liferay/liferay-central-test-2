/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="AdvancedSearchUsersTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchUsersTest extends BaseTestCase {
	public void testAdvancedSearchUsers() throws Exception {
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

		selenium.clickAt("link=Directory Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Advanced \u00bb", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_11_firstName")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_11_firstName", RuntimeVariables.replace("TestFirst"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_firstName", RuntimeVariables.replace("TestFirstA"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_firstName", RuntimeVariables.replace(""));
		selenium.type("_11_middleName", RuntimeVariables.replace("TestMiddle"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_middleName", RuntimeVariables.replace("TestMiddleA"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_middleName", RuntimeVariables.replace(""));
		selenium.type("_11_lastName", RuntimeVariables.replace("TestLast"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_lastName", RuntimeVariables.replace("TestLastA"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_lastName", RuntimeVariables.replace(""));
		selenium.type("_11_screenName", RuntimeVariables.replace("TestSN"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_screenName", RuntimeVariables.replace("TestSNA"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_screenName", RuntimeVariables.replace(""));
		selenium.type("_11_emailAddress",
			RuntimeVariables.replace("TestEMail1@liferay.com"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		selenium.type("_11_emailAddress",
			RuntimeVariables.replace("TestEMail2@liferay.com"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_emailAddress",
			RuntimeVariables.replace("TestEMail@liferay.com"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_emailAddress", RuntimeVariables.replace(""));
		selenium.clickAt("link=\u00ab Basic", RuntimeVariables.replace(""));
	}
}