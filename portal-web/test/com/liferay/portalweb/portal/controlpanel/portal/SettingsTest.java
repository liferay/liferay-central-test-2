/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.portal;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="SettingsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SettingsTest extends BaseTestCase {
	public void testSettings() throws Exception {
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

		selenium.click(RuntimeVariables.replace("link=Settings"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_130_legalId", RuntimeVariables.replace("LIFE"));
		selenium.type("_130_legalId", RuntimeVariables.replace("LIFE"));
		selenium.type("_130_sicCode", RuntimeVariables.replace("1234"));
		selenium.type("_130_tickerSymbol", RuntimeVariables.replace("LFRY"));
		selenium.type("_130_industry", RuntimeVariables.replace("Web Portal"));
		selenium.type("_130_type", RuntimeVariables.replace("Open Source"));
		selenium.click("displaySettingsLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_130_timeZoneId")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_130_timeZoneId",
			RuntimeVariables.replace("label=(UTC -08:00) Pacific Standard Time"));
		selenium.click("additionalEmailAddressesLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_130_emailAddressAddress0")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_130_emailAddressAddress0",
			RuntimeVariables.replace("Admin@Liferay.com"));
		selenium.select("_130_emailAddressTypeId0",
			RuntimeVariables.replace("label=E-mail"));
		selenium.click("_130_emailAddressPrimary0");
		selenium.click("addressesLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_130_addressStreet10")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_130_addressStreet10",
			RuntimeVariables.replace("123. Liferay Ln."));
		selenium.type("_130_addressCity0",
			RuntimeVariables.replace("Rays of Light"));
		selenium.type("_130_addressZip0", RuntimeVariables.replace("12345"));
		selenium.select("_130_addressCountryId0",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_130_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		selenium.select("_130_addressTypeId0",
			RuntimeVariables.replace("label=Billing"));
		selenium.click("_130_addressPrimary0");
		selenium.click("_130_addressMailing0Checkbox");
		selenium.click("websitesLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_130_websiteUrl0")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_130_websiteUrl0",
			RuntimeVariables.replace("http://www.liferay.com"));
		selenium.select("_130_websiteTypeId0",
			RuntimeVariables.replace("label=Public"));
		selenium.click("_130_websitePrimary0");
		selenium.click("phoneNumbersLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_130_phoneNumber0")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_130_phoneNumber0",
			RuntimeVariables.replace("123-123-1234"));
		selenium.type("_130_phoneExtension0", RuntimeVariables.replace("123"));
		selenium.select("_130_phoneTypeId0",
			RuntimeVariables.replace("label=Other"));
		selenium.click("_130_phonePrimary0");
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.click("authenticationLink");
		assertTrue(selenium.isElementPresent(
				"_130_settings(company.security.auto.login)Checkbox"));
		selenium.click("link=LDAP");
		assertTrue(selenium.isElementPresent(
				"_130_settings(ldap.auth.enabled)Checkbox"));
		selenium.click("link=CAS");
		assertTrue(selenium.isElementPresent(
				"_130_settings(cas.auth.enabled)Checkbox"));
		selenium.click("link=NTLM");
		assertTrue(selenium.isElementPresent(
				"_130_settings(ntlm.auth.enabled)Checkbox"));
		selenium.click("link=OpenID");
		assertTrue(selenium.isElementPresent(
				"_130_settings(open.id.auth.enabled)Checkbox"));
		selenium.click("link=Open SSO");
		assertTrue(selenium.isElementPresent(
				"_130_settings(open.sso.auth.enabled)Checkbox"));
		selenium.click("link=SiteMinder");
		assertTrue(selenium.isElementPresent(
				"_130_settings(siteminder.auth.enabled)Checkbox"));
		selenium.click("usersLink");
		selenium.click("link=Default User Associations");
		assertTrue(selenium.isElementPresent(
				"_130_settings(admin.default.group.names)"));
		assertTrue(selenium.isElementPresent(
				"_130_settings(admin.default.role.names)"));
		assertTrue(selenium.isElementPresent(
				"_130_settings(admin.default.user.group.names)"));
		selenium.click("link=Reserved Credentials");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"_130_settings(admin.reserved.screen.names)")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_130_settings(admin.reserved.screen.names)",
			RuntimeVariables.replace("Lifera"));
		selenium.type("_130_settings(admin.reserved.screen.names)",
			RuntimeVariables.replace("Liferay"));
		selenium.type("_130_settings(admin.reserved.email.addresses)",
			RuntimeVariables.replace("liferay@liferay.com"));
		selenium.click("mailHostNamesLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"_130_settings(admin.mail.host.names)")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_130_settings(admin.mail.host.names)",
			RuntimeVariables.replace("raysoflight.com"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.click("emailNotificationsLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"_130_settings(admin.email.from.name)")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("Joe Bloggs",
			selenium.getValue("_130_settings(admin.email.from.name)"));
		assertEquals("test@liferay.com",
			selenium.getValue("_130_settings(admin.email.from.address)"));
		selenium.click("link=Account Created Notification");
		assertTrue(selenium.isElementPresent(
				"_130_settings(admin.email.user.added.enabled)Checkbox"));
		selenium.click("link=Password Changed Notification");
		assertTrue(selenium.isElementPresent(
				"_130_settings(admin.email.password.sent.enabled)Checkbox"));
	}
}