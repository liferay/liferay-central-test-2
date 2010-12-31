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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentexpirationdate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWebContentExpirationDateTest extends BaseTestCase {
	public void testAddWebContentExpirationDate() throws Exception {
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
		selenium.clickAt("link=Web Content", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Web Content']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_15_title", RuntimeVariables.replace("Web Content Name"));
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"_15_structure_el_TextAreaField_content")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("cke_contents_CKEditor1")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.selectFrame(
			"//iframe[@id='_15_structure_el_TextAreaField_content']");
		selenium.selectFrame("//td[@id='cke_contents_CKEditor1']/iframe");
		selenium.type("//body", RuntimeVariables.replace("Web Content Content"));
		selenium.selectFrame("relative=top");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"//select[@id='_15_expirationDateMonth' and @disabled='disabled']"));
		assertTrue(selenium.isElementPresent(
				"//select[@id='_15_expirationDateDay' and @disabled='disabled']"));
		assertTrue(selenium.isElementPresent(
				"//select[@id='_15_expirationDateYear' and @disabled='disabled']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_15_expirationDateHour' and @disabled='disabled']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_15_expirationDateMinute' and @disabled='disabled']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_15_expirationDateAmPm' and @disabled='disabled']"));
		assertTrue(selenium.isChecked("_15_neverExpireCheckbox"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("_15_neverExpireCheckbox",
			RuntimeVariables.replace("Never Auto Expire"));
		assertFalse(selenium.isChecked("_15_neverExpireCheckbox"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isElementPresent(
				"//select[@id='_15_expirationDateMonth' and @disabled='disabled']"));
		assertFalse(selenium.isElementPresent(
				"//select[@id='_15_expirationDateDay' and @disabled='disabled']"));
		assertFalse(selenium.isElementPresent(
				"//select[@id='_15_expirationDateYear' and @disabled='disabled']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_15_expirationDateHour' and @disabled='disabled']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_15_expirationDateMinute' and @disabled='disabled']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_15_expirationDateAmPm' and @disabled='disabled']"));
		selenium.select("_15_expirationDateMonth",
			RuntimeVariables.replace("label=December"));
		selenium.select("_15_expirationDateDay",
			RuntimeVariables.replace("label=31"));
		selenium.select("_15_expirationDateYear",
			RuntimeVariables.replace("label=2015"));
		selenium.select("_15_expirationDateHour",
			RuntimeVariables.replace("label=12"));
		selenium.select("_15_expirationDateMinute",
			RuntimeVariables.replace("label=:00"));
		selenium.select("_15_expirationDateAmPm",
			RuntimeVariables.replace("label=AM"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div"));
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//td[5]/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("Web Content Name"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		assertFalse(selenium.isChecked("_15_neverExpireCheckbox"));
		selenium.saveScreenShotAndSource();
		assertEquals("December",
			selenium.getSelectedLabel("_15_expirationDateMonth"));
		assertEquals("31", selenium.getSelectedLabel("_15_expirationDateDay"));
		assertEquals("2015", selenium.getSelectedLabel("_15_expirationDateYear"));
		assertEquals("12", selenium.getSelectedLabel("_15_expirationDateHour"));
		assertEquals(":00",
			selenium.getSelectedLabel("_15_expirationDateMinute"));
		assertEquals("AM", selenium.getSelectedLabel("_15_expirationDateAmPm"));
	}
}