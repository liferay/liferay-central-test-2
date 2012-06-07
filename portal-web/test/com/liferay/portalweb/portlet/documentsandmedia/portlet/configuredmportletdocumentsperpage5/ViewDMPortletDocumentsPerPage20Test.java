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

package com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletdocumentsperpage5;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMPortletDocumentsPerPage20Test extends BaseTestCase {
	public void testViewDMPortletDocumentsPerPage20() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Documents and Media Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Name")
										.equals(selenium.getText(
								"//li[@class='folder selected']/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[@class='folder selected']/a"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Document1 Title")
										.equals(selenium.getText(
								"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder Document6 Title"));
		Thread.sleep(5000);
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-next-link'])[2]",
			RuntimeVariables.replace("Next"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Document6 Title")
										.equals(selenium.getText(
								"//a[contains(@class,'document-link')]/span[@class='entry-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Document6 Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DM Folder Document1 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document2 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document3 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document4 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document5 Title"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-prev-link'])[2]",
			RuntimeVariables.replace("Previous"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Document1 Title")
										.equals(selenium.getText(
								"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder Document6 Title"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-last-link'])[2]",
			RuntimeVariables.replace("Last"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Document6 Title")
										.equals(selenium.getText(
								"//a[contains(@class,'document-link')]/span[@class='entry-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Document6 Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DM Folder Document1 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document2 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document3 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document4 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document5 Title"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-first-link'])[2]",
			RuntimeVariables.replace("First"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Document1 Title")
										.equals(selenium.getText(
								"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder Document6 Title"));
		selenium.clickAt("//a[@page='2']", RuntimeVariables.replace("2"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Document6 Title")
										.equals(selenium.getText(
								"//a[contains(@class,'document-link')]/span[@class='entry-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Document6 Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DM Folder Document1 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document2 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document3 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document4 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document5 Title"));
		selenium.clickAt("xPath=(//a[@page='1'])[2]",
			RuntimeVariables.replace("1"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Document1 Title")
										.equals(selenium.getText(
								"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder Document6 Title"));
	}
}