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
import com.liferay.portalweb.portlet.samplelocalized.SampleLocalizedTests;
import com.liferay.portalweb.portlet.sampleorbeonforms.SampleOrbeonFormsTests;
import com.liferay.portalweb.portlet.samplepermissions.SamplePermissionsTests;
import com.liferay.portalweb.portlet.samplephp.SamplePHPTests;
import com.liferay.portalweb.portlet.sampleportalclient.SamplePortalClientTests;
import com.liferay.portalweb.portlet.samplepython.SamplePythonTests;
import com.liferay.portalweb.portlet.sampleruby.SampleRubyTests;
import com.liferay.portalweb.portlet.sampleservicebuilder.SampleServiceBuilderTests;
import com.liferay.portalweb.portlet.samplesignin.SampleSignInTests;
import com.liferay.portalweb.portlet.samplespring.SampleSpringTests;
import com.liferay.portalweb.portlet.samplestruts.SampleStrutsTests;
import com.liferay.portalweb.portlet.sampletapestry.SampleTapestryTests;
import com.liferay.portalweb.portlet.sampletest.SampleTestTests;
import com.liferay.portalweb.portlet.sampleuitaglibs.SampleUITagLibsTests;
import com.liferay.portalweb.portlet.samplewap.SampleWAPTests;
import com.liferay.portalweb.portlet.stocks.StocksTests;
import com.liferay.portalweb.portlet.sunbookmark.SunBookmarkTests;
import com.liferay.portalweb.portlet.sunelluminate.SunElluminateTests;
import com.liferay.portalweb.portlet.sunflickr.SunFlickrTests;
import com.liferay.portalweb.portlet.suniframe.SunIFrameTests;
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
import com.liferay.portalweb.portlet.twitter.TwitterTests;
import com.liferay.portalweb.portlet.weather.WeatherTests;
import com.liferay.portalweb.portlet.webform.WebFormTests;
import com.liferay.portalweb.portlet.westminstercatechism.WestminsterCatechismTests;
import com.liferay.portalweb.portlet.wikinavigation.WikiNavigationTests;
import com.liferay.portalweb.portlet.wol.WOLTests;

/**
 * <a href="Plugins2TestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Plugins2TestSuite extends BaseTests {

	public Plugins2TestSuite() {
		addTests(LoginTests.class);
		addTests(SampleLocalizedTests.class);
		addTests(SampleOrbeonFormsTests.class);
		addTests(SamplePermissionsTests.class);
		addTests(SamplePHPTests.class);
		addTests(SamplePortalClientTests.class);
		addTests(SamplePortalClientTests.class);
		addTests(SamplePythonTests.class);
		addTests(SampleRubyTests.class);
		addTests(SampleServiceBuilderTests.class);
		addTests(SampleSignInTests.class);
		addTests(SampleSpringTests.class);
		addTests(SampleStrutsTests.class);
		//addTests(SampleStrutsLiferayTests.class);
		addTests(SampleTapestryTests.class);
		addTests(SampleTestTests.class);
		addTests(SampleUITagLibsTests.class);
		addTests(SampleWAPTests.class);
		addTests(StocksTests.class);
		addTests(SunBookmarkTests.class);
		addTests(SunElluminateTests.class);
		addTests(SunFlickrTests.class);
		addTests(SunIFrameTests.class);
		addTests(SunMashupTests.class);
		addTests(SunNotepadTests.class);
		addTests(SunPhotoShowAjaxTests.class);
		addTests(SunPrivacyGuardTests.class);
		addTests(SunRSSTests.class);
		addTests(SunShowTimeTests.class);
		addTests(SunSingleVideoTests.class);
		addTests(SunTourDetailsTests.class);
		addTests(SunTourListingTests.class);
		addTests(SunTourMapTests.class);
		addTests(SunTourWeatherTests.class);
		addTests(SunYoutubeTests.class);
		addTests(TwitterTests.class);
		addTests(WeatherTests.class);
		addTests(WebFormTests.class);
		addTests(WestminsterCatechismTests.class);
		addTests(WikiNavigationTests.class);
		addTests(WOLTests.class);

		addTestSuite(StopSeleniumTest.class);
	}

}