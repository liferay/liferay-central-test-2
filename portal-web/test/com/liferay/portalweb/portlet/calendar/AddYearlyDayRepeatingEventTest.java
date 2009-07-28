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

package com.liferay.portalweb.portlet.calendar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddYearlyDayRepeatingEventTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddYearlyDayRepeatingEventTest extends BaseTestCase {
	public void testAddYearlyDayRepeatingEvent() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Events"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//strong");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//body/div[4]/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//body/div[4]/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@name='_8_recurrenceType' and @value='6']");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[5]/table/tbody/tr/td/input[4]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//div[5]/table/tbody/tr/td/input[4]");
		selenium.select("_8_yearlyPos", RuntimeVariables.replace("label=Second"));
		selenium.select("_8_yearlyDay1",
			RuntimeVariables.replace("label=Monday"));
		selenium.select("_8_yearlyMonth1",
			RuntimeVariables.replace("label=February"));
		selenium.type("_8_yearlyInterval1", RuntimeVariables.replace("1"));
		selenium.click("//td[2]/table/tbody/tr/td/input[2]");
		selenium.select("_8_endDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_8_endDateDay", RuntimeVariables.replace("label=1"));
		selenium.select("_8_endDateYear", RuntimeVariables.replace("label=2013"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.click(RuntimeVariables.replace("link=Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=2009"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//td[3]/div/table/tbody/tr[4]/td[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//td[3]/div/table/tbody/tr[4]/td[2]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Repeating Test Event"));
		selenium.click(RuntimeVariables.replace("link=Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", "label=2010");
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//tr[3]/td[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//tr[3]/td[2]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Repeating Test Event"));
		selenium.click(RuntimeVariables.replace("link=Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=2010"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//td[5]/div/table/tbody/tr[4]/td[2]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//td[5]/div/table/tbody/tr[4]/td[2]/a/span"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Repeating Test Event"));
	}
}