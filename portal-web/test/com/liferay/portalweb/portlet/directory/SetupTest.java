/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.directory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="SetupTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SetupTest extends BaseTestCase {
	public void testSetup() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[@id='_145_myPlacesContainer']/ul/li[2]/a/span[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//div[@id='_145_myPlacesContainer']/ul/li[2]/a/span[1]"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Users"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Add")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Add"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_125_screenName", RuntimeVariables.replace("TestSN1"));
		selenium.type("_125_screenName", RuntimeVariables.replace("TestSN1"));
		selenium.type("_125_emailAddress",
			RuntimeVariables.replace("TestEMail1@liferay.com"));
		selenium.type("_125_firstName", RuntimeVariables.replace("TestFirst1"));
		selenium.type("_125_middleName", RuntimeVariables.replace("TestMiddle1"));
		selenium.type("_125_lastName", RuntimeVariables.replace("TestLast1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_125_screenName", RuntimeVariables.replace("TestSN2"));
		selenium.type("_125_screenName", RuntimeVariables.replace("TestSN2"));
		selenium.type("_125_emailAddress",
			RuntimeVariables.replace("TestEMail2@liferay.com"));
		selenium.type("_125_firstName", RuntimeVariables.replace("TestFirst2"));
		selenium.type("_125_middleName", RuntimeVariables.replace("TestMiddle2"));
		selenium.type("_125_lastName", RuntimeVariables.replace("TestLast2"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_126_name",
			RuntimeVariables.replace("Test Organization"));
		selenium.type("_126_name", RuntimeVariables.replace("Test Organization"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("addressesLink");
		selenium.typeKeys("_126_addressStreet1_0",
			RuntimeVariables.replace("12345 Test Street"));
		selenium.type("_126_addressStreet1_0",
			RuntimeVariables.replace("12345 Test Street"));
		selenium.select("_126_addressCountryId0",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_126_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		selenium.select("_126_addressTypeId0",
			RuntimeVariables.replace("label=Billing"));
		selenium.type("_126_addressZip0", RuntimeVariables.replace("11111"));
		selenium.type("_126_addressCity0",
			RuntimeVariables.replace("Diamond Bar"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=User Groups"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_127_name",
			RuntimeVariables.replace("Test User Group"));
		selenium.type("_127_name", RuntimeVariables.replace("Test User Group"));
		selenium.type("_127_description",
			RuntimeVariables.replace("This is a test user group!"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//tr[3]/td[4]/ul/li/strong/span");

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

		selenium.click(RuntimeVariables.replace("link=Assign Members"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Available"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_127_keywords",
			RuntimeVariables.replace("TestFirst2"));
		selenium.type("_127_keywords", RuntimeVariables.replace("TestFirst2"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("_127_allRowIds");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Update Associations']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_126_keywords", RuntimeVariables.replace("Test"));
		selenium.type("_126_keywords", RuntimeVariables.replace("Test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//strong/span");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Add Regular Organization")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Add Regular Organization"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_126_name", RuntimeVariables.replace("Test Child"));
		selenium.type("_126_name", RuntimeVariables.replace("Test Child"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("addressesLink");
		selenium.typeKeys("_126_addressStreet1_0",
			RuntimeVariables.replace("11111 Main Street USA"));
		selenium.type("_126_addressStreet1_0",
			RuntimeVariables.replace("11111 Main Street USA"));
		selenium.select("_126_addressCountryId0",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_126_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		selenium.select("_126_addressTypeId0",
			RuntimeVariables.replace("label=Billing"));
		selenium.type("_126_addressZip0", RuntimeVariables.replace("90210"));
		selenium.type("_126_addressCity0", RuntimeVariables.replace("Cerritos"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_126_keywords",
			RuntimeVariables.replace("Diamond Bar"));
		selenium.type("_126_keywords", RuntimeVariables.replace("Diamond Bar"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//strong/span");

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

		selenium.click(RuntimeVariables.replace("link=Assign Members"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Available"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_126_keywords",
			RuntimeVariables.replace("Joe Bloggs"));
		selenium.type("_126_keywords", RuntimeVariables.replace("Joe Bloggs"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("_126_allRowIds");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Update Associations']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Back to My Community"));
		selenium.waitForPageToLoad("30000");
	}
}