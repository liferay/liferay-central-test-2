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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertActionsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_AddArticleTest.class);
		testSuite.addTestSuite(CA_ConfigureWCDPortletTest.class);
		testSuite.addTestSuite(CA_ConfigureWCLPortletTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AddConfigurationWCDPortletTest.class);
		testSuite.addTestSuite(Writer_AssertConfigurationWCDPortletTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_AddCommentTest.class);
		testSuite.addTestSuite(CA_AddCommentRatingTest.class);
		testSuite.addTestSuite(CA_AddCommentReplyTest.class);
		testSuite.addTestSuite(CA_AddRatingTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddCommentTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddCommentRatingTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddCommentReplyTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddRatingTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AddCommentTest.class);
		testSuite.addTestSuite(Member_AddCommentRatingTest.class);
		testSuite.addTestSuite(Member_AddCommentReplyTest.class);
		testSuite.addTestSuite(Member_AddRatingTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(Publisher_AddCommentTest.class);
		testSuite.addTestSuite(Publisher_AddCommentRatingTest.class);
		testSuite.addTestSuite(Publisher_AddCommentReplyTest.class);
		testSuite.addTestSuite(Publisher_AddRatingTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AddCommentTest.class);
		testSuite.addTestSuite(Writer_AddCommentRatingTest.class);
		testSuite.addTestSuite(Writer_AddCommentReplyTest.class);
		testSuite.addTestSuite(Writer_AddRatingTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_ExpireArticleTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEnterControlPanelTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotEnterControlPanelTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(Publisher_AssertCannotAddArticleTest.class);
		testSuite.addTestSuite(Publisher_AssertCannotEditArticleTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AssertCannotApproveArticleTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_EditArticleTest.class);
		testSuite.addTestSuite(CA_ApproveArticleTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(Publisher_AssertCannotDeleteArticleTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AssertCannotExpireArticleTest.class);
		testSuite.addTestSuite(Writer_AssertCannotDeleteArticleTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_DeleteArticleTest.class);
		testSuite.addTestSuite(CA_AddWCDArticleTest.class);
		testSuite.addTestSuite(CA_EditWCDArticleTest.class);
		testSuite.addTestSuite(CA_EnableWCDCommentsTest.class);
		testSuite.addTestSuite(CA_AddWCDCommentTest.class);
		testSuite.addTestSuite(CA_AssertEditWCSConfigurationTest.class);
		testSuite.addTestSuite(CA_AssertExportImportWCDLARTest.class);
		testSuite.addTestSuite(CA_AssertExportImportWCLLARTest.class);
		testSuite.addTestSuite(CA_AssertExportImportWCSLARTest.class);
		testSuite.addTestSuite(CA_AssertRemoveWCDPortletTest.class);
		testSuite.addTestSuite(CA_AssertRemoveWCLPortletTest.class);
		testSuite.addTestSuite(CA_AssertRemoveWCSPortletTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddWCDArticleTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditWCDArticleTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditCommentTest.class);
		testSuite.addTestSuite(Guest_AssertCannotDeleteCommentTest.class);
		testSuite.addTestSuite(
			Guest_AssertCannotEditWCDConfigurationTest.class);
		testSuite.addTestSuite(
			Guest_AssertCannotEditWCLConfigurationTest.class);
		testSuite.addTestSuite(
			Guest_AssertCannotEditWCSConfigurationTest.class);
		testSuite.addTestSuite(Guest_AssertCannotExportImportWCDLARTest.class);
		testSuite.addTestSuite(Guest_AssertCannotExportImportWCLLARTest.class);
		testSuite.addTestSuite(Guest_AssertCannotExportImportWCSLARTest.class);
		testSuite.addTestSuite(Guest_AssertCannotRemoveWCDPortletTest.class);
		testSuite.addTestSuite(Guest_AssertCannotRemoveWCLPortletTest.class);
		testSuite.addTestSuite(Guest_AssertCannotRemoveWCSPortletTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotAddWCDArticleTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditWCDArticleTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditCommentTest.class);
		testSuite.addTestSuite(Member_AssertCannotDeleteCommentTest.class);
		testSuite.addTestSuite(
			Member_AssertCannotEditWCDConfigurationTest.class);
		testSuite.addTestSuite(
			Member_AssertCannotEditWCLConfigurationTest.class);
		testSuite.addTestSuite(
			Member_AssertCannotEditWCSConfigurationTest.class);
		testSuite.addTestSuite(Member_AssertCannotExportImportWCDLARTest.class);
		testSuite.addTestSuite(Member_AssertCannotExportImportWCLLARTest.class);
		testSuite.addTestSuite(Member_AssertCannotExportImportWCSLARTest.class);
		testSuite.addTestSuite(Member_AssertCannotRemoveWCDPortletTest.class);
		testSuite.addTestSuite(Member_AssertCannotRemoveWCLPortletTest.class);
		testSuite.addTestSuite(Member_AssertCannotRemoveWCSPortletTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(Publisher_LoginTest.class);
		testSuite.addTestSuite(Publisher_AssertCannotAddWCDArticleTest.class);
		testSuite.addTestSuite(Publisher_AssertCannotEditWCDArticleTest.class);
		testSuite.addTestSuite(Publisher_AssertCannotEditCommentTest.class);
		testSuite.addTestSuite(Publisher_AssertCannotDeleteCommentTest.class);
		testSuite.addTestSuite(Publisher_AssertEditWCDConfigurationTest.class);
		testSuite.addTestSuite(Publisher_AssertEditWCLConfigurationTest.class);
		testSuite.addTestSuite(Publisher_AssertEditWCSConfigurationTest.class);
		testSuite.addTestSuite(Publisher_AssertExportImportWCDLARTest.class);
		testSuite.addTestSuite(Publisher_AssertExportImportWCLLARTest.class);
		testSuite.addTestSuite(Publisher_AssertExportImportWCSLARTest.class);
		testSuite.addTestSuite(Publisher_AssertRemoveWCDPortletTest.class);
		testSuite.addTestSuite(Publisher_AssertRemoveWCLPortletTest.class);
		testSuite.addTestSuite(Publisher_AssertRemoveWCSPortletTest.class);
		testSuite.addTestSuite(Publisher_LogoutTest.class);
		testSuite.addTestSuite(Writer_LoginTest.class);
		testSuite.addTestSuite(Writer_AssertCannotAddWCDArticleTest.class);
		testSuite.addTestSuite(Writer_EditWCDArticleTest.class);
		testSuite.addTestSuite(Writer_AssertCannotEditCommentTest.class);
		testSuite.addTestSuite(Writer_AssertCannotDeleteCommentTest.class);
		testSuite.addTestSuite(Writer_AssertEditWCSConfigurationTest.class);
		testSuite.addTestSuite(Writer_AssertExportImportWCDLARTest.class);
		testSuite.addTestSuite(Writer_AssertExportImportWCLLARTest.class);
		testSuite.addTestSuite(Writer_AssertExportImportWCSLARTest.class);
		testSuite.addTestSuite(Writer_AssertRemoveWCDPortletTest.class);
		testSuite.addTestSuite(Writer_AssertRemoveWCLPortletTest.class);
		testSuite.addTestSuite(Writer_AssertRemoveWCSPortletTest.class);
		testSuite.addTestSuite(Writer_LogoutTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_EditCommentTest.class);
		testSuite.addTestSuite(CA_DeleteCommentTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_CleanUpTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);

		return testSuite;
	}

}