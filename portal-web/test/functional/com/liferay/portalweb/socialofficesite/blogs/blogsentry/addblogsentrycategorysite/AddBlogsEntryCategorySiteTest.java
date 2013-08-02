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

package com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrycategorysite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryCategorySiteTest extends BaseTestCase {
	public void testAddBlogsEntryCategorySite() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//div[@id='so-sidebar']/h3"));
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Open"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Open Site Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Blogs"),
					selenium.getText("//nav/ul/li[contains(.,'Blogs')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Blogs')]/a/span",
					RuntimeVariables.replace("Blogs"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Blog Entry']",
					RuntimeVariables.replace("Add Blog Entry"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_33_title']",
					RuntimeVariables.replace("Blogs Entry Title"));
				selenium.waitForElementPresent(
					"//textarea[@id='_33_editor' and contains(@style,'display: none;')]");
				selenium.waitForVisible("//span[.='Source']");
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForVisible(
					"//a[@class='cke_button_source cke_on']");
				selenium.waitForVisible(
					"//td[@id='cke_contents__33_editor']/textarea");
				selenium.type("//td[@id='cke_contents__33_editor']/textarea",
					RuntimeVariables.replace("Blogs Entry Content"));
				selenium.waitForVisible("//span[.='Source']");
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForElementPresent(
					"//textarea[@id='_33_editor' and contains(@style,'display: none;')]");
				selenium.waitForVisible(
					"//td[@id='cke_contents__33_editor']/iframe");
				selenium.selectFrame(
					"//td[@id='cke_contents__33_editor']/iframe");
				selenium.waitForText("//body", "Blogs Entry Content");
				selenium.selectFrame("relative=top");

				boolean addTagsVisible = selenium.isVisible(
						"//input[@title='Add Tags']");

				if (addTagsVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText(
						"//div[@id='blogsEntryCategorizationPanel']/div/div/span"));
				selenium.clickAt("//div[@id='blogsEntryCategorizationPanel']/div/div/span",
					RuntimeVariables.replace("Categorization"));
				selenium.waitForVisible("//input[@title='Add Tags']");

			case 2:
				assertEquals(RuntimeVariables.replace("Vocabulary Name"),
					selenium.getText(
						"//label[contains(@id,'assetCategoriesLabel')]"));
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText(
						"//div[contains(@id,'assetCategoriesSelector')]/span/span/button"));
				selenium.clickAt("//div[contains(@id,'assetCategoriesSelector')]/span/span/button",
					RuntimeVariables.replace("Select"));
				selenium.waitForVisible(
					"//li[contains(@id,'category')]/div/div[3]");
				assertEquals(RuntimeVariables.replace("Category Name"),
					selenium.getText(
						"//li[contains(@id,'category')]/div/div[3]"));

				boolean categoryChecked = selenium.isElementPresent(
						"//div[contains(@class,'tree-node-checked')]");

				if (categoryChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li[contains(@id,'category')]/div/div[3]",
					RuntimeVariables.replace("Category Name"));
				selenium.waitForElementPresent(
					"//div[contains(@class,'tree-node-checked')]");

			case 3:
				assertTrue(selenium.isElementPresent(
						"//div[contains(@class,'tree-node-checked')]"));
				assertTrue(selenium.isElementPresent(
						"//button[@title='Close dialog']"));
				selenium.clickAt("//button[@title='Close dialog']",
					RuntimeVariables.replace("Close"));
				selenium.waitForElementPresent(
					"//span[contains(@class,'textboxlistentry')]");
				assertTrue(selenium.isElementPresent(
						"//span[contains(@class,'textboxlistentry')]"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
					selenium.getText("//div[@class='entry-title']/h2/a"));
				assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
					selenium.getText("//div[@class='entry-body']"));

			case 100:
				label = -1;
			}
		}
	}
}