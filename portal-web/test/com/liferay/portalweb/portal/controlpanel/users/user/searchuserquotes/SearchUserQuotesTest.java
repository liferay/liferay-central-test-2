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

package com.liferay.portalweb.portal.controlpanel.users.user.searchuserquotes;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchUserQuotesTest extends BaseTestCase {
	public void testSearchUserQuotes() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Search All Users"),
			selenium.getText("//a[@id='_125_allUsersLink']"));
		selenium.clickAt("//a[@id='_125_allUsersLink']",
			RuntimeVariables.replace("Search All Users"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"john smith\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"john doe\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"joe smith\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"jane smith\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"jane doe\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("janedoe"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"new york\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"chicago\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"new jersey\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"boston\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"john smith\" chicago"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertTrue(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"john smith\" new york"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("janesmith"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"john smith\" new jersey"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("janesmith"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"john smith\" boston"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("janesmith"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
	}
}