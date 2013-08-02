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

package com.liferay.portalweb.portal.controlpanel.calendar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class GetConcertEventsTest extends BaseTestCase {
	public void testGetConcertEvents() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Calendar", RuntimeVariables.replace("Calendar"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Day", RuntimeVariables.replace("Day"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='day-grid']");
		selenium.select("//select", RuntimeVariables.replace("Concert"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Caedmon Call Concert!");
		assertTrue(selenium.isElementPresent("link=Caedmon Call Concert!"));
		assertTrue(selenium.isElementNotPresent("link=Off to Yosemite!"));
		assertTrue(selenium.isElementNotPresent("link=Test Event"));
		selenium.clickAt("link=Week", RuntimeVariables.replace("Week"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//table[@class='calendar']");
		selenium.select("//select", RuntimeVariables.replace("Concert"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Caedmon Call Concert!");
		assertTrue(selenium.isElementPresent("link=Caedmon Call Concert!"));
		assertTrue(selenium.isElementNotPresent("link=Off to Yosemite!"));
		assertTrue(selenium.isElementNotPresent("link=Test Event"));
		selenium.clickAt("link=Month", RuntimeVariables.replace("Month"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//table[@class='calendar']/tbody/tr[2]");
		selenium.select("//select", RuntimeVariables.replace("Concert"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Caedmon Call Concert!");
		assertTrue(selenium.isElementPresent("link=Caedmon Call Concert!"));
		assertTrue(selenium.isElementNotPresent("link=Off to Yosemite!"));
		assertTrue(selenium.isElementNotPresent("link=Test Event"));
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Caedmon Call Concert!"));
	}
}