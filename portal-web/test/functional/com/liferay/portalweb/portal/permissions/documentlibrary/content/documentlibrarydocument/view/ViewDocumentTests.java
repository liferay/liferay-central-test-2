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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.view;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrary.adddocument.TearDownDLContentTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.AddMemberRoleTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.AddMemberTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.AssignUserRolesTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.LoginUsersTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.Member_LoginTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.RemoveRolePowerUserTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.TearDownRolesTest;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.TearDownUserTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDocumentTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddMemberTest.class);
		testSuite.addTestSuite(AddMemberRoleTest.class);
		testSuite.addTestSuite(DefineMemberRoleTest.class);
		testSuite.addTestSuite(RemoveRolePowerUserTest.class);
		testSuite.addTestSuite(AssignUserRolesTest.class);
		testSuite.addTestSuite(AddDocumentTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewDocumentTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AddPermissionViewDocumentTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_ViewDocumentTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownDLContentTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownRolesTest.class);

		return testSuite;
	}
}