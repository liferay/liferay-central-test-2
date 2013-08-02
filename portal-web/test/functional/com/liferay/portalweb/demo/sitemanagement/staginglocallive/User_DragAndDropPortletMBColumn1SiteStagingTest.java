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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_DragAndDropPortletMBColumn1SiteStagingTest
	extends BaseTestCase {
	public void testUser_DragAndDropPortletMBColumn1SiteStaging()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertEquals(RuntimeVariables.replace(
				"The data of this portlet is not staged. Any data changes are immediately available to the Local Live site. The portlet's own workflow is still honored. Portlet setup is still managed from staging."),
			selenium.getText("//div[@class='portlet-msg-alert']"));
		assertTrue(selenium.isVisible(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-message-boards')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-message-boards')]"));
		assertEquals(RuntimeVariables.replace("Message Boards"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[4]"));
		selenium.clickAt("xPath=(//span[@class='portlet-title-text'])[4]",
			RuntimeVariables.replace("Message Boards"));
		Thread.sleep(5000);
		selenium.dragAndDropToObject("xPath=(//span[@class='portlet-title-text'])[4]",
			"//div[@id='column-1']");
		selenium.waitForVisible(
			"//div[@id='column-1']/div/div[contains(@class,'portlet-message-boards')]");
		assertTrue(selenium.isVisible(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-message-boards')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-message-boards')]"));
	}
}