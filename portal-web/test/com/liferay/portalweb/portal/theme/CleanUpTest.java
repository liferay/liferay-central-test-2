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

package com.liferay.portalweb.portal.theme;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CleanUpTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CleanUpTest extends BaseTestCase {
	public void testCleanUp() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Theme Test Page"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Delete")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		Thread.sleep(3000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//img[@alt='Remove']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//img[@alt='Remove']");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to remove this component[\\s\\S]$"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//img[@alt='Remove']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//img[@alt='Remove']");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
		selenium.open("/user/joebloggs/home");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Manage Pages")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Manage Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//div[@id='_88_layoutsTreeOutput']/ul/li[2]/a/span"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Display Order"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(3000);
		selenium.select("_88_layoutIdsBox",
			RuntimeVariables.replace("label=Theme Test Page"));
		selenium.click(
			"//div[@id='portlet-wrapper-88']/div[2]/div/div/form/table/tbody/tr/td[2]/table/tbody/tr/td[2]/a[3]/img");
		Thread.sleep(3000);
		selenium.select("_88_layoutIdsBox",
			RuntimeVariables.replace("label=Theme Test Page 2"));
		selenium.click(
			"//div[@id='portlet-wrapper-88']/div[2]/div/div/form/table/tbody/tr/td[2]/table/tbody/tr/td[2]/a[3]/img");
		Thread.sleep(3000);
		selenium.select("_88_layoutIdsBox",
			RuntimeVariables.replace("label=Theme Test Page 3"));
		selenium.click(
			"//div[@id='portlet-wrapper-88']/div[2]/div/div/form/table/tbody/tr/td[2]/table/tbody/tr/td[2]/a[3]/img");
		Thread.sleep(3000);
		selenium.select("_88_layoutIdsBox",
			RuntimeVariables.replace("label=Theme Test Page 4"));
		selenium.click(
			"//div[@id='portlet-wrapper-88']/div[2]/div/div/form/table/tbody/tr/td[2]/table/tbody/tr/td[2]/a[3]/img");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Update Display Order']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Look and Feel"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@name='_88_themeId' and @value='classic']"));
		selenium.waitForPageToLoad("30000");
		selenium.open("/user/joebloggs/home");
	}
}