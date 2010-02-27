/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.pagecomments.comment.editcommentbodyspace;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="EditCommentBodySpaceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EditCommentBodySpaceTest extends BaseTestCase {
	public void testEditCommentBodySpace() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Page Comments Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("This is a test page comment."),
			selenium.getText(
				"//table[@class='lfr-table']/tbody/tr[2]/td[2]/div"));
		selenium.clickAt("link=Edit", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_107_editReplyBody1")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_107_editReplyBody1", RuntimeVariables.replace(" "));
		selenium.keyPress("_107_editReplyBody1",
			RuntimeVariables.replace("\\48"));
		selenium.keyPress("_107_editReplyBody1", RuntimeVariables.replace("\\8"));
		selenium.clickAt("_107_editReplyButton1", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertFalse(selenium.isTextPresent(
				"Your request processed successfully."));
		assertTrue(selenium.isVisible("_107_editReplyBody1"));
		assertTrue(selenium.isVisible("_107_editReplyButton1"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Page Comments Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("This is a test page comment."),
			selenium.getText(
				"//table[@class='lfr-table']/tbody/tr[2]/td[2]/div"));
	}
}