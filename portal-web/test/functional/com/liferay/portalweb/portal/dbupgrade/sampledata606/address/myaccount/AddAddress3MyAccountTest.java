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

package com.liferay.portalweb.portal.dbupgrade.sampledata606.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAddress3MyAccountTest extends BaseTestCase {
	public void testAddAddress3MyAccount() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Account",
			RuntimeVariables.replace("My Account"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a[@id='addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.waitForVisible("//input[@id='_2_addressStreet1_0']");
		selenium.waitForVisible("//div[2]/div/span/span/button[1]");
		selenium.clickAt("//div[2]/div/span/span/button[1]",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//input[@id='_2_addressStreet1_3']");
		selenium.type("//input[@id='_2_addressStreet1_3']",
			RuntimeVariables.replace("1220 Brea Canyon Rd"));
		selenium.select("//select[@id='_2_addressTypeId3']",
			RuntimeVariables.replace("label=Business"));
		selenium.type("//input[@id='_2_addressStreet2_3']",
			RuntimeVariables.replace("Ste 12"));
		selenium.type("//input[@id='_2_addressZip3']",
			RuntimeVariables.replace("91789"));
		selenium.type("//input[@id='_2_addressStreet3_3']",
			RuntimeVariables.replace("Walnut"));
		selenium.type("//input[@id='_2_addressCity3']",
			RuntimeVariables.replace("Los Angeles"));
		selenium.waitForPartialText("//select[@id='_2_addressCountryId3']",
			"United States");
		selenium.select("//select[@id='_2_addressCountryId3']",
			RuntimeVariables.replace("label=United States"));
		selenium.waitForPartialText("//select[@id='_2_addressRegionId3']",
			"California");
		selenium.select("//select[@id='_2_addressRegionId3']",
			RuntimeVariables.replace("label=California"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForSelectedLabel("//select[@id='_2_addressCountryId2']",
			"United States");
		selenium.waitForSelectedLabel("//select[@id='_2_addressRegionId2']",
			"California");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//section/div/div/div/div[1]"));
		assertEquals("1220 Brea Canyon Rd",
			selenium.getValue("//input[@id='_2_addressStreet1_2']"));
		assertEquals("Business",
			selenium.getSelectedLabel("//select[@id='_2_addressTypeId2']"));
		assertEquals("Ste 12",
			selenium.getValue("//input[@id='_2_addressStreet2_2']"));
		assertEquals("91789", selenium.getValue("//input[@id='_2_addressZip2']"));
		assertEquals("Walnut",
			selenium.getValue("//input[@id='_2_addressStreet3_2']"));
		assertEquals("Los Angeles",
			selenium.getValue("//input[@id='_2_addressCity2']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_2_addressCountryId2']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_2_addressRegionId2']"));
	}
}