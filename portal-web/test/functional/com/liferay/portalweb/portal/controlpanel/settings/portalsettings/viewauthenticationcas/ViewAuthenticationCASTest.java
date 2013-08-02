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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.viewauthenticationcas;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAuthenticationCASTest extends BaseTestCase {
	public void testViewAuthenticationCAS() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Portal Settings",
			RuntimeVariables.replace("Portal Settings"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_130_authenticationLink']");
		selenium.clickAt("//a[@id='_130_authenticationLink']",
			RuntimeVariables.replace("Authentication"));
		selenium.clickAt("link=CAS", RuntimeVariables.replace("CAS"));
		selenium.waitForVisible(
			"//input[@name='_130_settings--cas.import.from.ldap--Checkbox']");
		assertTrue(selenium.isElementPresent(
				"//input[@name='_130_settings--cas.import.from.ldap--Checkbox']"));
		assertTrue(selenium.isElementPresent(
				"//input[@name='_130_settings--cas.auth.enabled--Checkbox']"));
		assertTrue(selenium.isTextPresent("Login URL"));
		assertTrue(selenium.isTextPresent("Logout URL"));
		assertTrue(selenium.isTextPresent("Server Name"));
		assertTrue(selenium.isTextPresent("Service URL"));
		assertTrue(selenium.isTextPresent("Service URL"));
	}
}