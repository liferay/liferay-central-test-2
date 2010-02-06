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

package com.liferay.portalweb.portal.permissions.documentlibrary.assertactions;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="AssertActionsTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertActionsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_AddFolderTest.class);
		testSuite.addTestSuite(CA_AddSubfolderTest.class);
		testSuite.addTestSuite(CA_AddMoveFoldersTest.class);
		testSuite.addTestSuite(CA_AddDocumentTest.class);
		testSuite.addTestSuite(CA_AddCommentTest.class);
		testSuite.addTestSuite(CA_EditFolderTest.class);
		testSuite.addTestSuite(CA_EditDocumentTest.class);
		testSuite.addTestSuite(CA_EditCommentTest.class);
		testSuite.addTestSuite(CA_MoveDocumentTest.class);
		testSuite.addTestSuite(CA_AssertEditPermissionsTest.class);
		testSuite.addTestSuite(CA_AssertDeleteActionsTest.class);
		testSuite.addTestSuite(CA_EditConfigurationTest.class);
		testSuite.addTestSuite(CA_SearchPortletTest.class);
		testSuite.addTestSuite(CA_AssertRemovePortletTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertViewFoldersTest.class);
		testSuite.addTestSuite(Member_AssertViewDocumentTest.class);
		testSuite.addTestSuite(Member_AssertViewCommentsTest.class);
		testSuite.addTestSuite(Member_AssertAddCommentNotOwnerTest.class);
		testSuite.addTestSuite(Member_AddDocumentTest.class);
		testSuite.addTestSuite(Member_EditDocumentTest.class);
		testSuite.addTestSuite(Member_MoveDocumentTest.class);
		testSuite.addTestSuite(Member_AssertDeleteDocumentTest.class);
		testSuite.addTestSuite(Member_AddCommentTest.class);
		testSuite.addTestSuite(Member_EditCommentTest.class);
		testSuite.addTestSuite(Member_AssertDeleteCommentTest.class);
		testSuite.addTestSuite(Member_AddFolderTest.class);
		testSuite.addTestSuite(Member_DeleteFolderTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditFoldersTest.class);
		testSuite.addTestSuite(Member_AssertCannotDeleteFoldersTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditUserCommentsTest.class);
		testSuite.addTestSuite(Member_AssertCannotDeleteUserCommentsTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditUserDocumentsTest.class);
		testSuite.addTestSuite(
			Member_AssertCannotDeleteUserDocumentsTest.class);
		testSuite.addTestSuite(Member_AssertCannotMoveUserDocumentsTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditPermissionsTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditConfigurationTest.class);
		testSuite.addTestSuite(Member_AssertCannotRemovePortletTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_EditPermissionsTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNotSignedInTest.class);
		testSuite.addTestSuite(Guest_AssertViewFoldersTest.class);
		testSuite.addTestSuite(Guest_AssertViewDocumentsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewFolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewDocumentTest.class);
		testSuite.addTestSuite(Guest_SearchPortletTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddFoldersTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditFoldersTest.class);
		testSuite.addTestSuite(Guest_AssertCannotDeleteFoldersTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddDocumentsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditDocumentsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotDeleteDocumentsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotMoveDocumentsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddCommentsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditCommentsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotDeleteCommentsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditPermissionsTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditConfigurationTest.class);
		testSuite.addTestSuite(Guest_AssertCannotRemovePortletTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_CleanUpTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);

		return testSuite;
	}

}