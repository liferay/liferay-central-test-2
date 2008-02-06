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
 * <a href="AddUserTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddUserTest extends BaseTestCase {
	public void testAddUser() throws Exception {
		selenium.click("link=Users");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Add User']");
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_79_screenName", "selenium");
		selenium.type("_79_emailAddress", "test@selenium.com");
		selenium.typeKeys("_79_firstName", "this");
		selenium.typeKeys("_79_middleName", "is");
		selenium.typeKeys("_79_lastName", "selenium");
		selenium.typeKeys("_79_jobTitle", "Selenium Test");
		selenium.typeKeys("_79_prefixId", "label=Mr.");
		selenium.typeKeys("_79_suffixId", "label=Jr.");
		selenium.typeKeys("_79_birthdayMonth", "label=December");
		selenium.typeKeys("_79_birthdayDay", "label=25");
		selenium.typeKeys("_79_birthdayYear", "label=1986");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Add']");
		selenium.waitForPageToLoad("30000");
		selenium.type("_79_address", "test@selenium.com");
		selenium.select("_79_typeId", "label=E-mail");
		selenium.click("_79_primaryCheckbox");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.type("_79_comments", "This is a test comment!");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
		selenium.type("toggle_id_enterprise_admin_user_searchkeywords",
			"selenium");
		selenium.click("//input[@value='Search Users']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Selenium Test"));
		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
	}
}