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

package com.liferay.portalweb.portal.controlpanel.portal;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddOrganizationTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddOrganizationTest extends BaseTestCase {
	public void testAddOrganization() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				boolean InControlPanel = selenium.isElementPresent(
						"link=Back to My Community");

				if (InControlPanel) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@id='_145_myPlacesContainer']/ul/li[2]/a/span[1]"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Control Panel"));
				selenium.waitForPageToLoad("30000");

			case 2:

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

				selenium.click(RuntimeVariables.replace("link=Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Add"));
				selenium.waitForPageToLoad("30000");
				selenium.typeKeys("_126_name",
					RuntimeVariables.replace("Selenium"));
				selenium.type("_126_name", RuntimeVariables.replace("Selenium"));
				selenium.select("_126_type",
					RuntimeVariables.replace("label=Regular Organization"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				selenium.click("addressesLink");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_126_addressStreet10")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.typeKeys("_126_addressStreet10",
					RuntimeVariables.replace("12345 Selenium St"));
				selenium.type("_126_addressStreet10",
					RuntimeVariables.replace("12345 Selenium St"));
				selenium.type("_126_addressStreet10",
					RuntimeVariables.replace("12345 Selenium St"));
				selenium.select("_126_addressCountryId0",
					RuntimeVariables.replace("label=United States"));
				Thread.sleep(5000);
				selenium.select("_126_addressRegionId0",
					RuntimeVariables.replace("label=California"));
				selenium.select("_126_addressTypeId0",
					RuntimeVariables.replace("label=Billing"));
				selenium.type("_126_addressZip0",
					RuntimeVariables.replace("41111"));
				selenium.type("_126_addressCity0",
					RuntimeVariables.replace("Diamond Bar"));
				selenium.click("_126_addressPrimary0");
				selenium.click("phoneNumbersLink");
				selenium.type("_126_phoneNumber0",
					RuntimeVariables.replace("555-555-5555"));
				selenium.type("_126_phoneExtension0",
					RuntimeVariables.replace("555"));
				selenium.click("_126_phonePrimary0");
				selenium.click("additionalEmailAddressesLink");
				selenium.type("_126_emailAddressAddress0",
					RuntimeVariables.replace("Selenium@Selenium.com"));
				selenium.select("_126_emailAddressTypeId0",
					RuntimeVariables.replace("label=E-mail"));
				selenium.click("_126_emailAddressPrimary0");
				selenium.click("websitesLink");
				selenium.type("_126_websiteUrl0",
					RuntimeVariables.replace("http://www.liferay.com"));
				selenium.select("_126_websiteTypeId0",
					RuntimeVariables.replace("label=Public"));
				selenium.click("_126_websitePrimary0");
				selenium.click("servicesLink");
				selenium.select("_126_orgLaborTypeId0",
					RuntimeVariables.replace("label=Training"));
				selenium.select("_126_sunOpen0",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_sunClose0",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_monOpen0",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_monClose0",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_tueOpen0",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_tueClose0",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_wedOpen0",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_wedClose0",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_thuOpen0",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_thuClose0",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_friOpen0",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_friClose0",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_satOpen0",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_satClose0",
					RuntimeVariables.replace("label=05:00"));
				selenium.click("commentsLink");
				selenium.type("_126_comments",
					RuntimeVariables.replace("This is a test comment!"));
				assertTrue(selenium.isTextPresent("(Modified)"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 100:
				label = -1;
			}
		}
	}
}