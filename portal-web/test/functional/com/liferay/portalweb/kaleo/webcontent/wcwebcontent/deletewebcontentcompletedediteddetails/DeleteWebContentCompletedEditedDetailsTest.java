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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentcompletedediteddetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteWebContentCompletedEditedDetailsTest extends BaseTestCase {
	public void testDeleteWebContentCompletedEditedDetails()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content Name Edited"),
			selenium.getText(
				"//tr[contains(.,'Web Content Name Edited')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Pending"),
			selenium.getText(
				"//tr[contains(.,'Web Content Name Edited')]/td[4]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Web Content Name Edited')]/td[5]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Web Content Name Edited')]/td[6]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Web Content Name Edited')]/td[7]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//tr[contains(.,'Web Content Name Edited')]/td[8]/a"));
		selenium.clickAt("//tr[contains(.,'Web Content Name Edited')]/td[3]/a",
			RuntimeVariables.replace("Web Content Name Edited"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace(
				"There is a publication workflow in process. Some actions may be disabled depending on the status and your role through this process."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Submit for Publication' and @disabled='']"));
		assertTrue(selenium.isElementPresent("//button[2]"));
		selenium.click(RuntimeVariables.replace("//button[3]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this article version[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//tr[contains(.,'Web Content Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[contains(.,'Web Content Name')]/td[4]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Web Content Name')]/td[5]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Web Content Name')]/td[6]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Web Content Name')]/td[7]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'Web Content Name')]/td[8]/a"));
	}
}