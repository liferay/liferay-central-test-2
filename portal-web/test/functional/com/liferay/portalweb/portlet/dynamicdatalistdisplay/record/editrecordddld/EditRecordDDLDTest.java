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

package com.liferay.portalweb.portlet.dynamicdatalistdisplay.record.editrecordddld;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditRecordDDLDTest extends BaseTestCase {
	public void testEditRecordDDLD() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Dynamic Data List Display Test Page",
					RuntimeVariables.replace(
						"Dynamic Data List Display Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Boolean"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[1]/span/span/label"));

				boolean booleanUnchecked = selenium.isChecked(
						"//div[@class='aui-fieldset-content ']/div[1]/span/span/span/input[2]");

				if (!booleanUnchecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='aui-fieldset-content ']/div[1]/span/span/span/input[2]",
					RuntimeVariables.replace("checkbox"));

			case 2:
				assertFalse(selenium.isChecked(
						"//div[@class='aui-fieldset-content ']/div[1]/span/span/span/input[2]"));
				assertEquals(RuntimeVariables.replace("Date"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[2]/div/label"));
				selenium.waitForVisible(
					"//div[@class='aui-datepicker-select-wrapper']/select");
				selenium.clickAt("//div[@class='aui-datepicker-select-wrapper']/select",
					RuntimeVariables.replace("Month Drop Down"));
				selenium.select("//div[@class='aui-datepicker-select-wrapper']/select",
					RuntimeVariables.replace("August"));
				selenium.select("//div[@class='aui-datepicker-select-wrapper']/select[2]",
					RuntimeVariables.replace("9"));
				selenium.select("//div[@class='aui-datepicker-select-wrapper']/select[3]",
					RuntimeVariables.replace("2010"));
				assertEquals(RuntimeVariables.replace("Decimal"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[3]/span/span/label"));
				selenium.type("//div[@class='aui-fieldset-content ']/div[3]/span/span/span/input",
					RuntimeVariables.replace("8.910"));
				assertEquals(RuntimeVariables.replace("Documents and Media"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[4]/div/span/span/label"));
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				selenium.waitForVisible("link=Document_1.txt");
				assertEquals(RuntimeVariables.replace("Document_1.txt"),
					selenium.getText("link=Document_1.txt"));
				assertTrue(selenium.isVisible("//input[@value='Choose']"));
				selenium.clickAt("//input[@value='Choose']",
					RuntimeVariables.replace("Choose"));
				selenium.selectFrame("relative=top");
				assertTrue(selenium.isPartialText(
						"//div[@class='aui-fieldset-content ']/div[5]/span/span/label",
						"File Upload"));
				selenium.uploadCommonFile("//div[@class='aui-fieldset-content ']/div[5]/span/span/span/input",
					RuntimeVariables.replace("Document_3.txt"));
				assertEquals(RuntimeVariables.replace("Integer"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[6]/span/span/label"));
				selenium.type("//div[@class='aui-fieldset-content ']/div[6]/span/span/span/input",
					RuntimeVariables.replace("8910"));
				assertEquals(RuntimeVariables.replace("Number"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[7]/span/span/label"));
				selenium.type("//div[@class='aui-fieldset-content ']/div[7]/span/span/span/input",
					RuntimeVariables.replace("111213"));
				assertEquals(RuntimeVariables.replace("Radio"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[8]/div/label"));
				assertEquals(RuntimeVariables.replace("option 1"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[8]/div/span[1]/span"));
				assertEquals(RuntimeVariables.replace("option 2"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[8]/div/span[2]/span"));
				assertEquals(RuntimeVariables.replace("option 3"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[8]/div/span[3]/span"));
				selenium.clickAt("//div[@class='aui-fieldset-content ']/div[8]/div/span[3]/span/span/input",
					RuntimeVariables.replace("option 3"));
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/span[1]/span/label"));
				assertEquals(RuntimeVariables.replace("option 1"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/span[1]/span/span/select/option[1]"));
				assertEquals(RuntimeVariables.replace("option 2"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/span[1]/span/span/select/option[2]"));
				assertEquals(RuntimeVariables.replace("option 3"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/span[1]/span/span/select/option[3]"));
				selenium.select("//div[@class='aui-fieldset-content ']/span[1]/span/span/select",
					RuntimeVariables.replace("label=option 1"));
				assertEquals(RuntimeVariables.replace("Text"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[9]/span/span/label"));
				selenium.type("//div[@class='aui-fieldset-content ']/div[9]/span/span/span/input",
					RuntimeVariables.replace("Text Field Edited"));
				assertEquals(RuntimeVariables.replace("Text Box"),
					selenium.getText(
						"//div[@class='aui-fieldset-content ']/div[10]/span/span/label"));
				selenium.type("//div[@class='aui-fieldset-content ']/div[10]/span/span/span/textarea",
					RuntimeVariables.replace("Text\nBox\nEdited"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}