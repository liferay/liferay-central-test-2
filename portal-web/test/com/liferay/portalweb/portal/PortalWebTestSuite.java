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

package com.liferay.portalweb.portal;

import com.liferay.portalweb.portlet.admin.AdminTests;
import com.liferay.portalweb.portlet.blogs.BlogsTests;
import com.liferay.portalweb.portlet.communityannouncements.CommunityAnnouncementsTests;
import com.liferay.portalweb.portlet.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portlet.pagecomments.PageCommentsTests;
import com.liferay.portalweb.portlet.pageratings.PageRatingsTests;

/**
 * <a href="PortalWebTestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalWebTestSuite extends BaseTests {

	public PortalWebTestSuite() {

		addTestSuite(LoginTest.class);
		addTestSuite(AdminTests.class);
//		addTestSuite(AmazonRankingsTests.class);
//		addTestSuite(AnalogClockTests.class);
//		addTestSuite(BibleGatewayTests.class);
//		addTestSuite(BlogsTests.class);
//		addTestSuite(BlogsAggregatorTests.class);
//		addTestSuite(AssetPublisherTests.class);
//		addTestSuite(BookmarksTests.class);
//		addTestSuite(BreadcrumbTests.class);
//		addTestSuite(CalendarTests.class);
//		addTestSuite(ChatTests.class);
//		addTestSuite(CommunitiesTests.class);
		addTestSuite(CommunityAnnouncementsTests.class);
//		addTestSuite(CurrencyConverterTests.class);
//		addTestSuite(DictionaryTests.class);
//		addTestSuite(DirectoryTests.class);
//		addTestSuite(DocumentLibraryTests.class);
//		addTestSuite(DocumentLibraryDisplayTests.class);
//		addTestSuite(EnterpriseAdminTests.class);
//		addTestSuite(FlashTests.class);
//		addTestSuite(GlobalPrayerDigestTests.class);
//		addTestSuite(GospelForAsiaTests.class);
//		addTestSuite(HelloVelocityTests.class);
//		addTestSuite(HelloWorldTests.class);
//		addTestSuite(IFrameTests.class);
//		addTestSuite(ImageGalleryTests.class);
//		addTestSuite(InvitationTests.class);
//		addTestSuite(JournalTests.class);
//		addTestSuite(JournalArticlesTests.class);
//		addTestSuite(JournalContentTests.class);
//		addTestSuite(JournalContentSearchTests.class);
//		addTestSuite(LanguageTests.class);
//		addTestSuite(LoanCalculatorTests.class);
//		addTestSuite(MailTests.class);
		addTestSuite(MessageBoardsTests.class);
//		addTestSuite(NavigationTests.class);
//		addTestSuite(NestedPortletsTests.class);
//		addTestSuite(NetworkUtilitiesTests.class);
//		addTestSuite(OpenIDSignInTests.class);
//		addTestSuite(OrganizationAdminTests.class);
		addTestSuite(PageCommentsTests.class);
		addTestSuite(PageRatingsTests.class);
//		addTestSuite(PasswordGeneratorTests.class);
//		addTestSuite(PluginInstallerTests.class);
//		addTestSuite(PollsTests.class);
//		addTestSuite(PollsDisplayTests.class);
//		addTestSuite(QuickNoteTests.class);
//		addTestSuite(RandomBibleVerseTests.class);
//		addTestSuite(RecentBloggersTests.class);
//		addTestSuite(RecentDocumentsTests.class);
//		addTestSuite(ReverendFunTests.class);
//		addTestSuite(RSSTests.class);
//		addTestSuite(SearchTests.class);
//		addTestSuite(ShoppingTests.class);
//		addTestSuite(SiteMapTests.class);
//		addTestSuite(SMSTextMessengerTests.class);
//		addTestSuite(SoftwareCatalogTests.class);
//		addTestSuite(StocksTests.class);
//		addTestSuite(TagsAdminTests.class);
//		addTestSuite(TranslatorTests.class);
//		addTestSuite(TodayinChristianHistoryTests.class);
//		addTestSuite(UnitConverterTests.class);
//		addTestSuite(UpdateManagerTests.class);
//		addTestSuite(WeatherTests.class);
//		addTestSuite(WebFormTests.class);
//		addTestSuite(WebProxyTests.class);
//		addTestSuite(WikiTests.class);
//		addTestSuite(WikiDisplayTests.class);
//		addTestSuite(WordsTests.class);
//		addTestSuite(WSRPProxyTests.class);
//		addTestSuite(XSLContentTests.class);
	}

}