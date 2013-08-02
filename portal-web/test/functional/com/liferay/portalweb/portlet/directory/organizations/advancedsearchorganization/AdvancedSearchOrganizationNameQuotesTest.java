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

package com.liferay.portalweb.portlet.directory.organizations.advancedsearchorganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchOrganizationNameQuotesTest extends BaseTestCase {
	public void testAdvancedSearchOrganizationNameQuotes()
		throws Exception {
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

				boolean advancedVisible = selenium.isVisible(
						"//div/div/a[contains(.,'Advanced')]");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div/div/a[contains(.,'Advanced')]",
					RuntimeVariables.replace("Advanced \u00bb"));

			case 2:
				selenium.waitForVisible("//select[@id='_11_andOperator']");
				selenium.select("//select[@id='_11_andOperator']",
					RuntimeVariables.replace("Any"));
				selenium.type("//input[@id='_11_name']",
					RuntimeVariables.replace("\"Test Organization\""));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_11_name']",
					RuntimeVariables.replace(""));
				assertTrue(selenium.isElementPresent("link=Test Organization"));
				selenium.type("//input[@id='_11_name']",
					RuntimeVariables.replace("\"Test1 Organization1\""));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_11_name']",
					RuntimeVariables.replace(""));
				selenium.select("//select[@id='_11_andOperator']",
					RuntimeVariables.replace("All"));
				selenium.clickAt("//div/div/a[contains(.,'Basic')]",
					RuntimeVariables.replace("\u00ab Basic"));
				assertFalse(selenium.isTextPresent("Test Organization"));

			case 100:
				label = -1;
			}
		}
	}
}