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

package com.liferay.portalweb.portlet.blogs.lar.exportlarblogsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportExportLARBlogsEntryTest extends BaseTestCase {
	public void testImportExportLARBlogsEntry() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Blogs Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Export / Import"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Import", RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.uploadTempFile("//input[@id='_86_importFileName']",
			RuntimeVariables.replace("Blogs_Entry.Portlet.lar"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_PORTLET_SETUPCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_PORTLET_ARCHIVED_SETUPSCheckbox']"));
		selenium.clickAt("//input[@id='_86_PORTLET_ARCHIVED_SETUPSCheckbox']",
			RuntimeVariables.replace("Archived Setups"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_PORTLET_ARCHIVED_SETUPSCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_PORTLET_USER_PREFERENCESCheckbox']"));
		selenium.click("//input[@id='_86_PORTLET_USER_PREFERENCESCheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_86_PORTLET_USER_PREFERENCESCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_DELETE_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_86_DELETE_PORTLET_DATACheckbox']",
			RuntimeVariables.replace("Delete portlet data before importing."));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_DELETE_PORTLET_DATACheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_86_PORTLET_DATACheckbox']",
			RuntimeVariables.replace("Data"));
		assertTrue(selenium.isChecked("//input[@id='_86_PORTLET_DATACheckbox']"));
		assertFalse(selenium.isChecked("//input[@id='_86_PERMISSIONSCheckbox']"));
		selenium.clickAt("//input[@id='_86_PERMISSIONSCheckbox']",
			RuntimeVariables.replace("Permissions"));
		assertTrue(selenium.isChecked("//input[@id='_86_PERMISSIONSCheckbox']"));
		assertFalse(selenium.isChecked("//input[@id='_86_CATEGORIESCheckbox']"));
		selenium.clickAt("//input[@id='_86_CATEGORIESCheckbox']",
			RuntimeVariables.replace("Categories"));
		assertTrue(selenium.isChecked("//input[@id='_86_CATEGORIESCheckbox']"));
		selenium.clickAt("//input[@value='Import']",
			RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}