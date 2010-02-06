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

import com.liferay.portalweb.plugins.alfresco.AlfrescoTests;
import com.liferay.portalweb.plugins.alfrescocontent.AlfrescoContentTests;
import com.liferay.portalweb.plugins.analogclock.AnalogClockTests;
import com.liferay.portalweb.plugins.biblegateway.BibleGatewayTests;
import com.liferay.portalweb.plugins.chat.ChatTests;
import com.liferay.portalweb.plugins.flash.FlashTests;
import com.liferay.portalweb.plugins.googleadsense.GoogleAdSenseTests;
import com.liferay.portalweb.plugins.googlegadget.GoogleGadgetTests;
import com.liferay.portalweb.plugins.googlemaps.GoogleMapsTests;
import com.liferay.portalweb.plugins.gospelforasia.GospelForAsiaTests;
import com.liferay.portalweb.plugins.ipgeocoder.IPGeocoderTests;
import com.liferay.portalweb.plugins.journalpolice.JournalPoliceTests;
import com.liferay.portalweb.plugins.mail.MailTests;
import com.liferay.portalweb.plugins.novellcollaboration.NovellCollaborationTests;
import com.liferay.portalweb.plugins.randombibleverse.RandomBibleVerseTests;
import com.liferay.portalweb.plugins.releasetools.ReleaseToolsTests;
import com.liferay.portalweb.plugins.rubyconsole.RubyConsoleTests;
import com.liferay.portalweb.plugins.sampledao.SampleDAOTests;
import com.liferay.portalweb.plugins.samplegroovy.SampleGroovyTests;
import com.liferay.portalweb.plugins.samplehibernate.SampleHibernateTests;
import com.liferay.portalweb.plugins.sampleicefacesipcajaxpush.SampleIcefacesIPCAjaxPushTests;
import com.liferay.portalweb.plugins.sampleicefacesjsf11sunfacelets.SampleIcefacesJSF11SunFaceletsTests;
import com.liferay.portalweb.plugins.sampleicefacesjsf11sunjsp.SampleIcefacesJSF11SunJSPTests;
import com.liferay.portalweb.plugins.sampleicefacesjsf11sunmyfacesjsp.SampleIcefacesJSF11SunMyfacesJSPTests;
import com.liferay.portalweb.plugins.sampleicefacesjsf12sunfacelets.SampleIcefacesJSF12SunFaceletsTests;
import com.liferay.portalweb.plugins.samplejsf11myfacesfacelets.SampleJSF11MyfacesFaceletsTests;
import com.liferay.portalweb.plugins.samplejsf11myfacesjsp.SampleJSF11MyfacesJSPTests;
import com.liferay.portalweb.plugins.samplejsf11sunfacelets.SampleJSF11SunFaceletsTests;
import com.liferay.portalweb.plugins.samplejsf11sunjsp.SampleJSF11SunJSPTests;
import com.liferay.portalweb.plugins.samplejsf12sunfacelets.SampleJSF12SunFaceletsTests;
import com.liferay.portalweb.plugins.samplejsf12sunjsp.SampleJSF12SunJSPTests;
import com.liferay.portalweb.plugins.samplejson.SampleJSONTests;
import com.liferay.portalweb.plugins.samplejsp.SampleJSPTests;
import com.liferay.portalweb.plugins.samplelar.SampleLARTests;
import com.liferay.portalweb.plugins.samplelaszlo.SampleLaszloTests;
import com.liferay.portalweb.plugins.samplelocalized.SampleLocalizedTests;
import com.liferay.portalweb.portal.login.LoginTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="Plugins1TestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Plugins1TestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(AlfrescoTests.suite());
		testSuite.addTest(AlfrescoContentTests.suite());
		testSuite.addTest(AnalogClockTests.suite());
		testSuite.addTest(BibleGatewayTests.suite());
		testSuite.addTest(ChatTests.suite());
		testSuite.addTest(FlashTests.suite());
		testSuite.addTest(GoogleAdSenseTests.suite());
		testSuite.addTest(GoogleGadgetTests.suite());
		testSuite.addTest(GoogleMapsTests.suite());
		testSuite.addTest(GospelForAsiaTests.suite());
		testSuite.addTest(IPGeocoderTests.suite());
		testSuite.addTest(JournalPoliceTests.suite());
		testSuite.addTest(MailTests.suite());
		testSuite.addTest(NovellCollaborationTests.suite());
		testSuite.addTest(RandomBibleVerseTests.suite());
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
		testSuite.addTest(SampleLocalizedTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}