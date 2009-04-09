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

package com.liferay.portalweb.portal.controlpanel.communities;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="DeleteVirtualHostCommunityContentTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DeleteVirtualHostCommunityContentTest extends BaseTestCase {
	public void testDeleteVirtualHostCommunityContent()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				boolean HomepageLocation = !selenium.isElementPresent(
						"link=Plugins");

				if (!HomepageLocation) {
					label = 2;

					continue;
				}

				selenium.open("/web/guest/home");

			case 2:
				Thread.sleep(5000);
				selenium.click(RuntimeVariables.replace("//li[7]/ul/li[1]/a[1]"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));

			case 100:
				label = -1;
			}
		}
	}
}