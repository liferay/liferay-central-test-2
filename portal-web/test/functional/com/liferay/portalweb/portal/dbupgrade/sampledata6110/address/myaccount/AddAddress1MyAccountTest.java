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

package com.liferay.portalweb.portal.dbupgrade.sampledata6110.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAddress1MyAccountTest extends BaseTestCase {
	public void testAddAddress1MyAccount() throws Exception {
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
		selenium.clickAt("link=My Account",
			RuntimeVariables.replace("My Account"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_2_addressesLink']");
		assertTrue(selenium.isPartialText("//a[@id='_2_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_2_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.waitForVisible("//input[@id='_2_addressStreet1_0']");
		selenium.type("//input[@id='_2_addressStreet1_0']",
			RuntimeVariables.replace("1220 Brea Canyon Rd"));
		selenium.type("//input[@id='_2_addressStreet2_0']",
			RuntimeVariables.replace("Ste 12"));
		selenium.select("//select[@id='_2_addressTypeId0']",
			RuntimeVariables.replace("Business"));
		selenium.type("//input[@id='_2_addressZip0']",
			RuntimeVariables.replace("91789"));
		selenium.type("//input[@id='_2_addressStreet3_0']",
			RuntimeVariables.replace("Walnut"));
		selenium.type("//input[@id='_2_addressCity0']",
			RuntimeVariables.replace("Los Angeles"));
		selenium.waitForPartialText("//select[@id='_2_addressCountryId0']",
			"United States");
		selenium.select("//select[@id='_2_addressCountryId0']",
			RuntimeVariables.replace("United States"));
		selenium.clickAt("//input[@id='_2_addressPrimary0']",
			RuntimeVariables.replace("Primary Button"));
		selenium.clickAt("//input[@id='_2_addressMailing0Checkbox']",
			RuntimeVariables.replace("Mailing Checkbox"));
		selenium.waitForPartialText("//select[@id='_2_addressRegionId0']",
			"California");
		selenium.select("//select[@id='_2_addressRegionId0']",
			RuntimeVariables.replace("California"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForSelectedLabel("//select[@id='_2_addressCountryId0']",
			"United States");
		selenium.waitForSelectedLabel("//select[@id='_2_addressRegionId0']",
			"California");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("1220 Brea Canyon Rd",
			selenium.getValue("//input[@id='_2_addressStreet1_0']"));
		assertEquals("Ste 12",
			selenium.getValue("//input[@id='_2_addressStreet2_0']"));
		assertEquals("Business",
			selenium.getSelectedLabel("//select[@id='_2_addressTypeId0']"));
		assertEquals("91789", selenium.getValue("//input[@id='_2_addressZip0']"));
		assertEquals("Walnut",
			selenium.getValue("//input[@id='_2_addressStreet3_0']"));
		assertEquals("Los Angeles",
			selenium.getValue("//input[@id='_2_addressCity0']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_2_addressCountryId0']"));
		assertTrue(selenium.isChecked("//input[@id='_2_addressPrimary0']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_2_addressMailing0Checkbox']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_2_addressRegionId0']"));
	}
}