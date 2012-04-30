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

package com.liferay.portalweb.demo.useradmin.permissionsindividualscope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFolder2Image2Test extends BaseTestCase {
	public void testAddFolder2Image2() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"link=Documents and Media Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("DL Folder 2 Name"),
					selenium.getText(
						"xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
				selenium.clickAt("xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]",
					RuntimeVariables.replace("DL Folder 2 Name"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("DL Folder 2 Name")
												.equals(selenium.getText(
										"//li[@class='folder selected']/a/span[2]"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("DL Folder 2 Name"),
					selenium.getText("//li[@class='folder selected']/a/span[2]"));
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//span[@title='Add']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Add']/ul/li/strong/a",
					RuntimeVariables.replace("Add"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Basic Document"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a",
					RuntimeVariables.replace("Basic Document"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.type("//input[@id='_20_file']",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\demo\\media\\dependencies\\Document_2.jpg"));
				selenium.type("//input[@id='_20_title']",
					RuntimeVariables.replace("DL Folder 2 Image 2 Title"));
				selenium.type("//textarea[@id='_20_description']",
					RuntimeVariables.replace("DL Folder 2 Image 2 Description"));

				boolean categorizationVisible = selenium.isVisible(
						"//input[@title='Add Tags']");

				if (categorizationVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='dlFileEntryCategorizationPanel']/div/div/span",
					RuntimeVariables.replace("Categorization"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@title='Add Tags']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("cat"));
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//button[@id='add']"));
				selenium.clickAt("//button[@id='add']",
					RuntimeVariables.replace("Add"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Your request completed successfully.")
												.equals(selenium.getText(
										"//div[@class='portlet-msg-success']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"DL Folder 2 Image 2 Title"),
					selenium.getText(
						"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
				selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
					RuntimeVariables.replace("DL Folder 2 Image 2 Title"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"DL Folder 2 Image 2 Title")
												.equals(selenium.getText(
										"//h2[@class='document-title']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"DL Folder 2 Image 2 Title"),
					selenium.getText("//h2[@class='document-title']"));
				assertEquals(RuntimeVariables.replace("cat"),
					selenium.getText("//span[@class='tag']"));
				assertTrue(selenium.isVisible(
						"//div[@class='lfr-preview-file-image-container']/img"));
				assertEquals(RuntimeVariables.replace(
						"DL Folder 2 Image 2 Description"),
					selenium.getText("//span[@class='document-description']"));
				assertEquals(RuntimeVariables.replace("Status: Approved"),
					selenium.getText("//span[@class='workflow-status']"));
				assertEquals(RuntimeVariables.replace("Download (29.9k)"),
					selenium.getText("//span[@class='download-document']"));
				assertEquals(RuntimeVariables.replace("Content Type image/jpeg"),
					selenium.getText(
						"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div"));

			case 100:
				label = -1;
			}
		}
	}
}