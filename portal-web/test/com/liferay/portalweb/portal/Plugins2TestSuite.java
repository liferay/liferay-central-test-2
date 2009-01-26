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
import com.liferay.portalweb.portlet.worldofliferay.WorldOfLiferayTests;

/**
 * <a href="Plugins2TestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Plugins2TestSuite extends BaseTests {

	public Plugins2TestSuite() {
		addTestSuite(LoginTests.class);
		addTestSuite(SampleLocalizedTests.class);
		addTestSuite(SampleOrbeonFormsTests.class);
		addTestSuite(SamplePermissionsTests.class);
		addTestSuite(SamplePHPTests.class);
		addTestSuite(SamplePortalClientTests.class);
		addTestSuite(SamplePortalClientTests.class);
		addTestSuite(SamplePythonTests.class);
		addTestSuite(SampleRubyTests.class);
		addTestSuite(SampleServiceBuilderTests.class);
		addTestSuite(SampleSignInTests.class);
		addTestSuite(SampleSpringTests.class);
		addTestSuite(SampleStrutsTests.class);
		//addTestSuite(SampleStrutsLiferayTests.class);
		addTestSuite(SampleTapestryTests.class);
		addTestSuite(SampleTestTests.class);
		addTestSuite(SampleUITagLibsTests.class);
		addTestSuite(SampleWAPTests.class);
		addTestSuite(StocksTests.class);
		//addTestSuite(SSHTermTests.class);
		//addTestSuite(SSHVncTests.class);
		addTestSuite(SunBookmarkTests.class);
		addTestSuite(SunElluminateTests.class);
		addTestSuite(SunFlickrTests.class);
		addTestSuite(SunIFrameTests.class);
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
		addTestSuite(TwitterTests.class);
		addTestSuite(WeatherTests.class);
		//addTestSuite(WebcamTests.class);
		addTestSuite(WebFormTests.class);
		addTestSuite(WestminsterCatechismTests.class);
		addTestSuite(WorldOfLiferayTests.class);

		addTestSuite(StopSeleniumTest.class);
	}

}