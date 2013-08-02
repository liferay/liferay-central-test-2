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

package com.liferay.portalweb.portlet.directory.users.searchuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchUserTest extends BaseTestCase {
	public void testSearchUser() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Directory Test Page",
					RuntimeVariables.replace("Directory Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users", RuntimeVariables.replace("Users"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//div/div/a[contains(.,'Basic')]");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div/div/a[contains(.,'Basic')]",
					RuntimeVariables.replace("Basic \u00bb"));

			case 2:
				selenium.type("//input[@name='_11_keywords']",
					RuntimeVariables.replace("userfn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_11_keywords']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.isElementPresent("link=userfn"));
				selenium.type("//input[@name='_11_keywords']",
					RuntimeVariables.replace("userfn1"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_11_keywords']",
					RuntimeVariables.replace(""));
				assertFalse(selenium.isPartialText(
						"//div[@id='_11_usersSearchContainer']", "userfn"));
				assertEquals(RuntimeVariables.replace("No users were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}