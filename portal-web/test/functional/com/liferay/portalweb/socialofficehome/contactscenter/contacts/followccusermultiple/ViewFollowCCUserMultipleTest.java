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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.followccusermultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewFollowCCUserMultipleTest extends BaseTestCase {
	public void testViewFollowCCUserMultiple() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForVisible(
			"//nav/ul/li[contains(.,'Contacts Center')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("You are following 3 people."),
			selenium.getText("link=You are following 3 people."));
		selenium.clickAt("link=You are following 3 people.",
			RuntimeVariables.replace("You are following 3 people."));
		selenium.waitForText("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User01, Social01')]",
			"User01, Social01");
		assertEquals(RuntimeVariables.replace("User01, Social01"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User01, Social01')]"));
		Thread.sleep(1000);
		selenium.clickAt("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User01, Social01')]",
			RuntimeVariables.replace("User01, Social01"));
		selenium.waitForVisible(
			"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		assertEquals(RuntimeVariables.replace("socialoffice01@liferay.com"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		assertEquals(RuntimeVariables.replace("Unfollow"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_unfollowButton']"));
		assertFalse(selenium.isVisible(
				"//button[@id='_1_WAR_contactsportlet_followButton']"));
		selenium.waitForText("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User02, Social02')]",
			"User02, Social02");
		assertEquals(RuntimeVariables.replace("User02, Social02"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User02, Social02')]"));
		Thread.sleep(1000);
		selenium.clickAt("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User02, Social02')]",
			RuntimeVariables.replace("User02, Social02"));
		selenium.waitForText("//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a",
			"Social02 Office02 User02");
		assertEquals(RuntimeVariables.replace("Social02 Office02 User02"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		assertEquals(RuntimeVariables.replace("socialoffice02@liferay.com"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		assertEquals(RuntimeVariables.replace("Unfollow"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_unfollowButton']"));
		assertFalse(selenium.isVisible(
				"//button[@id='_1_WAR_contactsportlet_followButton']"));
		selenium.waitForText("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User03, Social03')]",
			"User03, Social03");
		assertEquals(RuntimeVariables.replace("User03, Social03"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User03, Social03')]"));
		Thread.sleep(1000);
		selenium.clickAt("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User03, Social03')]",
			RuntimeVariables.replace("User03, Social03"));
		selenium.waitForVisible("//div[contains(@class, 'contacts-profile')]");
		selenium.waitForText("//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a",
			"Social03 Office03 User03");
		assertEquals(RuntimeVariables.replace("Social03 Office03 User03"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		assertEquals(RuntimeVariables.replace("socialoffice03@liferay.com"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		assertEquals(RuntimeVariables.replace("Unfollow"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_unfollowButton']"));
		assertFalse(selenium.isVisible(
				"//button[@id='_1_WAR_contactsportlet_followButton']"));
	}
}