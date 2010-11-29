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

package com.liferay.portalweb.portlet.wiki.comment.editfrontpagecommentbody;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditFrontPageCommentBodyTest extends BaseTestCase {
	public void testEditFrontPageCommentBody() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"This is a wiki page test comment."),
			selenium.getText("//td[2]/div[1]"));
		selenium.clickAt("//td[4]/span/a/span", RuntimeVariables.replace(""));
		selenium.type("_36_editReplyBody1",
			RuntimeVariables.replace(
				"This is a wiki page test comment. Edited."));
		selenium.saveScreenShotAndSource();
		selenium.keyPress("_36_editReplyBody1", RuntimeVariables.replace("\\48"));
		selenium.keyPress("_36_editReplyBody1", RuntimeVariables.replace("\\8"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div[1]"));
		assertEquals(RuntimeVariables.replace(
				"This is a wiki page test comment. Edited."),
			selenium.getText("//td[2]/div[1]"));
		assertNotEquals(RuntimeVariables.replace(
				"This is a wiki page test comment."),
			selenium.getText("//td[2]/div[1]"));
	}
}