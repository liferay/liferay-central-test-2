/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.directory.organizations.vieworganizationuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="TearDownTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TearDownTest extends BaseTestCase {
	public void testTearDown() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean basic1Visible = selenium.isVisible("link=\u00ab Basic");

				if (!basic1Visible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 2:
				selenium.type("_125_keywords",
					RuntimeVariables.replace("TestFirst"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean UsersPresent1 = selenium.isElementPresent("_125_rowIds");

				if (!UsersPresent1) {
					label = 3;

					continue;
				}

				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_125_active")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_125_active",
					RuntimeVariables.replace("label=No"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Users")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_125_keywords",
					RuntimeVariables.replace("TestFirst"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 3:

				boolean UsersPresent2 = selenium.isElementPresent("_125_rowIds");

				if (!UsersPresent2) {
					label = 4;

					continue;
				}

				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_125_active")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_125_active",
					RuntimeVariables.replace("label=No"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Users")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_125_keywords",
					RuntimeVariables.replace("TestFirst"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 4:

				boolean UsersPresent3 = selenium.isElementPresent("_125_rowIds");

				if (!UsersPresent3) {
					label = 5;

					continue;
				}

				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_125_active")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_125_active",
					RuntimeVariables.replace("label=No"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Users")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_125_keywords",
					RuntimeVariables.replace("TestFirst"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 5:

				boolean UsersPresent4 = selenium.isElementPresent("_125_rowIds");

				if (!UsersPresent4) {
					label = 6;

					continue;
				}

				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_125_active")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_125_active",
					RuntimeVariables.replace("label=No"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Users")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_125_keywords",
					RuntimeVariables.replace("TestFirst"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 6:

				boolean UsersPresent5 = selenium.isElementPresent("_125_rowIds");

				if (!UsersPresent5) {
					label = 7;

					continue;
				}

				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_125_active")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_125_active",
					RuntimeVariables.replace("label=No"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_user_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_125_allRowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Users")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Users", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_125_keywords",
					RuntimeVariables.replace("TestFirst"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 7:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean advancedVisible = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!advancedVisible) {
					label = 8;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

			case 8:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_126_name")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean SuborganizationPresent1 = selenium.isElementPresent(
						"//strong/a");

				if (!SuborganizationPresent1) {
					label = 10;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean suborganizationUsers1Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!suborganizationUsers1Present) {
					label = 9;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 9:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 10:

				boolean SuborganizationPresent2 = selenium.isElementPresent(
						"//strong/a");

				if (!SuborganizationPresent2) {
					label = 12;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean suborganizationUsers2Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!suborganizationUsers2Present) {
					label = 11;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 11:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 12:

				boolean SuborganizationPresent3 = selenium.isElementPresent(
						"//strong/a");

				if (!SuborganizationPresent3) {
					label = 14;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean suborganizationUsers3Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!suborganizationUsers3Present) {
					label = 13;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 13:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 14:

				boolean SuborganizationPresent4 = selenium.isElementPresent(
						"//strong/a");

				if (!SuborganizationPresent4) {
					label = 16;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean suborganizationUsers4Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!suborganizationUsers4Present) {
					label = 15;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 15:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 16:

				boolean SuborganizationPresent5 = selenium.isElementPresent(
						"//strong/a");

				if (!SuborganizationPresent5) {
					label = 18;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean suborganizationUsers5Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!suborganizationUsers5Present) {
					label = 17;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 17:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_name",
					RuntimeVariables.replace("Test Child"));
				selenium.clickAt("//div[@id='toggle_id_enterprise_admin_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 18:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_126_keywords")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean OrganizationPresent1 = selenium.isElementPresent(
						"//strong/a");

				if (!OrganizationPresent1) {
					label = 20;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean organizationUsers1Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!organizationUsers1Present) {
					label = 19;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 19:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 20:

				boolean OrganizationPresent2 = selenium.isElementPresent(
						"//strong/a");

				if (!OrganizationPresent2) {
					label = 22;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean organizationUsers2Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!organizationUsers2Present) {
					label = 21;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 21:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 22:

				boolean OrganizationPresent3 = selenium.isElementPresent(
						"//strong/a");

				if (!OrganizationPresent3) {
					label = 24;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean organizationUsers3Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!organizationUsers3Present) {
					label = 23;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 23:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 24:

				boolean OrganizationPresent4 = selenium.isElementPresent(
						"//strong/a");

				if (!OrganizationPresent4) {
					label = 26;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean organizationUsers4Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!organizationUsers4Present) {
					label = 25;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 25:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 26:

				boolean OrganizationPresent5 = selenium.isElementPresent(
						"//strong/a");

				if (!OrganizationPresent5) {
					label = 28;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean organizationUsers5Present = selenium.isElementPresent(
						"_126_allRowIds");

				if (!organizationUsers5Present) {
					label = 27;

					continue;
				}

				selenium.clickAt("_126_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 27:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("_126_rowIds", RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Organizations")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 28:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean UserGroupPresent1 = selenium.isElementPresent(
						"//strong/a");

				if (!UserGroupPresent1) {
					label = 30;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean userGroupUsers1Present = selenium.isElementPresent(
						"_127_allRowIds");

				if (!userGroupUsers1Present) {
					label = 29;

					continue;
				}

				selenium.clickAt("_127_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 29:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 30:

				boolean UserGroupPresent2 = selenium.isElementPresent(
						"//strong/a");

				if (!UserGroupPresent2) {
					label = 32;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean userGroupUsers2Present = selenium.isElementPresent(
						"_127_allRowIds");

				if (!userGroupUsers2Present) {
					label = 31;

					continue;
				}

				selenium.clickAt("_127_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 31:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 32:

				boolean UserGroupPresent3 = selenium.isElementPresent(
						"//strong/a");

				if (!UserGroupPresent3) {
					label = 34;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean userGroupUsers3Present = selenium.isElementPresent(
						"_127_allRowIds");

				if (!userGroupUsers3Present) {
					label = 33;

					continue;
				}

				selenium.clickAt("_127_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 33:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 34:

				boolean UserGroupPresent4 = selenium.isElementPresent(
						"//strong/a");

				if (!UserGroupPresent4) {
					label = 36;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean userGroupUsers4Present = selenium.isElementPresent(
						"_127_allRowIds");

				if (!userGroupUsers4Present) {
					label = 35;

					continue;
				}

				selenium.clickAt("_127_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 35:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 36:

				boolean UserGroupPresent5 = selenium.isElementPresent(
						"//strong/a");

				if (!UserGroupPresent5) {
					label = 38;

					continue;
				}

				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Assign Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Assign Members",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean userGroupUsers5Present = selenium.isElementPresent(
						"_127_allRowIds");

				if (!userGroupUsers5Present) {
					label = 37;

					continue;
				}

				selenium.clickAt("_127_allRowIds", RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 37:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/a", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=User Groups")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 38:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//li[@class='first manage-page']/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//li[@class='first manage-page']/a"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Guest")) {
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
						if (selenium.isVisible(
									"//a[@class='lfr-tree-controls-label']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//a[@class='lfr-tree-controls-label']",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page1Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page1Present) {
					label = 39;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 39:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page2Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page2Present) {
					label = 40;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 40:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page3Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page3Present) {
					label = 41;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 41:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page4Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page4Present) {
					label = 42;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 42:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//ul[@class='aui-tree-container']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page5Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page5Present) {
					label = 43;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//li[@id='_88_tabs3pageTabsId']/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 43:
			case 100:
				label = -1;
			}
		}
	}
}