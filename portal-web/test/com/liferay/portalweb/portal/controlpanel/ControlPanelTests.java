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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ControlPanelTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ControlPanelTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddNullUserSNTest.class);
		testSuite.addTestSuite(AddEmailUserSNTest.class);
		testSuite.addTestSuite(AddCyrusUserSNTest.class);
		testSuite.addTestSuite(AddPostfixUserSNTest.class);
		testSuite.addTestSuite(AddNumberUserSNTest.class);
		testSuite.addTestSuite(AddAnonymousNameUserSNTest.class);
		testSuite.addTestSuite(AddInvalidUserSNTest.class);
		testSuite.addTestSuite(AddDuplicateUserSNTest.class);
		testSuite.addTestSuite(AddNullUserEmailTest.class);
		testSuite.addTestSuite(AddRootUserEmailTest.class);
		testSuite.addTestSuite(AddPostmasterUserEmailTest.class);
		testSuite.addTestSuite(AddInvalidUserEmailTest.class);
		testSuite.addTestSuite(AddDuplicateUserEmailTest.class);
		testSuite.addTestSuite(AddNullUserFirstNameTest.class);
		testSuite.addTestSuite(AddNullUserLastNameTest.class);
		testSuite.addTestSuite(AddNullPassword1Test.class);
		testSuite.addTestSuite(AddNullPassword2Test.class);
		testSuite.addTestSuite(AddDifferentPasswordTest.class);
		testSuite.addTestSuite(SearchUserTest.class);
		testSuite.addTestSuite(AdvancedSearchUserTest.class);
		testSuite.addTestSuite(AddTemporaryUserTest.class);
		testSuite.addTestSuite(DeactivateTemporaryUserTest.class);
		testSuite.addTestSuite(DeleteTemporaryUserTest.class);
		testSuite.addTestSuite(AddCommunitiesTest.class);
		testSuite.addTestSuite(AddNullCommunityNameTest.class);
		testSuite.addTestSuite(AddNumberCommunityNameTest.class);
		testSuite.addTestSuite(AddInvalidCommunityNameTest.class);
		testSuite.addTestSuite(AddDuplicateCommunityNameTest.class);
		testSuite.addTestSuite(ApplyCommunityTest.class);
		testSuite.addTestSuite(AssertApplyCommunityTest.class);
		testSuite.addTestSuite(RemoveApplyCommunityTest.class);
		testSuite.addTestSuite(AssertRemoveApplyCommunityTest.class);
		testSuite.addTestSuite(SearchCommunityTest.class);
		testSuite.addTestSuite(AddTemporaryCommunityTest.class);
		testSuite.addTestSuite(EditCommunityTest.class);
		testSuite.addTestSuite(DeleteTemporaryCommunityTest.class);
		testSuite.addTestSuite(DeleteInvalidCommunityTest.class);
		testSuite.addTestSuite(AddTemporaryLARCommunityTest.class);
		testSuite.addTestSuite(AssertNoLARCommunityContentTest.class);
		testSuite.addTestSuite(ImportCommunityLARTest.class);
		testSuite.addTestSuite(AssertCommunityLARImportTest.class);
		testSuite.addTestSuite(AddUserGroupTest.class);
		testSuite.addTestSuite(AddNullUserGroupNameTest.class);
		testSuite.addTestSuite(AddNumberUserGroupNameTest.class);
		testSuite.addTestSuite(AddCommaUserGroupNameTest.class);
		testSuite.addTestSuite(AddAsteriskUserGroupNameTest.class);
		testSuite.addTestSuite(AddDuplicateUserGroupNameTest.class);
		testSuite.addTestSuite(ApplyUserGroupTest.class);
		testSuite.addTestSuite(AssertApplyUserGroupTest.class);
		testSuite.addTestSuite(RemoveApplyUserGroupTest.class);
		testSuite.addTestSuite(AssertRemoveApplyUserGroupTest.class);
		testSuite.addTestSuite(SearchUserGroupTest.class);
		testSuite.addTestSuite(AddTemporaryUserGroupTest.class);
		testSuite.addTestSuite(DeleteTemporaryUserGroupTest.class);
		testSuite.addTestSuite(AddOrganizationTest.class);
		testSuite.addTestSuite(AddNullOrganizationTest.class);
		testSuite.addTestSuite(AddDuplicateOrganizationTest.class);
		testSuite.addTestSuite(ApplyOrganizationTest.class);
		testSuite.addTestSuite(AssertApplyOrganizationTest.class);
		testSuite.addTestSuite(AddOrganizationPageTest.class);
		testSuite.addTestSuite(MergeOrganizationPageTest.class);
		testSuite.addTestSuite(AssertMergeOrganizationPageTest.class);
		testSuite.addTestSuite(RemoveApplyOrganizationTest.class);
		testSuite.addTestSuite(AssertRemoveApplyOrganizationTest.class);
		testSuite.addTestSuite(SearchOrganizationTest.class);
		testSuite.addTestSuite(AdvancedSearchOrganizationTest.class);
		testSuite.addTestSuite(AddTemporaryOrganizationTest.class);
		testSuite.addTestSuite(DeleteTemporaryOrganizationTest.class);
		testSuite.addTestSuite(AddPasswordPoliciesTest.class);
		testSuite.addTestSuite(AddNullPasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddNumberPasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddCommaPasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddAsteriskPasswordPolicyNameTest.class);
		testSuite.addTestSuite(AddDuplicatePasswordPolicyNameTest.class);
		testSuite.addTestSuite(SettingsTest.class);
		testSuite.addTestSuite(SettingsMiscellaneousDisplaySettingsTest.class);
		testSuite.addTestSuite(MonitoringTest.class);
		testSuite.addTestSuite(PluginsConfigurationTest.class);
		testSuite.addTestSuite(AddReservedUserSNTest.class);
		testSuite.addTestSuite(AddReservedUserEmailTest.class);
		testSuite.addTestSuite(AddServerCategoryTest.class);
		testSuite.addTestSuite(EditServerCategoryTest.class);
		testSuite.addTestSuite(BrowseServerTest.class);
		testSuite.addTestSuite(BrowseServerPluginsInstallationTest.class);
		testSuite.addTestSuite(BrowseInstallMorePluginsTest.class);
		testSuite.addTestSuite(BrowseServerInstanceTest.class);
		testSuite.addTestSuite(AddServerInstanceTest.class);
		testSuite.addTestSuite(AddNullServerInstanceWebIDTest.class);
		testSuite.addTestSuite(AddDuplicateServerInstanceWebIDTest.class);
		testSuite.addTestSuite(AddNullServerInstanceVHTest.class);
		testSuite.addTestSuite(AddInvalidServerInstanceVHTest.class);
		testSuite.addTestSuite(AddNullServerInstanceMDTest.class);
		testSuite.addTestSuite(EditServerInstanceTest.class);
		testSuite.addTestSuite(AddVirtualHostCommunityTest.class);
		testSuite.addTestSuite(AddContentVirtualHostCommunityTest.class);
		testSuite.addTestSuite(AssertVirtualHostDefaultsTest.class);
		testSuite.addTestSuite(EnableVirtualHostingTest.class);
		testSuite.addTestSuite(AssertVirtualHostingAddressTest.class);
		testSuite.addTestSuite(DeleteVirtualHostCommunityTest.class);

		testSuite.addTestSuite(TearDownTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);

		return testSuite;
	}

}