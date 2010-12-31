/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertDeleteChoiceTest extends BaseTestCase {
	public void testAssertDeleteChoice() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Polls", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Question']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_25_title_en_US",
			RuntimeVariables.replace("Delete Choice Title Test"));
		selenium.saveScreenShotAndSource();
		selenium.type("_25_description_en_US",
			RuntimeVariables.replace("Delete Choice Description Test"));
		selenium.saveScreenShotAndSource();
		selenium.type("_25_choiceDescriptiona_en_US",
			RuntimeVariables.replace("Delete Choice A"));
		selenium.saveScreenShotAndSource();
		selenium.type("_25_choiceDescriptionb_en_US",
			RuntimeVariables.replace("Delete Choice B"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Choice']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_25_choiceDescriptionc_en_US",
			RuntimeVariables.replace("Delete Choice C"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Choice']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_25_choiceDescriptiond_en_US",
			RuntimeVariables.replace("Delete Choice D"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Choice']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_25_choiceDescriptione_en_US",
			RuntimeVariables.replace("Delete Choice E"));
		selenium.saveScreenShotAndSource();
		assertEquals("Delete Choice C",
			selenium.getValue("_25_choiceDescriptionc_en_US"));
		assertEquals("Delete Choice D",
			selenium.getValue("_25_choiceDescriptiond_en_US"));
		assertEquals("Delete Choice E",
			selenium.getValue("_25_choiceDescriptione_en_US"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals("Delete Choice C",
			selenium.getValue("_25_choiceDescriptionc_en_US"));
		assertEquals("Delete Choice D",
			selenium.getValue("_25_choiceDescriptiond_en_US"));
		assertFalse(selenium.isElementPresent("_25_choiceDescriptione_en_US"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals("Delete Choice C",
			selenium.getValue("_25_choiceDescriptionc_en_US"));
		assertFalse(selenium.isElementPresent("_25_choiceDescriptiond_en_US"));
		assertFalse(selenium.isElementPresent("_25_choiceDescriptione_en_US"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isElementPresent("_25_choiceDescriptionc_en_US"));
		assertFalse(selenium.isElementPresent("_25_choiceDescriptiond_en_US"));
		assertFalse(selenium.isElementPresent("_25_choiceDescriptione_en_US"));
	}
}