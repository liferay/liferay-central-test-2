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

package com.liferay.portalweb.portal.controlpanel.sites.site.leavesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_LeaveSitesTest extends BaseTestCase {
	public void testUser_LeaveSites() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/usersn/home/");
		selenium.clickAt("link=My Sites", RuntimeVariables.replace("My Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_29_name']",
			RuntimeVariables.replace("Site Name"));
		selenium.clickAt("xPath=(//input[@value='Search'])[3]",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[1]"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'Site Name')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Leave"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[4]/span/a/span"));
		selenium.clickAt("//tr[contains(.,'Site Name')]/td[4]/span/a/span",
			RuntimeVariables.replace("Leave"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/user/usersn/home/");
		selenium.clickAt("link=My Sites", RuntimeVariables.replace("My Sites"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No sites were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}