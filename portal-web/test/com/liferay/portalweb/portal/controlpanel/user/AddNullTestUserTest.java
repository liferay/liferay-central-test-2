/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.user;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddNullTestUserTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddNullTestUserTest extends BaseTestCase {
	public void testAddNullTestUser() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Users")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Users", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_125_prefixId", RuntimeVariables.replace("label=Mr."));
		selenium.type("_125_screenName",
			RuntimeVariables.replace("nullscreenname"));
		selenium.type("_125_emailAddress",
			RuntimeVariables.replace("nulltest01@selenium.com"));
		selenium.type("_125_firstName", RuntimeVariables.replace("nullselen01"));
		selenium.type("_125_middleName", RuntimeVariables.replace("nulllenn"));
		selenium.type("_125_lastName", RuntimeVariables.replace("nullnium01"));
		selenium.select("_125_suffixId", RuntimeVariables.replace("label=PhD."));
		selenium.select("_125_birthdayMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_125_birthdayDay", RuntimeVariables.replace("label=1"));
		selenium.select("_125_birthdayYear",
			RuntimeVariables.replace("label=2000"));
		selenium.select("_125_male", RuntimeVariables.replace("label=Female"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}