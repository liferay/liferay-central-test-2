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

package com.liferay.portalweb.portlet.recentbloggers.rbentry.viewrbusernameandimage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewRBUserNameAndImageTest extends BaseTestCase {
	public void testViewRBUserNameAndImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Recent Bloggers Test Page",
			RuntimeVariables.replace("Recent Bloggers Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//tr[@class='portlet-section-body results-row last']/td/div/a/span[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td/div/a/span[2]"));
		assertEquals(RuntimeVariables.replace("Posts: 1"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td/div/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Stars: 0"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td/div/div/div[2]"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td/div/div/div[3]",
				"Date:"));
	}
}