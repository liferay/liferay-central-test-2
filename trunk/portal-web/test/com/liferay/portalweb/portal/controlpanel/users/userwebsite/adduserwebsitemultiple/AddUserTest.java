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

package com.liferay.portalweb.portal.controlpanel.users.userwebsite.adduserwebsitemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserTest extends BaseTestCase {
	public void testAddUser() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Add", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.select("_125_prefixId",
					RuntimeVariables.replace("label=Mr."));
				selenium.type("_125_screenName",
					RuntimeVariables.replace("selenium01"));
				selenium.saveScreenShotAndSource();
				selenium.type("_125_emailAddress",
					RuntimeVariables.replace("test01@selenium.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("_125_firstName",
					RuntimeVariables.replace("selen01"));
				selenium.saveScreenShotAndSource();
				selenium.type("_125_middleName",
					RuntimeVariables.replace("lenn"));
				selenium.saveScreenShotAndSource();
				selenium.type("_125_lastName",
					RuntimeVariables.replace("nium01"));
				selenium.saveScreenShotAndSource();
				selenium.select("_125_suffixId",
					RuntimeVariables.replace("label=Phd."));
				selenium.select("_125_birthdayMonth",
					RuntimeVariables.replace("label=April"));
				selenium.select("_125_birthdayDay",
					RuntimeVariables.replace("label=10"));
				selenium.select("_125_birthdayYear",
					RuntimeVariables.replace("label=1986"));
				selenium.select("_125_male",
					RuntimeVariables.replace("label=Male"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//section/div/div/div/div[1]"));
				assertEquals("selenium01", selenium.getValue("_125_screenName"));
				assertEquals("test01@selenium.com",
					selenium.getValue("_125_emailAddress"));
				assertEquals("selen01", selenium.getValue("_125_firstName"));
				assertEquals("lenn", selenium.getValue("_125_middleName"));
				assertEquals("nium01", selenium.getValue("_125_lastName"));
				selenium.clickAt("//a[@id='_125_passwordLink']",
					RuntimeVariables.replace("Password Link"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_125_password1")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.type("_125_password1",
					RuntimeVariables.replace("password"));
				selenium.saveScreenShotAndSource();
				selenium.type("_125_password2",
					RuntimeVariables.replace("password"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//section/div/div/div/div[1]"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Sign Out")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isVisible("//input[@value='Sign In']"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_58_login")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.type("_58_login",
					RuntimeVariables.replace("test01@selenium.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("_58_password",
					RuntimeVariables.replace("password"));
				selenium.saveScreenShotAndSource();

				boolean rememberMeCheckboxChecked1 = selenium.isChecked(
						"_58_rememberMeCheckbox");

				if (rememberMeCheckboxChecked1) {
					label = 2;

					continue;
				}

				selenium.clickAt("_58_rememberMeCheckbox",
					RuntimeVariables.replace(""));

			case 2:
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean iAgreeVisible1 = selenium.isElementPresent(
						"//span/input");

				if (!iAgreeVisible1) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@value='I Agree']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 3:

				boolean newPasswordVisible1 = selenium.isElementPresent(
						"//span/input");

				if (!newPasswordVisible1) {
					label = 4;

					continue;
				}

				selenium.type("password1", RuntimeVariables.replace("test"));
				selenium.saveScreenShotAndSource();
				selenium.type("password2", RuntimeVariables.replace("test"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 4:

				boolean passwordReminderVisible1 = selenium.isElementPresent(
						"reminderQueryAnswer");

				if (!passwordReminderVisible1) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace(
						"Please choose a reminder query."),
					selenium.getText("//form/div[1]"));
				selenium.type("reminderQueryAnswer",
					RuntimeVariables.replace("test"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 5:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Sign Out")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isVisible("//input[@value='Sign In']"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_58_login")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.type("_58_login",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("_58_password", RuntimeVariables.replace("test"));
				selenium.saveScreenShotAndSource();

				boolean rememberMeCheckboxChecked2 = selenium.isChecked(
						"_58_rememberMeCheckbox");

				if (rememberMeCheckboxChecked2) {
					label = 6;

					continue;
				}

				selenium.clickAt("_58_rememberMeCheckbox",
					RuntimeVariables.replace(""));

			case 6:
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}