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

package com.liferay.portalweb.portlet.pagecomments.comment.addcommentreplymultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCommentReply3Test extends BaseTestCase {
	public void testAddCommentReply3() throws Exception {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//tr[8]/td[2]/table[1]/tbody/tr/td[2]/span/a/span",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_107_postReplyBody3")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("_107_postReplyBody3"));
		selenium.type("_107_postReplyBody3",
			RuntimeVariables.replace("This is a test reply3 comment3."));
		selenium.saveScreenShotAndSource();
		selenium.keyPress("_107_postReplyBody3",
			RuntimeVariables.replace("\\48"));
		selenium.keyPress("_107_postReplyBody3", RuntimeVariables.replace("\\8"));
		selenium.clickAt("//tr[8]/td[2]/table[2]/tbody/tr[1]/td/input[1]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div[1]"));
		assertEquals(RuntimeVariables.replace("This is a test reply1 comment1."),
			selenium.getText("//tr[5]/td[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("This is a test reply2 comment2."),
			selenium.getText("//tr[8]/td[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("This is a test reply3 comment3."),
			selenium.getText("//tr[11]/td[2]/div[1]"));
	}
}