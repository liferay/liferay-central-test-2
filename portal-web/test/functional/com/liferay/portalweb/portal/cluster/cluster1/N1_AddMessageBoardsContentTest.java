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

package com.liferay.portalweb.portal.cluster.cluster1;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class N1_AddMessageBoardsContentTest extends BaseTestCase {
	public void testN1_AddMessageBoardsContent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("//input[@id='_58_login']");
		selenium.type("//input[@id='_58_login']",
			RuntimeVariables.replace("test@liferay.com"));
		selenium.type("//input[@id='_58_password']",
			RuntimeVariables.replace("test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Sign In']"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//li[@id='_145_addContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_addContent']/a/span");
		selenium.waitForVisible("//a[@id='_145_addApplication']");
		assertTrue(selenium.isPartialText("//a[@id='_145_addApplication']",
				"More"));
		selenium.clickAt("//a[@id='_145_addApplication']",
			RuntimeVariables.replace("More"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-live-search/aui-live-search-min.js')]");
		selenium.waitForVisible("//input[@id='layout_configuration_content']");
		selenium.type("//input[@id='layout_configuration_content']",
			RuntimeVariables.replace("m"));
		selenium.keyDown("//input[@id='layout_configuration_content']",
			RuntimeVariables.replace("\\13"));
		selenium.keyUp("//input[@id='layout_configuration_content']",
			RuntimeVariables.replace("\\13"));
		selenium.waitForVisible("//div[@title='Message Boards']/p/a");
		selenium.clickAt("//div[@title='Message Boards']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
		selenium.waitForVisible("//input[@value='Add Category']");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Category']"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='_19_name']");
		selenium.type("//input[@id='_19_name']",
			RuntimeVariables.replace("Test Category 1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isElementPresent("link=Test Category 1"));
	}
}