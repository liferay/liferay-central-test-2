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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTests;

/**
 * <a href="AnnouncementsTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsTests extends BaseTests {

	public AnnouncementsTests() {
		addTestSuite(SA_LoginTest.class);
		addTestSuite(SA_AddPageTest.class);
		addTestSuite(SA_AddPortletTest.class);
		addTestSuite(SA_AddGeneralAnnouncementTest.class);
		addTestSuite(SA_AddCAAnnouncementTest.class);
		addTestSuite(SA_AddMemberAnnouncementTest.class);
		addTestSuite(SA_AddGuestAnnouncementTest.class);
		addTestSuite(SA_LogoutTest.class);
		addTestSuite(CA_LoginTest.class);
		addTestSuite(CA_AssertViewTest.class);
		addTestSuite(CA_AssertActionsTest.class);
		addTestSuite(CA_LogoutTest.class);
		addTestSuite(Member_LoginTest.class);
		addTestSuite(Member_AssertViewTest.class);
		addTestSuite(Member_DismissAnnouncementTest.class);
		addTestSuite(Member_AssertActionsTest.class);
		addTestSuite(Member_LogoutTest.class);
		addTestSuite(Guest_AssertViewTest.class);
		addTestSuite(Guest_AssertActionsTest.class);
		addTestSuite(DeletePageTest.class);
	}

}