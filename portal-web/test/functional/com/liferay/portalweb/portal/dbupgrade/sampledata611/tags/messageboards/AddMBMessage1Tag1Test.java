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

package com.liferay.portalweb.portal.dbupgrade.sampledata611.tags.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMBMessage1Tag1Test extends BaseTestCase {
	public void testAddMBMessage1Tag1() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/tags-message-board-community/");
				selenium.clickAt("link=Message Boards Page",
					RuntimeVariables.replace("Message Boards Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td/a/strong",
					RuntimeVariables.replace("Category Test"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Post New Thread']",
					RuntimeVariables.replace("Post New Thread"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_19_subject']",
					RuntimeVariables.replace("Message1 Tag1 Test1"));
				selenium.waitForElementPresent(
					"//textarea[@id='_19_editor' and @style='display: none;']");
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForVisible(
					"//a[@class='cke_button_source cke_on']");
				selenium.waitForVisible(
					"//td[@id='cke_contents__19_editor']/textarea");
				selenium.type("//td[@id='cke_contents__19_editor']/textarea",
					RuntimeVariables.replace("This is a message1 tag1 test1."));
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForElementPresent(
					"//textarea[@id='_19_editor' and @style='display: none;']");
				selenium.waitForVisible(
					"//td[@id='cke_contents__19_editor']/iframe");
				selenium.selectFrame(
					"//td[@id='cke_contents__19_editor']/iframe");
				selenium.waitForText("//body", "This is a message1 tag1 test1.");
				selenium.selectFrame("relative=top");

				boolean tagsVisible = selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']");

				if (tagsVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText("//div[@class='lfr-panel-title']/span"));
				selenium.clickAt("//div[@class='lfr-panel-title']/span",
					RuntimeVariables.replace("Categorization"));
				selenium.waitForVisible(
					"//input[@class='lfr-tag-selector-input aui-field-input-text']");
				assertTrue(selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']"));

			case 2:
				selenium.type("//li/span/span/input",
					RuntimeVariables.replace("selenium1"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Message1 Tag1 Test1"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace("selenium1"),
					selenium.getText("//span[@class='tag']"));
				assertEquals(RuntimeVariables.replace(
						"This is a message1 tag1 test1."),
					selenium.getText("//div[@class='thread-body']"));

			case 100:
				label = -1;
			}
		}
	}
}