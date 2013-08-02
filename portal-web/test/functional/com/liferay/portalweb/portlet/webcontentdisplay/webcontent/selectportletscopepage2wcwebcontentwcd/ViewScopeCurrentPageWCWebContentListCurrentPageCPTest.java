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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.selectportletscopepage2wcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewScopeCurrentPageWCWebContentListCurrentPageCPTest
	extends BaseTestCase {
	public void testViewScopeCurrentPageWCWebContentListCurrentPageCP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//div/span/ul/li/strong/a",
				"Scope: Default"));
		selenium.clickAt("//div/span/ul/li/strong/a",
			RuntimeVariables.replace("Scope: Default"));
		assertEquals(RuntimeVariables.replace("Web Content Display Test Page2"),
			selenium.getText("//li[2]/a"));
		selenium.clickAt("//li[2]/a",
			RuntimeVariables.replace("Web Content Display Test Page2"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//div/span/ul/li/strong/a",
				"Scope: Web Content Display Test Page2"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//td[4]/a"));
	}
}