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

import com.liferay.portalweb.portal.login.LoginTests;
import com.liferay.portalweb.portal.session.SessionExpirationTests;
import com.liferay.portalweb.portlet.admin.AdminTests;
import com.liferay.portalweb.portlet.alfrescocontent.AlfrescoContentTests;
import com.liferay.portalweb.portlet.amazonrankings.AmazonRankingsTests;
import com.liferay.portalweb.portlet.analogclock.AnalogClockTests;
import com.liferay.portalweb.portlet.announcements.AnnouncementsTests;
import com.liferay.portalweb.portlet.applicationbuilder.ApplicationBuilderTests;
import com.liferay.portalweb.portlet.assetpublisher.AssetPublisherTests;
import com.liferay.portalweb.portlet.biblegateway.BibleGatewayTests;
import com.liferay.portalweb.portlet.blogs.BlogsTests;
import com.liferay.portalweb.portlet.blogsaggregator.BlogsAggregatorTests;
import com.liferay.portalweb.portlet.bookmarks.BookmarksTests;
import com.liferay.portalweb.portlet.breadcrumb.BreadcrumbTests;
import com.liferay.portalweb.portlet.calendar.CalendarTests;
import com.liferay.portalweb.portlet.chat.ChatTests;
import com.liferay.portalweb.portlet.communities.CommunitiesTests;
import com.liferay.portalweb.portlet.currencyconverter.CurrencyConverterTests;
import com.liferay.portalweb.portlet.dictionary.DictionaryTests;
import com.liferay.portalweb.portlet.directory.DirectoryTests;
import com.liferay.portalweb.portlet.documentlibrary.DocumentLibraryTests;
import com.liferay.portalweb.portlet.documentlibrarydisplay.DocumentLibraryDisplayTests;
import com.liferay.portalweb.portlet.enterpriseadmin.EnterpriseAdminTests;
import com.liferay.portalweb.portlet.flash.FlashTests;
import com.liferay.portalweb.portlet.globalprayerdigest.GlobalPrayerDigestTests;
import com.liferay.portalweb.portlet.googleadsense.GoogleAdSenseTests;
import com.liferay.portalweb.portlet.googlegadget.GoogleGadgetTests;
import com.liferay.portalweb.portlet.googlemaps.GoogleMapsTests;
import com.liferay.portalweb.portlet.googlesearch.GoogleSearchTests;
import com.liferay.portalweb.portlet.gospelforasia.GospelForAsiaTests;
import com.liferay.portalweb.portlet.hellovelocity.HelloVelocityTests;
import com.liferay.portalweb.portlet.helloworld.HelloWorldTests;
import com.liferay.portalweb.portlet.iframe.IFrameTests;
import com.liferay.portalweb.portlet.imagegallery.ImageGalleryTests;
import com.liferay.portalweb.portlet.invitation.InvitationTests;
import com.liferay.portalweb.portlet.journal.JournalTests;
import com.liferay.portalweb.portlet.journalarticles.JournalArticlesTests;
import com.liferay.portalweb.portlet.journalcontent.JournalContentTests;
import com.liferay.portalweb.portlet.journalcontentsearch.JournalContentSearchTests;
import com.liferay.portalweb.portlet.language.LanguageTests;
import com.liferay.portalweb.portlet.loancalculator.LoanCalculatorTests;
import com.liferay.portalweb.portlet.mail.MailTests;
import com.liferay.portalweb.portlet.managepages.ManagePagesTests;
import com.liferay.portalweb.portlet.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portlet.navigation.NavigationTests;
import com.liferay.portalweb.portlet.nestedportlets.NestedPortletsTests;
import com.liferay.portalweb.portlet.networkutilities.NetworkUtilitiesTests;
import com.liferay.portalweb.portlet.openidsignin.OpenIDSignInTests;
import com.liferay.portalweb.portlet.organizationadmin.OrganizationAdminTests;
import com.liferay.portalweb.portlet.pagecomments.PageCommentsTests;
import com.liferay.portalweb.portlet.pageratings.PageRatingsTests;
import com.liferay.portalweb.portlet.passwordgenerator.PasswordGeneratorTests;
import com.liferay.portalweb.portlet.quicknote.QuickNoteTests;
import com.liferay.portalweb.portlet.randombibleverse.RandomBibleVerseTests;
import com.liferay.portalweb.portlet.recentbloggers.RecentBloggersTests;
import com.liferay.portalweb.portlet.recentdocuments.RecentDocumentsTests;
import com.liferay.portalweb.portlet.releasetools.ReleaseToolsTests;
import com.liferay.portalweb.portlet.reverendfun.ReverendFunTests;
import com.liferay.portalweb.portlet.rss.RSSTests;
import com.liferay.portalweb.portlet.rubyconsole.RubyConsoleTests;
import com.liferay.portalweb.portlet.sampledao.SampleDAOTests;
import com.liferay.portalweb.portlet.samplegroovy.SampleGroovyTests;
import com.liferay.portalweb.portlet.samplehibernate.SampleHibernateTests;
import com.liferay.portalweb.portlet.sampleicefacesjsf11sunfacelets.SampleIcefacesJSF11SunFaceletsTests;
import com.liferay.portalweb.portlet.sampleicefacesjsf11sunjsp.SampleIcefacesJSF11SunJSPTests;
import com.liferay.portalweb.portlet.sampleicefacesjsf11sunmyfacesjsp.SampleIcefacesJSF11SunMyfacesJSPTests;
import com.liferay.portalweb.portlet.sampleicefacesjsf12sunfacelets.SampleIcefacesJSF12SunFaceletsTests;
import com.liferay.portalweb.portlet.samplejavascript.SampleJavascriptTests;
import com.liferay.portalweb.portlet.samplejsf11myfacesfacelets.SampleJSF11MyfacesFaceletsTests;
import com.liferay.portalweb.portlet.samplejsf11myfacesjsp.SampleJSF11MyfacesJSPTests;
import com.liferay.portalweb.portlet.samplejsf11sunfacelets.SampleJSF11SunFaceletsTests;
import com.liferay.portalweb.portlet.samplejsf11sunjsp.SampleJSF11SunJSPTests;
import com.liferay.portalweb.portlet.samplejsf12sunfacelets.SampleJSF12SunFaceletsTests;
import com.liferay.portalweb.portlet.samplejsf12sunjsp.SampleJSF12SunJSPTests;
import com.liferay.portalweb.portlet.samplejson.SampleJSONTests;
import com.liferay.portalweb.portlet.samplejsp.SampleJSPTests;
import com.liferay.portalweb.portlet.samplelar.SampleLARTests;
import com.liferay.portalweb.portlet.samplelaszlo.SampleLaszloTests;
import com.liferay.portalweb.portlet.samplelocalized.SampleLocalizedTests;
import com.liferay.portalweb.portlet.sampleorbeonforms.SampleOrbeonFormsTests;
import com.liferay.portalweb.portlet.samplepermissions.SamplePermissionsTests;
import com.liferay.portalweb.portlet.samplephp.SamplePHPTests;
import com.liferay.portalweb.portlet.sampleportalclient.SamplePortalClientTests;
import com.liferay.portalweb.portlet.sampleportalservice.SamplePortalServiceTests;
import com.liferay.portalweb.portlet.samplepython.SamplePythonTests;
import com.liferay.portalweb.portlet.sampleruby.SampleRubyTests;
import com.liferay.portalweb.portlet.samplesignin.SampleSignInTests;
import com.liferay.portalweb.portlet.samplespring.SampleSpringTests;
import com.liferay.portalweb.portlet.samplestruts.SampleStrutsTests;
import com.liferay.portalweb.portlet.sampletapestry.SampleTapestryTests;
import com.liferay.portalweb.portlet.sampletest.SampleTestTests;
import com.liferay.portalweb.portlet.sampleuitaglibs.SampleUITagLibsTests;
import com.liferay.portalweb.portlet.samplewap.SampleWAPTests;
import com.liferay.portalweb.portlet.search.SearchTests;
import com.liferay.portalweb.portlet.shopping.ShoppingTests;
import com.liferay.portalweb.portlet.sitemap.SiteMapTests;
import com.liferay.portalweb.portlet.smstextmessenger.SMSTextMessengerTests;
import com.liferay.portalweb.portlet.softwarecatalog.SoftwareCatalogTests;
import com.liferay.portalweb.portlet.staging.StagingTests;
import com.liferay.portalweb.portlet.stocks.StocksTests;
import com.liferay.portalweb.portlet.sunbookmark.SunBookmarkTests;
import com.liferay.portalweb.portlet.sunelluminate.SunElluminateTests;
import com.liferay.portalweb.portlet.suniframe.SunIFrameTests;
import com.liferay.portalweb.portlet.sunflickr.SunFlickrTests;
import com.liferay.portalweb.portlet.sunmashup.SunMashupTests;
import com.liferay.portalweb.portlet.sunnotepad.SunNotepadTests;
import com.liferay.portalweb.portlet.sunphotoshowajax.SunPhotoShowAjaxTests;
import com.liferay.portalweb.portlet.sunprivacyguard.SunPrivacyGuardTests;
import com.liferay.portalweb.portlet.sunrss.SunRSSTests;
import com.liferay.portalweb.portlet.sunshowtime.SunShowTimeTests;
import com.liferay.portalweb.portlet.sunsinglevideo.SunSingleVideoTests;
import com.liferay.portalweb.portlet.suntourdetails.SunTourDetailsTests;
import com.liferay.portalweb.portlet.suntourlisting.SunTourListingTests;
import com.liferay.portalweb.portlet.suntourmap.SunTourMapTests;
import com.liferay.portalweb.portlet.suntourweather.SunTourWeatherTests;
import com.liferay.portalweb.portlet.sunyoutube.SunYoutubeTests;									
import com.liferay.portalweb.portlet.tagsadmin.TagsAdminTests;
import com.liferay.portalweb.portlet.translator.TranslatorTests;
import com.liferay.portalweb.portlet.twitter.TwitterTests;
import com.liferay.portalweb.portlet.unitconverter.UnitConverterTests;
import com.liferay.portalweb.portlet.updatemanager.UpdateManagerTests;
import com.liferay.portalweb.portlet.weather.WeatherTests;
import com.liferay.portalweb.portlet.webform.WebFormTests;
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
		addTestSuite(LoginTests.class);
		addTestSuite(EnterpriseAdminTests.class);
		addTestSuite(AdminTests.class);
		addTestSuite(AlfrescoContentTests.class);
		addTestSuite(AmazonRankingsTests.class);
		addTestSuite(AnalogClockTests.class);
		addTestSuite(AnnouncementsTests.class);
		addTestSuite(ApplicationBuilderTests.class);
		addTestSuite(BibleGatewayTests.class);
		addTestSuite(BlogsTests.class);
		addTestSuite(BlogsAggregatorTests.class);
		addTestSuite(AssetPublisherTests.class);
		addTestSuite(BookmarksTests.class);
		addTestSuite(BreadcrumbTests.class);
		addTestSuite(CalendarTests.class);
		addTestSuite(ChatTests.class);
		addTestSuite(CommunitiesTests.class);
		addTestSuite(CurrencyConverterTests.class);
		addTestSuite(DictionaryTests.class);
		addTestSuite(DirectoryTests.class);
		//addTestSuite(DocumentLibraryTests.class);
		//addTestSuite(DocumentLibraryDisplayTests.class);
		addTestSuite(FlashTests.class);
		addTestSuite(GlobalPrayerDigestTests.class);
		addTestSuite(GospelForAsiaTests.class);
		addTestSuite(GoogleAdSenseTests.class);
		addTestSuite(GoogleGadgetTests.class);
		addTestSuite(GoogleMapsTests.class);
		addTestSuite(GoogleSearchTests.class);
		addTestSuite(HelloVelocityTests.class);
		addTestSuite(HelloWorldTests.class);
		addTestSuite(IFrameTests.class);
		addTestSuite(ImageGalleryTests.class);
		addTestSuite(InvitationTests.class);
		addTestSuite(JournalTests.class);
		addTestSuite(JournalArticlesTests.class);
		addTestSuite(JournalContentTests.class);
		addTestSuite(JournalContentSearchTests.class);
		addTestSuite(LanguageTests.class);
		addTestSuite(LoanCalculatorTests.class);
		addTestSuite(MailTests.class);
		addTestSuite(ManagePagesTests.class);
		addTestSuite(MessageBoardsTests.class);
		addTestSuite(NavigationTests.class);
		addTestSuite(NestedPortletsTests.class);
		addTestSuite(NetworkUtilitiesTests.class);
		//addTestSuite(NovellCollaborationTests.class);
		addTestSuite(OpenIDSignInTests.class);
		addTestSuite(OrganizationAdminTests.class);
		addTestSuite(PageCommentsTests.class);
		addTestSuite(PageRatingsTests.class);
		addTestSuite(PasswordGeneratorTests.class);
		//addTestSuite(PluginInstallerTests.class);
		//addTestSuite(PollsTests.class);
		//addTestSuite(PollsDisplayTests.class);
		addTestSuite(QuickNoteTests.class);
		addTestSuite(RandomBibleVerseTests.class);
		addTestSuite(RecentBloggersTests.class);
		addTestSuite(RecentDocumentsTests.class);
		addTestSuite(ReleaseToolsTests.class);
		addTestSuite(ReverendFunTests.class);
		addTestSuite(RubyConsoleTests.class);
		addTestSuite(RSSTests.class);
		addTestSuite(SampleDAOTests.class);
		addTestSuite(SampleSignInTests.class);
		addTestSuite(SampleGroovyTests.class);
		addTestSuite(SampleHibernateTests.class);
		addTestSuite(SampleIcefacesJSF11SunJSPTests.class);
		addTestSuite(SampleIcefacesJSF11SunFaceletsTests.class);
		addTestSuite(SampleIcefacesJSF12SunFaceletsTests.class);
		addTestSuite(SampleIcefacesJSF11SunMyfacesJSPTests.class);
		addTestSuite(SampleJavascriptTests.class);
		addTestSuite(SampleJSF12SunJSPTests.class);
		addTestSuite(SampleJSF11SunJSPTests.class);
		addTestSuite(SampleJSF11SunFaceletsTests.class);
		addTestSuite(SampleJSF11MyfacesJSPTests.class);
		addTestSuite(SampleJSF11MyfacesFaceletsTests.class);
		addTestSuite(SampleJSF12SunFaceletsTests.class);
		addTestSuite(SampleLARTests.class);
		addTestSuite(SampleLaszloTests.class);
		addTestSuite(SampleLocalizedTests.class);
		addTestSuite(SampleJSONTests.class);
		addTestSuite(SampleJSPTests.class);
		addTestSuite(SampleOrbeonFormsTests.class);
		addTestSuite(SamplePermissionsTests.class);
		addTestSuite(SamplePHPTests.class);
		addTestSuite(SamplePortalClientTests.class);
		addTestSuite(SamplePortalServiceTests.class);
		addTestSuite(SamplePythonTests.class);
		addTestSuite(SampleRubyTests.class);
		//addTestSuite(SampleServiceBuilderTests.class);
		addTestSuite(SampleSpringTests.class);
		addTestSuite(SampleStrutsTests.class);
		//addTestSuite(SampleStrutsLiferayTests.class);
		addTestSuite(SampleTapestryTests.class);
		addTestSuite(SampleTestTests.class);
		addTestSuite(SampleUITagLibsTests.class);
		addTestSuite(SampleWAPTests.class);
		addTestSuite(SearchTests.class);
		addTestSuite(SessionExpirationTests.class);
		addTestSuite(ShoppingTests.class);
		addTestSuite(SiteMapTests.class);
		addTestSuite(SMSTextMessengerTests.class);
		addTestSuite(SoftwareCatalogTests.class);
		//addTestSuite(SSHTermTests.class);
		//addTestSuite(SSHVncTests.class);
		//addTestSuite(StagingTests.class);
		addTestSuite(StocksTests.class);
		addTestSuite(SunBookmarkTests.class);
		addTestSuite(SunElluminateTests.class);
		addTestSuite(SunIFrameTests.class);
		addTestSuite(SunFlickrTests.class);
		addTestSuite(SunMashupTests.class);
		addTestSuite(SunNotepadTests.class);
		addTestSuite(SunPhotoShowAjaxTests.class);
		addTestSuite(SunPrivacyGuardTests.class);
		addTestSuite(SunRSSTests.class);
		addTestSuite(SunShowTimeTests.class);
		addTestSuite(SunSingleVideoTests.class);
		addTestSuite(SunTourDetailsTests.class);
		addTestSuite(SunTourListingTests.class);
		addTestSuite(SunTourMapTests.class);
		addTestSuite(SunTourWeatherTests.class);
		addTestSuite(SunYoutubeTests.class);
		addTestSuite(TagsAdminTests.class);
		//addTestSuite(TodayinChristianHistoryTests.class);
		addTestSuite(TranslatorTests.class);
		addTestSuite(TwitterTests.class);
		addTestSuite(UnitConverterTests.class);
		addTestSuite(UpdateManagerTests.class);
		addTestSuite(WeatherTests.class);
		//addTestSuite(WebcamTests.class);
		addTestSuite(WebFormTests.class);
		addTestSuite(WebProxyTests.class);
		addTestSuite(WikiTests.class);
		addTestSuite(WikiDisplayTests.class);
		addTestSuite(WordsTests.class);
		addTestSuite(XSLContentTests.class);

		addTestSuite(StopSeleniumTest.class);
	}

}