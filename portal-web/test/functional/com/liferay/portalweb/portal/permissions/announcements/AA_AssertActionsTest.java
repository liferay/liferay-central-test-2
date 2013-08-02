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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AA_AssertActionsTest extends BaseTestCase {
	public void testAA_AssertActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Edit"));
		assertTrue(selenium.isElementPresent("link=Delete"));
		assertTrue(selenium.isElementPresent("link=Mark as Read"));
		selenium.clickAt("link=Mark as Read",
			RuntimeVariables.replace("Mark as Read"));
		selenium.waitForVisible("link=Show");
		selenium.clickAt("link=Show", RuntimeVariables.replace("Show"));
		selenium.waitForVisible("link=Hide");
		selenium.clickAt("link=Hide", RuntimeVariables.replace("Hide"));
		selenium.waitForVisible("link=Show");
		selenium.waitForElementNotPresent("link=Hide");
		selenium.clickAt("link=Manage Entries",
			RuntimeVariables.replace("Manage Entries"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@value='Add Entry']"));
	}
}