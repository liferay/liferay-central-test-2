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

package com.liferay.portalweb.portal.controlpanel.settings.address.deletesettingsaddress;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsAddressTest extends BaseTestCase {
	public void testAddSettingsAddress() throws Exception {
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
		selenium.waitForVisible("//a[@id='_130_addressesLink']");
		selenium.clickAt("//a[@id='_130_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.type("//input[@id='_130_addressStreet1_0']",
			RuntimeVariables.replace("123. Liferay Ln."));
		selenium.type("//input[@id='_130_addressCity0']",
			RuntimeVariables.replace("Rays of Light"));
		selenium.type("//input[@id='_130_addressZip0']",
			RuntimeVariables.replace("12345"));
		selenium.select("//select[@id='_130_addressCountryId0']",
			RuntimeVariables.replace("label=United States"));
		selenium.waitForPartialText("//select[@id='_130_addressRegionId0']",
			"California");
		selenium.select("//select[@id='_130_addressRegionId0']",
			RuntimeVariables.replace("label=California"));
		selenium.select("//select[@id='_130_addressTypeId0']",
			RuntimeVariables.replace("label=Billing"));
		selenium.clickAt("//input[@id='_130_addressMailing0Checkbox']",
			RuntimeVariables.replace("Mailing Checkbox"));
		selenium.clickAt("//input[@id='_130_addressPrimary0']",
			RuntimeVariables.replace("Primary Button"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForVisible("//input[@id='_130_addressStreet1_0']");
		assertEquals("123. Liferay Ln.",
			selenium.getValue("//input[@id='_130_addressStreet1_0']"));
		assertEquals("Rays of Light",
			selenium.getValue("//input[@id='_130_addressCity0']"));
		assertEquals("12345",
			selenium.getValue("//input[@id='_130_addressZip0']"));
		assertEquals("Billing",
			selenium.getSelectedLabel("//select[@id='_130_addressTypeId0']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_130_addressCountryId0']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_130_addressRegionId0']"));
	}
}