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

package com.liferay.portalweb.portal.dbupgrade.sampledata527.social.relation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SRl_AddPageFriendsTest extends BaseTestCase {
	public void testSRl_AddPageFriends() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialrelationsn1/home/");
		selenium.waitForVisible("//div[@id='add-page']/a/span");
		assertEquals(RuntimeVariables.replace("Add Page"),
			selenium.getText("//div[@id='add-page']/a/span"));
		selenium.clickAt("//div[@id='add-page']/a/span",
			RuntimeVariables.replace("Add Page"));
		selenium.waitForVisible("//input[@name='new_page']");
		selenium.type("//input[@name='new_page']",
			RuntimeVariables.replace("Friends Test Page"));
		selenium.waitForVisible("//a[@class='save-page']");
		selenium.clickAt("//a[@class='save-page']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("link=Friends Test Page");
		selenium.clickAt("link=Friends Test Page",
			RuntimeVariables.replace("Friends Test Page"));
		selenium.waitForPageToLoad("30000");
	}
}