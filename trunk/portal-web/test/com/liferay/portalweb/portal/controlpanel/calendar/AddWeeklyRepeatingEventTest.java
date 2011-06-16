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

package com.liferay.portalweb.portal.controlpanel.calendar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWeeklyRepeatingEventTest extends BaseTestCase {
	public void testAddWeeklyRepeatingEvent() throws Exception {
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
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Calendar", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Events", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//td[6]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click("//div/div/span[3]/span/span/input");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_8_weeklyInterval")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.type("_8_weeklyInterval", RuntimeVariables.replace("1"));
				selenium.saveScreenShotAndSource();

				boolean sundayChecked = selenium.isChecked(
						"_8_weeklyDayPos1Checkbox");

				if (!sundayChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_8_weeklyDayPos1Checkbox",
					RuntimeVariables.replace(""));

			case 2:

				boolean mondayChecked = selenium.isChecked(
						"_8_weeklyDayPos2Checkbox");

				if (!mondayChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("_8_weeklyDayPos2Checkbox",
					RuntimeVariables.replace(""));

			case 3:

				boolean tuesdayChecked = selenium.isChecked(
						"_8_weeklyDayPos3Checkbox");

				if (!tuesdayChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("_8_weeklyDayPos3Checkbox",
					RuntimeVariables.replace(""));

			case 4:

				boolean wednesdayChecked = selenium.isChecked(
						"_8_weeklyDayPos4Checkbox");

				if (!wednesdayChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("_8_weeklyDayPos4Checkbox",
					RuntimeVariables.replace(""));

			case 5:

				boolean thursdayChecked = selenium.isChecked(
						"_8_weeklyDayPos5Checkbox");

				if (thursdayChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("_8_weeklyDayPos5Checkbox",
					RuntimeVariables.replace(""));

			case 6:

				boolean fridayChecked = selenium.isChecked(
						"_8_weeklyDayPos6Checkbox");

				if (!fridayChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("_8_weeklyDayPos6Checkbox",
					RuntimeVariables.replace(""));

			case 7:

				boolean saturdayChecked = selenium.isChecked(
						"_8_weeklyDayPos7Checkbox");

				if (!saturdayChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("_8_weeklyDayPos7Checkbox",
					RuntimeVariables.replace(""));

			case 8:
				selenium.select("_8_endDateMonth",
					RuntimeVariables.replace("label=January"));
				selenium.select("_8_endDateDay",
					RuntimeVariables.replace("label=1"));
				selenium.select("_8_endDateYear",
					RuntimeVariables.replace("label=2011"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isTextPresent(
						"Your request completed successfully."));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Year")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Year", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//select")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("//select",
					RuntimeVariables.replace("label=2010"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//a[contains(@href, 'javascript:_8_updateCalendar(0, 7, 2010);')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(0, 7, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isElementPresent(
						"link=Repeating Test Event"));
				selenium.clickAt("link=Year", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//select")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("//select",
					RuntimeVariables.replace("label=2010"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//a[contains(@href, 'javascript:_8_updateCalendar(0, 14, 2010);')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(0, 14, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isElementPresent(
						"link=Repeating Test Event"));
				selenium.clickAt("link=Year", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//select")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("//select",
					RuntimeVariables.replace("label=2010"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//a[contains(@href, 'javascript:_8_updateCalendar(0, 15, 2010);')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(0, 15, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertFalse(selenium.isElementPresent(
						"link=Repeating Test Event"));

			case 100:
				label = -1;
			}
		}
	}
}