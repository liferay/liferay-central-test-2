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

package com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletrootfolderremovesubfolder;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ConfigurePortletRootFolderRemoveSubfolderTests.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletRootFolderRemoveSubfolderTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletTest.class);
		testSuite.addTestSuite(AddFolderTest.class);
		testSuite.addTestSuite(AddSubfolderTest.class);
		testSuite.addTestSuite(
			ConfigurePortletRootFolderSelectSubfolderTest.class);
		testSuite.addTestSuite(AssertRootFolderSelectSubfolderTest.class);
		testSuite.addTestSuite(
			ConfigurePortletRootFolderRemoveSubfolderTest.class);
		testSuite.addTestSuite(AssertRootFolderRemoveSubfolderTest.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}