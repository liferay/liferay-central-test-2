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
import com.liferay.portalweb.portal.session.SessionExpirationTests;
import com.liferay.portalweb.portlet.announcements.AnnouncementsTests;
import com.liferay.portalweb.portlet.assetpublisher.AssetPublisherTests;
import com.liferay.portalweb.portlet.biblegateway.BibleGatewayTests;
import com.liferay.portalweb.portlet.blogs.BlogsTests;
import com.liferay.portalweb.portlet.blogsaggregator.BlogsAggregatorTests;
import com.liferay.portalweb.portlet.bookmarks.BookmarksTests;
import com.liferay.portalweb.portlet.breadcrumb.BreadcrumbTests;
import com.liferay.portalweb.portlet.calendar.CalendarTests;
import com.liferay.portalweb.portlet.currencyconverter.CurrencyConverterTests;
import com.liferay.portalweb.portlet.dictionary.DictionaryTests;
import com.liferay.portalweb.portlet.directory.DirectoryTests;
import com.liferay.portalweb.portlet.documentlibrary.DocumentLibraryTests;
import com.liferay.portalweb.portlet.documentlibrarydisplay.DocumentLibraryDisplayTests;
import com.liferay.portalweb.portlet.globalprayerdigest.GlobalPrayerDigestTests;
import com.liferay.portalweb.portlet.gospelforasia.GospelForAsiaTests;
import com.liferay.portalweb.portlet.hellovelocity.HelloVelocityTests;
import com.liferay.portalweb.portlet.helloworld.HelloWorldTests;
import com.liferay.portalweb.portlet.iframe.IFrameTests;
import com.liferay.portalweb.portlet.imagegallery.ImageGalleryTests;
import com.liferay.portalweb.portlet.invitation.InvitationTests;
import com.liferay.portalweb.portlet.loancalculator.LoanCalculatorTests;
import com.liferay.portalweb.portlet.managepages.ManagePagesTests;
import com.liferay.portalweb.portlet.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portlet.navigation.NavigationTests;
import com.liferay.portalweb.portlet.nestedportlets.NestedPortletsTests;
import com.liferay.portalweb.portlet.networkutilities.NetworkUtilitiesTests;
import com.liferay.portalweb.portlet.pagecomments.PageCommentsTests;
import com.liferay.portalweb.portlet.pageratings.PageRatingsTests;
import com.liferay.portalweb.portlet.passwordgenerator.PasswordGeneratorTests;
import com.liferay.portalweb.portlet.polls.PollsTests;
import com.liferay.portalweb.portlet.pollsdisplay.PollsDisplayTests;
import com.liferay.portalweb.portlet.quicknote.QuickNoteTests;
import com.liferay.portalweb.portlet.recentbloggers.RecentBloggersTests;
import com.liferay.portalweb.portlet.recentdocuments.RecentDocumentsTests;
import com.liferay.portalweb.portlet.reverendfun.ReverendFunTests;
import com.liferay.portalweb.portlet.rss.RSSTests;
import com.liferay.portalweb.portlet.search.SearchTests;
import com.liferay.portalweb.portlet.shopping.ShoppingTests;
import com.liferay.portalweb.portlet.sitemap.SiteMapTests;
import com.liferay.portalweb.portlet.smstextmessenger.SMSTextMessengerTests;
import com.liferay.portalweb.portlet.softwarecatalog.SoftwareCatalogTests;
import com.liferay.portalweb.portlet.tagsadmin.TagsAdminTests;
import com.liferay.portalweb.portlet.translator.TranslatorTests;
import com.liferay.portalweb.portlet.unitconverter.UnitConverterTests;
import com.liferay.portalweb.portlet.updatemanager.UpdateManagerTests;
import com.liferay.portalweb.portlet.webcontent.WebContentTests;
import com.liferay.portalweb.portlet.webcontentdisplay.WebContentDisplayTests;
import com.liferay.portalweb.portlet.webcontentlist.WebContentListTests;
import com.liferay.portalweb.portlet.webcontentsearch.WebContentSearchTests;
import com.liferay.portalweb.portlet.webproxy.WebProxyTests;
import com.liferay.portalweb.portlet.wiki.WikiTests;
import com.liferay.portalweb.portlet.wikidisplay.WikiDisplayTests;
import com.liferay.portalweb.portlet.words.WordsTests;
import com.liferay.portalweb.portlet.xslcontent.XSLContentTests;

/**
 * <a href="PortalWebTestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalWebTestSuite extends BaseTests {

	public PortalWebTestSuite() {
		addTests(LoginTests.class);
		addTests(AnnouncementsTests.class);
		addTests(AssetPublisherTests.class);
		addTests(BibleGatewayTests.class);
		addTests(BlogsTests.class);
		addTests(BlogsAggregatorTests.class);
		addTests(BookmarksTests.class);
		addTests(BreadcrumbTests.class);
		addTests(CalendarTests.class);
		addTests(CurrencyConverterTests.class);
		addTests(DictionaryTests.class);
		addTests(DirectoryTests.class);
		addTests(DocumentLibraryTests.class);
		addTests(DocumentLibraryDisplayTests.class);
		addTests(GlobalPrayerDigestTests.class);
		addTests(GospelForAsiaTests.class);
		addTests(HelloVelocityTests.class);
		addTests(HelloWorldTests.class);
		addTests(IFrameTests.class);
		addTests(ImageGalleryTests.class);
		addTests(InvitationTests.class);
		//addTests(LanguageTests.class);
		addTests(LoanCalculatorTests.class);
		addTests(ManagePagesTests.class);
		addTests(MessageBoardsTests.class);
		addTests(NavigationTests.class);
		addTests(NestedPortletsTests.class);
		addTests(NetworkUtilitiesTests.class);
		//addTests(OrganizationAdminTests.class);
		addTests(PageCommentsTests.class);
		addTests(PageRatingsTests.class);
		addTests(PasswordGeneratorTests.class);
		//addTests(PluginInstallerTests.class);
		addTests(PollsTests.class);
		addTests(PollsDisplayTests.class);
		addTests(QuickNoteTests.class);
		//addTests(RandomBibleVerseTests.class);
		addTests(RecentBloggersTests.class);
		addTests(RecentDocumentsTests.class);
		addTests(ReverendFunTests.class);
		addTests(RSSTests.class);
		addTests(SearchTests.class);
		addTests(SessionExpirationTests.class);
		addTests(ShoppingTests.class);
		addTests(SiteMapTests.class);
		addTests(SMSTextMessengerTests.class);
		addTests(SoftwareCatalogTests.class);
		//addTests(StagingTests.class);
		addTests(TagsAdminTests.class);
		addTests(TranslatorTests.class);
		addTests(UnitConverterTests.class);
		addTests(UpdateManagerTests.class);
		addTests(WebContentTests.class);
		addTests(WebContentDisplayTests.class);
		addTests(WebContentListTests.class);
		addTests(WebContentSearchTests.class);
		addTests(WebProxyTests.class);
		addTests(WikiTests.class);
		addTests(WikiDisplayTests.class);
		addTests(WordsTests.class);
		addTests(XSLContentTests.class);

		addTestSuite(StopSeleniumTest.class);
	}

}