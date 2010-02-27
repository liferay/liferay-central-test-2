/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portlet.language;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="InternationalizationJapaneseTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class InternationalizationJapaneseTest extends BaseTestCase {
	public void testInternationalizationJapanese() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//img[@alt='\u65e5\u672c\u8a9e (\u65e5\u672c)']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//img[@alt='\u65e5\u672c\u8a9e (\u65e5\u672c)']"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.clickAt("//div[2]/ul/li[2]/a/span",
			RuntimeVariables.replace(""));
		selenium.typeKeys("//input",
			RuntimeVariables.replace(
				"\u8a00\u8a9e\u30c6\u30b9\u30c8\u30da\u30fc\u30b8"));
		selenium.type("//input",
			RuntimeVariables.replace(
				"\u8a00\u8a9e\u30c6\u30b9\u30c8\u30da\u30fc\u30b8"));
		selenium.clickAt("//span[@id='save']/span", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.clickAt("//img[@alt='English (United States)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
	}
}