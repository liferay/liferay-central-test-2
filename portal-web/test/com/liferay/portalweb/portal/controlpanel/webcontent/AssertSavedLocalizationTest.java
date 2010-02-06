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

package com.liferay.portalweb.portal.controlpanel.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertSavedLocalizationTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertSavedLocalizationTest extends BaseTestCase {
	public void testAssertSavedLocalization() throws Exception {
		selenium.clickAt("link=Web Content", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Hello World Localized Article",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_15_languageId",
			RuntimeVariables.replace("label=Chinese (China)"));
		selenium.waitForPageToLoad("30000");
		assertEquals("\u4e16\u754c\u60a8\u597d Page Name",
			selenium.getValue("_15_structure_el0_content"));
		assertEquals("\u4e16\u754c\u60a8\u597d Page Description",
			selenium.getValue("_15_structure_el1_content"));
		selenium.select("_15_languageId",
			RuntimeVariables.replace("label=English (United States)"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Hello World Page Name",
			selenium.getValue("_15_structure_el0_content"));
		assertEquals("Hello World Page Description",
			selenium.getValue("_15_structure_el1_content"));
	}
}