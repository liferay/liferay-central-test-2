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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.delete;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertCannotDeleteDLDocumentTest extends BaseTestCase {
	public void testMember_AssertCannotDeleteDLDocument()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Documents and Media",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"xpath=(//span[@class='overlay document-action']/span/ul/li/strong/a)[2]");
		selenium.clickAt("xpath=(//span[@class='overlay document-action']/span/ul/li/strong/a)[2]",
			RuntimeVariables.replace("Document Actions"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Download (0.3k)')]/a");
		assertEquals(RuntimeVariables.replace("Download (0.3k)"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Download (0.3k)')]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
		selenium.clickAt("//input[@id='_20_allRowIdsCheckbox']",
			RuntimeVariables.replace("Select All"));
		Thread.sleep(1000);
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]"));
	}
}