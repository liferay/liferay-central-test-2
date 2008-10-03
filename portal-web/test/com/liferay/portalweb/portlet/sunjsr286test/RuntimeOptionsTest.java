/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.sunjsr286test;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="RuntimeOptionsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RuntimeOptionsTest extends BaseTestCase {
	public void testRuntimeOptions() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=RuntimeOptions")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=RuntimeOptions"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("ActionScopeMap"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a[text()='setActionScope']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//a[text()='setActionScope']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("p2 = two p1 = one"));
		verifyTrue(selenium.isTextPresent("Simple String:b"));
		verifyTrue(selenium.isTextPresent("Action_address"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='Set a SerializableActionScopedAttribute']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='Set a SerializableActionScopedAttribute']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Name:Paul"));
		verifyTrue(selenium.isTextPresent("ActionScopeCheck1"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='ActionScopeEventSamePortlet']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='ActionScopeEventSamePortlet']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("PA():setinPA()"));
		verifyTrue(selenium.isTextPresent("sameportlet:a1"));
		verifyTrue(selenium.isTextPresent("sameportlet:a2"));
		verifyTrue(selenium.isTextPresent("ActionScopeCheck2Send"));
		verifyTrue(selenium.isTextPresent("ActionScopeCheck2Receive"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='ActionScopeandEventing']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='ActionScopeandEventing']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Event recieved:1stPortlet"));
		verifyTrue(selenium.isTextPresent(
				"ActionScopeCheck2Recieve portlet :2ndPortlet"));
		verifyTrue(selenium.isTextPresent("PA():123"));
		verifyTrue(selenium.isTextPresent("sameportlet:abcd"));
		verifyTrue(selenium.isTextPresent("sameportlet:abcde"));
	}
}