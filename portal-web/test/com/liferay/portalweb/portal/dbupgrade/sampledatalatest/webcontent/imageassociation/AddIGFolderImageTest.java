/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddIGFolderImageTest extends BaseTestCase {
	public void testAddIGFolderImage() throws Exception {
		selenium.open("/web/web-content-image-association-community/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Image Gallery Page",
			RuntimeVariables.replace("Image Gallery Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Folder Test"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("Folder Test"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[contains(.,'Add Media')]/a"));
		selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[contains(.,'Add Media')]/a",
			RuntimeVariables.replace("Add Media"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//tr[3]/td/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText("//tr[3]/td/a"));
		selenium.click(RuntimeVariables.replace("//tr[3]/td/a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_31_file']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_31_file']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata610\\webcontent\\imageassociation\\dependencies\\ImageGallery.jpg"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Folder Test"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("Folder Test"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isVisible(
				"//a[contains(@class,'image-thumbnail')]/img"));
		assertEquals(RuntimeVariables.replace("ImageGallery.jpg"),
			selenium.getText("//span[@class='image-title']"));
	}
}