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

package com.liferay.portalweb.demo.media.dmkaleo2workflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewResubmitDMFolderDocumentDocMSTest extends BaseTestCase {
	public void testUser_ViewResubmitDMFolderDocumentDocMS()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.clickAt("//li[@id='_145_mySites']/a/span",
			RuntimeVariables.replace("Go to"));
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Submissions",
			RuntimeVariables.replace("My Submissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Single Approver"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("DM Document Title Edit"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Documents and Media Document"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isElementPresent("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//td[6]/a"));
	}
}