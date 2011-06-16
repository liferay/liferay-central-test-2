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

package com.liferay.portalweb.portlet.announcements.announcementsentry.editannouncementsentrygeneral;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditAnnouncementsEntryGeneralTest extends BaseTestCase {
	public void testEditAnnouncementsEntryGeneral() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Announcements Entry Title"),
			selenium.getText("//h3[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace(
				"General Announcements Entry Content"),
			selenium.getText("//p[@class=' entry-content entry-type-general']"));
		assertFalse(selenium.isTextPresent("Edited Announcements Entry Title"));
		assertFalse(selenium.isTextPresent(
				"General Edited Announcements Entry Content"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//td[@class='edit-entry']/span/a/span"));
		selenium.clickAt("//td[@class='edit-entry']/span/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_84_title']",
			RuntimeVariables.replace("Edited Announcements Entry Title"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_84_url']",
			RuntimeVariables.replace("http://www.alloyui.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_84_content']",
			RuntimeVariables.replace("Edited Announcements Entry Content"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Edited Announcements Entry Title"),
			selenium.getText("//h3[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace(
				"General Edited Announcements Entry Content"),
			selenium.getText("//p[@class=' entry-content entry-type-general']"));
	}
}