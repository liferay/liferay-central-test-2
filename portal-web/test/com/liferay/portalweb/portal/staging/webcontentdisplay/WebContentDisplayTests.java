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

package com.liferay.portalweb.portal.staging.webcontentdisplay;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="WebContentDisplayTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WebContentDisplayTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(LoginTest.class);
		testSuite.addTestSuite(AddWebContentDisplayPageTest.class);
		testSuite.addTestSuite(AddWebContentDisplayPortletTest.class);
		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(AddWebContentArticleTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);
		testSuite.addTestSuite(SelectArticleTest.class);
		testSuite.addTestSuite(AssertEditWebContentDisplayArticleTest.class);
		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(ActivateStagingTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);
		testSuite.addTestSuite(
			AssertCannotEditWebContentDisplayArticleTest.class);
		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(DeactivateStagingTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);
		testSuite.addTestSuite(AssertEditWebContentDisplayArticleTest.class);
		testSuite.addTestSuite(TearDownTest.class);
		testSuite.addTestSuite(LogoutTest.class);

		return testSuite;
	}

}