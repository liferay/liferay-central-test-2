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

package com.liferay.portalweb.portlet.messageboards.message.viewcategorymessagepostcount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCategoryMessagePostCountTest extends BaseTestCase {
	public void testViewCategoryMessagePostCount() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("3"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
		assertEquals(RuntimeVariables.replace("6"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[4]"));
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("4"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[5]/td[4]"));
		selenium.clickAt("link=T\u00e9st1 M\u00e9ssag\u00e91",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.click(RuntimeVariables.replace(
				"//div[7]/table/tbody/tr[2]/td/ul/li[5]/span/a/span"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("3"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
		assertEquals(RuntimeVariables.replace("5"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[4]"));
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("3"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[5]/td[4]"));
		selenium.clickAt("link=T\u00e9st1 M\u00e9ssag\u00e91",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.click(RuntimeVariables.replace(
				"//div[6]/table/tbody/tr[2]/td/ul/li[5]/span/a/span"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("3"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
		assertEquals(RuntimeVariables.replace("4"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[4]"));
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[5]/td[4]"));
		selenium.clickAt("//td[7]/span/ul/li/strong/a",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[7]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[7]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[4]"));
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[4]/td[4]"));
	}
}