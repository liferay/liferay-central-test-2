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

package com.liferay.portalweb.stagingcommunity.sites.deactivatestaging;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivateStagingTest extends BaseTestCase {
	public void testActivateStaging() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.type("_134_name", RuntimeVariables.replace("Staging"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Site Settings"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText("//li[5]/span/span/a"));
				selenium.clickAt("//li[5]/span/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.select("_134_stagingType",
					RuntimeVariables.replace("label=Local Live"));

				boolean blogsChecked = selenium.isChecked(
						"_134_staged-portlet_161Checkbox");

				if (blogsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_161Checkbox",
					RuntimeVariables.replace("Blogs"));

			case 2:

				boolean bookmarksChecked = selenium.isChecked(
						"_134_staged-portlet_28Checkbox");

				if (bookmarksChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_28Checkbox",
					RuntimeVariables.replace("Bookmarks"));

			case 3:

				boolean calendarChecked = selenium.isChecked(
						"_134_staged-portlet_8Checkbox");

				if (calendarChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_8Checkbox",
					RuntimeVariables.replace("Calendar"));

			case 4:

				boolean documentLibraryChecked = selenium.isChecked(
						"_134_staged-portlet_20Checkbox");

				if (documentLibraryChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_20Checkbox",
					RuntimeVariables.replace("DocumentLibrary"));

			case 5:

				boolean documentLibraryDisplayChecked = selenium.isChecked(
						"_134_staged-portlet_110Checkbox");

				if (documentLibraryDisplayChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_110Checkbox",
					RuntimeVariables.replace("DocumentLibraryDisplay"));

			case 6:

				boolean imageGalleryChecked = selenium.isChecked(
						"_134_staged-portlet_31Checkbox");

				if (imageGalleryChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_31Checkbox",
					RuntimeVariables.replace("ImageGallery"));

			case 7:

				boolean messageBoardsChecked = selenium.isChecked(
						"_134_staged-portlet_162Checkbox");

				if (messageBoardsChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_162Checkbox",
					RuntimeVariables.replace("Message Boards"));

			case 8:

				boolean pageCommentsChecked = selenium.isChecked(
						"_134_staged-portlet_107Checkbox");

				if (pageCommentsChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_107Checkbox",
					RuntimeVariables.replace("Page Comments"));

			case 9:

				boolean pageRatingsChecked = selenium.isChecked(
						"_134_staged-portlet_108Checkbox");

				if (pageRatingsChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_108Checkbox",
					RuntimeVariables.replace("Page Ratings"));

			case 10:

				boolean pollsChecked = selenium.isChecked(
						"_134_staged-portlet_25Checkbox");

				if (pollsChecked) {
					label = 11;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_25Checkbox",
					RuntimeVariables.replace("Polls"));

			case 11:

				boolean pollsDisplayChecked = selenium.isChecked(
						"_134_staged-portlet_59Checkbox");

				if (pollsDisplayChecked) {
					label = 12;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_59Checkbox",
					RuntimeVariables.replace("Polls Display"));

			case 12:

				boolean rssChecked = selenium.isChecked(
						"_134_staged-portlet_39Checkbox");

				if (rssChecked) {
					label = 13;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_39Checkbox",
					RuntimeVariables.replace("RSS"));

			case 13:

				boolean webContentChecked = selenium.isChecked(
						"_134_staged-portlet_15Checkbox");

				if (webContentChecked) {
					label = 14;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_15Checkbox",
					RuntimeVariables.replace("Web Content"));

			case 14:

				boolean webContentDisplayChecked = selenium.isChecked(
						"_134_staged-portlet_56Checkbox");

				if (webContentDisplayChecked) {
					label = 15;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_56Checkbox",
					RuntimeVariables.replace("Web Content Display"));

			case 15:

				boolean wikiChecked = selenium.isChecked(
						"_134_staged-portlet_36Checkbox");

				if (wikiChecked) {
					label = 16;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_36Checkbox",
					RuntimeVariables.replace("Wiki"));

			case 16:

				boolean wikiDisplayChecked = selenium.isChecked(
						"_134_staged-portlet_54Checkbox");

				if (wikiDisplayChecked) {
					label = 17;

					continue;
				}

				selenium.clickAt("_134_staged-portlet_54Checkbox",
					RuntimeVariables.replace("Wiki Display Checked"));

			case 17:
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to activate local staging for Staging[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//section/div/div/div/div"));
				assertEquals(RuntimeVariables.replace("Staging (Staging)"),
					selenium.getText("//li[4]/span"));

			case 100:
				label = -1;
			}
		}
	}
}