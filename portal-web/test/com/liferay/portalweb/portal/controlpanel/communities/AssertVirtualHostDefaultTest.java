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

package com.liferay.portalweb.portal.controlpanel.communities;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertVirtualHostDefaultTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssertVirtualHostDefaultTest extends BaseTestCase {
	public void testAssertVirtualHostDefault() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//tr[6]/td[6]/ul/li/strong/span");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//body/div[2]/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//body/div[2]/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//form/ul[1]/li[3]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Virtual Host"));
		selenium.waitForPageToLoad("30000");
		assertEquals("", selenium.getValue("_134_publicVirtualHost"));
		assertEquals("", selenium.getValue("_134_privateVirtualHost"));
		assertEquals("/virtual-hosting-test-community",
			selenium.getValue("_134_friendlyURL"));
	}
}