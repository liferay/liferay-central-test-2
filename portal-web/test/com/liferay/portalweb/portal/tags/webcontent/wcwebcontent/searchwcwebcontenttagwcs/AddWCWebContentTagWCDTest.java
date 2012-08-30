/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.tags.webcontent.wcwebcontent.searchwcwebcontenttagwcs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentTagWCDTest extends BaseTestCase {
	public void testAddWCWebContentTagWCD() throws Exception {
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		selenium.clickAt("//img[@alt='Edit Web Content']",
			RuntimeVariables.replace("Edit Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//section[@id='portlet_15']/div/div/div/div/h1/span"));
		assertEquals(RuntimeVariables.replace("Version: 1.0"),
			selenium.getText("//span[@class='workflow-version']"));
		assertTrue(selenium.isPartialText("//a[@id='_15_categorizationLink']",
				"Categorization"));
		selenium.clickAt("//a[@id='_15_categorizationLink']",
			RuntimeVariables.replace("Categorization"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//input[@class='lfr-tag-selector-input aui-field-input-text']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@class='lfr-tag-selector-input aui-field-input-text']",
			RuntimeVariables.replace("tag"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		selenium.clickAt("//img[@alt='Edit Web Content']",
			RuntimeVariables.replace("Edit Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//section[@id='portlet_15']/div/div/div/div/h1/span"));
		assertEquals(RuntimeVariables.replace("Version: 1.1"),
			selenium.getText("//span[@class='workflow-version']"));
		assertTrue(selenium.isPartialText("//a[@id='_15_categorizationLink']",
				"Categorization"));
		selenium.clickAt("//a[@id='_15_categorizationLink']",
			RuntimeVariables.replace("Categorization"));
		assertEquals(RuntimeVariables.replace("tag"),
			selenium.getText(
				"//div[contains(@id,'assetTagsSelector')]/ul/li/span/span[contains(.,'tag')]"));
	}
}