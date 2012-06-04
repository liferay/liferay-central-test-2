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

package com.liferay.portalweb.socialofficeprofile.profile.sousaddphonenumberprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_AddPhoneNumberProfileTest extends BaseTestCase {
	public void testSOUs_AddPhoneNumberProfile() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Phones"),
			selenium.getText("//li[@data-title='Phone Numbers']"));
		selenium.clickAt("//li[@data-title='Phone Numbers']",
			RuntimeVariables.replace("Phones"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[contains(@id,'phoneNumber')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[contains(@id,'phoneNumber')]",
			RuntimeVariables.replace("123-123-1234"));
		selenium.type("//input[contains(@id,'phoneExtension')]",
			RuntimeVariables.replace("123"));
		selenium.select("//select[contains(@id,'phoneType')]",
			RuntimeVariables.replace("Business"));
		selenium.clickAt("//input[contains(@id,'phonePrimary')]",
			RuntimeVariables.replace("Primary"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@data-title='Phone Numbers']/h3")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Phones:"),
			selenium.getText("//div[@data-title='Phone Numbers']/h3"));
		assertEquals(RuntimeVariables.replace("Business"),
			selenium.getText("//div[@data-title='Phone Numbers']/ul/li/span"));
		assertEquals(RuntimeVariables.replace("123-123-1234 123"),
			selenium.getText("//div[@data-title='Phone Numbers']/ul/li/span[2]"));
	}
}