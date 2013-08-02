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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_ReplyMessageTest extends BaseTestCase {
	public void testMember_ReplyMessage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//tr[contains(.,'MB Category Name')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'MB Category Name')]/td[1]/a",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'MB Thread Message Subject')]/td[1]/a",
			RuntimeVariables.replace("MB Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Reply')]/span/a"));
		selenium.clickAt("//ul[@class='edit-controls lfr-component']/li[contains(.,'Reply')]/span/a",
			RuntimeVariables.replace("Reply"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.waitForVisible("//iframe[contains(@title,'Rich text editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich text editor')]",
			RuntimeVariables.replace("MB Thread Message Body Reply"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("RE: MB Thread Message Subject"),
			selenium.getText(
				"//tbody[contains(.,'MB Thread Message Body Reply')]/tr/td/div/div[@class='subject']/a"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Body Reply"),
			selenium.getText(
				"//tbody[contains(.,'MB Thread Message Body Reply')]/tr/td/div[@class='thread-body']"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//tbody[contains(.,'MB Thread Message Body Reply')]/tr/td/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//tbody[contains(.,'MB Thread Message Body Reply')]/tr/td/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Permissions')]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//tbody[contains(.,'MB Thread Message Body Reply')]/tr/td/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Delete')]"));
	}
}