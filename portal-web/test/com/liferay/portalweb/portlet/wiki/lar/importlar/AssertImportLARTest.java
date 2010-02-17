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

package com.liferay.portalweb.portlet.wiki.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertImportLARTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertImportLARTest extends BaseTestCase {
	public void testAssertImportLAR() throws Exception {
		selenium.open("/web/guest/home/");

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

		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Test Wiki Article"));
		assertTrue(selenium.isTextPresent("this is italics"));
		assertTrue(selenium.isTextPresent("bold"));
		assertTrue(selenium.isElementPresent("link=Link to website"));
		assertTrue(selenium.isTextPresent("this is a list item"));
		assertTrue(selenium.isTextPresent("this is a sub list item"));
		assertTrue(selenium.isElementPresent("link=Test"));
		selenium.clickAt("link=Test", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Test Child Article"));
		assertTrue(selenium.isTextPresent("this is italics"));
		assertTrue(selenium.isTextPresent("bold"));
		assertTrue(selenium.isElementPresent("link=Link to website"));
		assertTrue(selenium.isTextPresent("this is a list item"));
		assertTrue(selenium.isTextPresent("this is a sub list item"));
		selenium.clickAt("link=Second Edited Wiki Test",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Yes this is a second test article"));
		assertTrue(selenium.isTextPresent(
				"I love Liferay! This Wiki has been EDITED!"));
		assertTrue(selenium.isElementPresent("link=Link Me 1"));
		assertTrue(selenium.isElementPresent("link=Link Me 2"));
		selenium.clickAt("link=Link Me 1", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Hi Administrator! Hope you are well! Please link me to another page!"));
		selenium.clickAt("link=Link Me 2", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Hi Administrator!"));
		assertTrue(selenium.isTextPresent(
				"I made another mistake! Oh me. Please link this article to another!"));
	}
}