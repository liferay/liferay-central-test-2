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

package com.liferay.portalweb.socialofficeprofile.profile.souseditinstantmessengernullprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_AddInstantMessengerProfileTest extends BaseTestCase {
	public void testSOUs_AddInstantMessengerProfile() throws Exception {
		selenium.open("/web/socialoffice01/so/profile");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='lfr-contact-name']/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace(
				"To complete your profile, please add:"),
			selenium.getText("//p[@class='portlet-msg portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Instant Messenger"),
			selenium.getText("//li[@data-title='Instant Messenger']"));
		selenium.clickAt("//li[@data-title='Instant Messenger']",
			RuntimeVariables.replace("Instant Messenger"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[contains(@id,'aimSn')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[contains(@id,'aimSn')]",
			RuntimeVariables.replace("socialofficesn"));
		selenium.type("//input[contains(@id,'icqSn')]",
			RuntimeVariables.replace("socialofficesn"));
		selenium.type("//input[contains(@id,'jabberSn')]",
			RuntimeVariables.replace("socialofficesn"));
		selenium.type("//input[contains(@id,'msnSn')]",
			RuntimeVariables.replace("socialofficesn"));
		selenium.type("//input[contains(@id,'skypeSn')]",
			RuntimeVariables.replace("socialofficesn"));
		selenium.type("//input[contains(@id,'ymSn')]",
			RuntimeVariables.replace("socialofficesn"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@data-title='Instant Messenger']/h3")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Instant Messenger:"),
			selenium.getText("//div[@data-title='Instant Messenger']/h3"));
		assertEquals(RuntimeVariables.replace("AIM"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'AIM')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'AIM')]/span[2]"));
		assertEquals(RuntimeVariables.replace("ICQ"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'ICQ')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'ICQ')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Jabber"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'Jabber')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'Jabber')]/span[2]"));
		assertEquals(RuntimeVariables.replace("MSN"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'MSN')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'MSN')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Skype"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'Skype')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'Skype')]/span[2]"));
		assertEquals(RuntimeVariables.replace("YM"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'YM')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'YM')]/span[2]"));
	}
}