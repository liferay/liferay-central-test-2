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

package com.liferay.portalweb.portal.controlpanel.settings.settingsadditionalemailaddress.addsettingsadditionalemailaddress;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsAdditionalEmailAddressTest extends BaseTestCase {
	public void testAddSettingsAdditionalEmailAddress()
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
		selenium.clickAt("link=Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("additionalEmailAddressesLink",
			RuntimeVariables.replace(""));
		selenium.type("_130_emailAddressAddress0",
			RuntimeVariables.replace("Admin@Liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.select("_130_emailAddressTypeId0",
			RuntimeVariables.replace("label=E-mail"));
		selenium.clickAt("_130_emailAddressPrimary0",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div"));
		assertEquals("Admin@Liferay.com",
			selenium.getValue("_130_emailAddressAddress0"));
		assertEquals("E-mail",
			selenium.getSelectedLabel("_130_emailAddressTypeId0"));
		assertTrue(selenium.isChecked("_130_emailAddressPrimary0"));
		selenium.saveScreenShotAndSource();
	}
}