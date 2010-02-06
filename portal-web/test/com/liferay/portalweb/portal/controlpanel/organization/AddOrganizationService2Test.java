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

package com.liferay.portalweb.portal.controlpanel.organization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddOrganizationService2Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationService2Test extends BaseTestCase {
	public void testAddOrganizationService2() throws Exception {
		selenium.clickAt("link=Organizations", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Organizations", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_126_keywords", RuntimeVariables.replace("selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Selenium", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("servicesLink", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@id='services']/fieldset/div[2]/div/span/a[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[@id='services']/fieldset/div[2]/div/span/a[1]",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_126_orgLaborTypeId2")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_126_orgLaborTypeId2",
			RuntimeVariables.replace("label=Administrative"));
		selenium.select("_126_sunOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_sunClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_monOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_monClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_tueOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_tueClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_wedOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_wedClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_thuOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_thuClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_friOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_friClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.select("_126_satOpen2", RuntimeVariables.replace("label=08:00"));
		selenium.select("_126_satClose2",
			RuntimeVariables.replace("label=04:00"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
	}
}