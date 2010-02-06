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

package com.liferay.portalweb.portlet.wikidisplay;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="RatingTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RatingTest extends BaseTestCase {
	public void testRating() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//td[1]/ul/li[2]/a[2]"));
		assertEquals(RuntimeVariables.replace("(0 Votes)"),
			selenium.getText("//td[1]/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("\u00b10"),
			selenium.getText("//td[1]/ul/li[1]/span"));
		assertEquals(RuntimeVariables.replace("(0 Votes)"),
			selenium.getText("//tr[5]/td[2]/table[1]/tbody/tr/td[1]/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("\u00b10"),
			selenium.getText(
				"//tr[5]/td[2]/table[1]/tbody/tr/td[1]/ul/li[1]/span"));
		selenium.clickAt("//td[1]/ul/li[2]/a[2]", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("(1 Vote)"),
			selenium.getText("//td[1]/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("-1"),
			selenium.getText("//td[1]/ul/li[1]/span"));
		assertEquals(RuntimeVariables.replace("(0 Votes)"),
			selenium.getText("//tr[5]/td[2]/table[1]/tbody/tr/td[1]/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("\u00b10"),
			selenium.getText(
				"//tr[5]/td[2]/table[1]/tbody/tr/td[1]/ul/li[1]/span"));
		selenium.clickAt("//td[1]/ul/li[2]/a[1]", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("(1 Vote)"),
			selenium.getText("//td[1]/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("+1"),
			selenium.getText("//td[1]/ul/li[1]/span"));
		assertEquals(RuntimeVariables.replace("(0 Votes)"),
			selenium.getText("//tr[5]/td[2]/table[1]/tbody/tr/td[1]/ul/li[3]"));
		assertEquals(RuntimeVariables.replace("\u00b10"),
			selenium.getText(
				"//tr[5]/td[2]/table[1]/tbody/tr/td[1]/ul/li[1]/span"));
		selenium.clickAt("//td[1]/ul/li[2]/a[1]", RuntimeVariables.replace(""));
	}
}