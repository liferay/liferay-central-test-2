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

package com.liferay.portalweb.portal.permissions.enterpriseadmin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddMemberTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddMemberTest extends BaseTestCase {
	public void testAddMember() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Users"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Add User']"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_79_screenName", RuntimeVariables.replace("Member"));
		selenium.type("_79_screenName", RuntimeVariables.replace("Member"));
		selenium.type("_79_emailAddress",
			RuntimeVariables.replace("Member@liferay.com"));
		selenium.typeKeys("_79_prefixId", RuntimeVariables.replace("label=Mr."));
		selenium.type("_79_prefixId", RuntimeVariables.replace("label=Mr."));
		selenium.typeKeys("_79_firstName", RuntimeVariables.replace("Member"));
		selenium.type("_79_firstName", RuntimeVariables.replace("Member"));
		selenium.typeKeys("_79_lastName", RuntimeVariables.replace("Liferay"));
		selenium.type("_79_lastName", RuntimeVariables.replace("Liferay"));
		selenium.select("_79_suffixId", RuntimeVariables.replace("label=PhD."));
		selenium.select("_79_birthdayMonth",
			RuntimeVariables.replace("label=April"));
		selenium.select("_79_birthdayDay", RuntimeVariables.replace("label=10"));
		selenium.select("_79_birthdayYear",
			RuntimeVariables.replace("label=1986"));
		selenium.select("_79_male", RuntimeVariables.replace("label=Male"));
		selenium.typeKeys("_79_jobTitle",
			RuntimeVariables.replace("Community Member"));
		selenium.type("_79_jobTitle",
			RuntimeVariables.replace("Community Member"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Password");
		selenium.type("_79_password1", RuntimeVariables.replace("test"));
		selenium.type("_79_password2", RuntimeVariables.replace("test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Regular Roles");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Assign Regular Roles']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Available"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//tr[5]/td[1]/input");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Update Associations']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("_79_tabs2TabsBack"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
	}
}