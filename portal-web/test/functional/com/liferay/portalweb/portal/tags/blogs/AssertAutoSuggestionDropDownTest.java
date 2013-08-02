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
public class AssertAutoSuggestionDropDownTest extends BaseTestCase {
	public void testAssertAutoSuggestionDropDown() throws Exception {
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
				assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
					selenium.getText(
						"xPath=(//div[@class='entry-title']/h2/a)[1]"));
				selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[1]",
					RuntimeVariables.replace("Blogs Entry3 Title"));
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
					RuntimeVariables.replace(""));
				selenium.sendKeys("//input[@class='lfr-tag-selector-input aui-field-input-text focus']",
					RuntimeVariables.replace("selenium"));
				selenium.waitForText("//li[contains(@class,'aui-autocomplete-list-item')]",
					"selenium1 liferay1");
				assertEquals(RuntimeVariables.replace("selenium1 liferay1"),
					selenium.getText(
						"//li[contains(@class,'aui-autocomplete-list-item')]"));
				assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
					selenium.getText(
						"xPath=(//li[@class='aui-autocomplete-list-item'])[1]"));
				assertEquals(RuntimeVariables.replace("selenium3 liferay3"),
					selenium.getText(
						"xPath=(//li[@class='aui-autocomplete-list-item'])[2]"));
				assertEquals(RuntimeVariables.replace("selenium4 liferay4"),
					selenium.getText(
						"xPath=(//li[@class='aui-autocomplete-list-item'])[3]"));
				selenium.sendKeys("//input[@class='lfr-tag-selector-input aui-field-input-text focus']",
					RuntimeVariables.replace("2*"));
				selenium.waitForText("//li[contains(@class,'aui-autocomplete-list-item')]",
					"selenium2 liferay2");
				assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
					selenium.getText(
						"//li[contains(@class,'aui-autocomplete-list-item')]"));
				assertTrue(selenium.isElementNotPresent(
						"xPath=(//li[@class='aui-autocomplete-list-item'])[1]"));
				assertTrue(selenium.isElementNotPresent(
						"xPath=(//li[@class='aui-autocomplete-list-item'])[2]"));
				assertTrue(selenium.isElementNotPresent(
						"xPath=(//li[@class='aui-autocomplete-list-item'])[3]"));

			case 100:
				label = -1;
			}
		}
	}
}