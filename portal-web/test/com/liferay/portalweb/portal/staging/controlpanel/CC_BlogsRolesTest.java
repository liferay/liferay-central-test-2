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

package com.liferay.portalweb.portal.staging.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CC_BlogsRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CC_BlogsRolesTest extends BaseTestCase {
	public void testCC_BlogsRoles() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//input[@value='Add Portlet Permissions']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Portlet Permissions']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//tr[10]/td/a"));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_scope33CONFIGURATION",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scope33VIEW", RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.blogsADD_ENTRY",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_128_scopecom.liferay.portlet.blogs.model.BlogsEntryADD_DISCUSSION",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.blogs.model.BlogsEntryDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.blogs.model.BlogsEntryDELETE_DISCUSSION",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.blogs.model.BlogsEntryPERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.blogs.model.BlogsEntryUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.blogs.model.BlogsEntryUPDATE_DISCUSSION",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.blogs.model.BlogsEntryVIEW",
			RuntimeVariables.replace("label="));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}