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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

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
		testSuite.addTestSuite(CA_AddImageTest.class);
		testSuite.addTestSuite(CA_AddMoveFoldersTest.class);
		testSuite.addTestSuite(CA_MoveImageTest.class);
		testSuite.addTestSuite(CA_EditFolderTest.class);
		testSuite.addTestSuite(CA_EditImageTest.class);
		testSuite.addTestSuite(CA_RemoveGuestViewFolderPermissionsTest.class);
		testSuite.addTestSuite(CA_RemoveGuestViewImagePermissionsTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewFolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewImageTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_RemoveGuestViewPortletPermissionsTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewPortletTest.class);
		testSuite.addTestSuite(CA_LoginTest.class);
		testSuite.addTestSuite(CA_RestoreGuestViewImageTest.class);
		testSuite.addTestSuite(CA_RestoreGuestViewFolderTest.class);
		testSuite.addTestSuite(CA_RestoreGuestViewPortletTest.class);
		testSuite.addTestSuite(CA_AssertActionTest.class);
		testSuite.addTestSuite(CA_DeleteImageTest.class);
		testSuite.addTestSuite(CA_DeleteFolderTest.class);
		testSuite.addTestSuite(CA_AddSecondImageTest.class);
		testSuite.addTestSuite(CA_LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AddImageTest.class);
		testSuite.addTestSuite(Member_MoveImageTest.class);
		testSuite.addTestSuite(Member_EditImageTest.class);
		testSuite.addTestSuite(Member_AssertCannotAddFolderTest.class);
		testSuite.addTestSuite(Member_AssertCannotAddSubfolderTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditFolderTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditImageTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditPermissionsTest.class);
		testSuite.addTestSuite(Member_AssertActionTest.class);
		testSuite.addTestSuite(Member_DeleteImageTest.class);
		testSuite.addTestSuite(Member_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNotSignedInTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddFolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddSubfolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddImageTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditFolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditImageTest.class);
		testSuite.addTestSuite(Guest_AssertActionTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_CleanUpTest.class);
		testSuite.addTestSuite(SA_LogoutTest.class);

		return testSuite;
	}

}