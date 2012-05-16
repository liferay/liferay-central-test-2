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

package com.liferay.portalweb.portal.permissions.blogs.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_AddPortletMemberTest extends BaseTestCase {
	public void testSA_AddPortletMember() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(@id,'user')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(@id,'user')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(@id,'user')]"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select[@id='_125_prefixId']",
			RuntimeVariables.replace("label=Mrs."));
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("Portlet"));
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("Portlet@liferay.com"));
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("Portlet"));
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("Liferay"));
		selenium.select("//select[@id='_125_suffixId']",
			RuntimeVariables.replace("label=Jr."));
		selenium.select("//select[@id='_125_birthdayMonth']",
			RuntimeVariables.replace("label=August"));
		selenium.select("//select[@id='_125_birthdayDay']",
			RuntimeVariables.replace("label=5"));
		selenium.select("//select[@id='_125_birthdayYear']",
			RuntimeVariables.replace("label=1991"));
		selenium.select("//select[@id='_125_male']",
			RuntimeVariables.replace("label=Female"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isPartialText("//a[@id='_125_passwordLink']",
				"Password"));
		selenium.clickAt("//a[@id='_125_passwordLink']",
			RuntimeVariables.replace("Password"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_125_password1']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_125_password1']",
			RuntimeVariables.replace("password"));
		selenium.type("//input[@id='_125_password2']",
			RuntimeVariables.replace("password"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}