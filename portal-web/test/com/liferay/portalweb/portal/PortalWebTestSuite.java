/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portalweb.portal.login.LoginTests;
import com.liferay.portalweb.portlet.blogs.BlogsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="PortalWebTestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalWebTestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
//		testSuite.addTest(AnnouncementsTests.suite());
//		testSuite.addTest(AssetPublisherTests.suite());
		testSuite.addTest(BlogsTests.suite());
/*		testSuite.addTest(BlogsAggregatorTests.suite());
		testSuite.addTest(BookmarksTests.suite());
		testSuite.addTest(BreadcrumbTests.suite());
		testSuite.addTest(CalendarTests.suite());
		testSuite.addTest(CurrencyConverterTests.suite());
		testSuite.addTest(DictionaryTests.suite());
		testSuite.addTest(DirectoryTests.suite());
		testSuite.addTest(DocumentLibraryTests.suite());
		testSuite.addTest(DocumentLibraryDisplayTests.suite());
		testSuite.addTest(HelloVelocityTests.suite());
		testSuite.addTest(HelloWorldTests.suite());
		testSuite.addTest(IFrameTests.suite());
		testSuite.addTest(ImageGalleryTests.suite());
		testSuite.addTest(InvitationTests.suite());
		//testSuite.addTest(LanguageTests.suite());
		testSuite.addTest(LoanCalculatorTests.suite());
		testSuite.addTest(ManagePagesTests.suite());
		testSuite.addTest(MessageBoardsTests.suite());
		testSuite.addTest(NavigationTests.suite());
		testSuite.addTest(NestedPortletsTests.suite());
		testSuite.addTest(NetworkUtilitiesTests.suite());
		//testSuite.addTest(OrganizationAdminTests.suite());
		testSuite.addTest(PageCommentsTests.suite());
		testSuite.addTest(PageRatingsTests.suite());
		testSuite.addTest(PasswordGeneratorTests.suite());
		//testSuite.addTest(PluginInstallerTests.suite());
		testSuite.addTest(PollsTests.suite());
		testSuite.addTest(PollsDisplayTests.suite());
		testSuite.addTest(QuickNoteTests.suite());
		testSuite.addTest(RecentBloggersTests.suite());
		testSuite.addTest(RecentDocumentsTests.suite());
		testSuite.addTest(ReverendFunTests.suite());
		testSuite.addTest(RSSTests.suite());
		testSuite.addTest(SearchTests.suite());
		testSuite.addTest(SessionExpirationTests.suite());
		testSuite.addTest(ShoppingTests.suite());
		testSuite.addTest(SiteMapTests.suite());
		testSuite.addTest(SMSTextMessengerTests.suite());
		testSuite.addTest(SoftwareCatalogTests.suite());
		testSuite.addTest(TagsAdminTests.suite());
		testSuite.addTest(TranslatorTests.suite());
		testSuite.addTest(UnitConverterTests.suite());
		testSuite.addTest(WebContentTests.suite());
		testSuite.addTest(WebContentDisplayTests.suite());
		testSuite.addTest(WebContentListTests.suite());
		testSuite.addTest(WebContentSearchTests.suite());
		testSuite.addTest(WebProxyTests.suite());
		testSuite.addTest(WikiTests.suite());
		testSuite.addTest(WikiDisplayTests.suite());
		testSuite.addTest(WordsTests.suite());
		testSuite.addTest(XSLContentTests.suite());
*/
		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}