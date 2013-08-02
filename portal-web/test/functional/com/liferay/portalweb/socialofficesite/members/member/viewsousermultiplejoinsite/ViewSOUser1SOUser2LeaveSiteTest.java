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

package com.liferay.portalweb.socialofficesite.members.member.viewsousermultiplejoinsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSOUser1SOUser2LeaveSiteTest extends BaseTestCase {
	public void testViewSOUser1SOUser2LeaveSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Open"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText("//div[@class='community-title']/a/span"));
		assertEquals(RuntimeVariables.replace("Members"),
			selenium.getText("//nav/ul/li[contains(.,'Members')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Members')]/a/span",
			RuntimeVariables.replace("Members"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have 1 connections in this site."),
			selenium.getText(
				"//div[@class='aui-layout contacts-count connections']"));
		assertEquals(RuntimeVariables.replace(
				"You are following 1 people in this site."),
			selenium.getText(
				"//div[@class='aui-layout contacts-count followings']"));
		assertEquals(RuntimeVariables.replace("View all 3 users."),
			selenium.getText("//div[@class='aui-layout contacts-count all']"));
		selenium.clickAt("//div[@class='aui-layout contacts-count connections']",
			RuntimeVariables.replace("You have 1 connections in this site."));
		selenium.waitForText("//div[contains(@class, 'lfr-contact-name')]/a",
			"User03, Social03");
		assertEquals(RuntimeVariables.replace("User03, Social03"),
			selenium.getText("//div[contains(@class, 'lfr-contact-name')]/a"));
		assertEquals(RuntimeVariables.replace("socialoffice03@liferay.com"),
			selenium.getText("//div[contains(@class, 'lfr-contact-extra')]"));
		selenium.clickAt("//div[@class='aui-layout contacts-count followings']",
			RuntimeVariables.replace("You are following 1 people in this site."));
		selenium.waitForText("//div[contains(@class, 'lfr-contact-name')]/a",
			"User04, Social04");
		assertEquals(RuntimeVariables.replace("User04, Social04"),
			selenium.getText("//div[contains(@class, 'lfr-contact-name')]/a"));
		assertEquals(RuntimeVariables.replace("socialoffice04@liferay.com"),
			selenium.getText("//div[contains(@class, 'lfr-contact-extra')]"));
	}
}