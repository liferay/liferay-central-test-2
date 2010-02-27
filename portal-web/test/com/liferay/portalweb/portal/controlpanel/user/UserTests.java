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

package com.liferay.portalweb.portal.controlpanel.user;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="UserTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UserTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AddUserPasswordTest.class);
		testSuite.addTestSuite(AddUserAddressTest.class);
		testSuite.addTestSuite(AddUserAddress2Test.class);
		testSuite.addTestSuite(AddUserPhoneNumberTest.class);
		testSuite.addTestSuite(AddUserPhoneNumber2Test.class);
		testSuite.addTestSuite(AddUserEmailAddressTest.class);
		testSuite.addTestSuite(AddUserEmailAddress2Test.class);
		testSuite.addTestSuite(AddUserWebsiteTest.class);
		testSuite.addTestSuite(AddUserWebsite2Test.class);
		testSuite.addTestSuite(AddUserInstantMessengerTest.class);
		testSuite.addTestSuite(AddUserSocialNetworkTest.class);
		testSuite.addTestSuite(AddUserSMSTest.class);
		testSuite.addTestSuite(AddUserOpenIDTest.class);
		testSuite.addTestSuite(AddUserAnnouncementsTest.class);
		testSuite.addTestSuite(AddUserDisplaySettingsTest.class);
		testSuite.addTestSuite(AddUserCommentsTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddAnonymousNameUserSNTest.class);
		testSuite.addTestSuite(AddCyrusUserSNTest.class);
		testSuite.addTestSuite(AddDuplicateUserSNTest.class);
		testSuite.addTestSuite(AddEmailUserSNTest.class);
		testSuite.addTestSuite(AddInvalidUserSNTest.class);
		testSuite.addTestSuite(AddNullUserSNTest.class);
		testSuite.addTestSuite(AddNumberUserSNTest.class);
		testSuite.addTestSuite(AddPostfixUserSNTest.class);
		testSuite.addTestSuite(AddDuplicateUserEmailTest.class);
		testSuite.addTestSuite(AddInvalidUserEmailTest.class);
		testSuite.addTestSuite(AddNullUserEmailTest.class);
		testSuite.addTestSuite(AddPostmasterUserEmailTest.class);
		testSuite.addTestSuite(AddRootUserEmailTest.class);
		testSuite.addTestSuite(AddNullUserFirstNameTest.class);
		testSuite.addTestSuite(AddNullUserLastNameTest.class);
		testSuite.addTestSuite(AddDifferentPasswordTest.class);
		testSuite.addTestSuite(AddNullPassword1Test.class);
		testSuite.addTestSuite(AddNullPassword2Test.class);
		testSuite.addTestSuite(AddNullTestUserTest.class);
		testSuite.addTestSuite(AddNullUserStreetAddressTest.class);
		testSuite.addTestSuite(AddNullUserCityAddressTest.class);
		testSuite.addTestSuite(AddNullUserZipAddressTest.class);
		testSuite.addTestSuite(AddInvalidUserPhoneNumberTest.class);
		testSuite.addTestSuite(AddNullUserPhoneNumberTest.class);
		testSuite.addTestSuite(AddNullAdditionalUserEmailTest.class);
		testSuite.addTestSuite(AddInvalidAdditionalUserEmailTest.class);
		testSuite.addTestSuite(AddInvalidUserURLWebsiteTest.class);
		testSuite.addTestSuite(AddNullUserURLWebsiteTest.class);
		testSuite.addTestSuite(DeactivateNullTestUserTest.class);
		testSuite.addTestSuite(DeleteNullTestUserTest.class);
		testSuite.addTestSuite(SearchUserTest.class);
		testSuite.addTestSuite(AdvancedSearchUserTest.class);
		testSuite.addTestSuite(AddTemporaryUserTest.class);
		testSuite.addTestSuite(DeactivateTemporaryUserTest.class);
		testSuite.addTestSuite(DeleteTemporaryUserTest.class);
		testSuite.addTestSuite(TearDownTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);

		return testSuite;
	}

}