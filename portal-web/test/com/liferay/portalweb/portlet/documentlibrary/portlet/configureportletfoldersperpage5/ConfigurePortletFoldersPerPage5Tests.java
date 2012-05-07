/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletfoldersperpage5;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocument.TearDownDLDocumentTest;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportletdm.AddPageDMTest;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportletdm.AddPortletDMTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletFoldersPerPage5Tests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(AddFolder1Test.class);
		testSuite.addTestSuite(AddFolder2Test.class);
		testSuite.addTestSuite(AddFolder3Test.class);
		testSuite.addTestSuite(AddFolder4Test.class);
		testSuite.addTestSuite(AddFolder5Test.class);
		testSuite.addTestSuite(AddFolder6Test.class);
		testSuite.addTestSuite(ConfigurePortletFoldersPerPage20Test.class);
		testSuite.addTestSuite(ConfigurePortletFoldersPerPage5Test.class);
		testSuite.addTestSuite(TearDownDLConfigurationTest.class);
		testSuite.addTestSuite(TearDownDLDocumentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}