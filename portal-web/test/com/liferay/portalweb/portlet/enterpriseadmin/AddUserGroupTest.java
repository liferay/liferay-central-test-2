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
 * <a href="AddUserGroupTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddUserGroupTest extends BaseTestCase {
	public void testAddUserGroup() throws Exception {
		selenium.click("link=User Groups");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Add User Group']");
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_79_name", "Selenium");
		selenium.type("_79_description", "This is a selenium user group.");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Assign Members");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Available");
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("toggle_id_enterprise_admin_user_searchkeywords",
			"selenium");
		selenium.click("//input[@value='Search Users']");
		selenium.waitForPageToLoad("30000");
		selenium.click("document._79_fm._79_rowIds[1]");
		selenium.click("//input[@value='Update Associations']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
	}
}