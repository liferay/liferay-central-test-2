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

package com.liferay.portalweb.socialofficesite.members.member.sendmemberinvitesousersiterolesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SendMemberInviteSOUser1SiteOwnerRoleSiteTest extends BaseTestCase {
	public void testSendMemberInviteSOUser1SiteOwnerRoleSite()
		throws Exception {
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
		assertEquals(RuntimeVariables.replace("Invite members to this site."),
			selenium.getText(
				"//a[contains(text(),'Invite members to this site.')]"));
		selenium.clickAt("//a[contains(text(),'Invite members to this site.')]",
			RuntimeVariables.replace("Invite members to this site."));
		selenium.waitForVisible("//div[@class='search']");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[contains(@class, 'user')]/span"));
		selenium.clickAt("//div[contains(@class, 'user')]/span",
			RuntimeVariables.replace("Social01 Office01 User01"));
		selenium.waitForVisible("//div[@class='user-invited']/div/div");
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 socialoffice01@liferay.com"),
			selenium.getText("//div[@class='user-invited']/div/div"));
		selenium.select("//select[contains(@name,'roleId')]",
			RuntimeVariables.replace("Site Owner"));
		assertEquals("Send Invitations",
			selenium.getValue("//input[@value='Send Invitations']"));
		selenium.clickAt("//input[@value='Send Invitations']",
			RuntimeVariables.replace("Send Invitations"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}