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

package com.liferay.portalweb.portal.controlpanel.admin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="BrowseServerTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BrowseServerTest extends BaseTestCase {
	public void testBrowseServer() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Server Administration")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Server Administration"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Execute']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Log Levels"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Update Categories"));
		assertTrue(selenium.isElementPresent("link=Add Category"));
		selenium.click(RuntimeVariables.replace("link=Update Categories"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Showing 1 - 20"));
		assertTrue(selenium.isTextPresent("com.ecyrd.jspwiki"));
		selenium.click(RuntimeVariables.replace("link=Next"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Showing 21 - 40"));
		assertTrue(selenium.isTextPresent("com.liferay.portal.editor"));
		selenium.click(RuntimeVariables.replace("link=Properties"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=System Properties"));
		assertTrue(selenium.isElementPresent("link=Portal Properties"));
		selenium.click(RuntimeVariables.replace("link=System Properties"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Showing 1 - 20"));
		assertTrue(selenium.isTextPresent("catalina.home"));
		selenium.click(RuntimeVariables.replace("link=Next"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Showing 21 - 40"));
		assertTrue(selenium.isTextPresent("env.CLIENTNAME"));
		selenium.click(RuntimeVariables.replace("link=Portal Properties"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Showing 1 - 20"));
		assertTrue(selenium.isTextPresent("admin.email.from.address"));
		selenium.click(RuntimeVariables.replace("link=Next"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Showing 21 - 40"));
		assertTrue(selenium.isTextPresent("auth.failure"));
		selenium.click(RuntimeVariables.replace("link=Data Migration"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=File Uploads"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Configure the file upload settings."));
		assertTrue(selenium.isElementPresent("//input[@value='Save']"));
		selenium.click(RuntimeVariables.replace("link=Mail"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Configure the mail server settings."));
		assertTrue(selenium.isElementPresent("//input[@value='Save']"));
		selenium.click(RuntimeVariables.replace("link=OpenOffice"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Enabling OpenOffice integration provides document conversion functionality."));
		assertTrue(selenium.isElementPresent("_137_enabledCheckbox"));
		assertTrue(selenium.isElementPresent("//input[@value='Save']"));
		selenium.click(RuntimeVariables.replace("link=Shutdown"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//input[@value='Shutdown']"));
	}
}