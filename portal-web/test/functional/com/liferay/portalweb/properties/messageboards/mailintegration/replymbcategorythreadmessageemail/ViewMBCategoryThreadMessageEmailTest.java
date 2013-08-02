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

package com.liferay.portalweb.properties.messageboards.mailintegration.replymbcategorythreadmessageemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMBCategoryThreadMessageEmailTest extends BaseTestCase {
	public void testViewMBCategoryThreadMessageEmail()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name");
		Thread.sleep(60000);
		selenium.waitForVisible("link=Message Boards Test Page");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//td[1]/a", "MB Message Subject");
		assertEquals(RuntimeVariables.replace("MB Message Subject"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//td[5]/a"));
		assertTrue(selenium.isPartialText("//td[6]/a", "By: userfn userln"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("MB Message Subject"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace(
				"Re: [MB Category Name] MB Message Subject"),
			selenium.getText("//tr[2]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("MB Message Subject"),
			selenium.getText("xPath=(//div[@class='subject'])[1]/a/strong"));
		assertEquals(RuntimeVariables.replace("MB Message Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[1]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//span[@class='user-name'])[1]", "Joe Bloggs"));
		assertEquals(RuntimeVariables.replace(
				"Re: [MB Category Name] MB Message Subject"),
			selenium.getText("xPath=(//div[@class='subject'])[2]/a/strong"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='thread-body'])[2]",
				"MB Message Email Reply"));
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText("xPath=(//span[@class='user-name'])[2]"));
	}
}