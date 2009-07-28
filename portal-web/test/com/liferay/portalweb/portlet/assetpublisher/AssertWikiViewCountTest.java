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

package com.liferay.portalweb.portlet.assetpublisher;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertWikiViewCountTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertWikiViewCountTest extends BaseTestCase {
	public void testAssertWikiViewCount() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Asset Publisher Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"link=Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");

		String ViewCount = selenium.getIncrementedText("//div[2]/span");
		RuntimeVariables.setValue("ViewCount", ViewCount);
		selenium.click(RuntimeVariables.replace("link=AP Setup Test Wiki Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Back"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//div[2]/span",
				RuntimeVariables.getValue("ViewCount")));

		String ViewCount2 = selenium.getIncrementedText("//div[2]/span");
		RuntimeVariables.setValue("ViewCount2", ViewCount2);
		selenium.click(RuntimeVariables.replace("link=AP Setup Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//a[3]/span"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//td[5]/ul/li/strong/span");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//div[5]/ul/li[6]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//div[5]/ul/li[6]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.click(RuntimeVariables.replace("link=AP Setup Test Wiki Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//div[3]/div[2]",
				RuntimeVariables.getValue("ViewCount2")));
		selenium.click(RuntimeVariables.replace(
				"link=Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//div[2]/span",
				RuntimeVariables.getValue("ViewCount2")));
	}
}