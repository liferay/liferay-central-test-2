/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portalweb.portlet.directory.organizations.advancedsearchorganizations;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AdvancedSearchOrganizationsTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchOrganizationsTest extends BaseTestCase {
	public void testAdvancedSearchOrganizations() throws Exception {
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
						if (selenium.isElementPresent(
									"link=Directory Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Directory Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean advancedVisible = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_11_name")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_11_name", RuntimeVariables.replace("Test"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("Diamond Bar"));
				selenium.type("_11_name", RuntimeVariables.replace("Test1"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("Diamond Bar"));
				selenium.type("_11_name", RuntimeVariables.replace(""));
				selenium.type("_11_street", RuntimeVariables.replace("Test"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("Diamond Bar"));
				selenium.type("_11_street", RuntimeVariables.replace("Test1"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("Diamond Bar"));
				selenium.type("_11_street", RuntimeVariables.replace(""));
				selenium.type("_11_city", RuntimeVariables.replace("Diamond"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("Diamond Bar"));
				selenium.type("_11_city", RuntimeVariables.replace("Diamond1"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("Diamond Bar"));
				selenium.type("_11_city", RuntimeVariables.replace(""));
				selenium.type("_11_zip", RuntimeVariables.replace("11111"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("Diamond Bar"));
				selenium.type("_11_zip", RuntimeVariables.replace("111111"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("Diamond Bar"));
				selenium.type("_11_zip", RuntimeVariables.replace(""));
				selenium.select("_11_type",
					RuntimeVariables.replace("label=Regular Organization"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("Diamond Bar"));
				selenium.select("_11_type",
					RuntimeVariables.replace("label=Location"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("Diamond Bar"));
				selenium.select("_11_type",
					RuntimeVariables.replace("label=Any"));
				selenium.select("_11_countryId",
					RuntimeVariables.replace("label=United States"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("Diamond Bar"));
				selenium.select("_11_countryId",
					RuntimeVariables.replace("label=United Kingdom"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("Diamond Bar"));
				selenium.select("_11_countryId",
					RuntimeVariables.replace("label=United States"));
				Thread.sleep(5000);
				selenium.select("_11_regionId",
					RuntimeVariables.replace("label=California"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("Diamond Bar"));
				selenium.select("_11_regionId",
					RuntimeVariables.replace("label=Hawaii"));
				selenium.clickAt("//div[@id='toggle_id_directory_organization_searchadvanced']/span[2]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("Diamond Bar"));
				selenium.select("_11_countryId",
					RuntimeVariables.replace("label="));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 100:
				label = -1;
			}
		}
	}
}