/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.calendar.event.addeventrepeatingmonthlyday;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatingMonthlyDayTest extends BaseTestCase {
	public void testAddEventRepeatingMonthlyDay() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("_8_startDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_8_startDateDay", RuntimeVariables.replace("label=1"));
		selenium.select("_8_startDateYear",
			RuntimeVariables.replace("label=2010"));
		selenium.type("_8_title",
			RuntimeVariables.replace("Monthly Day Repeating Event"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='5']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_8_monthlyType")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("_8_monthlyType", RuntimeVariables.replace(""));
		selenium.type("_8_monthlyDay0", RuntimeVariables.replace("1"));
		selenium.type("_8_monthlyInterval0", RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@name='_8_endDateType' and @value='2']",
			RuntimeVariables.replace(""));
		selenium.select("_8_endDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_8_endDateDay", RuntimeVariables.replace("label=1"));
		selenium.select("_8_endDateYear", RuntimeVariables.replace("label=2011"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Events", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Monthly Day Repeating Event",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Monthly Day Repeating Event"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("1/1/10"),
			selenium.getText("//dl[@class='property-list']/dd[1]"));
		assertEquals(RuntimeVariables.replace("1/1/11"),
			selenium.getText("//dl[@class='property-list']/dd[2]"));
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Events", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		selenium.clickAt("//td[6]/span/ul/li/strong/a",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals("January", selenium.getSelectedLabel("_8_startDateMonth"));
		assertEquals("1", selenium.getSelectedLabel("_8_startDateDay"));
		assertEquals("2010", selenium.getSelectedLabel("_8_startDateYear"));
		assertEquals("Monthly Day Repeating Event",
			selenium.getValue("_8_title"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_recurrenceType' and @value='5']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_8_monthlyType")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isChecked("_8_monthlyType"));
		assertEquals("1", selenium.getValue("_8_monthlyDay0"));
		assertEquals("1", selenium.getValue("_8_monthlyInterval0"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_endDateType' and @value='2']"));
		assertEquals("January", selenium.getSelectedLabel("_8_endDateMonth"));
		assertEquals("1", selenium.getSelectedLabel("_8_endDateDay"));
		assertEquals("2011", selenium.getSelectedLabel("_8_endDateYear"));
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select", RuntimeVariables.replace("label=2010"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[contains(@href, 'javascript:_8_updateCalendar(1, 1, 2010);')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 1, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent("link=Monthly Day Repeating Event"));
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select", RuntimeVariables.replace("label=2010"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[contains(@href, 'javascript:_8_updateCalendar(2, 1, 2010);')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(2, 1, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent("link=Monthly Day Repeating Event"));
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select", RuntimeVariables.replace("label=2010"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[contains(@href, 'javascript:_8_updateCalendar(2, 8, 2010);')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(2, 8, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementNotPresent(
				"link=Monthly Day Repeating Event"));
	}
}