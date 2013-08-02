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

package com.liferay.portalweb.portlet.directory.organizations.viewsuborganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSuborganizationTest extends BaseTestCase {
	public void testViewSuborganization() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Directory Test Page",
					RuntimeVariables.replace("Directory Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace("Organizations"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//div/div/a[contains(.,'Basic')]");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div/div/a[contains(.,'Basic')]",
					RuntimeVariables.replace("Basic \u00bb"));

			case 2:
				selenium.type("//input[@name='_11_keywords']",
					RuntimeVariables.replace("Test Organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText("//tr[3]/td[1]/a"));
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText("//tr[3]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText("//tr[3]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("Diamond Bar"),
					selenium.getText("//tr[3]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("California"),
					selenium.getText("//tr[3]/td[5]/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText("//tr[3]/td[6]/a"));
				assertEquals(RuntimeVariables.replace("Test Suborganization"),
					selenium.getText("//tr[4]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText("//tr[4]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText("//tr[4]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("Cerritos"),
					selenium.getText("//tr[4]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("Florida"),
					selenium.getText("//tr[4]/td[5]/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText("//tr[4]/td[6]/a"));
				selenium.clickAt("//tr[3]/td[7]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("View Suborganizations"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Suborganization"),
					selenium.getText("//td[1]/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText("//td[3]/a"));
				assertEquals(RuntimeVariables.replace("Cerritos"),
					selenium.getText("//td[4]/a"));
				assertEquals(RuntimeVariables.replace("Florida"),
					selenium.getText("//td[5]/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText("//td[6]/a"));
				selenium.clickAt("//td[1]/a",
					RuntimeVariables.replace("Test Suborganization"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Suborganization"),
					selenium.getText(
						"//div[@class='organization-information']/div[1]/h2"));
				assertEquals(RuntimeVariables.replace("Type"),
					selenium.getText("//dl[@class='property-list']/dt[1]"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText("//dl[@class='property-list']/dd[1]"));
				assertEquals(RuntimeVariables.replace("Parent Organization"),
					selenium.getText("//dl[@class='property-list']/dt[2]"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText("//dl[@class='property-list']/dd[2]"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"Billing"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"11111 Main Street USA"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"90210, Cerritos "));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"(Mailing)"));

			case 100:
				label = -1;
			}
		}
	}
}