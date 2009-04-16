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

package com.liferay.portalweb.portlet.wiki;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="TearDownTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TearDownTest extends BaseTestCase {
	public void testTearDown() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Main"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Test"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.click(RuntimeVariables.replace("link=All Pages"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//td[5]/ul/li/strong/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//td[5]/ul/li/strong/span");
		selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.click(RuntimeVariables.replace("link=Second Edited Wiki Test"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=FrontPage"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Link Me 1"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.click(RuntimeVariables.replace("link=Second Edited Wiki Test"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=FrontPage"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Link Me 2"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//img[@alt='Details']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.click(RuntimeVariables.replace("link=All Pages"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//td[5]/ul/li/strong/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//td[5]/ul/li/strong/span");
		selenium.click(RuntimeVariables.replace("link=Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//img[@alt='Manage Wikis']"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//td[4]/ul/li/strong/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//td[4]/ul/li/strong/span");
		selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//td[4]/ul/li/strong/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//td[4]/ul/li/strong/span");
		selenium.click(RuntimeVariables.replace("//div[4]/ul/li[6]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.click(RuntimeVariables.replace("//div[2]/ul/li[1]/a/span"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//li[2]/a[2]"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Display Order"));
		selenium.waitForPageToLoad("30000");
		selenium.select("_2_layoutIdsBox",
			RuntimeVariables.replace("label=Wiki Test Page"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[@id='portlet-wrapper-2']/div[2]/div/div/form/table/tbody/tr/td[2]/table/tbody/tr/td[2]/a[3]/img")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(
			"//div[@id='portlet-wrapper-2']/div[2]/div/div/form/table/tbody/tr/td[2]/table/tbody/tr/td[2]/a[3]/img");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Update Display Order']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
	}
}