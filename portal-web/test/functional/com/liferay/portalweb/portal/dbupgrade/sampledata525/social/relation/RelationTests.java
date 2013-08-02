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

package com.liferay.portalweb.portal.dbupgrade.sampledata525.social.relation;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class RelationTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserSRlTest.class);
		testSuite.addTestSuite(AddUserSRlPasswordTest.class);
		testSuite.addTestSuite(AddUserSRl2Test.class);
		testSuite.addTestSuite(AddUserSRl2PasswordTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SRl_SignInTest.class);
		testSuite.addTestSuite(SRl_AddPageSummaryTest.class);
		testSuite.addTestSuite(SRl_AddPortletSummaryTest.class);
		testSuite.addTestSuite(SRl_AddPageRequestsTest.class);
		testSuite.addTestSuite(SRl_AddPortletRequestsTest.class);
		testSuite.addTestSuite(SRl_AddPageFriendsTest.class);
		testSuite.addTestSuite(SRl_AddPortletFriendsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SRl2_SignInTest.class);
		testSuite.addTestSuite(SRl2_AddAsFriendTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SRl_SignInTest.class);
		testSuite.addTestSuite(SRl_ConfirmFriendRequestTest.class);
		testSuite.addTestSuite(SRl_ViewFriendsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);

		return testSuite;
	}
}