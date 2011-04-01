/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentryprioritynormal;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAnnouncementsEntryPriorityNormalTest extends BaseTestCase {
	public void testAddAnnouncementsEntryPriorityNormal()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Announcements Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Manage Entries",
			RuntimeVariables.replace("Manage Entries"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_84_distributionScope']",
			RuntimeVariables.replace("General"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Entry']",
			RuntimeVariables.replace("Add Entry"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_84_title']",
			RuntimeVariables.replace(
				"Announcements Entry Title Priority Normal"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_84_url']",
			RuntimeVariables.replace("http://www.liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_84_content']",
			RuntimeVariables.replace(
				"Announcements Entry Content Priority Normal"));
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_84_priority']",
			RuntimeVariables.replace("Normal"));
		selenium.keyPress("//select[@id='_84_expirationDateYear']",
			RuntimeVariables.replace("\\34"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Title Priority Normal"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace(
				"Announcements Entry Title Priority Normal"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("//input[@id='_84_title']"));
		assertTrue(selenium.isVisible("//textarea[@id='_84_content']"));
		assertTrue(selenium.isVisible("//select[@id='_84_priority']"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Announcements Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Title Priority Normal"),
			selenium.getText("//h3[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace(
				"General Announcements Entry Content Priority Normal"),
			selenium.getText("//p[@class=' entry-content entry-type-general']"));
	}
}