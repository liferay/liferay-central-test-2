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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationservice.addorganizationservices;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationService1Test extends BaseTestCase {
	public void testAddOrganizationService1() throws Exception {
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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Selenium"),
			selenium.getText("//a[2]/strong"));
		selenium.clickAt("//a[2]/strong", RuntimeVariables.replace("Selenium"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_125_servicesLink']");
		selenium.clickAt("//a[@id='_125_servicesLink']",
			RuntimeVariables.replace("Services"));
		selenium.waitForVisible("//select[@id='_125_orgLaborTypeId0']");
		selenium.select("//select[@id='_125_orgLaborTypeId0']",
			RuntimeVariables.replace("label=Training"));
		selenium.select("//select[@id='_125_sunOpen0']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_sunClose0']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_monOpen0']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_monClose0']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_tueOpen0']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_tueClose0']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_wedOpen0']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_wedClose0']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_thuOpen0']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_thuClose0']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_friOpen0']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_friClose0']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_satOpen0']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_satClose0']",
			RuntimeVariables.replace("label=05:00"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("Training",
			selenium.getSelectedLabel("//select[@id='_125_orgLaborTypeId0']"));
	}
}