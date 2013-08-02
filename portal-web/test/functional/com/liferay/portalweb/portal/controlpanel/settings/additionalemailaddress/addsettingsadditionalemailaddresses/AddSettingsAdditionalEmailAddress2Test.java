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

package com.liferay.portalweb.portal.controlpanel.settings.additionalemailaddress.addsettingsadditionalemailaddresses;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsAdditionalEmailAddress2Test extends BaseTestCase {
	public void testAddSettingsAdditionalEmailAddress2()
		throws Exception {
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
		selenium.waitForVisible("//a[@id='_130_additionalEmailAddressesLink']");
		selenium.clickAt("//a[@id='_130_additionalEmailAddressesLink']",
			RuntimeVariables.replace("Additional Email Addresses"));
		selenium.waitForVisible(
			"//div[8]/fieldset/div[2]/div/span/span/button[1]");
		selenium.clickAt("//div[8]/fieldset/div[2]/div/span/span/button[1]",
			RuntimeVariables.replace("Add"));
		selenium.type("//input[@id='_130_emailAddressAddress2']",
			RuntimeVariables.replace("Admin@Liferay.com"));
		selenium.select("//select[@id='_130_emailAddressTypeId2']",
			RuntimeVariables.replace("label=Email Address 2"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("Admin@Liferay.com",
			selenium.getValue("//input[@id='_130_emailAddressAddress1']"));
		assertEquals("Email Address 2",
			selenium.getSelectedLabel(
				"//select[@id='_130_emailAddressTypeId1']"));
	}
}