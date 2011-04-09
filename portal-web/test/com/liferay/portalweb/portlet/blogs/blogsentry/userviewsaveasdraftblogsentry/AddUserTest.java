/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.blogs.blogsentry.userviewsaveasdraftblogsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserTest extends BaseTestCase {
	public void testAddUser() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Users", RuntimeVariables.replace("Users"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_125_prefixId']",
			RuntimeVariables.replace("Mr."));
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("usersn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("userea@liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("userfn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_middleName']",
			RuntimeVariables.replace(""));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("userln"));
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_125_birthdayMonth']",
			RuntimeVariables.replace("April"));
		selenium.select("//select[@id='_125_birthdayDay']",
			RuntimeVariables.replace("10"));
		selenium.select("//select[@id='_125_birthdayYear']",
			RuntimeVariables.replace("1986"));
		selenium.select("//select[@id='_125_male']",
			RuntimeVariables.replace("Male"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("usersn",
			selenium.getValue("//input[@id='_125_screenName']"));
		assertEquals("userea@liferay.com",
			selenium.getValue("//input[@id='_125_emailAddress']"));
		assertEquals("userfn",
			selenium.getValue("//input[@id='_125_firstName']"));
		assertEquals("", selenium.getValue("//input[@id='_125_middleName']"));
		assertEquals("userln", selenium.getValue("//input[@id='_125_lastName']"));
	}
}