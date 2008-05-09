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
 * <a href="ReserveTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ReserveTest extends BaseTestCase {
	public void testReserve() throws Exception {
		selenium.click("link=\u00bb");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Settings");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Reserved Screen Names");
		selenium.waitForPageToLoad("30000");
		selenium.type("_79_reservedScreenNames", "reserve");
		selenium.type("_79_reservedEmailAddresses", "reserve@reserved.com");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Return to Full Page");
		selenium.waitForPageToLoad("30000");
	}
}