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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.ratewcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateWCWebContentWCDTest extends BaseTestCase {
	public void testRateWCWebContentWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Web Content Display Test Page");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("//div/div[4]/div/div[1]/div/div"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("//div/div[4]/div/div[2]/div/div"));
		assertTrue(selenium.isVisible("//a[5]"));
		selenium.clickAt("//a[5]",
			RuntimeVariables.replace("Rate this 5 stars out of 5."));
		selenium.waitForText("//div/div[4]/div/div[2]/div/div",
			"Average (1 Vote)");
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("//div/div[4]/div/div[2]/div/div"));
	}
}