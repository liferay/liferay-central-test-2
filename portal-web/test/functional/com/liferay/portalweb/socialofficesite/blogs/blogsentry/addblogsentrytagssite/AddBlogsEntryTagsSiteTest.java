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

package com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrytagssite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryTagsSiteTest extends BaseTestCase {
	public void testAddBlogsEntryTagsSite() throws Exception {
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
				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("tag1"));
				selenium.clickAt("//button[@id='add']",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"xPath=(//span[@class='aui-textboxlistentry-text'])[1]");
				assertEquals(RuntimeVariables.replace("tag1"),
					selenium.getText(
						"xPath=(//span[@class='aui-textboxlistentry-text'])[1]"));
				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("tag2"));
				selenium.clickAt("//button[@id='add']",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"xPath=(//span[@class='aui-textboxlistentry-text'])[2]");
				assertEquals(RuntimeVariables.replace("tag2"),
					selenium.getText(
						"xPath=(//span[@class='aui-textboxlistentry-text'])[2]"));
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