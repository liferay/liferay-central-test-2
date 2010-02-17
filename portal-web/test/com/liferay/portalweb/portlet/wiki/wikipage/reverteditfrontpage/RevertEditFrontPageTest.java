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

package com.liferay.portalweb.portlet.wiki.wikipage.reverteditfrontpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="RevertEditFrontPageTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RevertEditFrontPageTest extends BaseTestCase {
	public void testRevertEditFrontPage() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Test Wiki Article Edited"),
			selenium.getText("//div[@class='wiki-body']/h2"));
		assertEquals(RuntimeVariables.replace("this is italics. edited."),
			selenium.getText("//i"));
		assertEquals(RuntimeVariables.replace("bold edited"),
			selenium.getText("//b"));
		assertTrue(selenium.isElementPresent("link=Link to website. Edited."));
		assertEquals(RuntimeVariables.replace(
				"this is a list item. edited. this is a sub list item. edited."),
			selenium.getText("//div[@class='wiki-body']/ul/li"));
		selenium.clickAt("link=Details", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=History", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=1.3"));
		selenium.clickAt("link=Revert", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=1.3"));
		assertEquals(RuntimeVariables.replace("Reverted to 1.1"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[7]"));
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
		assertEquals(RuntimeVariables.replace("Test Wiki Article"),
			selenium.getText("//div[@class='wiki-body']/h2"));
		assertEquals(RuntimeVariables.replace("this is italics"),
			selenium.getText("//i"));
		assertEquals(RuntimeVariables.replace("bold"), selenium.getText("//b"));
		assertTrue(selenium.isElementPresent("link=Link to website"));
		assertEquals(RuntimeVariables.replace(
				"this is a list item this is a sub list item"),
			selenium.getText("//div[@class='wiki-body']/ul/li"));
		assertNotEquals(RuntimeVariables.replace("Test Wiki Article Edited"),
			selenium.getText("//div[@class='wiki-body']/h2"));
		assertNotEquals(RuntimeVariables.replace("this is italics. edited."),
			selenium.getText("//i"));
		assertNotEquals(RuntimeVariables.replace("bold edited"),
			selenium.getText("//b"));
		assertFalse(selenium.isElementPresent("link=Link to website. Edited."));
		assertNotEquals(RuntimeVariables.replace(
				"this is a list item. edited. this is a sub list item. edited."),
			selenium.getText("//div[@class='wiki-body']/ul/li"));
	}
}