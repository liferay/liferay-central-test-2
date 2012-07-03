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

package com.liferay.portalweb.portal.dbupgrade.sampledata610.groups.pagelayout;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPageLayoutTest extends BaseTestCase {
	public void testEditPageLayout() throws Exception {
		selenium.open("/web/group-page-layout-community/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Page Layout Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Page Layout Page",
			RuntimeVariables.replace("Page Layout Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//div[@id='layout-column_column-1' and @class='portlet-dropzone portlet-column-content portlet-column-content-first']/div[1]/div[1]/section/header/h1/span[2]"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='layout-column_column-2' and @class='portlet-dropzone portlet-column-content portlet-column-content-last']/div[1]/div[1]/section/header/h1/span[2]"));
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']"));
		selenium.mouseOver("//li[@id='_145_manageContent']");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Page Layout")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Page Layout",
			RuntimeVariables.replace("Page Layout"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_88_layoutTemplateId2']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//input[@id='_88_layoutTemplateId2']",
			RuntimeVariables.replace("2 Columns"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//li[@class='selected section-modified']/a[@id='_88_layoutLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Layout (Modified)"),
			selenium.getText(
				"//li[@class='selected section-modified']/a[@id='_88_layoutLink']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/group-page-layout-community/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Page Layout Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Page Layout Page",
			RuntimeVariables.replace("Page Layout Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//div[@id='column-1' and @class='aui-w50 portlet-column portlet-column-first']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='column-2' and @class='aui-w50 portlet-column portlet-column-last']"));
		assertEquals(RuntimeVariables.replace("Breadcrumb"),
			selenium.getText(
				"//div[@id='layout-column_column-1' and @class='portlet-dropzone portlet-column-content portlet-column-content-first']/div[1]/div[1]/section/header/h1/span[2]"));
		assertEquals(RuntimeVariables.replace("Navigation"),
			selenium.getText(
				"//div[@id='layout-column_column-2' and @class='portlet-dropzone portlet-column-content portlet-column-content-last']/div[1]/div[1]/section/header/h1/span[2]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-1' and @class='aui-w30 portlet-column portlet-column-first']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2' and @class='aui-w70 portlet-column portlet-column-last']"));
	}
}