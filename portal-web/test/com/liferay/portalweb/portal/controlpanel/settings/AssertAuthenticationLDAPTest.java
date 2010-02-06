/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portalweb.portal.controlpanel.settings;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertAuthenticationLDAPTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertAuthenticationLDAPTest extends BaseTestCase {
	public void testAssertAuthenticationLDAP() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Settings")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("authenticationLink")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("authenticationLink", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=LDAP")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=LDAP", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"_130_settings(ldap.auth.enabled)Checkbox")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isElementPresent(
				"_130_settings(ldap.auth.enabled)Checkbox"));
		assertTrue(selenium.isElementPresent(
				"_130_settings(ldap.auth.required)Checkbox"));
		selenium.clickAt("_130_defaultLdap", RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Reset Values']",
			RuntimeVariables.replace(""));
		assertEquals("ldap://localhost:10389",
			selenium.getValue("_130_settings(ldap.base.provider.url)"));
		selenium.clickAt("//input[@name='_130_defaultLdap' and @value='microsoft']",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Reset Values']",
			RuntimeVariables.replace(""));
		assertEquals("ldap://localhost:389",
			selenium.getValue("_130_settings(ldap.base.provider.url)"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Test LDAP Connection']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Test LDAP Users']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Test LDAP Groups']"));
		assertTrue(selenium.isElementPresent("_130_ldapImportEnabledCheckbox"));
		assertTrue(selenium.isElementPresent("_130_ldapExportEnabledCheckbox"));
		assertTrue(selenium.isElementPresent(
				"_130_settings(ldap.password.policy.enabled)Checkbox"));
	}
}