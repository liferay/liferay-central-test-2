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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletMemberTest extends BaseTestCase {
	public void testAddPortletMember() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.clickAt("link=Users", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Add");
		selenium.clickAt("link=Add", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("_125_screenName");
		selenium.typeKeys("_125_screenName", RuntimeVariables.replace("Portlet"));
		selenium.type("_125_screenName", RuntimeVariables.replace("Portlet"));
		selenium.type("_125_emailAddress",
			RuntimeVariables.replace("Portlet@liferay.com"));
		selenium.select("_125_prefixId", RuntimeVariables.replace("label=Ms."));
		selenium.typeKeys("_125_firstName", RuntimeVariables.replace("Portlet"));
		selenium.type("_125_firstName", RuntimeVariables.replace("Portlet"));
		selenium.typeKeys("_125_lastName", RuntimeVariables.replace("Lifera"));
		selenium.type("_125_lastName", RuntimeVariables.replace("Liferay"));
		selenium.select("_125_suffixId", RuntimeVariables.replace("label=Jr."));
		selenium.select("_125_birthdayMonth",
			RuntimeVariables.replace("label=August"));
		selenium.select("_125_birthdayDay", RuntimeVariables.replace("label=5"));
		selenium.select("_125_birthdayYear",
			RuntimeVariables.replace("label=1991"));
		selenium.select("_125_male", RuntimeVariables.replace("label=Female"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("passwordLink", RuntimeVariables.replace(""));
		selenium.waitForElementPresent("_125_password1");
		selenium.type("_125_password1", RuntimeVariables.replace("test"));
		selenium.type("_125_password2", RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("rolesLink", RuntimeVariables.replace(""));
		selenium.waitForVisible("//div[6]/span/a/span");
		selenium.clickAt("//div[6]/span/a/span", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.waitForPopUp("role", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=role");
		selenium.waitForElementPresent("link=Portlet");
		selenium.click("link=Portlet");
		selenium.selectWindow("null");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		assertTrue(selenium.isTextPresent("Portlet"));
	}
}