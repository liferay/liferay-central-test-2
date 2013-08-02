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

package com.liferay.portalweb.portlet.pagecomments.comment.editcommentbody;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditCommentBodyTest extends BaseTestCase {
	public void testEditCommentBody() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace("Page Comments Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("PC Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		selenium.mouseOver("//li[3]/span/a/span[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//li[3]/span/a/span[contains(.,'Edit')]"));
		selenium.clickAt("//li[3]/span/a/span[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//textarea[@name='_107_editReplyBody1']");
		selenium.type("//textarea[@name='_107_editReplyBody1']",
			RuntimeVariables.replace("PC Comment Edit"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForText("//div[@class='lfr-message-response portlet-msg-success']",
			"Your request processed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("PC Comment Edit"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
	}
}