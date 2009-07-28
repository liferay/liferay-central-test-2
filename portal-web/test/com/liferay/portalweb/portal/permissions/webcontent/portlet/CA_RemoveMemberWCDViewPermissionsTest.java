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

package com.liferay.portalweb.portal.permissions.webcontent.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CA_RemoveMemberWCDViewPermissionsTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CA_RemoveMemberWCDViewPermissionsTest extends BaseTestCase {
	public void testCA_RemoveMemberWCDViewPermissions()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Web Content Display Permissions Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Web Content Display Permissions Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Configuration"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Permissions"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("15_ACTION_VIEW")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean Checked1 = selenium.isChecked("15_ACTION_VIEW");

				if (!Checked1) {
					label = 2;

					continue;
				}

				selenium.click("15_ACTION_VIEW");

			case 2:

				boolean Checked2 = selenium.isChecked("//tr[11]/td[3]/input");

				if (!Checked2) {
					label = 3;

					continue;
				}

				selenium.click("//tr[11]/td[3]/input");

			case 3:
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Submit']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 100:
				label = -1;
			}
		}
	}
}