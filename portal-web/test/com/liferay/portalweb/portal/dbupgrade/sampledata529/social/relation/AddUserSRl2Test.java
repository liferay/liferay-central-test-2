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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.social.relation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSRl2Test extends BaseTestCase {
	public void testAddUserSRl2() throws Exception {
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
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Users", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Add", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.select("_125_prefixId", RuntimeVariables.replace("label=Mrs."));
		selenium.type("_125_screenName",
			RuntimeVariables.replace("socialrelationsn2"));
		selenium.saveScreenShotAndSource();
		selenium.type("_125_emailAddress",
			RuntimeVariables.replace("socialrelationea2@liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("_125_firstName",
			RuntimeVariables.replace("socialrelationfn2"));
		selenium.saveScreenShotAndSource();
		selenium.type("_125_middleName",
			RuntimeVariables.replace("socialrelationmn2"));
		selenium.saveScreenShotAndSource();
		selenium.type("_125_lastName",
			RuntimeVariables.replace("socialrelationln2"));
		selenium.saveScreenShotAndSource();
		selenium.select("_125_suffixId", RuntimeVariables.replace("label=Sr."));
		selenium.select("_125_birthdayMonth",
			RuntimeVariables.replace("label=September"));
		selenium.select("_125_birthdayDay", RuntimeVariables.replace("label=24"));
		selenium.select("_125_birthdayYear",
			RuntimeVariables.replace("label=1984"));
		selenium.select("_125_male", RuntimeVariables.replace("label=Female"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("socialrelationsn2", selenium.getValue("_125_screenName"));
		assertEquals("socialrelationea2@liferay.com",
			selenium.getValue("_125_emailAddress"));
		assertEquals("socialrelationfn2", selenium.getValue("_125_firstName"));
		assertEquals("socialrelationmn2", selenium.getValue("_125_middleName"));
		assertEquals("socialrelationln2", selenium.getValue("_125_lastName"));
	}
}