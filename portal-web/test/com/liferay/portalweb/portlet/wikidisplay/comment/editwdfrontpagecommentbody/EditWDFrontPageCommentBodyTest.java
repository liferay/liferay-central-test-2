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

package com.liferay.portalweb.portlet.wikidisplay.comment.editwdfrontpagecommentbody;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="EditWDFrontPageCommentBodyTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class EditWDFrontPageCommentBodyTest extends BaseTestCase {
	public void testEditWDFrontPageCommentBody() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This is a wiki page test comment."),
			selenium.getText("//td[2]/div[1]"));
		selenium.clickAt("//td[4]/span/a/span", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//tr[2]/td/div/textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//tr[2]/td/div/textarea",
			RuntimeVariables.replace(
				"This is a wiki page test comment. Edited."));
		selenium.keyPress("//tr[2]/td/div/textarea",
			RuntimeVariables.replace("\\48"));
		selenium.keyPress("//tr[2]/td/div/textarea",
			RuntimeVariables.replace("\\8"));
		selenium.clickAt("//input[@value='Update']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertEquals(RuntimeVariables.replace(
				"This is a wiki page test comment. Edited."),
			selenium.getText("//td[2]/div[1]"));
		assertNotEquals(RuntimeVariables.replace(
				"This is a wiki page test comment."),
			selenium.getText("//td[2]/div[1]"));
	}
}