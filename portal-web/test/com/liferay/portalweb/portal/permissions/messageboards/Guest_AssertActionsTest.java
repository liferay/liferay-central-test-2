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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Guest_AssertActionsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertActionsTest extends BaseTestCase {
	public void testGuest_AssertActions() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Categories")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isElementPresent("//input[@value='Add Category']"));
		assertFalse(selenium.isElementPresent("//img[@alt='Edit']"));
		assertFalse(selenium.isElementPresent("//img[@alt='Permissions']"));
		assertFalse(selenium.isElementPresent("link=Subscribe"));
		assertFalse(selenium.isElementPresent("link=Banned Users"));
		assertFalse(selenium.isElementPresent("link=Delete"));
		selenium.clickAt("link=Permissions Test 1", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent(
				"//input[@value='Add Subcategory']"));
		assertFalse(selenium.isElementPresent("//img[@alt='Edit']"));
		assertFalse(selenium.isElementPresent("//img[@alt='Permissions']"));
		assertFalse(selenium.isElementPresent("link=Subscribe"));
		assertFalse(selenium.isElementPresent("link=Delete"));
		assertFalse(selenium.isElementPresent(
				"//input[@value='Post New Thread']"));
		selenium.clickAt("link=Test Thread 1", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Test Thread Message 1"));
		assertTrue(selenium.isTextPresent("Test Thread Message Reply 1"));
		assertFalse(selenium.isElementPresent("link=Edit"));
		assertFalse(selenium.isElementPresent("link=Permissions"));
		assertFalse(selenium.isElementPresent("link=Delete"));
		assertTrue(selenium.isElementPresent("link=Sign in to vote."));
		assertFalse(selenium.isElementPresent("link=Split Thread"));
		selenium.clickAt("link=Categories", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
	}
}