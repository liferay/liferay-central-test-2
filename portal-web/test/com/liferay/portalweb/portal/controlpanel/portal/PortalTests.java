/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.portal;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="PortalTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalTests extends BaseTests {

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
		testSuite.addTestSuite(SearchUserTest.class);
		testSuite.addTestSuite(AdvancedSearchUserTest.class);
		testSuite.addTestSuite(AddTemporaryUserTest.class);
		testSuite.addTestSuite(DeactivateTemporaryUserTest.class);
		testSuite.addTestSuite(DeleteTemporaryUserTest.class);
		testSuite.addTestSuite(AddOrganizationTest.class);
		testSuite.addTestSuite(AddOrganizationAddressTest.class);
		testSuite.addTestSuite(AddOrganizationAddress2Test.class);
		testSuite.addTestSuite(AddOrganizationPhoneNumberTest.class);
		testSuite.addTestSuite(AddOrganizationPhoneNumber2Test.class);
		testSuite.addTestSuite(AddOrganizationEmailAddressTest.class);
		testSuite.addTestSuite(AddOrganizationEmailAddress2Test.class);
		testSuite.addTestSuite(AddOrganizationWebsiteTest.class);
		testSuite.addTestSuite(AddOrganizationWebsite2Test.class);
		testSuite.addTestSuite(AddOrganizationServiceTest.class);
		testSuite.addTestSuite(AddOrganizationService2Test.class);
		testSuite.addTestSuite(AddOrganizationCommentsTest.class);
		testSuite.addTestSuite(AddDuplicateOrganizationNameTest.class);
		testSuite.addTestSuite(AddNullOrganizationNameTest.class);
		testSuite.addTestSuite(ApplyOrganizationTest.class);
		testSuite.addTestSuite(AssertApplyOrganizationTest.class);
		testSuite.addTestSuite(AssertCannotDeleteApplyOrganizationTest.class);
		testSuite.addTestSuite(AddOrganizationPageTest.class);
		testSuite.addTestSuite(AssertNotMergeOrganizationPageTest.class);
		testSuite.addTestSuite(MergeOrganizationPageTest.class);
		testSuite.addTestSuite(AssertMergeOrganizationPageTest.class);
		testSuite.addTestSuite(RemoveApplyOrganizationTest.class);
		testSuite.addTestSuite(AssertRemoveApplyOrganizationTest.class);
		testSuite.addTestSuite(SearchOrganizationTest.class);
		testSuite.addTestSuite(AdvancedSearchOrganizationTest.class);
		testSuite.addTestSuite(AddTemporaryOrganizationTest.class);
		testSuite.addTestSuite(DeleteTemporaryOrganizationTest.class);
		testSuite.addTestSuite(AddUserGroupTest.class);
		testSuite.addTestSuite(AddAsteriskUserGroupNameTest.class);
		testSuite.addTestSuite(AddCommaUserGroupNameTest.class);
		testSuite.addTestSuite(AddDuplicateUserGroupNameTest.class);
		testSuite.addTestSuite(AddNullUserGroupNameTest.class);
		testSuite.addTestSuite(AddNumberUserGroupNameTest.class);
		testSuite.addTestSuite(ApplyUserGroupTest.class);
		testSuite.addTestSuite(AssertApplyUserGroupTest.class);
		testSuite.addTestSuite(AssertCannotDeleteApplyUserGroupTest.class);
		testSuite.addTestSuite(RemoveApplyUserGroupTest.class);
		testSuite.addTestSuite(AssertRemoveApplyUserGroupTest.class);
		testSuite.addTestSuite(SearchUserGroupTest.class);
		testSuite.addTestSuite(AddTemporaryUserGroupTest.class);
		testSuite.addTestSuite(DeleteTemporaryUserGroupTest.class);
		testSuite.addTestSuite(AddPasswordPoliciesTest.class);
		testSuite.addTestSuite(AddAsteriskPasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddCommaPasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddDuplicatePasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddNullPasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddNumberPasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddTemporaryPasswordPoliciesTest.class);
		testSuite.addTestSuite(DeleteTemporaryPasswordPoliciesTest.class);
		testSuite.addTestSuite(EditGeneralSettingsTest.class);
		testSuite.addTestSuite(AssertAuthenticationGeneralTest.class);
		testSuite.addTestSuite(AssertAuthenticationLDAPTest.class);
		testSuite.addTestSuite(AssertAuthenticationCASTest.class);
		testSuite.addTestSuite(AssertAuthenticationNTLMTest.class);
		testSuite.addTestSuite(AssertAuthenticationOpenIDTest.class);
		testSuite.addTestSuite(AssertAuthenticationOpenSSOTest.class);
		testSuite.addTestSuite(AssertAuthenticationSiteMinderTest.class);
		testSuite.addTestSuite(AssertUsersFieldsSettingsTest.class);
		testSuite.addTestSuite(EnterReservedUserSNTest.class);
		testSuite.addTestSuite(AddReservedUserSNTest.class);
		testSuite.addTestSuite(EnterReservedUserEmailTest.class);
		testSuite.addTestSuite(AddReservedUserEmailTest.class);
		testSuite.addTestSuite(AssertUsersDefaultAssociationsTest.class);
		testSuite.addTestSuite(AssertMailHostNamesTest.class);
		testSuite.addTestSuite(AssertEmailNotificationSenderTest.class);
		testSuite.addTestSuite(
			AssertEmailNotificationAccountCreationTest.class);
		testSuite.addTestSuite(
			AssertEmailNotificationPasswordChangedTest.class);
		testSuite.addTestSuite(AddSettingsAddressTest.class);
		testSuite.addTestSuite(AddSettingsAddress2Test.class);
		testSuite.addTestSuite(AddSettingsPhoneNumberTest.class);
		testSuite.addTestSuite(AddSettingsPhoneNumber2Test.class);
		testSuite.addTestSuite(AddSettingsEmailAddressTest.class);
		testSuite.addTestSuite(AddSettingsEmailAddress2Test.class);
		testSuite.addTestSuite(AddSettingsWebsitesTest.class);
		testSuite.addTestSuite(AddSettingsWebsites2Test.class);
		testSuite.addTestSuite(EditTimeZoneTest.class);
		testSuite.addTestSuite(AssertGoogleAppsTest.class);
		testSuite.addTestSuite(MonitoringTest.class);
		testSuite.addTestSuite(PluginsConfigurationTest.class);
		testSuite.addTestSuite(AddPageTemplateTest.class);
		testSuite.addTestSuite(DeactivatePageTemplateTest.class);
		testSuite.addTestSuite(ActivatePageTemplateTest.class);
		testSuite.addTestSuite(EditPageTemplateTest.class);
		testSuite.addTestSuite(DeletePageTemplateTest.class);
		testSuite.addTestSuite(TearDownTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);

		return testSuite;
	}

}