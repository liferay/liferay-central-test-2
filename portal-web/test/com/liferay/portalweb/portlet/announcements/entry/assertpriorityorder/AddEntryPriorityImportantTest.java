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

package com.liferay.portalweb.portlet.announcements.entry.assertpriorityorder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddEntryPriorityImportantTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddEntryPriorityImportantTest extends BaseTestCase {
	public void testAddEntryPriorityImportant() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Announcements Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.clickAt("link=Manage Entries", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_84_distributionScope",
			RuntimeVariables.replace("label=General"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value='Add Entry']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//input[@value='Add Entry']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_84_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_84_title",
			RuntimeVariables.replace("Important Priority Announcement"));
		selenium.type("_84_url", RuntimeVariables.replace("www.liferay.com"));
		selenium.type("_84_content",
			RuntimeVariables.replace(
				"Hi everyone. This is an important priority announcement."));
		selenium.select("_84_priority",
			RuntimeVariables.replace("label=Important"));
		selenium.select("_84_displayDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("//select[3]", RuntimeVariables.replace("label=2005"));
		selenium.select("//div[2]/select[1]",
			RuntimeVariables.replace("label=12"));
		selenium.select("//div[2]/select[2]",
			RuntimeVariables.replace("label=:00"));
		selenium.select("//div[2]/select[3]",
			RuntimeVariables.replace("label=AM"));
		selenium.select("//tr[12]/td[2]/div[1]/div[1]/select[3]",
			RuntimeVariables.replace("label=2015"));
		selenium.select("//tr[12]/td[2]/div[1]/div[1]/select[1]",
			RuntimeVariables.replace("label=December"));
		selenium.select("//tr[12]/td[2]/div[2]/select[1]",
			RuntimeVariables.replace("label=11"));
		selenium.select("//tr[12]/td[2]/div[2]/select[2]",
			RuntimeVariables.replace("label=:59"));
		selenium.select("//tr[12]/td[2]/div[2]/select[3]",
			RuntimeVariables.replace("label=PM"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Entries", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"link=Important Priority Announcement"));
		assertTrue(selenium.isTextPresent(
				"Hi everyone. This is an important priority announcement."));
	}
}