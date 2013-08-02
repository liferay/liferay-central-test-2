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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.viewauthenticationldap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAuthenticationLDAPTest extends BaseTestCase {
	public void testViewAuthenticationLDAP() throws Exception {
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
		selenium.clickAt("link=Portal Settings",
			RuntimeVariables.replace("Portal Settings"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_authenticationLink']", "Authentication"));
		selenium.clickAt("//a[@id='_130_authenticationLink']",
			RuntimeVariables.replace("Authentication"));
		selenium.clickAt("link=LDAP", RuntimeVariables.replace("LDAP"));
		selenium.waitForVisible(
			"//input[@name='_130_settings--ldap.auth.enabled--Checkbox']");
		assertFalse(selenium.isChecked(
				"//input[@name='_130_settings--ldap.auth.enabled--Checkbox']"));
		assertFalse(selenium.isChecked(
				"//input[@name='_130_settings--ldap.auth.required--Checkbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_130_ldapImportEnabledCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_130_ldapExportEnabledCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@name='_130_settings--ldap.password.policy.enabled--Checkbox']"));
		selenium.clickAt("//input[@value='Add']",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Server Name"),
			selenium.getText("//label[@for='_130_ldap.server.name.0']"));
		assertEquals("",
			selenium.getValue("//input[@id='_130_ldap.base.provider.url.0']"));
		assertEquals(RuntimeVariables.replace("Default Values"),
			selenium.getText("//h3"));
		selenium.clickAt("//input[@value='microsoft']",
			RuntimeVariables.replace("Microsoft Active Directory Server Button"));
		selenium.clickAt("//input[@value='Reset Values']",
			RuntimeVariables.replace("Reset Values"));
		assertEquals(RuntimeVariables.replace("Connection"),
			selenium.getText("//h3[2]"));
		assertEquals("ldap://localhost:389",
			selenium.getValue("//input[@id='_130_ldap.base.provider.url.0']"));
		assertEquals("dc=example,dc=com",
			selenium.getValue("//input[@id='_130_ldap.base.dn.0']"));
		assertEquals("admin",
			selenium.getValue("//input[@id='_130_ldap.security.principal.0']"));
		assertEquals("secret",
			selenium.getValue("//input[@id='_130_ldap.security.credentials.0']"));
		assertTrue(selenium.isVisible("//input[@value='Test LDAP Connection']"));
		assertTrue(selenium.isVisible("//input[@value='Test LDAP Users']"));
		assertTrue(selenium.isVisible("//input[@value='Test LDAP Groups']"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}