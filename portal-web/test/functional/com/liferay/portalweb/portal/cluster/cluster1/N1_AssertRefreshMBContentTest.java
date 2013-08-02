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

package com.liferay.portalweb.portal.cluster.cluster1;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * @author Brian Wing Shun Chan
 */
public class N1_AssertRefreshMBContentTest extends BaseTestCase {
	public void testN1_AssertRefreshMBContent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.refresh();
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Category 2"));
		selenium.refresh();
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Category 2"));
		selenium.refresh();
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Category 2"));
	}
}