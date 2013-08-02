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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertActionTest extends BaseTestCase {
	public void testMember_AssertAction() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Options']/ul/li/strong/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Look and Feel')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Remove']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Subfolder')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Permissions')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Folder')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Repository')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Shortcut')]"));
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Subfolder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Subfolder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Subfolder"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Subfolder')]"));
		assertEquals(RuntimeVariables.replace("Multiple Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Multiple Media')]"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Media')]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Mine", RuntimeVariables.replace("Mine"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Permissions Image 3 Test Edited"));
		selenium.clickAt("link=Recent", RuntimeVariables.replace("Recent"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//img[@alt='Permissions Image 2 Test - ']");
		assertTrue(selenium.isTextPresent("Permissions Image 2 Test"));
		assertTrue(selenium.isTextPresent("Permissions Image 3 Test Edited"));
	}
}