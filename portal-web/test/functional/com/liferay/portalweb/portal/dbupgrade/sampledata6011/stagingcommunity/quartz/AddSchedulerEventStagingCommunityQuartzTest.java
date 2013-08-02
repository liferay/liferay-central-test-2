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

package com.liferay.portalweb.portal.dbupgrade.sampledata6011.stagingcommunity.quartz;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSchedulerEventStagingCommunityQuartzTest extends BaseTestCase {
	public void testAddSchedulerEventStagingCommunityQuartz()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities",
			RuntimeVariables.replace("Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Community Staging Community Quartz"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//tr[3]/td[1]",
				"Community Staging Community Quartz"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Manage Pages"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Schedule Publication to Live']",
			RuntimeVariables.replace("Schedule Publication to Live"));
		selenium.waitForVisible("//input[@name='_134_description']");
		selenium.type("//input[@name='_134_description']",
			RuntimeVariables.replace("Quartz Scheduler Event"));
		selenium.select("//select[@id='_134_schedulerStartDateMonth']",
			RuntimeVariables.replace("December"));
		selenium.select("//select[@id='_134_schedulerStartDateDay']",
			RuntimeVariables.replace("31"));
		selenium.select("//select[@id='_134_schedulerStartDateYear']",
			RuntimeVariables.replace("2016"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		assertEquals(RuntimeVariables.replace("View All"),
			selenium.getText("//span[1]/a"));
		selenium.clickAt("//span[1]/a", RuntimeVariables.replace("View All"));
		selenium.waitForText("//tr[3]/td[1]", "Quartz Scheduler Event");
		assertEquals(RuntimeVariables.replace("Quartz Scheduler Event"),
			selenium.getText("//tr[3]/td[1]"));
	}
}