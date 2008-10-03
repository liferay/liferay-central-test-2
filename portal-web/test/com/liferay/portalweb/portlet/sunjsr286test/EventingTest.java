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
 * <a href="EventingTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EventingTest extends BaseTestCase {
	public void testEventing() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Eventing")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Eventing"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("EventCore1"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='MatchLongestWildcard']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='MatchLongestWildcard']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("annotation a.b.c."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[text()='Send multiple events from PA()']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[text()='Send multiple events from PA()']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("data1:data2"));
		verifyTrue(selenium.isTextPresent("WildCardSend"));
		verifyTrue(selenium.isTextPresent("WildCardReceive1"));
		verifyTrue(selenium.isTextPresent("WildCardReceive2"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a[text()='WildcardTrigger']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//a[text()='WildcardTrigger']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("test1234"));
		verifyTrue(selenium.isTextPresent("EventAlias1"));
		verifyTrue(selenium.isTextPresent("EventAlias1"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a[text()='EventAlias']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//a[text()='EventAlias']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("aliasvalue"));
		verifyTrue(selenium.isTextPresent("Cyclic1"));
		verifyTrue(selenium.isTextPresent("Cyclic2"));
		verifyTrue(selenium.isTextPresent("Cyclic3"));
		verifyTrue(selenium.isTextPresent("Cyclic4"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a[text()='CyclicEvents']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//a[text()='CyclicEvents']"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Recieved:R1"));
		verifyTrue(selenium.isTextPresent("Recieved:R2"));
		verifyTrue(selenium.isTextPresent("Recieved:"));
	}
}