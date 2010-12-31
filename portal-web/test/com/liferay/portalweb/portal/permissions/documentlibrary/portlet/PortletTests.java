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

package com.liferay.portalweb.portal.permissions.documentlibrary.portlet;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_SetupTest.class);
		testSuite.addTestSuite(SA_RemoveViewPortletPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotViewPortletTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowViewPortletPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertViewPortletTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveConfigurePortletPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotConfigurePortletTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowConfigurePortletPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertConfigurePortletTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveConfigurePortletPermissionsTest.class);
		testSuite.addTestSuite(
			SA_DisableAllowAccessInControlPanelPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_ControlPanelTest.class);
		testSuite.addTestSuite(Portlet_AssertNoAccessInControlPanelTest.class);
		testSuite.addTestSuite(Portlet_EndControlPanelTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AddTemporaryFolderTest.class);
		testSuite.addTestSuite(SA_RemoveViewFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotViewFolderTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowViewFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertViewFolderTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotAddDocumentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowAddDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AddDocumentTest.class);
		testSuite.addTestSuite(Portlet_DeleteOwnDocumentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_RemoveAddShortcutPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotAddShortcutTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowAddShortcutPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_MyCommunityControlPanelTest.class);
		testSuite.addTestSuite(Portlet_AddMyCommunityFolderTest.class);
		testSuite.addTestSuite(Portlet_AddMyCommunityDocumentTest.class);
		testSuite.addTestSuite(Portlet_EndMyCommunityControlPanelTest.class);
		testSuite.addTestSuite(Portlet_AddShortcutTest.class);
		testSuite.addTestSuite(Portlet_DeleteOwnShortcutTest.class);
		testSuite.addTestSuite(Portlet_MyCommunityControlPanelTest.class);
		testSuite.addTestSuite(Portlet_DeleteMyCommunityFolderTest.class);
		testSuite.addTestSuite(Portlet_EndMyCommunityControlPanelTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddShortcutPermissionsTest.class);
		testSuite.addTestSuite(SA_RemoveAddSubfolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotAddSubfolderTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowAddSubfolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AddSubfolderTest.class);
		testSuite.addTestSuite(Portlet_DeleteOwnSubfolderTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddSubfolderPermissionsTest.class);
		testSuite.addTestSuite(SA_RemovePermissionsFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(
			Portlet_AssertCannotEditFolderPermissionsTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowPermissionsFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertEditFolderPermissionsTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemovePermissionsFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_RemoveEditFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditFolderTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowEditFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_EditFolderTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveEditFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_RemoveDeleteFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotDeleteFolderTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowDeleteFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_DeleteFolderTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveDeleteFolderPermissionsTest.class);
		testSuite.addTestSuite(SA_AddTemporaryDocumentTest.class);
		testSuite.addTestSuite(SA_RemoveViewDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotViewDocumentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowViewDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertViewDocumentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotAddCommentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowAddCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AddCommentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveEditCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditCommentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowEditCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_EditCommentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveEditCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_RemoveDeleteCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotDeleteCommentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowDeleteCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_DeleteCommentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_RemoveDeleteCommentPermissionsTest.class);
		testSuite.addTestSuite(
			SA_RemovePermissionsDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(
			Portlet_AssertCannotEditDocumentPermissionsTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(
			SA_AllowPermissionsDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertEditDocumentPermissionsTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(
			SA_RemovePermissionsDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_RemoveEditDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditDocumentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowEditDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_EditDocumentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveEditDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_RemoveDeleteDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotDeleteDocumentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowDeleteDocumentPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_DeleteDocumentTest.class);
		testSuite.addTestSuite(Portlet_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_CleanUpTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);

		return testSuite;
	}

}