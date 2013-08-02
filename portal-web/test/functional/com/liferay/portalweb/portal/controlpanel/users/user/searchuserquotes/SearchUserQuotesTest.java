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

package com.liferay.portalweb.portal.controlpanel.users.user.searchuserquotes;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchUserQuotesTest extends BaseTestCase {
	public void testSearchUserQuotes() throws Exception {
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
		selenium.clickAt("link=Search All Users",
			RuntimeVariables.replace("Search All Users"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("\"john smith\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
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
		assertTrue(selenium.isTextPresent("janesmith"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
	}
}