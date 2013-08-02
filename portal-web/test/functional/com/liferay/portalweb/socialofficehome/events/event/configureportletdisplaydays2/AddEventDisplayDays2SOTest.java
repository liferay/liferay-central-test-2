/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficehome.events.event.configureportletdisplaydays2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventDisplayDays2SOTest extends BaseTestCase {
	public void testAddEventDisplayDays2SO() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.waitForVisible("link=Calendar Test Page");
				selenium.clickAt("link=Calendar Test Page",
					RuntimeVariables.replace("Calendar Test Page"));
				selenium.waitForPageToLoad("30000");

				String dayNumber = selenium.getFirstNumberIncrement(
						"//div[@class='day-number']");
				RuntimeVariables.setValue("dayNumber", dayNumber);
				selenium.clickAt("//input[@value='Add Event']",
					RuntimeVariables.replace("Add Event"));
				selenium.waitForPageToLoad("30000");

				boolean newStartDateDaySelectPresent = selenium.isElementPresent(
						"//select[@id='_8_startdateday']");

				if (!newStartDateDaySelectPresent) {
					label = 2;

					continue;
				}

				selenium.select("//select[@id='_8_startdateday']",
					RuntimeVariables.replace(RuntimeVariables.getValue(
							"dayNumber")));

			case 2:

				boolean oldStartDateDaySelectPresent = selenium.isElementPresent(
						"//select[@id='_8_startDateDay']");

				if (!oldStartDateDaySelectPresent) {
					label = 3;

					continue;
				}

				selenium.select("//select[@id='_8_startDateDay']",
					RuntimeVariables.replace(RuntimeVariables.getValue(
							"dayNumber")));

			case 3:
				selenium.type("//input[@id='_8_title']",
					RuntimeVariables.replace("Calendar Event Title"));
				Thread.sleep(1000);
				selenium.waitForVisible("//span[.='Source']");
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForVisible(
					"//td[@id='cke_contents__8_editor']/textarea");
				selenium.type("//td[@id='cke_contents__8_editor']/textarea",
					RuntimeVariables.replace("Calendar Event Description"));
				selenium.waitForVisible("//span[.='Source']");
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForVisible(
					"//td[@id='cke_contents__8_editor']/iframe");
				selenium.selectFrame(
					"//td[@id='cke_contents__8_editor']/iframe");
				selenium.waitForText("//body", "Calendar Event Description");
				selenium.selectFrame("relative=top");
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Events"),
					selenium.getText("link=Events"));
				selenium.clickAt("link=Events",
					RuntimeVariables.replace("Events"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("Calendar Event Title"),
					selenium.getText("//td[3]/a"));
				assertEquals(RuntimeVariables.replace("Anniversary"),
					selenium.getText("//td[4]/a"));

			case 100:
				label = -1;
			}
		}
	}
}