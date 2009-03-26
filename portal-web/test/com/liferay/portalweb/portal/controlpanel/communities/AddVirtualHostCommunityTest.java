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
 * <a href="AddVirtualHostCommunityTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddVirtualHostCommunityTest extends BaseTestCase {
	public void testAddVirtualHostCommunity() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_134_name",
			RuntimeVariables.replace("Virtual Hosting Test Communit"));
		selenium.type("_134_name",
			RuntimeVariables.replace("Virtual Hosting Test Community"));
		selenium.typeKeys("_134_description",
			RuntimeVariables.replace(
				"This is the Virtual Hosting test communit."));
		selenium.type("_134_description",
			RuntimeVariables.replace(
				"This is the Virtual Hosting test community."));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertTrue(selenium.isTextPresent("Virtual Hosting Test Community"));
	}
}