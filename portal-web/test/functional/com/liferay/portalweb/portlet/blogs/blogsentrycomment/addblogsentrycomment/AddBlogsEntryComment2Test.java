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

package com.liferay.portalweb.portlet.blogs.blogsentrycomment.addblogsentrycomment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryComment2Test extends BaseTestCase {
	public void testAddBlogsEntryComment2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		selenium.clickAt("//div[@class='entry-title']/h2/a",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Comment"),
			selenium.getText(
				"//fieldset[contains(@class,'add-comment')]/div/span/a"));
		selenium.clickAt("//fieldset[contains(@class,'add-comment')]/div/span/a",
			RuntimeVariables.replace("Add Comment"));
		selenium.waitForVisible("//textarea[@name='_33_postReplyBody0']");
		selenium.type("//textarea[@name='_33_postReplyBody0']",
			RuntimeVariables.replace("Blogs Entry Comment2 Body"));
		selenium.clickAt("//input[@value='Reply']",
			RuntimeVariables.replace("Reply"));
		selenium.waitForText("//div[@class='lfr-message-response portlet-msg-success']",
			"Your request processed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Comment2 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[2]"));
	}
}