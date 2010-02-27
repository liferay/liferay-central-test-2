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

package com.liferay.portalweb.portal.permissions.blogs.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Member_AssertActionsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Member_AssertActionsTest extends BaseTestCase {
	public void testMember_AssertActions() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Blogs Permissions Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Permissions Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"link=Permissions Blogs Test Entry"));
		assertTrue(selenium.isElementPresent("//span[2]/a"));
		assertTrue(selenium.isElementPresent("link=Subscribe to this blog."));
		assertFalse(selenium.isElementPresent("link=Delete"));
		assertFalse(selenium.isElementPresent(
				"//input[@value='Add Blog Entry']"));
		assertFalse(selenium.isElementPresent("link=Permissions"));
		assertFalse(selenium.isElementPresent("link=Edit"));
		selenium.clickAt("link=Permissions Blogs Test Entry",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//div[3]/ul/li[3]/span/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isElementPresent("link=Delete"));
		assertFalse(selenium.isElementPresent(
				"//input[@value='Add Blog Entry']"));
		assertFalse(selenium.isElementPresent("link=Permissions"));
		assertFalse(selenium.isElementPresent("link=Edit"));
		assertTrue(selenium.isElementPresent("link=Add Comment"));
	}
}