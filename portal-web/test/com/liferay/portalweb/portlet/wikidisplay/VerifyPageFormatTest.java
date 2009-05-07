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

package com.liferay.portalweb.portlet.wikidisplay;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="VerifyPageFormatTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class VerifyPageFormatTest extends BaseTestCase {
	public void testVerifyPageFormat() throws Exception {
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

		selenium.click(RuntimeVariables.replace("link=Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add Child Page"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", "label=HTML");
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//td[2]/input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//td[2]/input",
			RuntimeVariables.replace("This is Test Page Format"));
		selenium.click(
			"//td[@id='xToolbar']/table[8]/tbody/tr/td[2]/div/table/tbody/tr/td[1]/img");
		selenium.typeKeys("//td[@id='xEditingArea']/textarea",
			RuntimeVariables.replace(
				"<a href=http://www.lifera.com>Welcome to LIFERA</a>"));
		selenium.type("//td[@id='xEditingArea']/textarea",
			RuntimeVariables.replace(
				"<a href=http://www.liferay.com>Welcome to LIFERAY</a>"));
		selenium.selectFrame("relative=top");
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertTrue(selenium.isElementPresent("link=This is Test Page Format"));
		selenium.click(RuntimeVariables.replace("link=This is Test Page Format"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Welcome to LIFERAY"));
		selenium.click(RuntimeVariables.replace("link=Details"));
		selenium.waitForPageToLoad("30000");
		assertEquals("HTML", selenium.getText("//tr[2]/td"));
	}
}