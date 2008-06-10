/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.enterpriseadmin;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * <a href="AddPasswordPoliciesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddPasswordPoliciesTest extends BaseTestCase {
	public void testAddPasswordPolicies() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=\u00bb")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("link=\u00bb");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Password Policies");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Add Password Policy']");
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_79_name", "Test");
		selenium.type("_79_name", "Test");
		selenium.type("_79_description", "This is a test password policy!");
		selenium.click("_79_changeableCheckbox");
		selenium.select("_79_minAge", "label=1 Week");
		selenium.click("_79_expireableCheckbox");
		selenium.select("_79_maxAge", "label=4 Weeks");
		selenium.select("_79_warningTime", "label=2 Days");
		selenium.type("_79_graceLimit", "7");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_79_name", "test");
		selenium.type("_79_name", "test");
		selenium.click("//input[@value='Search Password Policies']");
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isTextPresent("This is a test password policy!")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
	}
}