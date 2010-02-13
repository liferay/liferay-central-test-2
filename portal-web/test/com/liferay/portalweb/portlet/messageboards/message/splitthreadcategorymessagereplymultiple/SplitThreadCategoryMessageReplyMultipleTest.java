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

package com.liferay.portalweb.portlet.messageboards.message.splitthreadcategorymessagereplymultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="SplitThreadCategoryMessageReplyMultipleTest.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SplitThreadCategoryMessageReplyMultipleTest extends BaseTestCase {
	public void testSplitThreadCategoryMessageReplyMultiple()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent(
				"link=RE: T\u00e9st M\u00e9ssag\u00e9"));
		selenium.clickAt("link=T\u00e9st M\u00e9ssag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("RE: T\u00e9st M\u00e9ssag\u00e9"));
		assertTrue(selenium.isTextPresent(
				"This is a t\u00e9st1 r\u00e9ply1 m\u00e9ssag\u00e91."));
		assertTrue(selenium.isTextPresent(
				"This is a t\u00e9st2 r\u00e9ply2 m\u00e9ssag\u00e92."));
		assertTrue(selenium.isTextPresent(
				"This is a t\u00e9st3 r\u00e9ply3 m\u00e9ssag\u00e93."));
		selenium.clickAt("link=Split Thread", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"link=RE: T\u00e9st M\u00e9ssag\u00e9"));
		selenium.clickAt("link=T\u00e9st M\u00e9ssag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("RE: T\u00e9st M\u00e9ssag\u00e9"));
		assertFalse(selenium.isTextPresent(
				"This is a t\u00e9st1 r\u00e9ply1 m\u00e9ssag\u00e91."));
		assertFalse(selenium.isTextPresent(
				"This is a t\u00e9st2 r\u00e9ply2 m\u00e9ssag\u00e92."));
		assertFalse(selenium.isTextPresent(
				"This is a t\u00e9st3 r\u00e9ply3 m\u00e9ssag\u00e93."));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=RE: T\u00e9st M\u00e9ssag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"This is a t\u00e9st1 r\u00e9ply1 m\u00e9ssag\u00e91."));
		assertTrue(selenium.isTextPresent(
				"This is a t\u00e9st2 r\u00e9ply2 m\u00e9ssag\u00e92."));
		assertTrue(selenium.isTextPresent(
				"This is a t\u00e9st3 r\u00e9ply3 m\u00e9ssag\u00e93."));
	}
}