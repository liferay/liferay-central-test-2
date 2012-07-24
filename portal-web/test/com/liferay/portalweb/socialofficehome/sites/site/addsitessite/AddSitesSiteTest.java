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

package com.liferay.portalweb.socialofficehome.sites.site.addsitessite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSitesSiteTest extends BaseTestCase {
	public void testAddSitesSite() throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//li[contains(@class, 'selected')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//button[contains(.,'Add Site')]/span[2]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add Site"),
			selenium.getText("//button[contains(.,'Add Site')]/span[2]"));
		selenium.clickAt("//button[contains(.,'Add Site')]/span[2]",
			RuntimeVariables.replace("Add Site"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//label[contains(@for,'_5_WAR_soportlet')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//label[contains(@for,'_5_WAR_soportlet')]"));
		assertTrue(selenium.isVisible("//input[@id='_5_WAR_soportlet_name']"));
		selenium.type("//input[@id='_5_WAR_soportlet_name']",
			RuntimeVariables.replace("Open Site Name"));
		assertTrue(selenium.isVisible(
				"//textarea[@id='_5_WAR_soportlet_description']"));
		selenium.type("//textarea[@id='_5_WAR_soportlet_description']",
			RuntimeVariables.replace("Open Site Description"));
		assertEquals("Next", selenium.getValue("//input[@value='Next']"));
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//select[@id='_5_WAR_soportlet_layoutSetPrototypeSelect']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//select[@id='_5_WAR_soportlet_layoutSetPrototypeSelect']"));
		selenium.select("//select[@id='_5_WAR_soportlet_layoutSetPrototypeSelect']",
			RuntimeVariables.replace("Default Social Office Site"));
		assertTrue(selenium.isVisible(
				"//select[@id='_5_WAR_soportlet_typeSelect']"));
		selenium.select("//select[@id='_5_WAR_soportlet_typeSelect']",
			RuntimeVariables.replace("Open"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Home')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Calendar')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Documents')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Forums')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Blog')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Wiki')]/input"));
		assertTrue(selenium.isChecked(
				"//div[2]/div/div/div/div/div/div[contains(.,'Members')]/input"));
		assertEquals("Save", selenium.getValue("//input[@value='Save']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//span[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//span[@class='portlet-msg-success']"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
	}
}