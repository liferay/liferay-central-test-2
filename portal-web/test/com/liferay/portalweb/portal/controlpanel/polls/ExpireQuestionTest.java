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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ExpireQuestionTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExpireQuestionTest extends BaseTestCase {
	public void testExpireQuestion() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Polls")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Polls"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Edited Test Question 2"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//label[1]/input"));
		assertTrue(selenium.isElementPresent("//label[2]/input"));
		assertTrue(selenium.isElementPresent("//label[3]/input"));
		assertTrue(selenium.isElementPresent("//label[4]/input"));
		selenium.click(RuntimeVariables.replace("//input[@value='Cancel']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//td[5]/ul/li/strong/span");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//body/div[2]/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//body/div[2]/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click("_25_neverExpireCheckbox");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_25_expirationDateMonth")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_25_expirationDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_25_expirationDateDay",
			RuntimeVariables.replace("label=1"));
		selenium.select("_25_expirationDateYear",
			RuntimeVariables.replace("label=2008"));
		selenium.select("_25_expirationDateHour",
			RuntimeVariables.replace("label=12"));
		selenium.select("_25_expirationDateMinute",
			RuntimeVariables.replace("label=:00"));
		selenium.select("_25_expirationDateAmPm",
			RuntimeVariables.replace("label=AM"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}