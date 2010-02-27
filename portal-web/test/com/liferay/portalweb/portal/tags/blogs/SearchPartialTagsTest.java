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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="SearchPartialTagsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SearchPartialTagsTest extends BaseTestCase {
	public void testSearchPartialTags() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Blogs Tags Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Tags Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_33_keywords", RuntimeVariables.replace("selenium1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Tags1 Blogs1 Test1 Entry1"));
		assertFalse(selenium.isTextPresent("Tags2 Blogs2 Test2 Entry2"));
		assertFalse(selenium.isTextPresent("Tags3 Blogs3 Test3 Entry3"));
		selenium.type("_33_keywords", RuntimeVariables.replace("liferay3"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Tags1 Blogs1 Test1 Entry1"));
		assertTrue(selenium.isTextPresent("Tags2 Blogs2 Test2 Entry2"));
		assertTrue(selenium.isTextPresent("Tags3 Blogs3 Test3 Entry3"));
		selenium.type("_33_keywords", RuntimeVariables.replace("selen"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Tags1 Blogs1 Test1 Entry1"));
		assertTrue(selenium.isTextPresent("Tags2 Blogs2 Test2 Entry2"));
		assertTrue(selenium.isTextPresent("Tags2 Blogs2 Test2 Entry2"));
	}
}