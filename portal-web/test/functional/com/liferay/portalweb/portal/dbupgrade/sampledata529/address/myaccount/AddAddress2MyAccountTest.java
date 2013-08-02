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
public class AddAddress2MyAccountTest extends BaseTestCase {
	public void testAddAddress2MyAccount() throws Exception {
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
		selenium.waitForVisible("//div[2]/div/span/a[1]");
		selenium.clickAt("//div[2]/div/span/a[1]",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//input[@id='_2_addressStreet1_2']");
		selenium.type("//input[@id='_2_addressStreet1_2']",
			RuntimeVariables.replace("123 Lets"));
		selenium.select("//select[@id='_2_addressTypeId2']",
			RuntimeVariables.replace("label=Other"));
		selenium.type("//input[@id='_2_addressStreet2_2']",
			RuntimeVariables.replace("897 Hope"));
		selenium.type("//input[@id='_2_addressZip2']",
			RuntimeVariables.replace("00000"));
		selenium.type("//input[@id='_2_addressStreet3_2']",
			RuntimeVariables.replace("7896 This"));
		selenium.type("//input[@id='_2_addressCity2']",
			RuntimeVariables.replace("Works"));
		selenium.waitForPartialText("//select[@id='_2_addressCountryId2']",
			"Canada");
		selenium.select("//select[@id='_2_addressCountryId2']",
			RuntimeVariables.replace("label=Canada"));
		Thread.sleep(5000);
		selenium.waitForPartialText("//select[@id='_2_addressRegionId2']",
			"Ontario");
		selenium.select("//select[@id='_2_addressRegionId2']",
			RuntimeVariables.replace("label=Ontario"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForSelectedLabel("//select[@id='_2_addressCountryId1']",
			"Canada");
		selenium.waitForSelectedLabel("//select[@id='_2_addressRegionId1']",
			"Ontario");
		assertEquals("123 Lets",
			selenium.getValue("//input[@id='_2_addressStreet1_1']"));
		assertEquals("Other",
			selenium.getSelectedLabel("//select[@name='_2_addressTypeId1']"));
		assertEquals("897 Hope",
			selenium.getValue("//input[@id='_2_addressStreet2_1']"));
		assertEquals("00000", selenium.getValue("//input[@id='_2_addressZip1']"));
		assertEquals("7896 This",
			selenium.getValue("//input[@id='_2_addressStreet3_1']"));
		assertEquals("Works",
			selenium.getValue("//input[@id='_2_addressCity1']"));
		assertEquals("Canada",
			selenium.getSelectedLabel("//select[@id='_2_addressCountryId1']"));
		assertEquals("Ontario",
			selenium.getSelectedLabel("//select[@id='_2_addressRegionId1']"));
	}
}