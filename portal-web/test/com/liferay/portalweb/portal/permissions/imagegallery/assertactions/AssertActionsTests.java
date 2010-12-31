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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

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