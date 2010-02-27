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

package com.liferay.portalweb.portlet.managepages.page;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.managepages.page.addpage.AddPageTests;
import com.liferay.portalweb.portlet.managepages.page.addpagechild.AddPageChildTests;
import com.liferay.portalweb.portlet.managepages.page.addpagechildmultiple.AddPageChildMultipleTests;
import com.liferay.portalweb.portlet.managepages.page.addpagemultiple.AddPageMultipleTests;
import com.liferay.portalweb.portlet.managepages.page.setdisplayorder.SetDisplayOrderTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="PageTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPageTests.suite());
		testSuite.addTest(AddPageChildTests.suite());
		testSuite.addTest(AddPageChildMultipleTests.suite());
		testSuite.addTest(AddPageMultipleTests.suite());
		testSuite.addTest(SetDisplayOrderTests.suite());

		return testSuite;
	}

}