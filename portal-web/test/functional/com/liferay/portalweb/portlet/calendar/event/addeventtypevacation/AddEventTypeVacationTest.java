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

package com.liferay.portalweb.portlet.calendar.event.addeventtypevacation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventTypeVacationTest extends BaseTestCase {
	public void testAddEventTypeVacation() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.type("//input[@id='_8_title']",
			RuntimeVariables.replace("Off to Yosemite."));
		selenium.waitForVisible("//a[@class='cke_button_cut cke_disabled']");
		selenium.waitForVisible("//iframe[contains(@title,'Rich text editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich text editor')]",
			RuntimeVariables.replace("Looking forward to camping."));
		selenium.clickAt("//input[@id='_8_timeZoneSensitiveCheckbox']",
			RuntimeVariables.replace("Time Zone Sensitive Checkbox"));
		selenium.select("//select[@id='_8_type']",
			RuntimeVariables.replace("vacation"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Off to Yosemite."),
			selenium.getText("//tr[contains(.,'Off to Yosemite.')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Vacation"),
			selenium.getText("//tr[contains(.,'Off to Yosemite.')]/td[3]/a"));
	}
}