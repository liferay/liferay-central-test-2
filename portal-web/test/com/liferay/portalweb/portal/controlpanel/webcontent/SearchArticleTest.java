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

package com.liferay.portalweb.portal.controlpanel.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="SearchArticleTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SearchArticleTest extends BaseTestCase {
	public void testSearchArticle() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Web Content")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//li[@id='_15_tabs1web-contentTabsId']/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Advanced \u00bb");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_searchArticleId")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_15_searchArticleId",
			RuntimeVariables.replace("Test"));
		selenium.type("_15_searchArticleId", RuntimeVariables.replace("Test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("No Web Content was found."));
		selenium.type("_15_searchArticleId", RuntimeVariables.replace(""));
		selenium.type("_15_version", RuntimeVariables.replace("1.0"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Web Content Article"));
		selenium.type("_15_version", RuntimeVariables.replace("1.1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("No Web Content was found."));
		selenium.type("_15_version", RuntimeVariables.replace(""));
		selenium.type("_15_title", RuntimeVariables.replace("Test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Web Content Article"));
		selenium.type("_15_title", RuntimeVariables.replace("Test1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("No Web Content was found."));
		selenium.type("_15_title", RuntimeVariables.replace(""));
		selenium.type("_15_description", RuntimeVariables.replace("test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Web Content Article"));
		selenium.type("_15_description", RuntimeVariables.replace("test1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("No Web Content was found."));
		selenium.type("_15_description", RuntimeVariables.replace(""));
		selenium.type("_15_content", RuntimeVariables.replace("test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Web Content Article"));
		selenium.type("_15_content", RuntimeVariables.replace("test1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("No Web Content was found."));
		selenium.type("_15_content", RuntimeVariables.replace(""));
		selenium.select("_15_type",
			RuntimeVariables.replace("label=Announcements"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Web Content Article"));
		selenium.select("_15_type", RuntimeVariables.replace("label=Blogs"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("No Web Content was found."));
		selenium.select("_15_type", RuntimeVariables.replace("label="));
		selenium.select("_15_status", RuntimeVariables.replace("label=Approved"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Web Content Article"));
		selenium.select("_15_status",
			RuntimeVariables.replace("label=Not Approved"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("No Web Content was found."));
		selenium.select("_15_status", RuntimeVariables.replace("label="));
		selenium.click("link=\u00ab Basic");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_keywords")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}
}