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

package com.liferay.portalweb.kaleo.assetpublisher.wcwebcontent.viewwebcontentcompleted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentCompletedGuestTest extends BaseTestCase {
	public void testViewWebContentCompletedGuest() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//section"));
		assertEquals(RuntimeVariables.replace("Asset Publisher"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertTrue(selenium.isPartialText("//div[@class='asset-more']/a",
				"Read More"));
		selenium.clickAt("//div[@class='asset-more']/a",
			RuntimeVariables.replace("Read More"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText("//div[@class='asset-content']",
				"Web Content Content"));
	}
}