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

package com.liferay.portalweb.portlet.plugininstaller;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="PluginUpdateTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PluginUpdateTest extends BaseTestCase {
	public void testPluginUpdate() throws Exception {
		selenium.click(RuntimeVariables.replace(
				"link=Plugin Installer Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("_111_keywords", RuntimeVariables.replace("google"));
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Search Plugins']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Google Gadget 4.4.0.1"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Install']"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(10000);
		verifyTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Refresh']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Refresh']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Plugins"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"link=Plugin Installer Test Page"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Updates are available for Liferay. Click here to open the Update Manager for details.")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"link=Updates are available for Liferay. Click here to open the Update Manager for details."));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Update"));
		selenium.click(RuntimeVariables.replace("Link=Update"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(10000);
		selenium.click(RuntimeVariables.replace("//input[@value='Refresh']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"link=Plugin Installer Test Page"));
		selenium.waitForPageToLoad("30000");
		verifyFalse(selenium.isTextPresent(
				"Updates are available for Liferay. Click here to open the Update Manager for details."));
	}
}