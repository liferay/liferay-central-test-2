/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.webcontentdisplay.comment.editwcdwebcontentcomment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWCDWebContentCommentTest extends BaseTestCase {
	public void testEditWCDWebContentComment() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Web Content Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"This is a test wcd web content comment."),
			selenium.getText("//div/div[3]/div/div[1]"));
		selenium.clickAt("link=Edit", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[4]/div/div[2]/span/span/span/textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("//div[4]/div/div[2]/span/span/span/textarea",
			RuntimeVariables.replace(
				"This is a test wcd web content comment. Edited."));
		selenium.saveScreenShotAndSource();
		selenium.keyPress("//div[4]/div/div[2]/span/span/span/textarea",
			RuntimeVariables.replace("\\48"));
		selenium.keyPress("//div[4]/div/div[2]/span/span/span/textarea",
			RuntimeVariables.replace("\\8"));
		selenium.click(RuntimeVariables.replace("//input[@value='Publish']"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		assertEquals(RuntimeVariables.replace(
				"This is a test wcd web content comment. Edited."),
			selenium.getText("//div/div[3]/div/div[1]"));
		assertNotEquals(RuntimeVariables.replace(
				"This is a test wcd web content comment."),
			selenium.getText("//div/div[3]/div/div[1]"));
	}
}