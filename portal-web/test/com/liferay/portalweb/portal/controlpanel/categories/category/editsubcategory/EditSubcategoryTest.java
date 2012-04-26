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

package com.liferay.portalweb.portal.controlpanel.categories.category.editsubcategory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditSubcategoryTest extends BaseTestCase {
	public void testEditSubcategory() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//li/div/div[4]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText("//li/div/div[4]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='aui-tree-hitarea']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[@class='aui-tree-hitarea']",
			RuntimeVariables.replace("Drop Down Arrow"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//li/ul/li/div/div[4]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Subcategory Name"),
			selenium.getText("//li/ul/li/div/div[4]"));
		selenium.clickAt("//li/ul/li/div/div[4]",
			RuntimeVariables.replace("Subcategory Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Subcategory Name")
										.equals(selenium.getText(
								"//div[@class='view-category']/div/h1/span"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Subcategory Name"),
			selenium.getText("//div[@class='view-category']/div/h1/span"));
		selenium.clickAt("//input[@value='Edit']",
			RuntimeVariables.replace("Edit"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_147_title_en_US']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_147_title_en_US']",
			RuntimeVariables.replace("Subcategory Name Edit"));
		selenium.type("//textarea[@id='_147_description_en_US']",
			RuntimeVariables.replace("Subcategory Description Edit"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-message-response portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='aui-tree-hitarea']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[@class='aui-tree-hitarea']",
			RuntimeVariables.replace("Drop Down Arrow"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//li/ul/li/div/div[4]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Subcategory Name Edit"),
			selenium.getText("//li/ul/li/div/div[4]"));
	}
}