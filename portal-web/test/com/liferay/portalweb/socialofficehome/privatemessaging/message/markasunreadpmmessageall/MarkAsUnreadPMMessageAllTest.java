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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.markasunreadpmmessageall;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MarkAsUnreadPMMessageAllTest extends BaseTestCase {
	public void testMarkAsUnreadPMMessageAll() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//nav/ul/li[contains(.,'Messages')]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//nav/ul/li[contains(.,'Messages')]/a/span",
					RuntimeVariables.replace("Messages"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Private Messaging"),
					selenium.getText("//span[@class='portlet-title-default']"));
				assertEquals("Mark as Unread",
					selenium.getValue("//input[@value='Mark as Unread']"));
				assertEquals("Delete",
					selenium.getValue("//input[@value='Delete']"));
				assertEquals("New Message",
					selenium.getValue("//input[@value='New Message']"));
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01"),
					selenium.getText(
						"xPath=(//span[@class='author-sender'])[1]"));
				assertEquals(RuntimeVariables.replace("Message3 Subject"),
					selenium.getText("xPath=(//div[@class='subject'])[1]"));
				assertEquals(RuntimeVariables.replace("Message3 Body"),
					selenium.getText("xPath=(//div[@class='body'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01"),
					selenium.getText(
						"xPath=(//span[@class='author-sender'])[2]"));
				assertEquals(RuntimeVariables.replace("Message2 Subject"),
					selenium.getText("xPath=(//div[@class='subject'])[2]"));
				assertEquals(RuntimeVariables.replace("Message2 Body"),
					selenium.getText("xPath=(//div[@class='body'])[2]"));
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01"),
					selenium.getText(
						"xPath=(//span[@class='author-sender'])[3]"));
				assertEquals(RuntimeVariables.replace("Message1 Subject"),
					selenium.getText("xPath=(//div[@class='subject'])[3]"));
				assertEquals(RuntimeVariables.replace("Message1 Body"),
					selenium.getText("xPath=(//div[@class='body'])[3]"));
				assertEquals(RuntimeVariables.replace("Showing 3 results."),
					selenium.getText("//div[@class='search-results']"));
				assertTrue(selenium.isElementPresent(
						"//td[1]/span/span/span/input[2]"));

				boolean message1Checked = selenium.isChecked(
						"//td[1]/span/span/span/input[2]");

				if (!message1Checked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Uncheck"));

			case 2:
				assertTrue(selenium.isElementPresent(
						"//tr[4]/td[1]/span/span/span/input[2]"));

				boolean message2Checked = selenium.isChecked(
						"//tr[4]/td[1]/span/span/span/input[2]");

				if (!message2Checked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//tr[4]/td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Uncheck"));

			case 3:
				assertTrue(selenium.isElementPresent(
						"//tr[5]/td[1]/span/span/span/input[2]"));

				boolean message3Checked = selenium.isChecked(
						"//tr[5]/td[1]/span/span/span/input[2]");

				if (!message3Checked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//tr[5]/td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Uncheck"));

			case 4:
				assertFalse(selenium.isChecked(
						"//td[1]/span/span/span/input[2]"));
				assertFalse(selenium.isChecked(
						"//tr[4]/td[1]/span/span/span/input[2]"));
				assertFalse(selenium.isChecked(
						"//tr[5]/td[1]/span/span/span/input[2]"));
				assertEquals(RuntimeVariables.replace("All"),
					selenium.getText("//span[@class='select-all']"));
				selenium.clickAt("//span[@class='select-all']",
					RuntimeVariables.replace("All"));
				assertTrue(selenium.isChecked("//td[1]/span/span/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//tr[4]/td[1]/span/span/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//tr[5]/td[1]/span/span/span/input[2]"));
				assertEquals("Mark as Unread",
					selenium.getValue("//input[@value='Mark as Unread']"));
				selenium.clickAt("//input[@value='Mark as Unread']",
					RuntimeVariables.replace("Mark as Unread"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isElementPresent(
						"xPath=(//tr[contains(@class, 'unread')])[1]"));
				assertTrue(selenium.isElementPresent(
						"xPath=(//tr[contains(@class, 'unread')])[2]"));
				assertTrue(selenium.isElementPresent(
						"xPath=(//tr[contains(@class, 'unread')])[3]"));

			case 100:
				label = -1;
			}
		}
	}
}