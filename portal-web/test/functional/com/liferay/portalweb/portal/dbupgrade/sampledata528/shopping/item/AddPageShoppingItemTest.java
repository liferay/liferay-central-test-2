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

package com.liferay.portalweb.portal.dbupgrade.sampledata528.shopping.item;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageShoppingItemTest extends BaseTestCase {
	public void testAddPageShoppingItem() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertTrue(selenium.isPartialText("//h2[@class='user-greeting']/span",
				"Welcome"));
		selenium.mouseOver("//h2[@class='user-greeting']/span");
		selenium.clickAt("//h2[@class='user-greeting']/span",
			RuntimeVariables.replace("Welcome"));
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities",
			RuntimeVariables.replace("Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Shopping Item Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//strong/span", RuntimeVariables.replace("Actions"));
		selenium.waitForElementPresent(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_134_name_en_US']",
			RuntimeVariables.replace("Shopping Item Page"));
		selenium.clickAt("//input[@value='Add Page']",
			RuntimeVariables.replace("Add Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[2]/div/div/div"));
		assertEquals(RuntimeVariables.replace("Shopping Item Page"),
			selenium.getText("//div/ul/li/ul/li[1]/a/span"));
	}
}