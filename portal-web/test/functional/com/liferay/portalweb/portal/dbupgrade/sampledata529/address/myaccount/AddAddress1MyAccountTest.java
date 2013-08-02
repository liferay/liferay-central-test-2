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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAddress1MyAccountTest extends BaseTestCase {
	public void testAddAddress1MyAccount() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		assertTrue(selenium.isPartialText("//h2[@class='user-greeting']/span",
				"Welcome"));
		selenium.mouseOver("//h2[@class='user-greeting']/span");
		selenium.clickAt("//h2[@class='user-greeting']/span",
			RuntimeVariables.replace("Welcome"));
		selenium.waitForVisible("link=My Account");
		selenium.clickAt("link=My Account",
			RuntimeVariables.replace("My Account"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a[@id='addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.waitForVisible("//input[@id='_2_addressStreet1_0']");
		selenium.type("//input[@id='_2_addressStreet1_0']",
			RuntimeVariables.replace("1220 Brea Canyon Rd"));
		selenium.type("//input[@id='_2_addressStreet2_0']",
			RuntimeVariables.replace("Ste 12"));
		selenium.select("//select[@name='_2_addressTypeId0']",
			RuntimeVariables.replace("label=Business"));
		selenium.type("//input[@id='_2_addressZip0']",
			RuntimeVariables.replace("91789"));
		selenium.type("//input[@id='_2_addressStreet3_0']",
			RuntimeVariables.replace("Walnut"));
		selenium.type("//input[@id='_2_addressCity0']",
			RuntimeVariables.replace("Los Angeles"));
		selenium.waitForPartialText("//select[@name='_2_addressCountryId0']",
			"United States");
		selenium.select("//select[@name='_2_addressCountryId0']",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@id='_2_addressPrimary0']",
			RuntimeVariables.replace("Primary"));
		selenium.clickAt("//input[@id='_2_addressMailing0Checkbox']",
			RuntimeVariables.replace("Mailing"));
		selenium.waitForPartialText("//select[@name='_2_addressRegionId0']",
			"California");
		selenium.select("//select[@name='_2_addressRegionId0']",
			RuntimeVariables.replace("label=California"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForSelectedLabel("//select[@name='_2_addressCountryId0']",
			"United States");
		selenium.waitForSelectedLabel("//select[@name='_2_addressRegionId0']",
			"California");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("1220 Brea Canyon Rd",
			selenium.getValue("//input[@id='_2_addressStreet1_0']"));
		assertEquals("Ste 12",
			selenium.getValue("//input[@id='_2_addressStreet2_0']"));
		assertEquals("Business",
			selenium.getSelectedLabel("//select[@name='_2_addressTypeId0']"));
		assertEquals("91789", selenium.getValue("//input[@id='_2_addressZip0']"));
		assertEquals("Walnut",
			selenium.getValue("//input[@id='_2_addressStreet3_0']"));
		assertEquals("Los Angeles",
			selenium.getValue("//input[@id='_2_addressCity0']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@name='_2_addressCountryId0']"));
		assertTrue(selenium.isChecked("//input[@id='_2_addressPrimary0']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_2_addressMailing0Checkbox']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@name='_2_addressRegionId0']"));
	}
}