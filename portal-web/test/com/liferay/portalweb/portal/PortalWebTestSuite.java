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

import com.liferay.portalweb.portlet.blogs.BlogsTests;
import com.liferay.portalweb.portlet.bookmarks.BookmarksTests;
import com.liferay.portalweb.portlet.calendar.CalendarTests;
import com.liferay.portalweb.portlet.currencyconverter.CurrencyConverterTests;
import com.liferay.portalweb.portlet.documentlibrary.DocumentLibraryTests;
import com.liferay.portalweb.portlet.imagegallery.ImageGalleryTests;
import com.liferay.portalweb.portlet.loancalculator.LoanCalculatorTests;
import com.liferay.portalweb.portlet.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portlet.polls.PollsTests;
import com.liferay.portalweb.portlet.sitemap.SiteMapTests;
import com.liferay.portalweb.portlet.stocks.StocksTests;
import com.liferay.portalweb.portlet.weather.WeatherTests;

/**
 * <a href="PortalWebTestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalWebTestSuite extends BaseTests {

	public PortalWebTestSuite() {
		addTestSuite(LoginTest.class);
		addTestSuite(BlogsTests.class);
		addTestSuite(BookmarksTests.class);
		addTestSuite(CalendarTests.class);
		addTestSuite(CurrencyConverterTests.class);
		addTestSuite(DocumentLibraryTests.class);
		addTestSuite(ImageGalleryTests.class);
		addTestSuite(LoanCalculatorTests.class);
		addTestSuite(MessageBoardsTests.class);
		addTestSuite(PollsTests.class);
		addTestSuite(SiteMapTests.class);
		addTestSuite(StocksTests.class);
		addTestSuite(WeatherTests.class);
	}

}