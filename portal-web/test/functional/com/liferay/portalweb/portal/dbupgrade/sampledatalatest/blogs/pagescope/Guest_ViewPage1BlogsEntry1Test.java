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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewPage1BlogsEntry1Test extends BaseTestCase {
	public void testGuest_ViewPage1BlogsEntry1() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/blogs-page-scope-community/");
		selenium.clickAt("link=Blogs Test Page1",
			RuntimeVariables.replace("Blogs Test Page1"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//span[@class='portlet-title-text']", "Blogs");
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("2 Comments"),
			selenium.getText("//span[@class='comments']/a"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@class,'aui-rating-element aui-rating-element-on')])[4]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//img[contains(@class,'aui-rating-element aui-rating-element-on')])[5]"));
		assertEquals(RuntimeVariables.replace("2 Comments"),
			selenium.getText("//span[@class='comments']/a"));
		selenium.clickAt("//span[@class='comments']/a",
			RuntimeVariables.replace("2 Comments"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("2 Comments"),
			selenium.getText("//span[@class='comments']"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@class,'aui-rating-element aui-rating-element-on')])[4]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//img[contains(@class,'aui-rating-element aui-rating-element-on')])[5]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Comment1 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Comment2 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[2]"));
		selenium.open("/web/blogs-page-scope-community/");
		selenium.clickAt("link=Blogs Test Page1",
			RuntimeVariables.replace("Blogs Test Page1"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@id='_33_keywords']");
		selenium.type("//input[@id='_33_keywords']",
			RuntimeVariables.replace("Entry1"));
		selenium.waitForVisible("//input[@value='Search']");
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//td[2]/a", "Blogs Entry1 Title");
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Blogs Entry1 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("2 Comments"),
			selenium.getText("//span[@class='comments']"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@class,'aui-rating-element aui-rating-element-on')])[4]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//img[contains(@class,'aui-rating-element aui-rating-element-on')])[5]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Comment1 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Comment2 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[2]"));
		selenium.open("/web/blogs-page-scope-community/");
		selenium.clickAt("link=Blogs Test Page2",
			RuntimeVariables.replace("Blogs Test Page2"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Blogs (Blogs Test Page2)");
		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Content"));
		selenium.waitForVisible("//input[@id='_33_keywords']");
		selenium.type("//input[@id='_33_keywords']",
			RuntimeVariables.replace("Entry1"));
		selenium.waitForVisible("//input[@value='Search']");
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-info']");
		assertEquals(RuntimeVariables.replace(
				"No entries were found that matched the keywords: Entry1."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Content"));
		selenium.open("/web/blogs-page-scope-community/");
		selenium.clickAt("link=Blogs Test Page3",
			RuntimeVariables.replace("Blogs Test Page3"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Blogs (Blogs Test Page2)");
		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Content"));
		selenium.waitForVisible("//input[@id='_33_keywords']");
		selenium.type("//input[@id='_33_keywords']",
			RuntimeVariables.replace("Entry1"));
		selenium.waitForVisible("//input[@value='Search']");
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-info']");
		assertEquals(RuntimeVariables.replace(
				"No entries were found that matched the keywords: Entry1."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Content"));
	}
}