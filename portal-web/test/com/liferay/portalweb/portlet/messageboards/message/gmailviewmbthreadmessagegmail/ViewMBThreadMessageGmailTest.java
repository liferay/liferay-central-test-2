/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.messageboards.message.gmailviewmbthreadmessagegmail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMBThreadMessageGmailTest extends BaseTestCase {
	public void testViewMBThreadMessageGmail() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		loadRequiredJavaScriptModules();
		Thread.sleep(60000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Message Boards Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("MB Message Subject"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//td[5]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Message Subject"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Re: MB Message Subject"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[2]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='thread-body'])[2]",
				"MB Message Email Reply"));
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText("xpath=(//span[@class='user-name'])[2]"));
		assertEquals(RuntimeVariables.replace("Posts: 1"),
			selenium.getText(
				"xpath=(//div[@class='thread-user-post-count'])[2]"));
	}
}