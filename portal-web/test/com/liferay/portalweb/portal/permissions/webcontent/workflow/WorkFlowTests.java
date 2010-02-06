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

package com.liferay.portalweb.portal.permissions.webcontent.workflow;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="WorkFlowTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WorkFlowTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AddArticleTest.class);
		testSuite.addTestSuite(Writer_ConfigureWCDPortletTest.class);
		testSuite.addTestSuite(Writer_ConfigureWCLPortletTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_AssertCannotViewWCDPreApprovalTest.class);
		testSuite.addTestSuite(CA_AssertCannotViewWCLPreApprovalTest.class);
		testSuite.addTestSuite(CA_AssertCannotSearchWCSPreApprovalTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewWCDPreApprovalTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewWCLPreApprovalTest.class);
		testSuite.addTestSuite(
			Guest_AssertCannotSearchWCSPreApprovalTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCDPreApprovalTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCLPreApprovalTest.class);
		testSuite.addTestSuite(
			Member_AssertCannotSearchWCSPreApprovalTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(
			Publisher_AssertCannotViewWCDPreApprovalTest.class);
		testSuite.addTestSuite(
			Publisher_AssertCannotViewWCLPreApprovalTest.class);
		testSuite.addTestSuite(
			Publisher_AssertCannotSearchWCSPreApprovalTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AssertCannotViewWCDPreApprovalTest.class);
		testSuite.addTestSuite(Writer_AssertCannotViewWCLPreApprovalTest.class);
		testSuite.addTestSuite(
			Writer_AssertCannotSearchWCSPreApprovalTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(Publisher_ApproveArticleTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_AssertViewWCDContentTest.class);
		testSuite.addTestSuite(CA_AssertViewWCLContentTest.class);
		testSuite.addTestSuite(CA_AssertSearchWCSPortletTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertViewWCDContentTest.class);
		testSuite.addTestSuite(Guest_AssertViewWCLContentTest.class);
		testSuite.addTestSuite(Guest_AssertSearchWCSPortletTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertViewWCDContentTest.class);
		testSuite.addTestSuite(Member_AssertViewWCLContentTest.class);
		testSuite.addTestSuite(Member_AssertSearchWCSPortletTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(Publisher_AssertViewWCDContentTest.class);
		testSuite.addTestSuite(Publisher_AssertViewWCLContentTest.class);
		testSuite.addTestSuite(Publisher_AssertSearchWCSPortletTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AssertViewWCDContentTest.class);
		testSuite.addTestSuite(Writer_AssertViewWCLContentTest.class);
		testSuite.addTestSuite(Writer_AssertSearchWCSPortletTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(Publisher_ExpireArticleTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_AssertCannotViewWCDPostExpireTest.class);
		testSuite.addTestSuite(CA_AssertCannotViewWCLPostExpireTest.class);
		testSuite.addTestSuite(CA_AssertCannotSearchWCSPostExpireTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewWCDPostExpireTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewWCLPostExpireTest.class);
		testSuite.addTestSuite(Guest_AssertCannotSearchWCSPostExpireTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCDPostExpireTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCLPostExpireTest.class);
		testSuite.addTestSuite(
			Member_AssertCannotSearchWCSPostExpireTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(
			Publisher_AssertCannotViewWCDPostExpireTest.class);
		testSuite.addTestSuite(
			Publisher_AssertCannotViewWCLPostExpireTest.class);
		testSuite.addTestSuite(
			Publisher_AssertCannotSearchWCSPostExpireTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AssertCannotViewWCDPostExpireTest.class);
		testSuite.addTestSuite(Writer_AssertCannotViewWCLPostExpireTest.class);
		testSuite.addTestSuite(
			Writer_AssertCannotSearchWCSPostExpireTest.class);
		testSuite.addTestSuite(Writer_EditOwnArticleTest.class);
		testSuite.addTestSuite(Writer_DeleteOwnArticleTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_CleanUpTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);

		return testSuite;
	}

}