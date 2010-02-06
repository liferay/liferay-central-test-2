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

package com.liferay.portalweb.plugins.googleadsense;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddAdSenseTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddAdSenseTest extends BaseTestCase {
	public void testAddAdSense() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Google Adsense Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Google Adsense Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Configuration"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_86_adClient",
			RuntimeVariables.replace("pub-0000000000"));
		selenium.type("_86_adClient", RuntimeVariables.replace("pub-0000000000"));
		selenium.typeKeys("_86_adChannel", RuntimeVariables.replace("12345678"));
		selenium.type("_86_adChannel", RuntimeVariables.replace("12345678"));
		selenium.select("_86_adType", RuntimeVariables.replace("label=Text"));
		selenium.select("_86_adFormat",
			RuntimeVariables.replace("label=(728 x 90) - Leaderboard"));
		selenium.typeKeys("_86_colorBorder", RuntimeVariables.replace("FFFFFF"));
		selenium.type("_86_colorBorder", RuntimeVariables.replace("FFFFFF"));
		selenium.typeKeys("_86_colorBg", RuntimeVariables.replace("0000FF"));
		selenium.type("_86_colorBg", RuntimeVariables.replace("0000FF"));
		selenium.typeKeys("_86_colorLink", RuntimeVariables.replace("FFFFFF"));
		selenium.type("_86_colorLink", RuntimeVariables.replace("FFFFFF"));
		selenium.typeKeys("_86_colorText", RuntimeVariables.replace("000000"));
		selenium.type("_86_colorText", RuntimeVariables.replace("000000"));
		selenium.typeKeys("_86_colorUrl", RuntimeVariables.replace("008000"));
		selenium.type("_86_colorUrl", RuntimeVariables.replace("008000"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have successfully updated the setup."));
	}
}