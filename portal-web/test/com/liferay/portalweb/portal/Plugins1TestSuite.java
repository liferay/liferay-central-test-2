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
import com.liferay.portalweb.portlet.alfrescocontent.AlfrescoContentTests;
import com.liferay.portalweb.portlet.analogclock.AnalogClockTests;
import com.liferay.portalweb.portlet.chat.ChatTests;
import com.liferay.portalweb.portlet.flash.FlashTests;
import com.liferay.portalweb.portlet.googleadsense.GoogleAdSenseTests;
import com.liferay.portalweb.portlet.googlegadget.GoogleGadgetTests;
import com.liferay.portalweb.portlet.googlemaps.GoogleMapsTests;
import com.liferay.portalweb.portlet.googlesearch.GoogleSearchTests;
import com.liferay.portalweb.portlet.ipgeocoder.IPGeocoderTests;
import com.liferay.portalweb.portlet.mail.MailTests;
import com.liferay.portalweb.portlet.releasetools.ReleaseToolsTests;
import com.liferay.portalweb.portlet.rubyconsole.RubyConsoleTests;
import com.liferay.portalweb.portlet.sampledao.SampleDAOTests;
import com.liferay.portalweb.portlet.samplegroovy.SampleGroovyTests;
import com.liferay.portalweb.portlet.samplehibernate.SampleHibernateTests;
import com.liferay.portalweb.portlet.sampleicefacesipcajaxpush.SampleIcefacesIPCAjaxPushTests;
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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="Plugins1TestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Plugins1TestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(AlfrescoContentTests.suite());
		testSuite.addTest(AnalogClockTests.suite());
		//testSuite.addTest(ApplicationBuilderTests.suite());
		testSuite.addTest(ChatTests.suite());
		testSuite.addTest(FlashTests.suite());
		testSuite.addTest(GoogleAdSenseTests.suite());
		testSuite.addTest(GoogleGadgetTests.suite());
		testSuite.addTest(GoogleMapsTests.suite());
		testSuite.addTest(GoogleSearchTests.suite());
		testSuite.addTest(IPGeocoderTests.suite());
		testSuite.addTest(MailTests.suite());
		testSuite.addTest(ReleaseToolsTests.suite());
		testSuite.addTest(RubyConsoleTests.suite());
		testSuite.addTest(SampleDAOTests.suite());
		testSuite.addTest(SampleGroovyTests.suite());
		testSuite.addTest(SampleHibernateTests.suite());
		testSuite.addTest(SampleIcefacesIPCAjaxPushTests.suite());
		testSuite.addTest(SampleIcefacesJSF11SunFaceletsTests.suite());
		testSuite.addTest(SampleIcefacesJSF11SunJSPTests.suite());
		testSuite.addTest(SampleIcefacesJSF11SunMyfacesJSPTests.suite());
		testSuite.addTest(SampleIcefacesJSF12SunFaceletsTests.suite());
		testSuite.addTest(SampleJavascriptTests.suite());
		testSuite.addTest(SampleJSF11MyfacesFaceletsTests.suite());
		testSuite.addTest(SampleJSF11MyfacesJSPTests.suite());
		testSuite.addTest(SampleJSF11SunFaceletsTests.suite());
		testSuite.addTest(SampleJSF11SunJSPTests.suite());
		testSuite.addTest(SampleJSF12SunFaceletsTests.suite());
		testSuite.addTest(SampleJSF12SunJSPTests.suite());
		testSuite.addTest(SampleJSONTests.suite());
		testSuite.addTest(SampleJSPTests.suite());
		testSuite.addTest(SampleLARTests.suite());
		testSuite.addTest(SampleLaszloTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}