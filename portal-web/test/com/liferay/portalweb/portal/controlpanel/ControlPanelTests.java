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

package com.liferay.portalweb.portal.controlpanel;

import com.liferay.portalweb.portal.BaseTests;

/**
 * <a href="ControlPanelTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ControlPanelTests extends BaseTests {

	public ControlPanelTests() {
		addTestSuite(ControlPanelTest.class);
		addTestSuite(AddUserTest.class);
		addTestSuite(AddUser2Test.class);
		addTestSuite(AddNullUserSNTest.class);
		addTestSuite(AddEmailUserSNTest.class);
		addTestSuite(AddCyrusUserSNTest.class);
		addTestSuite(AddPostfixUserSNTest.class);
		addTestSuite(AddNumberUserSNTest.class);
		addTestSuite(AddAnonymousNameUserSNTest.class);
		addTestSuite(AddInvalidUserSNTest.class);
		addTestSuite(AddDuplicateUserSNTest.class);
		addTestSuite(AddNullUserEmailTest.class);
		addTestSuite(AddRootUserEmailTest.class);
		addTestSuite(AddPostmasterUserEmailTest.class);
		addTestSuite(AddInvalidUserEmailTest.class);
		addTestSuite(AddDuplicateUserEmailTest.class);
		addTestSuite(AddNullUserFirstNameTest.class);
		addTestSuite(AddNullUserLastNameTest.class);
		addTestSuite(AddNullPassword1Test.class);
		addTestSuite(AddNullPassword2Test.class);
		addTestSuite(AddDifferentPasswordTest.class);
		addTestSuite(SearchUserTest.class);
		addTestSuite(AdvancedSearchUserTest.class);
		addTestSuite(AddTemporaryUserTest.class);
		addTestSuite(DeactivateTemporaryUserTest.class);
		addTestSuite(DeleteTemporaryUserTest.class);
		addTestSuite(AddCommunitiesTest.class);
		addTestSuite(AddNullCommunityNameTest.class);
		addTestSuite(AddNumberCommunityNameTest.class);
		addTestSuite(AddInvalidCommunityNameTest.class);
		addTestSuite(AddDuplicateCommunityNameTest.class);
		addTestSuite(ApplyCommunityTest.class);
		addTestSuite(AssertApplyCommunityTest.class);
		addTestSuite(RemoveApplyCommunityTest.class);
		addTestSuite(AssertRemoveApplyCommunityTest.class);
		addTestSuite(SearchCommunityTest.class);
		addTestSuite(AddTemporaryCommunityTest.class);
		addTestSuite(EditCommunityTest.class);
		addTestSuite(DeleteTemporaryCommunityTest.class);
		addTestSuite(DeleteInvalidCommunityTest.class);
		addTestSuite(AddTemporaryLARCommunityTest.class);
		addTestSuite(AssertNoLARCommunityContentTest.class);
		addTestSuite(ImportCommunityLARTest.class);
		addTestSuite(AssertCommunityLARImportTest.class);
		addTestSuite(AddUserGroupTest.class);
		addTestSuite(AddNullUserGroupNameTest.class);
		addTestSuite(AddNumberUserGroupNameTest.class);
		addTestSuite(AddCommaUserGroupNameTest.class);
		addTestSuite(AddAsteriskUserGroupNameTest.class);
		addTestSuite(AddDuplicateUserGroupNameTest.class);
		addTestSuite(ApplyUserGroupTest.class);
		addTestSuite(AssertApplyUserGroupTest.class);
		addTestSuite(RemoveApplyUserGroupTest.class);
		addTestSuite(AssertRemoveApplyUserGroupTest.class);
		addTestSuite(SearchUserGroupTest.class);
		addTestSuite(AddTemporaryUserGroupTest.class);
		addTestSuite(DeleteTemporaryUserGroupTest.class);
		addTestSuite(AddOrganizationTest.class);
		addTestSuite(AddNullOrganizationTest.class);
		addTestSuite(AddDuplicateOrganizationTest.class);
		addTestSuite(ApplyOrganizationTest.class);
		addTestSuite(AssertApplyOrganizationTest.class);
		addTestSuite(AddOrganizationPageTest.class);
		addTestSuite(MergeOrganizationPageTest.class);
		addTestSuite(AssertMergeOrganizationPageTest.class);
		addTestSuite(RemoveApplyOrganizationTest.class);
		addTestSuite(AssertRemoveApplyOrganizationTest.class);
		addTestSuite(SearchOrganizationTest.class);
		addTestSuite(AdvancedSearchOrganizationTest.class);
		addTestSuite(AddTemporaryOrganizationTest.class);
		addTestSuite(DeleteTemporaryOrganizationTest.class);
		addTestSuite(AddPasswordPoliciesTest.class);
		addTestSuite(AddNullPasswordPolicyNameTest.class);
		addTestSuite(AddNumberPasswordPolicyNameTest.class);
		addTestSuite(AddCommaPasswordPolicyNameTest.class);
		addTestSuite(AddAsteriskPasswordPolicyNameTest.class);
		addTestSuite(AddDuplicatePasswordPolicyNameTest.class);
		addTestSuite(SettingsTest.class);
		addTestSuite(MonitoringTest.class);
		addTestSuite(PluginsConfigurationTest.class);
		addTestSuite(AddReservedUserSNTest.class);
		addTestSuite(AddReservedUserEmailTest.class);
		addTestSuite(AddServerCategoryTest.class);
		addTestSuite(EditServerCategoryTest.class);
		addTestSuite(BrowseServerTest.class);
		addTestSuite(BrowseServerPluginsInstallationTest.class);
		addTestSuite(BrowseServerInstanceTest.class);
		addTestSuite(AddServerInstanceTest.class);
		addTestSuite(AddNullServerInstanceWebIDTest.class);
		addTestSuite(AddDuplicateServerInstanceWebIDTest.class);
		addTestSuite(AddNullServerInstanceVHTest.class);
		addTestSuite(AddInvalidServerInstanceVHTest.class);
		addTestSuite(AddNullServerInstanceMDTest.class);
		addTestSuite(EditServerInstanceTest.class);

		addTestSuite(TearDownTest.class);
		addTestSuite(EndControlPanelTest.class);
	}

}