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

package com.liferay.portalweb.portal.controlpanel.organization;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="OrganizationTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class OrganizationTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
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
		testSuite.addTestSuite(AddNullTestOrganizationTest.class);
		testSuite.addTestSuite(AddNullOrganizationStreetAddressTest.class);
		testSuite.addTestSuite(AddNullOrganizationCityAddressTest.class);
		testSuite.addTestSuite(AddNullOrganizationZipAddressTest.class);
		testSuite.addTestSuite(AddInvalidOrganizationPhoneNumberTest.class);
		testSuite.addTestSuite(AddNullOrganizationPhoneNumberTest.class);
		testSuite.addTestSuite(AddNullAdditionalOrganizationEmailTest.class);
		testSuite.addTestSuite(AddInvalidAdditionalOrganizationEmailTest.class);
		testSuite.addTestSuite(AddInvalidOrganizationURLWebsiteTest.class);
		testSuite.addTestSuite(AddNullOrganizationURLWebsiteTest.class);
		testSuite.addTestSuite(DeleteNullTestOrganizationTest.class);
		testSuite.addTestSuite(ApplyOrganizationTest.class);
		testSuite.addTestSuite(AssertApplyOrganizationTest.class);
		testSuite.addTestSuite(AssertCannotDeleteApplyOrganizationTest.class);
		testSuite.addTestSuite(AddOrganizationPageTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);
		testSuite.addTestSuite(AssertNotMergeOrganizationPageTest.class);
		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(MergeOrganizationPageTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);
		testSuite.addTestSuite(AssertMergeOrganizationPageTest.class);
		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(RemoveApplyOrganizationTest.class);
		testSuite.addTestSuite(AssertRemoveApplyOrganizationTest.class);
		testSuite.addTestSuite(SearchOrganizationTest.class);
		testSuite.addTestSuite(AdvancedSearchOrganizationTest.class);
		testSuite.addTestSuite(AddTemporaryOrganizationTest.class);
		testSuite.addTestSuite(DeleteTemporaryOrganizationTest.class);
		testSuite.addTestSuite(TearDownTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);

		return testSuite;
	}

}