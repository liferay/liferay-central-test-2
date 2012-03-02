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

package com.liferay.portalweb.portal.permissions.usecase.permissionssiteleveldemo;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class User2_ViewSiteMemberRolePermissionTests extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUser1Test.class);
		testSuite.addTestSuite(AddUser1PasswordTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddUser2PasswordTest.class);
		testSuite.addTestSuite(AddSitesTest.class);
		testSuite.addTestSuite(AddPageBlogsSiteTest.class);
		testSuite.addTestSuite(AddPortletBlogsSiteTest.class);
		testSuite.addTestSuite(AddBlogsEntrySiteTest.class);
		testSuite.addTestSuite(EditUser2SitesTest.class);
		testSuite.addTestSuite(EditSiteMemberRoleTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertNotViewableBlogsAddEntryButtonTest.class);
		testSuite.addTestSuite(Guest_AssertNotViewableBlogsPermissionsButtonTest.class);
		testSuite.addTestSuite(User1_SignInTest.class);
		testSuite.addTestSuite(User1_AssertNotViewableBlogsAddEntryButtonTest.class);
		testSuite.addTestSuite(User1_AssertNotViewableBlogsPermissionsButtonTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User2_SignInTest.class);
		testSuite.addTestSuite(User2_AssertViewableBlogsAddEntryButtonTest.class);
		testSuite.addTestSuite(User2_AssertViewableBlogsPermissionsButtonTest.class);
		testSuite.addTestSuite(User2_AddBlogsEntrySiteTest.class);
		testSuite.addTestSuite(User2_ViewSiteMemberRolePermissionTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownUser1Test.class);
		testSuite.addTestSuite(TearDownUser2Test.class);
		testSuite.addTestSuite(TearDownSitesTest.class);
		testSuite.addTestSuite(TearDownSiteMemberRoleTest.class);

		return testSuite;
	}
}
