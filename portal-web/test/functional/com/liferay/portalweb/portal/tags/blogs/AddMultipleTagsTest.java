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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMultipleTagsTest extends BaseTestCase {
	public void testAddMultipleTags() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
					selenium.getText(
						"xPath=(//div[@class='entry-title']/h2/a)[2]"));
				selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[2]",
					RuntimeVariables.replace("Blogs Entry2 Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//a[@class='taglib-icon']/span[contains(.,'Edit')]"));
				selenium.click(RuntimeVariables.replace(
						"//a[@class='taglib-icon']/span[contains(.,'Edit')]"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//td[@id='cke_contents__33_editor']/iframe");

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
				selenium.waitForVisible(
					"//input[@class='lfr-tag-selector-input aui-field-input-text']");
				assertTrue(selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']"));

			case 2:
				selenium.type("//input[@class='lfr-tag-selector-input aui-field-input-text']",
					RuntimeVariables.replace(
						"selenium3 liferay3, selenium4 liferay4"));
				selenium.clickAt("//button[@id='add']",
					RuntimeVariables.replace("Add"));
				selenium.waitForText("xPath=(//span[@class='aui-textboxlistentry-text'])[1]",
					"selenium3 liferay3");
				assertEquals(RuntimeVariables.replace("selenium3 liferay3"),
					selenium.getText(
						"xPath=(//span[@class='aui-textboxlistentry-text'])[1]"));
				selenium.waitForText("xPath=(//span[@class='aui-textboxlistentry-text'])[2]",
					"selenium4 liferay4");
				assertEquals(RuntimeVariables.replace("selenium4 liferay4"),
					selenium.getText(
						"xPath=(//span[@class='aui-textboxlistentry-text'])[2]"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("selenium3 liferay3"),
					selenium.getText(
						"xPath=(//span[@class='taglib-asset-tags-summary']/a)[1]"));
				assertEquals(RuntimeVariables.replace("selenium4 liferay4"),
					selenium.getText(
						"xPath=(//span[@class='taglib-asset-tags-summary']/a)[2]"));

			case 100:
				label = -1;
			}
		}
	}
}