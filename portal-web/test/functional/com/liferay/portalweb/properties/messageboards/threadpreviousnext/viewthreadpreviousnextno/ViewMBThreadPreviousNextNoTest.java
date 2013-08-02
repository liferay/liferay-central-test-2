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

package com.liferay.portalweb.properties.messageboards.threadpreviousnext.viewthreadpreviousnextno;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMBThreadPreviousNextNoTest extends BaseTestCase {
	public void testViewMBThreadPreviousNextNo() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Thread2 Message Subject"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-thread_row-1']/a"));
		selenium.clickAt("//td[@id='_19_mbThreadsSearchContainer_col-thread_row-1']/a",
			RuntimeVariables.replace("MB Thread2 Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Previous"));
		assertFalse(selenium.isTextPresent("Next"));
	}
}