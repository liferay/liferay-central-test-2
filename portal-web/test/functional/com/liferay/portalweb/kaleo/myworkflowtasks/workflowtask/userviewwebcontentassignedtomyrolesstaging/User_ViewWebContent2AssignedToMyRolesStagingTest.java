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

package com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.userviewwebcontentassignedtomyrolesstaging;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewWebContent2AssignedToMyRolesStagingTest
	extends BaseTestCase {
	public void testUser_ViewWebContent2AssignedToMyRolesStaging()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Workflow Tasks",
			RuntimeVariables.replace("My Workflow Tasks"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Web Content2 Name"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt last']/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Web Content1 Name"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt last']/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt last']/td[3]/a"));
		selenium.clickAt("//tr[@class='portlet-section-body results-row']/td[2]/a",
			RuntimeVariables.replace("Web Content2 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("My Workflow Tasks"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertEquals(RuntimeVariables.replace("Review: Web Content2 Name"),
			selenium.getText("//div[@class='taglib-header ']/h1/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Preview of Web Content"),
			selenium.getText("xPath=(//div[@class='lfr-panel-title']/span)[4]"));
		assertEquals(RuntimeVariables.replace("Web Content2 Name"),
			selenium.getText("//h3[@class='task-content-title']"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='lfr-panel-content'])[4]",
				"Web Content2 Content"));
	}
}