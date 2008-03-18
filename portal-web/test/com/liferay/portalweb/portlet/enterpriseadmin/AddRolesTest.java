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
 * <a href="AddRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddRolesTest extends BaseTestCase {
	public void testAddRoles() throws Exception {
		selenium.click("link=Roles");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Add Role']");
		selenium.waitForPageToLoad("30000");
		selenium.type("_79_name",
			"1 Selenium Message Boards Role and Calendar Role");
		selenium.type("_79_description",
			"This is the Selenium Message Boards and Calendar Role for Deleting and Updating.");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.click("Link=Define Permissions");
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isTextPresent(
							"1 Selenium Message Boards Role and Calendar Role")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//input[@value='Add Portlet Permissions']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Calendar");
		selenium.waitForPageToLoad("30000");
		selenium.select("_79_scopecom.liferay.portlet.calendar.model.CalEventDELETE",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.calendar.model.CalEventUPDATE",
			"label=Enterprise");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Roles");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Define Permissions");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Add Portlet Permissions']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Blogs");
		selenium.waitForPageToLoad("30000");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryUPDATE",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryDELETE",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryADD_DISCUSSION",
			"label=Enterprise");
		selenium.select("_79_scopecom.liferay.portlet.blogs.model.BlogsEntryDELETE_DISCUSSION",
			"label=Enterprise");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
	}
}