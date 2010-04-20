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

package com.liferay.portalweb.portlet.blogs.portlet.configureportletdisplaystyletitle;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="TearDownBlogsConfigurationTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class TearDownBlogsConfigurationTest extends BaseTestCase {
	public void testTearDownBlogsConfiguration() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Blogs Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Configuration",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//form/ul/li[4]/span/span/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.select("_86_pageDelta",
					RuntimeVariables.replace("label=5"));
				selenium.select("_86_pageDisplayStyle",
					RuntimeVariables.replace("label=Full Content"));

				boolean enableFlagsChecked = selenium.isChecked(
						"_86_enableFlagsCheckbox");

				if (enableFlagsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_86_enableFlagsCheckbox",
					RuntimeVariables.replace(""));

			case 2:

				boolean enableRatingsChecked = selenium.isChecked(
						"_86_enableRatingsCheckbox");

				if (enableRatingsChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("_86_enableRatingsCheckbox",
					RuntimeVariables.replace(""));

			case 3:

				boolean enableCommentsChecked = selenium.isChecked(
						"_86_enableCommentsCheckbox");

				if (enableCommentsChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("_86_enableCommentsCheckbox",
					RuntimeVariables.replace(""));

			case 4:

				boolean enableCommentRatingsChecked = selenium.isChecked(
						"_86_enableCommentRatingsCheckbox");

				if (enableCommentRatingsChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("_86_enableCommentRatingsCheckbox",
					RuntimeVariables.replace(""));

			case 5:
				selenium.clickAt("//div[2]/span[1]/span/input",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}