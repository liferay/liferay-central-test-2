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

package com.liferay.portalweb.asset.bookmarks.bmbookmark.selectexistingbmfolderbookmarkapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectExistingBMFolderBookmarkAPActionsTest extends BaseTestCase {
	public void testSelectExistingBMFolderBookmarkAPActions()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("BM Folder Bookmark Name"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//span[@title='Select Existing']/ul/li/strong/a/span");
		assertEquals(RuntimeVariables.replace("Select Existing"),
			selenium.getText(
				"//span[@title='Select Existing']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Select Existing']/ul/li/strong/a/span",
			RuntimeVariables.replace("Select Existing"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Bookmarks Entry')]/a");
		assertEquals(RuntimeVariables.replace("Bookmarks Entry"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Bookmarks Entry')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Bookmarks Entry')]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("BM Folder Bookmark Name"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("BM Folder Bookmark Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Bookmarks Entry"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("BM Folder Bookmark Name"),
			selenium.getText("//td[2]/a"));
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("BM Folder Bookmark Name"),
			selenium.getText("//h3[@class='asset-title']/a"));
	}
}