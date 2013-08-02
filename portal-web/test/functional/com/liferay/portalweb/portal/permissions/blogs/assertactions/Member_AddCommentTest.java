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

package com.liferay.portalweb.portal.permissions.blogs.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AddCommentTest extends BaseTestCase {
	public void testMember_AddComment() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Blogs Test Entry"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		selenium.clickAt("//div[@class='entry-title']/h2/a",
			RuntimeVariables.replace("Permissions Blogs Test Entry"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//span/a[contains(.,'Add Comment')]");
		assertEquals(RuntimeVariables.replace("Add Comment"),
			selenium.getText("//span/a[contains(.,'Add Comment')]"));
		selenium.click("//span/a[contains(.,'Add Comment')]");
		selenium.waitForVisible("//textarea[@name='_33_postReplyBody0']");
		selenium.type("//textarea[@name='_33_postReplyBody0']",
			RuntimeVariables.replace("Member Permissions Blogs Test Comment"));
		selenium.clickAt("//input[@value='Reply']",
			RuntimeVariables.replace("Reply"));
		selenium.waitForVisible("//div[@id='_33_discussion-status-messages']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@id='_33_discussion-status-messages']"));
		assertEquals(RuntimeVariables.replace(
				"Member Permissions Blogs Test Comment"),
			selenium.getText(
				"//div[@class='lfr-discussion last']/div[3]/div/div"));
	}
}