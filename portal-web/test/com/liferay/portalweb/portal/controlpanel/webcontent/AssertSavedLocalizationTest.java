/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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