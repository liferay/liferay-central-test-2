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

package com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.sousfavoritesite1typeprivaterestricted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_SendMemberRequestSite1PrivateRestrictTest extends BaseTestCase {
	public void testSOUs_SendMemberRequestSite1PrivateRestrict()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		selenium.waitForVisible(
			"//button[contains(.,'Sites Directory')]/span[2]");
		assertEquals(RuntimeVariables.replace("Sites Directory"),
			selenium.getText("//button[contains(.,'Sites Directory')]/span[2]"));
		selenium.clickAt("//button[contains(.,'Sites Directory')]/span[2]",
			RuntimeVariables.replace("Sites Directory"));
		selenium.waitForVisible("xPath=(//h1[@class='header-title']/span)[1]");
		assertEquals(RuntimeVariables.replace("Directory"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
		assertTrue(selenium.isVisible(
				"//input[@id='_5_WAR_soportlet_dialogKeywords']"));
		selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
			RuntimeVariables.replace("Private Restricted Site1 Name"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Private Restricted Site1 Name"),
			selenium.getText("//span[@class='name']"));
		assertEquals(RuntimeVariables.replace(
				"Private Restricted Site1 Description"),
			selenium.getText("//span[@class='description']"));
		assertTrue(selenium.isVisible("//a[@class='request-site']"));
		selenium.clickAt("//a[@class='request-site']",
			RuntimeVariables.replace("Request Membership"));
		assertTrue(selenium.getConfirmation()
						   .matches("^This is a restricted site. Do you want to send a membership request to Private Restricted Site1 Name[\\s\\S]$"));
		selenium.waitForVisible("//span[@class='action requested']");
	}
}