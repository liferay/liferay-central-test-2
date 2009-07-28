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
 * <a href="AddPasswordPoliciesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddPasswordPoliciesTest extends BaseTestCase {
	public void testAddPasswordPolicies() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Password Policies")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Password Policies"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_129_name", RuntimeVariables.replace("Test"));
		selenium.type("_129_name", RuntimeVariables.replace("Test"));
		selenium.type("_129_description",
			RuntimeVariables.replace("This is a test password policy!"));
		selenium.click("_129_changeableCheckbox");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_129_changeRequiredCheckbox")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("_129_changeRequiredCheckbox");
		selenium.select("_129_minAge", RuntimeVariables.replace("label=1 Week"));
		selenium.click("_129_checkSyntaxCheckbox");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"_129_allowDictionaryWordsCheckbox")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("_129_allowDictionaryWordsCheckbox");
		selenium.type("_129_minLength", RuntimeVariables.replace("5"));
		selenium.click("_129_historyCheckbox");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_129_historyCount")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_129_historyCount", RuntimeVariables.replace("label=4"));
		selenium.click("_129_expireableCheckbox");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_129_maxAge")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_129_maxAge", RuntimeVariables.replace("label=4 Weeks"));
		selenium.select("_129_warningTime",
			RuntimeVariables.replace("label=2 Days"));
		selenium.type("_129_graceLimit", RuntimeVariables.replace("1"));
		selenium.click("_129_lockoutCheckbox");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_129_maxFailure")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_129_maxFailure", RuntimeVariables.replace("3"));
		selenium.select("_129_resetFailureCount",
			RuntimeVariables.replace("label=10 Minutes"));
		selenium.select("_129_lockoutDuration",
			RuntimeVariables.replace("label=5 Minutes"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertTrue(selenium.isElementPresent("link=Test"));
		assertTrue(selenium.isElementPresent(
				"link=This is a test password policy!"));
	}
}