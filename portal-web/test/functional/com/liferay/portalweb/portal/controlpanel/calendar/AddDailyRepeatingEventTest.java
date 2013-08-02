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
public class AddDailyRepeatingEventTest extends BaseTestCase {
	public void testAddDailyRepeatingEvent() throws Exception {
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
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div/div/span[2]/span/span/input",
			RuntimeVariables.replace("Repeat Daily"));
		selenium.waitForVisible("//input[@name='_8_dailyType']");
		selenium.clickAt("//input[@name='_8_dailyType']",
			RuntimeVariables.replace(""));
		selenium.type("//input[@id='_8_dailyInterval']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@name='_8_endDateType' and @value='2']",
			RuntimeVariables.replace("End by"));
		selenium.select("//select[@id='_8_enddatemonth']",
			RuntimeVariables.replace("label=January"));
		selenium.select("//select[@id='_8_enddateday']",
			RuntimeVariables.replace("label=1"));
		selenium.select("//select[@id='_8_enddateyear']",
			RuntimeVariables.replace("label=2011"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForPartialText("//select", "2010");
		selenium.select("//select", RuntimeVariables.replace("2010"));
		selenium.waitForVisible(
			"//a[contains(@href, 'javascript:_8_updateCalendar(0, 1, 2010);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(0, 1, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Repeating Test Event");
		assertTrue(selenium.isVisible("link=Repeating Test Event"));
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForPartialText("//select", "2010");
		selenium.select("//select", RuntimeVariables.replace("2010"));
		selenium.waitForVisible(
			"//a[contains(@href, 'javascript:_8_updateCalendar(0, 5, 2010);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(0, 5, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Repeating Test Event");
		assertTrue(selenium.isVisible("link=Repeating Test Event"));
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForPartialText("//select", "2011");
		selenium.select("//select", RuntimeVariables.replace("2011"));
		selenium.waitForVisible(
			"//a[contains(@href, 'javascript:_8_updateCalendar(1, 1, 2011);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 1, 2011);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementNotPresent("link=Repeating Test Event");
		assertTrue(selenium.isElementNotPresent("link=Repeating Test Event"));
	}
}