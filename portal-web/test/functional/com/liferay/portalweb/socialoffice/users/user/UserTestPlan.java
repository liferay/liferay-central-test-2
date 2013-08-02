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

package com.liferay.portalweb.socialoffice.users.user;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUserTests;
import com.liferay.portalweb.socialoffice.users.user.assignsorolesousersoconfiguration.AssignSORoleSOUserSOConfigurationTests;
import com.liferay.portalweb.socialoffice.users.user.configuredefaultrolesouser.ConfigureDefaultRoleSOUserTests;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUserPasswordTests;
import com.liferay.portalweb.socialoffice.users.user.removeregularrolessouser.RemoveRegularRolesSOUserTests;
import com.liferay.portalweb.socialoffice.users.user.removeregularrolessouserroles.RemoveRegularRolesSOUserRolesTests;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUserTests;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouserroles.SelectRegularRolesSOUserRolesTests;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignInSOTests;
import com.liferay.portalweb.socialoffice.users.user.viewfootertext.ViewFooterTextTests;
import com.liferay.portalweb.socialoffice.users.user.viewsiterolesouser.ViewSiteRoleSOUserTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddSOUserTests.suite());
		testSuite.addTest(AssignSORoleSOUserSOConfigurationTests.suite());
		testSuite.addTest(ConfigureDefaultRoleSOUserTests.suite());
		testSuite.addTest(EditSOUserPasswordTests.suite());
		testSuite.addTest(RemoveRegularRolesSOUserTests.suite());
		testSuite.addTest(RemoveRegularRolesSOUserRolesTests.suite());
		testSuite.addTest(SelectRegularRolesSOUserTests.suite());
		testSuite.addTest(SelectRegularRolesSOUserRolesTests.suite());
		testSuite.addTest(SignInSOTests.suite());
		testSuite.addTest(ViewFooterTextTests.suite());
		testSuite.addTest(ViewSiteRoleSOUserTests.suite());

		return testSuite;
	}

}