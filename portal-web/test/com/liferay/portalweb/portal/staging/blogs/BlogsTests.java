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

package com.liferay.portalweb.portal.staging.blogs;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="BlogsTests.java.html"><b><i>View Source</i></b></a>
 *
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