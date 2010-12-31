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

package com.liferay.portalweb.portal.controlpanel.communities.general;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class GeneralTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AddCommunitiesTest.class);
		testSuite.addTestSuite(AddCommunitiesPublicPageTest.class);
		testSuite.addTestSuite(AddCommunitiesPrivatePageTest.class);
		testSuite.addTestSuite(AssertCommunityDropDownTest.class);
		testSuite.addTestSuite(AddAsteriskCommunityNameTest.class);
		testSuite.addTestSuite(AddCommaCommunityNameTest.class);
		testSuite.addTestSuite(AddDuplicateCommunityNameTest.class);
		testSuite.addTestSuite(AddNullCommunityNameTest.class);
		testSuite.addTestSuite(AddNumberCommunityNameTest.class);
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
//		testSuite.addTestSuite(ImportCommunityLARTest.class);
//		testSuite.addTestSuite(AssertCommunityLARImportTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownCommunityTest.class);

		return testSuite;
	}

}