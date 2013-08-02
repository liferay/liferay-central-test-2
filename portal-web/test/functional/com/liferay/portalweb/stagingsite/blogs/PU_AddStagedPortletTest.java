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

package com.liferay.portalweb.stagingsite.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PU_AddStagedPortletTest extends BaseTestCase {
	public void testPU_AddStagedPortlet() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertTrue(selenium.isElementPresent("//div[@class='staging-bar']"));
		assertEquals(RuntimeVariables.replace("Live"),
			selenium.getText("//span[contains(.,'Live')]"));
		assertEquals(RuntimeVariables.replace(
				"You are viewing the live version of Liferay and cannot make changes here. Make your changes in staging and publish them to Live afterwards to make them public."),
			selenium.getText("//span[@class='staging-live-help']"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//span/a[contains(.,'Staging')]"));
		selenium.clickAt("//span/a[contains(.,'Staging')]",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You are viewing the staged version of Liferay. You can make changes here and publish them to Live afterwards to make them public."),
			selenium.getText("//span[@class='staging-live-help']"));
		selenium.waitForVisible("link=Blogs Test Page");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
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
		selenium.sendKeys("//input[@id='layout_configuration_content']",
			RuntimeVariables.replace("b"));
		selenium.waitForVisible("//div[@title='Blogs']/p/a");
		selenium.clickAt("//div[@title='Blogs']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
	}
}