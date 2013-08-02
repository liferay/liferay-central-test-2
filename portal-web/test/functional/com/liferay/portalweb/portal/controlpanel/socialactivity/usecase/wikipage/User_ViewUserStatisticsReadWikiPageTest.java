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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.wikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewUserStatisticsReadWikiPageTest extends BaseTestCase {
	public void testUser_ViewUserStatisticsReadWikiPage()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=User Statistics Test Page",
			RuntimeVariables.replace("User Statistics Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//tr[contains(.,'Joe Bloggs')]//span[@class='user-name']",
			"Joe Bloggs");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//tr[contains(.,'Joe Bloggs')]//span[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Rank: 1"),
			selenium.getText(
				"//tr[contains(.,'Joe Bloggs')]//div[@class='user-rank']"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 0"),
			selenium.getText(
				"//tr[contains(.,'Joe Bloggs')]//div[@class='contribution-score']"));
		assertEquals(RuntimeVariables.replace("Participation Score: 28"),
			selenium.getText(
				"//tr[contains(.,'Joe Bloggs')]//div[@class='participation-score']"));
		assertEquals(RuntimeVariables.replace("User's Wiki Pages: 1"),
			selenium.getText(
				"//tr[contains(.,'Joe Bloggs')]//div[@class='social-counter-user.wikis']"));
		assertEquals(RuntimeVariables.replace("User's Attachments: 2"),
			selenium.getText(
				"//tr[contains(.,'Joe Bloggs')]//div[@class='social-counter-user.attachments']"));
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText(
				"//tr[contains(.,'userfn userln')]//span[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Rank: 2"),
			selenium.getText(
				"//tr[contains(.,'userfn userln')]//div[@class='user-rank']"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 0"),
			selenium.getText(
				"//tr[contains(.,'userfn userln')]//div[@class='contribution-score']"));
		assertEquals(RuntimeVariables.replace("Participation Score: 4"),
			selenium.getText(
				"//tr[contains(.,'userfn userln')]//div[@class='participation-score']"));
	}
}