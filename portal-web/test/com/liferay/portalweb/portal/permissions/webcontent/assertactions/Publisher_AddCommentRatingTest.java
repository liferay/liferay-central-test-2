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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Publisher_AddCommentRatingTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class Publisher_AddCommentRatingTest extends BaseTestCase {
	public void testPublisher_AddCommentRating() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Web Content Display Permissions Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Web Content Display Permissions Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[1]/ul/li[2]/a[1]", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertTrue(selenium.isTextPresent("+1"));
		selenium.clickAt("//td[1]/ul/li[2]/a[2]", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertTrue(selenium.isTextPresent("-1"));
		selenium.clickAt("//td[1]/ul/li[2]/a[2]", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertTrue(selenium.isTextPresent("\u00b10"));
	}
}