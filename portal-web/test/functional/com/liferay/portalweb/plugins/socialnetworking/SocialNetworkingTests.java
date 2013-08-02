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

package com.liferay.portalweb.plugins.socialnetworking;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialNetworkingTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageSNTest.class);
		testSuite.addTestSuite(AddPortletFriendsTest.class);
		testSuite.addTestSuite(RemovePortletSNTest.class);
		testSuite.addTestSuite(AddPortletFATest.class);
		testSuite.addTestSuite(RemovePortletSNTest.class);
		testSuite.addTestSuite(AddPortletMapTest.class);
		testSuite.addTestSuite(RemovePortletSNTest.class);
		testSuite.addTestSuite(AddPortletMeetupsTest.class);
		testSuite.addTestSuite(RemovePortletSNTest.class);
		testSuite.addTestSuite(AddPortletMembersTest.class);
		testSuite.addTestSuite(RemovePortletSNTest.class);
		testSuite.addTestSuite(AddPortletMATest.class);
		testSuite.addTestSuite(RemovePortletSNTest.class);
		testSuite.addTestSuite(AddPortletSummaryTest.class);
		testSuite.addTestSuite(RemovePortletSNTest.class);
		testSuite.addTestSuite(AddPortletWallTest.class);
		testSuite.addTestSuite(RemovePortletSNTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}