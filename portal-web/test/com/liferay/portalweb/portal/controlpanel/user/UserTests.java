/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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