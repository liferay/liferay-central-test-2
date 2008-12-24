/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.permissions.documentlibrary;

import com.liferay.portalweb.portal.BaseTests;

/**
 * <a href="DocumentLibraryTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DocumentLibraryTests extends BaseTests {

	public DocumentLibraryTests() {
		addTestSuite(SA_LoginTest.class);
		addTestSuite(SA_SetupTest.class);
		addTestSuite(SA_LogoutTest.class);
		addTestSuite(CA_LoginTest.class);
		addTestSuite(CA_AddFolderTest.class);
		addTestSuite(CA_AddSubfolderTest.class);
		addTestSuite(CA_AddMoveFoldersTest.class);
		addTestSuite(CA_AddDocumentTest.class);
		addTestSuite(CA_AddCommentTest.class);
		addTestSuite(CA_AssertEditFolderTest.class);
		addTestSuite(CA_EditDocumentTest.class);
		addTestSuite(CA_EditCommentTest.class);
		addTestSuite(CA_MoveDocumentTest.class);
		addTestSuite(CA_AssertEditPermissionsTest.class);
		addTestSuite(CA_AssertDeleteActionsTest.class);
		addTestSuite(CA_EditConfigurationTest.class);
		addTestSuite(CA_SearchPortletTest.class);
		addTestSuite(CA_AssertRemovePortletTest.class);
		addTestSuite(CA_LogoutTest.class);
		addTestSuite(Member_LoginTest.class);
		addTestSuite(Member_AssertViewFoldersTest.class);
		addTestSuite(Member_AssertViewDocumentTest.class);
		addTestSuite(Member_AssertViewCommentsTest.class);
		addTestSuite(Member_AssertAddCommentNotOwnerTest.class);
		addTestSuite(Member_AddDocumentTest.class);
		addTestSuite(Member_EditDocumentTest.class);
		addTestSuite(Member_MoveDocumentTest.class);
		addTestSuite(Member_AssertDeleteDocumentTest.class);
		addTestSuite(Member_AddCommentTest.class);
		addTestSuite(Member_EditCommentTest.class);
		addTestSuite(Member_AssertDeleteCommentTest.class);
		addTestSuite(Member_AssertCannotAddFoldersTest.class);
		addTestSuite(Member_AssertCannotEditFoldersTest.class);
		addTestSuite(Member_AssertCannotDeleteFoldersTest.class);
		addTestSuite(Member_AssertCannotEditUserCommentsTest.class);
		addTestSuite(Member_AssertCannotDeleteUserCommentsTest.class);
		addTestSuite(Member_AssertCannotEditUserDocumentsTest.class);
		addTestSuite(Member_AssertCannotDeleteUserDocumentsTest.class);
		addTestSuite(Member_AssertCannotMoveUserDocumentsTest.class);
		addTestSuite(Member_AssertCannotEditPermissionsTest.class);
		addTestSuite(Member_AssertCannotEditConfigurationTest.class);
		addTestSuite(Member_AssertCannotRemovePortletTest.class);
		addTestSuite(Member_LogoutTest.class);
		addTestSuite(Guest_AssertNotSignedInTest.class);
		addTestSuite(Guest_AssertViewFoldersTest.class);
		addTestSuite(Guest_AssertViewDocumentsTest.class);
		addTestSuite(Guest_SearchPortletTest.class);
		addTestSuite(Guest_AssertCannotAddFoldersTest.class);
		addTestSuite(Guest_AssertCannotEditFoldersTest.class);
		addTestSuite(Guest_AssertCannotDeleteFoldersTest.class);
		addTestSuite(Guest_AssertCannotAddDocumentsTest.class);
		addTestSuite(Guest_AssertCannotEditDocumentsTest.class);
		addTestSuite(Guest_AssertCannotDeleteDocumentsTest.class);
		addTestSuite(Guest_AssertCannotMoveDocumentsTest.class);
		addTestSuite(Guest_AssertCannotAddCommentsTest.class);
		addTestSuite(Guest_AssertCannotEditCommentsTest.class);
		addTestSuite(Guest_AssertCannotDeleteCommentsTest.class);
		addTestSuite(Guest_AssertCannotEditPermissionsTest.class);
		addTestSuite(Guest_AssertCannotEditConfigurationTest.class);
		addTestSuite(Guest_AssertCannotRemovePortletTest.class);
		addTestSuite(TearDownTest.class);

	}

}