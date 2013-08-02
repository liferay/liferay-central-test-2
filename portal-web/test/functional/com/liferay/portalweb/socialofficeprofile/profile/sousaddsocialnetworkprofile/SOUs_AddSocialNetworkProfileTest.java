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

package com.liferay.portalweb.socialofficeprofile.profile.sousaddsocialnetworkprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_AddSocialNetworkProfileTest extends BaseTestCase {
	public void testSOUs_AddSocialNetworkProfile() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace(
				"To complete your profile, please add:"),
			selenium.getText("//p[@class='portlet-msg portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Social Network"),
			selenium.getText("//li[@data-title='Social Network']"));
		selenium.clickAt("//li[@data-title='Social Network']",
			RuntimeVariables.replace("Social Network"));
		selenium.waitForVisible("//input[contains(@id,'facebookSn')]");
		selenium.type("//input[contains(@id,'facebookSn')]",
			RuntimeVariables.replace("socialoffice01"));
		selenium.type("//input[contains(@id,'mySpaceSn')]",
			RuntimeVariables.replace("socialoffice01"));
		selenium.type("//input[contains(@id,'twitterSn')]",
			RuntimeVariables.replace("socialoffice01"));
		Thread.sleep(1000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@data-title='Social Network']/h3");
		assertEquals(RuntimeVariables.replace("Social Network:"),
			selenium.getText("//div[@data-title='Social Network']/h3"));
		assertEquals(RuntimeVariables.replace("Facebook"),
			selenium.getText(
				"//div[@data-title='Social Network']/ul/li[contains(.,'Facebook')]/span"));
		assertEquals(RuntimeVariables.replace("socialoffice01"),
			selenium.getText(
				"//div[@data-title='Social Network']/ul/li[contains(.,'Facebook')]/span[2]"));
		assertEquals(RuntimeVariables.replace("MySpace"),
			selenium.getText(
				"//div[@data-title='Social Network']/ul/li[contains(.,'MySpace')]/span"));
		assertEquals(RuntimeVariables.replace("socialoffice01"),
			selenium.getText(
				"//div[@data-title='Social Network']/ul/li[contains(.,'MySpace')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Twitter"),
			selenium.getText(
				"//div[@data-title='Social Network']/ul/li[contains(.,'Twitter')]/span"));
		assertEquals(RuntimeVariables.replace("socialoffice01"),
			selenium.getText(
				"//div[@data-title='Social Network']/ul/li[contains(.,'Twitter')]/span[2]"));
	}
}