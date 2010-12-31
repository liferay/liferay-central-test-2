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

package com.liferay.portalweb.portal.dbupgrade.sampledata512.announcementsdelivery.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAnnouncementDeliveryTest extends BaseTestCase {
	public void testAddAnnouncementDelivery() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=My Account")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=My Account", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Alerts and Announcements",
					RuntimeVariables.replace(""));

				boolean generalEmailChecked = selenium.isChecked(
						"_2_announcementsTypegeneralEmailCheckbox");

				if (generalEmailChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_2_announcementsTypegeneralEmailCheckbox",
					RuntimeVariables.replace(""));

			case 2:

				boolean generalSmsChecked = selenium.isChecked(
						"_2_announcementsTypegeneralSmsCheckbox");

				if (generalSmsChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("_2_announcementsTypegeneralSmsCheckbox",
					RuntimeVariables.replace(""));

			case 3:

				boolean generalWebsiteChecked = selenium.isChecked(
						"_2_announcementsTypegeneralWebsiteCheckbox");

				if (generalWebsiteChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("_2_announcementsTypegeneralWebsiteCheckbox",
					RuntimeVariables.replace(""));

			case 4:

				boolean newsEmailChecked = selenium.isChecked(
						"_2_announcementsTypenewsEmailCheckbox");

				if (newsEmailChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("_2_announcementsTypenewsEmailCheckbox",
					RuntimeVariables.replace(""));

			case 5:

				boolean newsSmsChecked = selenium.isChecked(
						"_2_announcementsTypenewsSmsCheckbox");

				if (newsSmsChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("_2_announcementsTypenewsSmsCheckbox",
					RuntimeVariables.replace(""));

			case 6:

				boolean newsWebsiteChecked = selenium.isChecked(
						"_2_announcementsTypenewsWebsiteCheckbox");

				if (newsWebsiteChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("_2_announcementsTypenewsWebsiteCheckbox",
					RuntimeVariables.replace(""));

			case 7:

				boolean testEmailChecked = selenium.isChecked(
						"_2_announcementsTypetestEmailCheckbox");

				if (testEmailChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("_2_announcementsTypetestEmailCheckbox",
					RuntimeVariables.replace(""));

			case 8:

				boolean testSmsChecked = selenium.isChecked(
						"_2_announcementsTypetestSmsCheckbox");

				if (testSmsChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("_2_announcementsTypetestSmsCheckbox",
					RuntimeVariables.replace(""));

			case 9:

				boolean testWebsiteChecked = selenium.isChecked(
						"_2_announcementsTypetestWebsiteCheckbox");

				if (testWebsiteChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("_2_announcementsTypetestWebsiteCheckbox",
					RuntimeVariables.replace(""));

			case 10:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//div[2]/div/div/div"));
				assertTrue(selenium.isChecked(
						"_2_announcementsTypegeneralEmailCheckbox"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"_2_announcementsTypegeneralSmsCheckbox"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"_2_announcementsTypegeneralWebsiteCheckbox"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"_2_announcementsTypenewsSmsCheckbox"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"_2_announcementsTypenewsSmsCheckbox"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"_2_announcementsTypenewsWebsiteCheckbox"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"_2_announcementsTypetestEmailCheckbox"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"_2_announcementsTypetestSmsCheckbox"));
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isChecked(
						"_2_announcementsTypetestWebsiteCheckbox"));
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}