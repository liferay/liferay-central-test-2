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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTests;

/**
 * <a href="ControlPanelTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ControlPanelTests extends BaseTests {

	public ControlPanelTests() {
		addTestSuite(CreateRolesTest.class);
		addTestSuite(DefineCARolesTest.class);
		addTestSuite(CA_MessageBoardsRolesTest.class);
		addTestSuite(CA_AnnouncementsRolesTest.class);
		addTestSuite(CA_BlogsRolesTest.class);
		addTestSuite(CA_PortalRolesTest.class);
		addTestSuite(DefineMemberRolesTest.class);
		addTestSuite(Member_MessageBoardsRolesTest.class);
		addTestSuite(Member_AnnouncementsRolesTest.class);
		addTestSuite(Member_BlogsRolesTest.class);
		addTestSuite(DefinePublisherRolesTest.class);
		addTestSuite(DefineWriterRolesTest.class);
		addTestSuite(AddCATest.class);
		addTestSuite(AddMemberTest.class);
		addTestSuite(AddPublisherTest.class);
		addTestSuite(AddWriterTest.class);
		addTestSuite(LoginUsersTest.class);
		addTestSuite(LogoutTest.class);
	}

}