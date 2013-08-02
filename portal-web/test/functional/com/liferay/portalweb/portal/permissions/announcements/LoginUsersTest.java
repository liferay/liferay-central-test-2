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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LoginUsersTest extends BaseTestCase {
	public void testLoginUsers() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Welcome", RuntimeVariables.replace("Welcome"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_58_login']",
			RuntimeVariables.replace("aa@liferay.com"));
		selenium.type("//input[@id='_58_password']",
			RuntimeVariables.replace("password"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace("Sign In"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace("I Agree"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='password1']",
			RuntimeVariables.replace("test"));
		selenium.type("//input[@id='password2']",
			RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='reminderQueryAnswer']",
			RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("You are signed in as AA Liferay."));
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace("Sign Out"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='Sign In']");
		assertTrue(selenium.isVisible("//input[@value='Sign In']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Welcome", RuntimeVariables.replace("Welcome"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_58_login']",
			RuntimeVariables.replace("member@liferay.com"));
		selenium.type("//input[@id='_58_password']",
			RuntimeVariables.replace("password"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace("Sign In"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace("I Agree"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='password1']",
			RuntimeVariables.replace("test"));
		selenium.type("//input[@id='password2']",
			RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//input[@id='reminderQueryAnswer']");
		selenium.type("//input[@id='reminderQueryAnswer']",
			RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You are signed in as Member Liferay."));
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace("Sign Out"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='Sign In']");
		assertTrue(selenium.isVisible("//input[@value='Sign In']"));
	}
}