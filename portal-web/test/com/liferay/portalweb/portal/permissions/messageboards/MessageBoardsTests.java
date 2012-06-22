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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageBoardsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(SA_AddMATest.class);
		testSuite.addTestSuite(SA_AddMARoleTest.class);
		testSuite.addTestSuite(SA_DefineMARoleTest.class);
		testSuite.addTestSuite(SA_AddMemberTest.class);
		testSuite.addTestSuite(SA_AddMemberRoleTest.class);
		testSuite.addTestSuite(SA_DefineMemberRoleTest.class);
		testSuite.addTestSuite(SA_AssignUserRolesTest.class);
		testSuite.addTestSuite(SA_AddSiteTest.class);
		testSuite.addTestSuite(SA_AddSiteMembersTest.class);
		testSuite.addTestSuite(SA_AddPageTest.class);
		testSuite.addTestSuite(SA_AddPortletTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(MA_LoginTest.class);
		testSuite.addTestSuite(MA_AddCategoryTest.class);
		testSuite.addTestSuite(MA_AddThreadTest.class);
		testSuite.addTestSuite(MA_AssertActionsTest.class);
		testSuite.addTestSuite(MA_DeleteMessageTest.class);
		testSuite.addTestSuite(MA_DeleteCategoryTest.class);
		testSuite.addTestSuite(MA_AddCategoryTest.class);
		testSuite.addTestSuite(MA_AddThreadTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertActionsTest.class);
		testSuite.addTestSuite(Member_ViewMessageTest.class);
		testSuite.addTestSuite(Member_ReplyMessageTest.class);
		testSuite.addTestSuite(Member_AddThreadTest.class);
		testSuite.addTestSuite(Member_EditThreadTest.class);
		testSuite.addTestSuite(Member_DeleteMessageTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Guest_ViewTest.class);
		testSuite.addTestSuite(Guest_AssertActionsTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(TearDownMBCategoryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownRoleTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}
}