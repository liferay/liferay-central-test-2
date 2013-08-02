/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.stagingorganization.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownBlogsEntryOrganizationSiteTest extends BaseTestCase {
	public void testTearDownBlogsEntryOrganizationSite()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/selenium/home/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean blogsEntry1Present = selenium.isElementPresent(
						"link=Delete");

				if (!blogsEntry1Present) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this?");

				boolean blogsEntry2Present = selenium.isElementPresent(
						"link=Delete");

				if (!blogsEntry2Present) {
					label = 3;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this?");

				boolean blogsEntry3Present = selenium.isElementPresent(
						"link=Delete");

				if (!blogsEntry3Present) {
					label = 4;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this?");

				boolean blogsEntry4Present = selenium.isElementPresent(
						"link=Delete");

				if (!blogsEntry4Present) {
					label = 5;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this?");

				boolean blogsEntry5Present = selenium.isElementPresent(
						"link=Delete");

				if (!blogsEntry5Present) {
					label = 6;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this?");

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}