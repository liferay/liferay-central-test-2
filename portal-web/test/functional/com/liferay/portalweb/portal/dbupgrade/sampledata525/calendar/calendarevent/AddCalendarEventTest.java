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

package com.liferay.portalweb.portal.dbupgrade.sampledata525.calendar.calendarevent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCalendarEventTest extends BaseTestCase {
	public void testAddCalendarEvent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertTrue(selenium.isPartialText("//h2[@class='user-greeting']/span",
				"Welcome"));
		selenium.mouseOver("//h2[@class='user-greeting']/span");
		selenium.clickAt("//h2[@class='user-greeting']/span",
			RuntimeVariables.replace("Welcome"));
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities",
			RuntimeVariables.replace("Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Calendar Event Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Calendar Event Page",
			RuntimeVariables.replace("Calendar Event Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_8_startDateMonth']",
			RuntimeVariables.replace("May"));
		selenium.select("//select[@id='_8_startDateDay']",
			RuntimeVariables.replace("label=31"));
		selenium.select("//select[@id='_8_startDateYear']",
			RuntimeVariables.replace("2010"));
		selenium.select("//select[@name='_8_startDateHour']",
			RuntimeVariables.replace("12"));
		selenium.clickAt("//td[2]/input[2]", RuntimeVariables.replace(""));
		selenium.select("//select[@name='_8_startDateMinute']",
			RuntimeVariables.replace(":00"));
		selenium.select("//select[@name='_8_startDateAmPm']",
			RuntimeVariables.replace("PM"));
		selenium.type("//tr[6]/td[2]/input",
			RuntimeVariables.replace("Hashi's birthday bash"));
		selenium.type("//textarea",
			RuntimeVariables.replace("This is so much fun!"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("5/31/10"),
			selenium.getText("//td[2]"));
		assertEquals(RuntimeVariables.replace("Hashi's birthday bash"),
			selenium.getText("//td[2]/span"));
		assertEquals(RuntimeVariables.replace("This is so much fun!"),
			selenium.getText("//tr[5]/td[2]"));
	}
}