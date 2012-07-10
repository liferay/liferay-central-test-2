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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfoldersdmd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolder3AddMultipleDocumentsDMDActionsTest
	extends BaseTestCase {
	public void testViewDMFolder3AddMultipleDocumentsDMDActions()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Documents and Media Display Test Page",
			RuntimeVariables.replace("Documents and Media Display Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText("//tr[5]/td[1]/a[2]/strong"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[3]"));
		selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[3]",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Multiple Documents')]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Multiple Documents"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Multiple Documents')]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Multiple Documents')]/a",
			RuntimeVariables.replace("Multiple Documents"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Add Multiple Documents"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"link=Browse (you can select multiple files).")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"link=Browse (you can select multiple files)."));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-upload-movie-content']/object"));
	}
}