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

package com.liferay.portalweb.portal.permissions.documentlibrary.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Guest_AssertViewDocumentsTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertViewDocumentsTest extends BaseTestCase {
	public void testGuest_AssertViewDocuments() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Document Library Permissions Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Document Library Permissions Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Documents", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"link=Member Permissions Edited Test Document.txt"));
		assertFalse(selenium.isElementPresent(
				"link=Admin Permissions Edited Test Document.txt"));
		assertTrue(selenium.isTextPresent(
				"Admin Permissions Edited Test Document.txt"));
		selenium.clickAt("link=Recent Documents", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"link=Member Permissions Edited Test Document.txt"));
		assertFalse(selenium.isElementPresent(
				"link=Admin Permissions Edited Test Document.txt"));
		assertTrue(selenium.isTextPresent(
				"Admin Permissions Edited Test Document.txt"));
		selenium.clickAt("link=View", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=TXT"));
		assertTrue(selenium.isElementPresent(
				"link=Member Permissions Edited Test Document.txt"));
	}
}