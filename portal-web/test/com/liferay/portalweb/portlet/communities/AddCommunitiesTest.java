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

package com.liferay.portalweb.portlet.communities;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * <a href="AddCommunitiesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddCommunitiesTest extends BaseTestCase {
	public void testAddCommunities() throws Exception {
		selenium.click("//input[@value='Add Community']");
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_29_name", "Test Community");
		selenium.type("_29_description", "This is a test community!");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.type("_29_name", "Test");
		selenium.click("//input[@value='Search Communities']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Assign Members");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Available");
		selenium.waitForPageToLoad("30000");
		selenium.click("document._29_fm._29_rowIds[1]");
		selenium.click("document._29_fm._29_rowIds[2]");
		selenium.click("document._29_fm._29_rowIds[3]");
		selenium.click("document._29_fm._29_rowIds[4]");
		selenium.click("document._29_fm._29_rowIds[5]");
		selenium.click("//input[@value='Update Associations']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Current");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Test DLC 1"));
		verifyTrue(selenium.isTextPresent("Test DLC 10"));
		verifyTrue(selenium.isTextPresent("Test DLC 2"));
		selenium.click("link=Organizations");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Available");
		selenium.waitForPageToLoad("30000");
		selenium.click("document._29_fm._29_rowIds[7]");
		selenium.click("//input[@value='Update Associations']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Current");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Liferay Los Angeles"));
		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
	}
}