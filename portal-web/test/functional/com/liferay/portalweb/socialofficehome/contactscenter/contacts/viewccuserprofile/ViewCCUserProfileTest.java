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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.viewccuserprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCCUserProfileTest extends BaseTestCase {
	public void testViewCCUserProfile() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForVisible(
			"//nav/ul/li[contains(.,'Contacts Center')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//input[@id='_1_WAR_contactsportlet_name']"));
		selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
			RuntimeVariables.replace("socialoffice01@liferay.com"));
		selenium.waitForText("//div[contains(@class, 'lfr-contact-name')]/a",
			"User01, Social01");
		assertEquals(RuntimeVariables.replace("User01, Social01"),
			selenium.getText("//div[contains(@class, 'lfr-contact-name')]/a"));
		assertEquals(RuntimeVariables.replace("socialoffice01@liferay.com"),
			selenium.getText("//div[contains(@class, 'lfr-contact-extra')]"));
		Thread.sleep(1000);
		selenium.clickAt("//div[contains(@class, 'lfr-contact-name')]/a",
			RuntimeVariables.replace("User01, Social01"));
		selenium.waitForVisible("//div[contains(@class, 'contacts-profile')]");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		assertEquals(RuntimeVariables.replace("socialoffice01@liferay.com"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		selenium.waitForVisible(
			"//button[@id='_1_WAR_contactsportlet_addConnectionButton']");
		assertEquals(RuntimeVariables.replace("Connect"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_addConnectionButton']"));
		assertEquals(RuntimeVariables.replace("Follow"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_followButton']"));
		assertEquals(RuntimeVariables.replace("Block"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_blockButton']"));
		assertEquals(RuntimeVariables.replace("About"),
			selenium.getText(
				"xPath=(//div[@class='user-information-title'])[1]"));
		assertEquals(RuntimeVariables.replace("Projects"),
			selenium.getText(
				"xPath=(//div[@class='user-information-title'])[2]"));
	}
}