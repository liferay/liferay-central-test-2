/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename255characterchildpagemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiPageName255CharacterChildPage2Test extends BaseTestCase {
	public void testAddWikiPageName255CharacterChildPage2()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=All Pages", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=lllllllll1lllllllll2lllllllll3lllllllll4lllllllll5lllllllll6lllllllll7lllllllll8lllllllll9llllllll10llllllll11llllllll12llllllll13llllllll14llllllll15llllllll16llllllll17llllllll18llllllll19llllllll20llllllll21llllllll22llllllll23llllllll24llllllll25llll",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Add Child Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_36_title",
			RuntimeVariables.replace("Front2 Page2 Child2 Page2 Test2"));
		selenium.saveScreenShotAndSource();
		selenium.type("_36_content",
			RuntimeVariables.replace(
				"This is a front2 page2 child2 page2 test2."));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		assertEquals(RuntimeVariables.replace("Front2 Page2 Child2 Page2 Test2"),
			selenium.getText("//div[@class='child-pages']/ul/li[2]/a"));
		selenium.clickAt("link=Front2 Page2 Child2 Page2 Test2",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText("//div[2]/h1/span",
				"Front2 Page2 Child2 Page2 Test2"));
		assertEquals(RuntimeVariables.replace(
				"This is a front2 page2 child2 page2 test2."),
			selenium.getText("//div/div[5]/div"));
	}
}