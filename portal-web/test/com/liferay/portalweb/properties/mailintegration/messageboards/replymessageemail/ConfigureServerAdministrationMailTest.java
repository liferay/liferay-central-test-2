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

package com.liferay.portalweb.properties.mailintegration.messageboards.replymessageemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureServerAdministrationMailTest extends BaseTestCase {
	public void testConfigureServerAdministrationMail()
		throws Exception {
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
		selenium.clickAt("link=Server Administration",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Mail", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_137_pop3Host", RuntimeVariables.replace("pop.gmail.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("_137_pop3Port", RuntimeVariables.replace("995"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked("_137_pop3SecureCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("_137_pop3SecureCheckbox", RuntimeVariables.replace(""));
		assertTrue(selenium.isChecked("_137_pop3SecureCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.type("_137_pop3User",
			RuntimeVariables.replace("liferay.qa.server.trunk@gmail.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("_137_pop3Password",
			RuntimeVariables.replace("loveispatient"));
		selenium.saveScreenShotAndSource();
		selenium.type("_137_smtpHost",
			RuntimeVariables.replace("smtp.gmail.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("_137_smtpPort", RuntimeVariables.replace("465"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked("_137_smtpSecureCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.click("_137_smtpSecureCheckbox");
		assertTrue(selenium.isChecked("_137_smtpSecureCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.type("_137_smtpUser",
			RuntimeVariables.replace("liferay.qa.server.trunk@gmail.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("_137_smtpPassword",
			RuntimeVariables.replace("loveispatient"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div"));
		assertEquals("pop.gmail.com", selenium.getValue("_137_pop3Host"));
		assertEquals("995", selenium.getValue("_137_pop3Port"));
		assertTrue(selenium.isChecked("_137_pop3SecureCheckbox"));
		selenium.saveScreenShotAndSource();
		assertEquals("liferay.qa.server.trunk@gmail.com",
			selenium.getValue("_137_pop3User"));
		assertEquals("loveispatient", selenium.getValue("_137_pop3Password"));
		assertEquals("smtp.gmail.com", selenium.getValue("_137_smtpHost"));
		assertEquals("465", selenium.getValue("_137_smtpPort"));
		assertTrue(selenium.isChecked("_137_smtpSecureCheckbox"));
		selenium.saveScreenShotAndSource();
		assertEquals("liferay.qa.server.trunk@gmail.com",
			selenium.getValue("_137_smtpUser"));
		assertEquals("loveispatient", selenium.getValue("_137_smtpPassword"));
	}
}