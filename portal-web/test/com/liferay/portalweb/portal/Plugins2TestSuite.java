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
import com.liferay.portalweb.portlet.samplesearchcontainer.SampleSearchContainerTests;
import com.liferay.portalweb.portlet.sampleservicebuilder.SampleServiceBuilderTests;
import com.liferay.portalweb.portlet.samplesignin.SampleSignInTests;
import com.liferay.portalweb.portlet.samplespring.SampleSpringTests;
import com.liferay.portalweb.portlet.samplestruts.SampleStrutsTests;
import com.liferay.portalweb.portlet.sampletapestry.SampleTapestryTests;
import com.liferay.portalweb.portlet.sampletest.SampleTestTests;
import com.liferay.portalweb.portlet.sampletestdependency.SampleTestDependencyTests;
import com.liferay.portalweb.portlet.sampletesthook.SampleTestHookTests;
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
import com.liferay.portalweb.portlet.widgetconsumer.WidgetConsumerTests;
import com.liferay.portalweb.portlet.wikinavigation.WikiNavigationTests;
import com.liferay.portalweb.portlet.wol.WOLTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="Plugins2TestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Plugins2TestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(SampleLocalizedTests.suite());
		testSuite.addTest(SampleOrbeonFormsTests.suite());
		testSuite.addTest(SamplePermissionsTests.suite());
		testSuite.addTest(SamplePHPTests.suite());
		testSuite.addTest(SamplePortalClientTests.suite());
		testSuite.addTest(SamplePortalClientTests.suite());
		testSuite.addTest(SamplePythonTests.suite());
		testSuite.addTest(SampleRubyTests.suite());
		testSuite.addTest(SampleSearchContainerTests.suite());
		testSuite.addTest(SampleServiceBuilderTests.suite());
		testSuite.addTest(SampleSignInTests.suite());
		testSuite.addTest(SampleSpringTests.suite());
		testSuite.addTest(SampleStrutsTests.suite());
		//testSuite.addTest(SampleStrutsLiferayTests.suite());
		testSuite.addTest(SampleTapestryTests.suite());
		testSuite.addTest(SampleTestTests.suite());
		testSuite.addTest(SampleTestDependencyTests.suite());
		testSuite.addTest(SampleTestHookTests.suite());
		testSuite.addTest(SampleUITagLibsTests.suite());
		testSuite.addTest(SampleWAPTests.suite());
		testSuite.addTest(StocksTests.suite());
		testSuite.addTest(SunBookmarkTests.suite());
		testSuite.addTest(SunElluminateTests.suite());
		testSuite.addTest(SunFlickrTests.suite());
		testSuite.addTest(SunIFrameTests.suite());
		testSuite.addTest(SunMashupTests.suite());
		testSuite.addTest(SunNotepadTests.suite());
		testSuite.addTest(SunPhotoShowAjaxTests.suite());
		testSuite.addTest(SunPrivacyGuardTests.suite());
		testSuite.addTest(SunRSSTests.suite());
		testSuite.addTest(SunShowTimeTests.suite());
		testSuite.addTest(SunSingleVideoTests.suite());
		testSuite.addTest(SunTourDetailsTests.suite());
		testSuite.addTest(SunTourListingTests.suite());
		testSuite.addTest(SunTourMapTests.suite());
		testSuite.addTest(SunTourWeatherTests.suite());
		testSuite.addTest(SunYoutubeTests.suite());
		testSuite.addTest(TwitterTests.suite());
		testSuite.addTest(WeatherTests.suite());
		testSuite.addTest(WebFormTests.suite());
		testSuite.addTest(WestminsterCatechismTests.suite());
		testSuite.addTest(WidgetConsumerTests.suite());
		testSuite.addTest(WikiNavigationTests.suite());
		testSuite.addTest(WOLTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}