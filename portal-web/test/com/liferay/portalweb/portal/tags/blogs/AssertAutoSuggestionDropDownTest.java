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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertAutoSuggestionDropDownTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertAutoSuggestionDropDownTest extends BaseTestCase {
	public void testAssertAutoSuggestionDropDown() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Blogs Tags Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Tags Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Tags3 Blogs3 Test3 Entry3")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Tags3 Blogs3 Test3 Entry3",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//li[5]/span/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Edit", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.type("//div[7]/div/input[2]", RuntimeVariables.replace(""));
		selenium.typeKeys("//div[7]/div/input[2]",
			RuntimeVariables.replace("selenium"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("selenium1 liferay1"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[1]"));
		assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[2]"));
		assertEquals(RuntimeVariables.replace("selenium3 liferay3"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("selenium4 liferay4"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[4]"));
		selenium.typeKeys("//div[6]/input[2]", RuntimeVariables.replace("2"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
			selenium.getText("//div[6]/div[1]/div[2]/ul/li[1]"));
		assertFalse(selenium.isVisible("//div[6]/div[1]/div[2]/ul/li[2]"));
		assertFalse(selenium.isVisible("//div[6]/div[1]/div[2]/ul/li[3]"));
		assertFalse(selenium.isVisible("//div[6]/div[1]/div[2]/ul/li[4]"));
	}
}