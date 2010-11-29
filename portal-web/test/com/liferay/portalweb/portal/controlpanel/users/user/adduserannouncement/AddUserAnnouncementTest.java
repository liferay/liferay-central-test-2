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

package com.liferay.portalweb.portal.controlpanel.users.user.adduserannouncement;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserAnnouncementTest extends BaseTestCase {
	public void testAddUserAnnouncement() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Users")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Users", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_125_keywords", RuntimeVariables.replace("selen01"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("User Name"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("announcementsLink", RuntimeVariables.replace(""));
		selenium.clickAt("_125_announcementsTypegeneralEmailCheckbox",
			RuntimeVariables.replace(""));
		selenium.uncheck("_125_announcementsTypegeneralSmsCheckbox");
		selenium.uncheck("_125_announcementsTypenewsEmailCheckbox");
		selenium.clickAt("_125_announcementsTypenewsSmsCheckbox",
			RuntimeVariables.replace(""));
		selenium.clickAt("_125_announcementsTypetestEmailCheckbox",
			RuntimeVariables.replace(""));
		selenium.uncheck("_125_announcementsTypetestSmsCheckbox");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div[1]"));
		assertTrue(selenium.isChecked(
				"_125_announcementsTypegeneralEmailCheckbox"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked(
				"_125_announcementsTypegeneralSmsCheckbox"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked(
				"_125_announcementsTypenewsEmailCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_125_announcementsTypenewsSmsCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_125_announcementsTypetestEmailCheckbox"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked("_125_announcementsTypetestSmsCheckbox"));
		selenium.saveScreenShotAndSource();
	}
}