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

package com.liferay.portalweb.plugins.drools.user.usecase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User3_ViewBlogsEntry3TagWCSSDTest extends BaseTestCase {
	public void testUser3_ViewBlogsEntry3TagWCSSD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Sample Drools Test Page",
			RuntimeVariables.replace("Sample Drools Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//div[@class='portlet-content']",
				"Welcome User Three!"));
		assertTrue(selenium.isPartialText("//div[@class='portlet-content']",
				"West Coast Symposium"));
		assertTrue(selenium.isPartialText("//div[@class='portlet-content']",
				"Blogs Entry Content"));
	}
}