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

package com.liferay.portalweb.asset.bookmarks.bmbookmark.addnewbmfolderbookmarkapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNewBMFolderBookmarkAPActionsTest extends BaseTestCase {
	public void testAddNewBMFolderBookmarkAPActions() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Asset Publisher Test Page",
					RuntimeVariables.replace("Asset Publisher Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Add New"),
					selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Add New']/ul/li/strong/a",
					RuntimeVariables.replace("Add New"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Bookmarks Entry')]");
				assertEquals(RuntimeVariables.replace("Bookmarks Entry"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Bookmarks Entry')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Bookmarks Entry')]",
					RuntimeVariables.replace("Bookmarks Entry"));
				selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
				selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/search_container.js')]");
				selenium.waitForVisible("//input[@value='Select']");
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				Thread.sleep(1000);
				selenium.selectWindow("title=Bookmarks");

				boolean choose1Present = selenium.isElementPresent(
						"//td[4]/input");

				if (choose1Present) {
					label = 2;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
				selenium.selectFrame("//iframe[contains(@id,'editAsset')]");

			case 2:

				boolean choose2Present = selenium.isElementPresent(
						"//td[4]/input");

				if (!choose2Present) {
					label = 3;

					continue;
				}

				selenium.waitForElementPresent("//input[@value='Choose']");
				selenium.click("//input[@value='Choose']");

			case 3:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
				selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
				selenium.waitForText("//a[@id='_28_folderName']",
					"BM Folder Name");
				assertEquals(RuntimeVariables.replace("BM Folder Name"),
					selenium.getText("//a[@id='_28_folderName']"));
				selenium.type("//input[@id='_28_name']",
					RuntimeVariables.replace("BM Folder Bookmark Name"));
				selenium.type("//input[@id='_28_url']",
					RuntimeVariables.replace("http://www.liferay.com"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.selectFrame("relative=top");
				selenium.waitForVisible("//h3[@class='asset-title']/a");
				assertEquals(RuntimeVariables.replace("BM Folder Bookmark Name"),
					selenium.getText("//h3[@class='asset-title']/a"));

			case 100:
				label = -1;
			}
		}
	}
}