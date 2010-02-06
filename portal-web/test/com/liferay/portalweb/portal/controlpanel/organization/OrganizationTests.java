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