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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.searchfollowingdropdownfollowingcc;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchUserDropDownFollowingCCTest extends BaseTestCase {
	public void testSearchUserDropDownFollowingCC() throws Exception {
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
		assertTrue(selenium.isVisible(
				"//select[@id='_1_WAR_contactsportlet_filterBy']"));
		selenium.select("//select[@id='_1_WAR_contactsportlet_filterBy']",
			RuntimeVariables.replace("Following"));
		selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
			RuntimeVariables.replace("Social01"));
		selenium.waitForText("//div[@class='empty']", "There are no results.");
		assertEquals(RuntimeVariables.replace("There are no results."),
			selenium.getText("//div[@class='empty']"));
	}
}