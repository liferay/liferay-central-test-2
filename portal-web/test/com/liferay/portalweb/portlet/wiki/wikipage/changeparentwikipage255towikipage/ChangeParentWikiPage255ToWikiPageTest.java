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

package com.liferay.portalweb.portlet.wiki.wikipage.changeparentwikipage255towikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ChangeParentWikiPage255ToWikiPageTest extends BaseTestCase {
	public void testChangeParentWikiPage255ToWikiPage()
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
		selenium.click(RuntimeVariables.replace("link=All Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=lllllllll1lllllllll2lllllllll3lllllllll4lllllllll5lllllllll6lllllllll7lllllllll8lllllllll9llllllll10llllllll11llllllll12llllllll13llllllll14llllllll15llllllll16llllllll17llllllll18llllllll19llllllll20llllllll21llllllll22llllllll23llllllll24llllllll25llll",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"lllllllll1lllllllll2lllllllll3lllllllll4lllllllll5lllllllll6lllllllll7lllllllll8lllllllll9llllllll10llllllll11llllllll12llllllll13llllllll14llllllll15llllllll16llllllll17llllllll18llllllll19llllllll20llllllll21llllllll22llllllll23llllllll24llllllll25llll"),
			selenium.getText(
				"//ul[@class='breadcrumbs lfr-component']/li[3]/span/a"));
		selenium.clickAt("link=Details", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Move", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Change Parent", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_36_newParentTitle")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.select("_36_newParentTitle",
			RuntimeVariables.replace("label=- Wiki Page Test"));
		selenium.clickAt("//input[@value='Change Parent']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
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
		selenium.clickAt("link=Wiki Page Test", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"link=lllllllll1lllllllll2lllllllll3lllllllll4lllllllll5lllllllll6lllllllll7lllllllll8lllllllll9llllllll10llllllll11llllllll12llllllll13llllllll14llllllll15llllllll16llllllll17llllllll18llllllll19llllllll20llllllll21llllllll22llllllll23llllllll24llllllll25llll"));
		selenium.clickAt("link=lllllllll1lllllllll2lllllllll3lllllllll4lllllllll5lllllllll6lllllllll7lllllllll8lllllllll9llllllll10llllllll11llllllll12llllllll13llllllll14llllllll15llllllll16llllllll17llllllll18llllllll19llllllll20llllllll21llllllll22llllllll23llllllll24llllllll25llll",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Wiki Page Test"),
			selenium.getText(
				"//ul[@class='breadcrumbs lfr-component']/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace(
				"lllllllll1lllllllll2lllllllll3lllllllll4lllllllll5lllllllll6lllllllll7lllllllll8lllllllll9llllllll10llllllll11llllllll12llllllll13llllllll14llllllll15llllllll16llllllll17llllllll18llllllll19llllllll20llllllll21llllllll22llllllll23llllllll24llllllll25llll"),
			selenium.getText(
				"//ul[@class='breadcrumbs lfr-component']/li[4]/span/a"));
	}
}