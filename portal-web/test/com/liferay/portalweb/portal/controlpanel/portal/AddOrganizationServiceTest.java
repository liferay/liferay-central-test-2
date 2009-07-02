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
 * <a href="AddOrganizationServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddOrganizationServiceTest extends BaseTestCase {
	public void testAddOrganizationService() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_126_keywords", RuntimeVariables.replace("selenium"));
		selenium.type("_126_keywords", RuntimeVariables.replace("selenium"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Selenium"));
		selenium.waitForPageToLoad("30000");
		selenium.click("servicesLink");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_126_orgLaborTypeId0")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_126_orgLaborTypeId0",
			RuntimeVariables.replace("label=Training"));
		selenium.select("_126_sunOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_sunClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_monOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_monClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_tueOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_tueClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_wedOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_wedClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_thuOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_thuClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_friOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_friClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("_126_satOpen0", RuntimeVariables.replace("label=09:00"));
		selenium.select("_126_satClose0",
			RuntimeVariables.replace("label=05:00"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}