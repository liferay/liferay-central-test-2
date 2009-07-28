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

package com.liferay.portalweb.portal.permissions.documentlibrary;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Member_AssertCannotEditPermissionsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Member_AssertCannotEditPermissionsTest extends BaseTestCase {
	public void testMember_AssertCannotEditPermissions()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.click(RuntimeVariables.replace(
						"link=Document Library Permissions Test Page"));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Permissions"));
				selenium.click(RuntimeVariables.replace(
						"link=Permissions Edited Test Folder"));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Permissions"));
				selenium.click(RuntimeVariables.replace(
						"link=Permissions Test Subfolder"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=View"));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent("link=Permissions"));
				selenium.click(RuntimeVariables.replace(
						"link=Document Library Permissions Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"link=Permissions2 Test2 Subfolder2"));
				selenium.waitForPageToLoad("30000");

				boolean MemberDLActionBar = selenium.isElementPresent(
						"//strong/span");

				if (!MemberDLActionBar) {
					label = 2;

					continue;
				}

				selenium.click("//strong/span");
				selenium.click(RuntimeVariables.replace("link=Permissions"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"You do not have the required permissions."));

			case 2:
			case 100:
				label = -1;
			}
		}
	}
}