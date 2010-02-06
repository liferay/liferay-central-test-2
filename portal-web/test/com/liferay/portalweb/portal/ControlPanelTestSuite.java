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

package com.liferay.portalweb.portal;

import com.liferay.portalweb.portal.controlpanel.admin.AdminTests;
import com.liferay.portalweb.portal.controlpanel.blogs.BlogsTests;
import com.liferay.portalweb.portal.controlpanel.bookmarks.BookmarksTests;
import com.liferay.portalweb.portal.controlpanel.calendar.CalendarTests;
import com.liferay.portalweb.portal.controlpanel.communities.CommunitiesTests;
import com.liferay.portalweb.portal.controlpanel.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portal.controlpanel.organization.OrganizationTests;
import com.liferay.portalweb.portal.controlpanel.pagetemplates.PageTemplatesTests;
import com.liferay.portalweb.portal.controlpanel.passwordpolicies.PasswordPoliciesTests;
import com.liferay.portalweb.portal.controlpanel.polls.PollsTests;
import com.liferay.portalweb.portal.controlpanel.settings.SettingsTests;
import com.liferay.portalweb.portal.controlpanel.user.UserTests;
import com.liferay.portalweb.portal.controlpanel.usergroup.UserGroupTests;
import com.liferay.portalweb.portal.controlpanel.virtualhosting.VirtualHostingTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.WebContentTests;
import com.liferay.portalweb.portal.login.LoginTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ControlPanelTestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ControlPanelTestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(AdminTests.suite());
		testSuite.addTest(BlogsTests.suite());
		testSuite.addTest(BookmarksTests.suite());
		testSuite.addTest(CalendarTests.suite());
		testSuite.addTest(CommunitiesTests.suite());
		testSuite.addTest(MessageBoardsTests.suite());
		testSuite.addTest(OrganizationTests.suite());
		testSuite.addTest(PageTemplatesTests.suite());
		testSuite.addTest(PasswordPoliciesTests.suite());
		testSuite.addTest(PollsTests.suite());
		testSuite.addTest(SettingsTests.suite());
		testSuite.addTest(UserTests.suite());
		testSuite.addTest(UserGroupTests.suite());
		testSuite.addTest(VirtualHostingTests.suite());
		testSuite.addTest(WebContentTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}