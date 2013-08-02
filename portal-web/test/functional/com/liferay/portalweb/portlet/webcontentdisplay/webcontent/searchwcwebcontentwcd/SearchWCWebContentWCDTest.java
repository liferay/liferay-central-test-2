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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.searchwcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchWCWebContentWCDTest extends BaseTestCase {
	public void testSearchWCWebContentWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Configuration",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible("//input[@name='_86_keywords']");
		selenium.type("//input[@name='_86_keywords']",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//td[contains(.,'WC WebContent Title')]/a"));
		selenium.type("//input[@name='_86_keywords']",
			RuntimeVariables.replace("WCD1 WebContent1 Title1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No Web Content was found."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		assertTrue(selenium.isElementNotPresent(
				"//td[contains(.,'WC WebContent Title')]/a"));
	}
}