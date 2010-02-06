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

package com.liferay.portalweb.portlet.announcements.entry.showentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddEntryGeneralTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddEntryGeneralTest extends BaseTestCase {
	public void testAddEntryGeneral() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Announcements Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.clickAt("link=Manage Entries", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_84_distributionScope",
			RuntimeVariables.replace("label=General"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value='Add Entry']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//input[@value='Add Entry']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_84_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_84_title",
			RuntimeVariables.replace("Test General Announcement"));
		selenium.type("_84_url", RuntimeVariables.replace("www.liferay.com"));
		selenium.type("_84_content",
			RuntimeVariables.replace(
				"Hello Everyone! This is a test general announcement for everyone! Yay."));
		selenium.select("_84_displayDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("//select[3]", RuntimeVariables.replace("label=2005"));
		selenium.select("//div[2]/select[1]",
			RuntimeVariables.replace("label=12"));
		selenium.select("//div[2]/select[2]",
			RuntimeVariables.replace("label=:00"));
		selenium.select("//div[2]/select[3]",
			RuntimeVariables.replace("label=AM"));
		selenium.select("//tr[12]/td[2]/div[1]/div[1]/select[3]",
			RuntimeVariables.replace("label=2015"));
		selenium.select("//tr[12]/td[2]/div[1]/div[1]/select[1]",
			RuntimeVariables.replace("label=December"));
		selenium.select("//tr[12]/td[2]/div[2]/select[1]",
			RuntimeVariables.replace("label=11"));
		selenium.select("//tr[12]/td[2]/div[2]/select[2]",
			RuntimeVariables.replace("label=:59"));
		selenium.select("//tr[12]/td[2]/div[2]/select[3]",
			RuntimeVariables.replace("label=PM"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.clickAt("link=Entries", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test General Announcement"));
	}
}