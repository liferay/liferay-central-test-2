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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ControlPanelTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ControlPanelTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(CreateRolesTest.class);
		testSuite.addTestSuite(DefineCARolesTest.class);
		testSuite.addTestSuite(CA_PortalRolesTest.class);
		testSuite.addTestSuite(CA_AnnouncementsRolesTest.class);
		testSuite.addTestSuite(CA_BlogsRolesTest.class);
		testSuite.addTestSuite(CA_DocumentLibraryRolesTest.class);
		testSuite.addTestSuite(CA_ImageGalleryRolesTest.class);
		testSuite.addTestSuite(CA_MessageBoardsRolesTest.class);
		testSuite.addTestSuite(CA_WebContentDisplayRolesTest.class);
		testSuite.addTestSuite(CA_WebContentListRolesTest.class);
		testSuite.addTestSuite(CA_WebContentRolesTest.class);
		testSuite.addTestSuite(CA_WebContentSearchRolesTest.class);
		testSuite.addTestSuite(DefineMemberRolesTest.class);
		testSuite.addTestSuite(Member_AnnouncementsRolesTest.class);
		testSuite.addTestSuite(Member_BlogsRolesTest.class);
		testSuite.addTestSuite(Member_DocumentLibraryRolesTest.class);
		testSuite.addTestSuite(Member_ImageGalleryRolesTest.class);
		testSuite.addTestSuite(Member_MessageBoardsRolesTest.class);
		testSuite.addTestSuite(Member_WebContentRolesTest.class);
		testSuite.addTestSuite(DefinePublisherRolesTest.class);
		testSuite.addTestSuite(Publisher_PortalRolesTest.class);
		testSuite.addTestSuite(Publisher_WebContentRolesTest.class);
		testSuite.addTestSuite(DefineWriterRolesTest.class);
		testSuite.addTestSuite(Writer_PortalRolesTest.class);
		testSuite.addTestSuite(Writer_WebContentDisplayRolesTest.class);
		testSuite.addTestSuite(Writer_WebContentListRolesTest.class);
		testSuite.addTestSuite(Writer_WebContentRolesTest.class);
		testSuite.addTestSuite(Writer_WebContentSearchRolesTest.class);
		testSuite.addTestSuite(AddCATest.class);
		testSuite.addTestSuite(AddMemberTest.class);
		testSuite.addTestSuite(AddPortletMemberTest.class);
		testSuite.addTestSuite(AddPublisherTest.class);
		testSuite.addTestSuite(AddScopeTest.class);
		testSuite.addTestSuite(AddWriterTest.class);
		testSuite.addTestSuite(AssignMembersTest.class);
		testSuite.addTestSuite(AddScopeCommunityTest.class);
		testSuite.addTestSuite(AddScopeCommunityPageTest.class);
		testSuite.addTestSuite(AssignScopeMemberToScopeCommunityTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(LogoutTest.class);

		return testSuite;
	}

}