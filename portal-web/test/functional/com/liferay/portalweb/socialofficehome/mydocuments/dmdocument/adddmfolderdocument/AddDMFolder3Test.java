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

package com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolder3Test extends BaseTestCase {
	public void testAddDMFolder3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("My Documents"),
			selenium.getText("//nav/ul/li[contains(.,'My Documents')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'My Documents')]/a/span",
			RuntimeVariables.replace("My Documents"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Folder')]/a");
		assertEquals(RuntimeVariables.replace("Folder"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Folder')]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Folder')]/a",
			RuntimeVariables.replace("Folder"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_name']",
			RuntimeVariables.replace("DM Folder3 Name"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
	}
}