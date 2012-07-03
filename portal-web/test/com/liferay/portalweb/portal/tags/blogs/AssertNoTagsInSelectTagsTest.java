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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertNoTagsInSelectTagsTest extends BaseTestCase {
	public void testAssertNoTagsInSelectTags() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Blogs Tags Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Blogs Tags Test Page",
					RuntimeVariables.replace("Blogs Tags Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Tags Blog Entry1 Title"),
					selenium.getText(
						"xPath=(//div[@class='entry-title']/h2/a)[3]"));
				selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[3]",
					RuntimeVariables.replace("Tags Blog Entry1 Title"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText("//span/a/span"));
				selenium.click(RuntimeVariables.replace("//span/a/span"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//td[@id='cke_contents__33_editor']/iframe")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean tagsVisible = selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']");

				if (tagsVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText(
						"xPath=(//div[@class='lfr-panel-title'])[2]/span"));
				selenium.clickAt("xPath=(//div[@class='lfr-panel-title'])[2]/span",
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

				assertTrue(selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']"));

			case 2:
				selenium.clickAt("//button[@id='select']",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				assertTrue(selenium.isElementNotPresent("//label[1]/input"));
				assertTrue(selenium.isElementNotPresent("//label[2]/input"));
				assertTrue(selenium.isElementNotPresent("//label[3]/input"));
				assertTrue(selenium.isElementNotPresent("//label[4]/input"));
				selenium.clickAt("//button[@id='closethick']",
					RuntimeVariables.replace("Close"));
				selenium.clickAt("//input[@value='Cancel']",
					RuntimeVariables.replace("Cancel"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 100:
				label = -1;
			}
		}
	}
}