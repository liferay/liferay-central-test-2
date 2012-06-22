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

package com.liferay.portalweb.portlet.pollsdisplay.question.deletequestionselect;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddQuestionTest extends BaseTestCase {
	public void testAddQuestion() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
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
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//input[@value='Add Question']",
			RuntimeVariables.replace("Add Question"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_25_title_en_US']",
			RuntimeVariables.replace("PD Question Title"));
		selenium.type("//textarea[@id='_25_description_en_US']",
			RuntimeVariables.replace("PD Question Description"));
		selenium.type("//input[@name='_25_choiceDescriptiona_en_US']",
			RuntimeVariables.replace("PD Question ChoiceA"));
		selenium.type("//input[@name='_25_choiceDescriptionb_en_US']",
			RuntimeVariables.replace("PD Question ChoiceB"));
		selenium.clickAt("//input[@value='Add Choice']",
			RuntimeVariables.replace("Add Choice"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_25_choiceDescriptionc_en_US']",
			RuntimeVariables.replace("PD Question ChoiceC"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("PD Question Title"),
			selenium.getText("//tr[3]/td[1]/a"));
	}
}