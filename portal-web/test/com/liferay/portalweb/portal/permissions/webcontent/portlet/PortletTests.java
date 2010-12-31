/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.permissions.webcontent.portlet;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(
			CA_AddMemberWCDConfigurationPermissionsTest.class);
		testSuite.addTestSuite(
			CA_AddMemberWCLConfigurationPermissionsTest.class);
		testSuite.addTestSuite(
			CA_AddMemberWCSConfigurationPermissionsTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertEditWCDConfigurationTest.class);
		testSuite.addTestSuite(Member_AssertEditWCLConfigurationTest.class);
		testSuite.addTestSuite(Member_AssertEditWCSConfigurationTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(
			CA_RemoveMemberWCDConfigurationPermissionsTest.class);
		testSuite.addTestSuite(
			CA_RemoveMemberWCLConfigurationPermissionsTest.class);
		testSuite.addTestSuite(
			CA_RemoveMemberWCSConfigurationPermissionsTest.class);
		testSuite.addTestSuite(CA_RemoveMemberWCDViewPermissionsTest.class);
		testSuite.addTestSuite(CA_RemoveMemberWCLViewPermissionsTest.class);
		testSuite.addTestSuite(CA_RemoveMemberWCSViewPermissionsTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCDTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCLTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCSTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_RestoreMemberWCDViewPermissionsTest.class);
		testSuite.addTestSuite(CA_RestoreMemberWCLViewPermissionsTest.class);
		testSuite.addTestSuite(CA_RestoreMemberWCSViewPermissionsTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertViewWCDTest.class);
		testSuite.addTestSuite(Member_AssertViewWCLTest.class);
		testSuite.addTestSuite(Member_AssertViewWCSTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_CleanUpTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);

		return testSuite;
	}

}