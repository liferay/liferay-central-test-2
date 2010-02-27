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

package com.liferay.portalweb.plugins.sampletestdependency;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="SampleTestDependencyTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SampleTestDependencyTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletAbleTest.class);
		testSuite.addTestSuite(RemovePortletAbleTest.class);
		testSuite.addTestSuite(AddPortletBakerTest.class);
		testSuite.addTestSuite(RemovePortletBakerTest.class);
		testSuite.addTestSuite(AddPortletCharlieTest.class);
		testSuite.addTestSuite(RemovePortletCharlieTest.class);
		testSuite.addTestSuite(AddPortletDogTest.class);
		testSuite.addTestSuite(RemovePortletDogTest.class);
		testSuite.addTestSuite(AddPortletEasyTest.class);
		testSuite.addTestSuite(RemovePortletEasyTest.class);
		testSuite.addTestSuite(AddPortletFoxTest.class);
		testSuite.addTestSuite(RemovePortletFoxTest.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}