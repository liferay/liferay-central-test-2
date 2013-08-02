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

package com.liferay.portalweb.portal.dbupgrade.sampledata6010.community.membershiprequest;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserMRTest extends BaseTestCase {
	public void testAddUserMR() throws Exception {
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
		selenium.clickAt("link=Users", RuntimeVariables.replace("Users"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_125_prefixId']",
			RuntimeVariables.replace("label=Mr."));
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("requestmembersn"));
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("requestmemberea@liferay.com"));
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("requestmemberfn"));
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("requestmemberln"));
		selenium.select("//select[@id='_125_birthdayMonth']",
			RuntimeVariables.replace("label=April"));
		selenium.select("//select[@id='_125_birthdayDay']",
			RuntimeVariables.replace("label=10"));
		selenium.select("//select[@id='_125_birthdayYear']",
			RuntimeVariables.replace("label=1986"));
		selenium.select("//select[@id='_125_male']",
			RuntimeVariables.replace("label=Male"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("requestmembersn",
			selenium.getValue("//input[@id='_125_screenName']"));
		assertEquals("requestmemberea@liferay.com",
			selenium.getValue("//input[@id='_125_emailAddress']"));
		assertEquals("requestmemberfn",
			selenium.getValue("//input[@id='_125_firstName']"));
		assertEquals("requestmemberln",
			selenium.getValue("//input[@id='_125_lastName']"));
	}
}