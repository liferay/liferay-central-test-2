/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 *
 */
public class SetupTest extends BaseTestCase {
	public void testSetup() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[@id=\"my-community-private-pages\"]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//a[@id=\"my-community-private-pages\"]"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//div/a/span");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("new_page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("new_page",
			RuntimeVariables.replace("Director Setup Test Page"));
		selenium.type("new_page",
			RuntimeVariables.replace("Directory Setup Test Page"));
		selenium.click("link=Save");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Directory Setup Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"link=Directory Setup Test Page"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Add Application")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("link=Add Application");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[@id='Undefined-EnterpriseAdmin']/p/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//div[@id='Undefined-EnterpriseAdmin']/p/a");
		Thread.sleep(5000);

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

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_79_screenName")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_79_screenName", RuntimeVariables.replace("TestSN1"));
		selenium.type("_79_screenName", RuntimeVariables.replace("TestSN1"));
		selenium.typeKeys("_79_emailAddress",
			RuntimeVariables.replace("TestEMail1@lifera.com"));
		selenium.type("_79_emailAddress",
			RuntimeVariables.replace("TestEMail1@liferay.com"));
		selenium.typeKeys("_79_firstName",
			RuntimeVariables.replace("TestFirst1"));
		selenium.type("_79_firstName", RuntimeVariables.replace("TestFirst1"));
		selenium.typeKeys("_79_middleName",
			RuntimeVariables.replace("TestMiddle1"));
		selenium.type("_79_middleName", RuntimeVariables.replace("TestMiddle1"));
		selenium.typeKeys("_79_lastName", RuntimeVariables.replace("TestLast1"));
		selenium.type("_79_lastName", RuntimeVariables.replace("TestLast1"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);

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

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_79_screenName")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_79_screenName", RuntimeVariables.replace("TestSN2"));
		selenium.type("_79_screenName", RuntimeVariables.replace("TestSN2"));
		selenium.typeKeys("_79_emailAddress",
			RuntimeVariables.replace("TestEMail2@lifera.com"));
		selenium.type("_79_emailAddress",
			RuntimeVariables.replace("TestEMail2@liferay.com"));
		selenium.typeKeys("_79_firstName",
			RuntimeVariables.replace("TestFirst2"));
		selenium.type("_79_firstName", RuntimeVariables.replace("TestFirst2"));
		selenium.typeKeys("_79_middleName",
			RuntimeVariables.replace("TestMiddle2"));
		selenium.type("_79_middleName", RuntimeVariables.replace("TestMiddle2"));
		selenium.typeKeys("_79_lastName", RuntimeVariables.replace("TestLast2"));
		selenium.type("_79_lastName", RuntimeVariables.replace("TestLast2"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Organizations"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);

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

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_79_name")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_79_name",
			RuntimeVariables.replace("Test Organization"));
		selenium.type("_79_name", RuntimeVariables.replace("Test Organization"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("addressesLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_79_addressStreet10")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_79_addressStreet10",
			RuntimeVariables.replace("12345 Test Street"));
		selenium.type("_79_addressStreet10",
			RuntimeVariables.replace("12345 Test Street"));
		selenium.typeKeys("_79_addressZip0", RuntimeVariables.replace("11111"));
		selenium.type("_79_addressZip0", RuntimeVariables.replace("11111"));
		selenium.typeKeys("_79_addressCity0",
			RuntimeVariables.replace("Diamond Bar"));
		selenium.type("_79_addressCity0",
			RuntimeVariables.replace("Diamond Bar"));
		selenium.select("_79_addressCountryId0",
			RuntimeVariables.replace("label=United States"));
		Thread.sleep(5000);
		selenium.select("_79_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=User Groups"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);

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

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_79_name")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_79_name",
			RuntimeVariables.replace("Test User Group"));
		selenium.type("_79_name", RuntimeVariables.replace("Test User Group"));
		selenium.typeKeys("_79_description",
			RuntimeVariables.replace("This is a test user group!"));
		selenium.type("_79_description",
			RuntimeVariables.replace("This is a test user group!"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//strong/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

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

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"toggle_id_enterprise_admin_user_searchkeywords")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("toggle_id_enterprise_admin_user_searchkeywords",
			RuntimeVariables.replace("TestFirst2"));
		selenium.type("toggle_id_enterprise_admin_user_searchkeywords",
			RuntimeVariables.replace("TestFirst2"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("_79_allRowIds");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Update Associations']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}