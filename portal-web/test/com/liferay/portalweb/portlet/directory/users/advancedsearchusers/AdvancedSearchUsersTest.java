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

package com.liferay.portalweb.portlet.directory.users.advancedsearchusers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AdvancedSearchUsersTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchUsersTest extends BaseTestCase {
	public void testAdvancedSearchUsers() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Directory Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Directory Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean advancedVisible = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_11_firstName")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_11_firstName",
					RuntimeVariables.replace("TestFirst"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_firstName",
					RuntimeVariables.replace("TestFirstA"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_firstName", RuntimeVariables.replace(""));
				selenium.type("_11_middleName",
					RuntimeVariables.replace("TestMiddle"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_middleName",
					RuntimeVariables.replace("TestMiddleA"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_middleName", RuntimeVariables.replace(""));
				selenium.type("_11_lastName",
					RuntimeVariables.replace("TestLast"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_lastName",
					RuntimeVariables.replace("TestLastA"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_lastName", RuntimeVariables.replace(""));
				selenium.type("_11_screenName",
					RuntimeVariables.replace("TestSN"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_screenName",
					RuntimeVariables.replace("TestSNA"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_screenName", RuntimeVariables.replace(""));
				selenium.type("_11_emailAddress",
					RuntimeVariables.replace("TestEMail1@liferay.com"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=TestFirst1"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_11_emailAddress",
					RuntimeVariables.replace("TestEMail@liferay.com"));
				selenium.clickAt("//div[@id='toggle_id_directory_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=TestFirst1"));
				selenium.type("_11_emailAddress", RuntimeVariables.replace(""));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 100:
				label = -1;
			}
		}
	}
}