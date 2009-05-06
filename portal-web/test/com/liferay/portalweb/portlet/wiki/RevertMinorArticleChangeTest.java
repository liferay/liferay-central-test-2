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

package com.liferay.portalweb.portlet.wiki;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="RevertMinorArticleChangeTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RevertMinorArticleChangeTest extends BaseTestCase {
	public void testRevertMinorArticleChange() throws Exception {
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

		selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Second Edited Wiki Test"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Details"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=History"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Revert"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertEquals("1.4", selenium.getText("//td[3]/a"));
		assertEquals("Reverted to 1.2", selenium.getText("//tr[3]/td[6]"));
		assertTrue(selenium.isElementPresent("link=1.4"));
		selenium.click(RuntimeVariables.replace("link=1.4"));
		selenium.waitForPageToLoad("30000");
		assertEquals("This is a second test article",
			selenium.getText("//div[2]/div/h2"));
		assertTrue(selenium.isTextPresent(
				"I love Liferay! This Wiki has been EDITED!"));
		assertTrue(selenium.isTextPresent("Yes this is a second test article"));
	}
}