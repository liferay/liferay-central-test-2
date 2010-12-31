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

package com.liferay.portalweb.portal.stagingcommunity.blogs;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_SetupTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(CC_LoginTest.class);
		testSuite.addTestSuite(CC_AddStagedPortletTest.class);
		testSuite.addTestSuite(CC_SendPortletProposalTest.class);
		testSuite.addTestSuite(CC_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNoPortletPreApprovalTest.class);
		testSuite.addTestSuite(CP_LoginTest.class);
		testSuite.addTestSuite(CP_AssertCannotPublishProposalTest.class);
		testSuite.addTestSuite(CP_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_ApprovePortletProposalTest.class);
		testSuite.addTestSuite(CA_AssignPortletPublicationTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNoPortletPrePublishTest.class);
		testSuite.addTestSuite(CP_LoginTest.class);
		testSuite.addTestSuite(CP_PublishPortletToLiveTest.class);
		testSuite.addTestSuite(CP_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertPortletPresentTest.class);
		testSuite.addTestSuite(CC_LoginTest.class);
		testSuite.addTestSuite(CC_AddStagedContentTest.class);
		testSuite.addTestSuite(CC_SendContentProposalTest.class);
		testSuite.addTestSuite(CC_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNoContentPreApprovalTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_ApproveContentProposalTest.class);
		testSuite.addTestSuite(CA_AssignContentPublicationTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNoContentPrePublishTest.class);
		testSuite.addTestSuite(CP_LoginTest.class);
		testSuite.addTestSuite(CP_PublishContentToLiveTest.class);
		testSuite.addTestSuite(CP_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertContentPresentTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_TearDownTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);

		return testSuite;
	}

}