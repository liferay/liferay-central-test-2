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

		selenium.click(RuntimeVariables.replace("link=Directory Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Users"));
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Advanced \u00bb");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_11_firstName")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_11_firstName", RuntimeVariables.replace("TestFirst"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_firstName", RuntimeVariables.replace("TestFirstA"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_firstName", RuntimeVariables.replace(""));
		selenium.type("_11_middleName", RuntimeVariables.replace("TestMiddle"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_middleName", RuntimeVariables.replace("TestMiddleA"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_middleName", RuntimeVariables.replace(""));
		selenium.type("_11_lastName", RuntimeVariables.replace("TestLast"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_lastName", RuntimeVariables.replace("TestLastA"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_lastName", RuntimeVariables.replace(""));
		selenium.type("_11_screenName", RuntimeVariables.replace("TestSN"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_screenName", RuntimeVariables.replace("TestSNA"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_screenName", RuntimeVariables.replace(""));
		selenium.type("_11_emailAddress",
			RuntimeVariables.replace("TestEMail1@liferay.com"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst1"));
		selenium.type("_11_emailAddress",
			RuntimeVariables.replace("TestEMail2@liferay.com"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_emailAddress",
			RuntimeVariables.replace("TestEMail@liferay.com"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=TestFirst1"));
		assertFalse(selenium.isElementPresent("link=TestFirst2"));
		selenium.type("_11_emailAddress", RuntimeVariables.replace(""));
		selenium.click("link=\u00ab Basic");
	}
}