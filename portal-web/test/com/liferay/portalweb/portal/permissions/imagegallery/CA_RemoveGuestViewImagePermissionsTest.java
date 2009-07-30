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

package com.liferay.portalweb.portal.permissions.imagegallery;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CA_RemoveGuestViewImagePermissionsTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CA_RemoveGuestViewImagePermissionsTest extends BaseTestCase {
	public void testCA_RemoveGuestViewImagePermissions()
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
									"link=Image Gallery Permissions Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Image Gallery Permissions Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//tr[4]/td[1]/a[1]/b"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");
				selenium.click("//img[@alt='Image']");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Permissions")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("link=Permissions");

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

				boolean AbleToView = selenium.isChecked("15_ACTION_VIEW");

				if (!AbleToView) {
					label = 2;

					continue;
				}

				selenium.click("15_ACTION_VIEW");

			case 2:
				assertTrue(selenium.isTextPresent(
						"Edit Permissions for Image Gallery Image:"));
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