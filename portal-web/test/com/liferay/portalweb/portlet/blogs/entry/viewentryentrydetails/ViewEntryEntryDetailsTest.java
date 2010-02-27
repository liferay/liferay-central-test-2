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

package com.liferay.portalweb.portlet.blogs.entry.viewentryentrydetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ViewEntryEntryDetailsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewEntryEntryDetailsTest extends BaseTestCase {
	public void testViewEntryEntryDetails() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Blogs Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Title", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText("//form/div/div[1]/div[1]"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("//form/div/div[4]/div[1]"));
		assertEquals(RuntimeVariables.replace("Content."),
			selenium.getText("//p"));
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("//div[4]/div[5]/div[1]/div/div"));
		assertTrue(selenium.isElementPresent("//div[5]/div[2]/div/div"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//div[2]/div/div/div[1]/span[1]"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//div[2]/div/div/div[1]/span[2]"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//div[2]/div/div/ul/li/span"));
	}
}