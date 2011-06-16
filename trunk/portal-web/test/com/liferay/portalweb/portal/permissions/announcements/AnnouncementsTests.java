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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AddPageTest.class);
		testSuite.addTestSuite(SA_AddPortletTest.class);
		testSuite.addTestSuite(SA_AddGeneralAnnouncementTest.class);
		testSuite.addTestSuite(SA_AddCAAnnouncementTest.class);
		testSuite.addTestSuite(SA_AddMemberAnnouncementTest.class);
		testSuite.addTestSuite(SA_AddGuestAnnouncementTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_AssertViewTest.class);
		testSuite.addTestSuite(CA_AssertActionsTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertViewTest.class);
		testSuite.addTestSuite(Member_DismissAnnouncementTest.class);
		testSuite.addTestSuite(Member_AssertActionsTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertViewTest.class);
		testSuite.addTestSuite(Guest_AssertActionsTest.class);
		testSuite.addTestSuite(SA_TearDownTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);

		return testSuite;
	}

}