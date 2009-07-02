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
 * <a href="AssertMergeOrganizationPageTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssertMergeOrganizationPageTest extends BaseTestCase {
	public void testAssertMergeOrganizationPage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				boolean InControlPanel = selenium.isElementPresent(
						"link=Back to My Community");

				if (!InControlPanel) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Back to My Community"));
				selenium.waitForPageToLoad("30000");

			case 2:
				selenium.click(RuntimeVariables.replace(
						"//div[@id='_145_myPlacesContainer']/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Welcome"));
				assertTrue(selenium.isElementPresent(
						"link=Selenium Test Home Page"));
				selenium.click(RuntimeVariables.replace("link=Guest"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Welcome"));
				assertTrue(selenium.isElementPresent(
						"link=Selenium Test Home Page"));
				selenium.click(RuntimeVariables.replace(
						"//div[@id='_145_myPlacesContainer']/ul/li[2]/a/span[1]"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Control Panel"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}