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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.addpmmessagemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPMMessage1Test extends BaseTestCase {
	public void testAddPMMessage1() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForVisible("//nav/ul/li[contains(.,'Messages')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Messages')]/a/span",
			RuntimeVariables.replace("Messages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Private Messaging"),
			selenium.getText(
				"xPath=(//span[@class='portlet-title-default'])[contains(.,'Private Messaging')]"));
		assertEquals("Mark as Unread",
			selenium.getValue("//input[@value='Mark as Unread']"));
		assertEquals("Delete", selenium.getValue("//input[@value='Delete']"));
		assertEquals("New Message",
			selenium.getValue("//input[@value='New Message']"));
		selenium.clickAt("//input[@value='New Message']",
			RuntimeVariables.replace("New Message"));
		selenium.waitForVisible(
			"//button[contains(@class,'autocomplete-button')]/span");
		selenium.clickAt("//button[contains(@class,'autocomplete-button')]/span",
			RuntimeVariables.replace("Dropdown"));
		selenium.sendKeys("//input[@id='_1_WAR_privatemessagingportlet_to']",
			RuntimeVariables.replace("Social01"));
		Thread.sleep(1000);
		selenium.waitForPartialText("//li[contains(@data-text,'Social01 Office01 User01')]",
			"Social01 Office01 User01");
		assertTrue(selenium.isPartialText(
				"//li[contains(@data-text,'Social01 Office01 User01')]",
				"Social01 Office01 User01"));
		selenium.clickAt("//li[contains(@data-text,'Social01 Office01 User01')]",
			RuntimeVariables.replace("Social01 Office01 User01"));
		assertEquals("Social01 Office01 User01 <socialoffice01>,",
			selenium.getValue(
				"//input[@id='_1_WAR_privatemessagingportlet_to']"));
		assertTrue(selenium.isVisible(
				"//input[@id='_1_WAR_privatemessagingportlet_subject']"));
		selenium.type("//input[@id='_1_WAR_privatemessagingportlet_subject']",
			RuntimeVariables.replace("Message1 Subject"));
		assertTrue(selenium.isVisible(
				"//textarea[@id='_1_WAR_privatemessagingportlet_body']"));
		selenium.type("//textarea[@id='_1_WAR_privatemessagingportlet_body']",
			RuntimeVariables.replace("Message1 Body"));
		selenium.clickAt("//input[@value='Send']",
			RuntimeVariables.replace("Send"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//span[@class='author-sender']"));
		assertEquals(RuntimeVariables.replace("Message1 Subject"),
			selenium.getText("//div[@class='subject']"));
		assertEquals(RuntimeVariables.replace("Message1 Body"),
			selenium.getText("//div[@class='body']"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}