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

package com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletfoldersperpage1;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ConfigurePortletFoldersPerPage1Test.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletFoldersPerPage1Test extends BaseTestCase {
	public void testConfigurePortletFoldersPerPage1() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Document Library Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Document Library Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Configuration", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_86_foldersPerPage")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_86_foldersPerPage", RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@id='p_p_id_86_']/div/div"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Document Library Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Document Library Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Test1 Folder1\nThis is test1 folder1."));
		assertFalse(selenium.isTextPresent(
				"Test2 Folder2\nThis is test2 folder2."));
		assertFalse(selenium.isTextPresent(
				"Test3 Folder3\nThis is test3 folder3."));
		selenium.clickAt("link=Next", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"Test1 Folder1\nThis is test1 folder1."));
		assertTrue(selenium.isTextPresent(
				"Test2 Folder2\nThis is test2 folder2."));
		assertFalse(selenium.isTextPresent(
				"Test3 Folder3\nThis is test3 folder3."));
		selenium.clickAt("link=Next", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"Test1 Folder1\nThis is test1 folder1."));
		assertFalse(selenium.isTextPresent(
				"Test2 Folder2\nThis is test2 folder2."));
		assertTrue(selenium.isTextPresent(
				"Test3 Folder3\nThis is test3 folder3."));
	}
}