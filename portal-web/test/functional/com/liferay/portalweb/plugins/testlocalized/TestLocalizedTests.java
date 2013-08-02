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

package com.liferay.portalweb.plugins.testlocalized;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class TestLocalizedTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageTLTest.class);
		testSuite.addTestSuite(AddPortletTLTest.class);
		testSuite.addTestSuite(ViewPortletChineseLocalizedTest.class);
		testSuite.addTestSuite(ViewPortletFrenchLocalizedTest.class);
		testSuite.addTestSuite(ViewPortletSpanishLocalizedTest.class);
		testSuite.addTestSuite(ViewPortletEnglishLocalizedTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}