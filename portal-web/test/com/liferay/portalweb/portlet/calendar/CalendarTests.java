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

package com.liferay.portalweb.portlet.calendar;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="CalendarTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CalendarTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletTest.class);
		testSuite.addTestSuite(SetTimezoneTest.class);
		testSuite.addTestSuite(AddAppointmentEventTest.class);
		testSuite.addTestSuite(AddConcertEventTest.class);
		testSuite.addTestSuite(AddVacationEventTest.class);
		testSuite.addTestSuite(GetAppointmentEventsTest.class);
		testSuite.addTestSuite(GetConcertEventsTest.class);
		testSuite.addTestSuite(GetVacationEventsTest.class);
		testSuite.addTestSuite(EditEventTest.class);
		testSuite.addTestSuite(AddTemporaryEventTest.class);
		testSuite.addTestSuite(DeleteTemporaryEventTest.class);
		testSuite.addTestSuite(AssertConfigurationTest.class);
		testSuite.addTestSuite(AddNullTitleEventTest.class);
		testSuite.addTestSuite(AddInvalidStartDateEventTest.class);
		testSuite.addTestSuite(AddInvalidEndDateEventTest.class);
		testSuite.addTestSuite(AddInvalidDurationEventTest.class);
		testSuite.addTestSuite(AddInvalidRepeatEventTest.class);
		testSuite.addTestSuite(DeleteAllEventsTest.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(AddRepeatingEventTest.class);
		testSuite.addTestSuite(AddDailyRepeatingEventTest.class);
		testSuite.addTestSuite(AddWeeklyRepeatingEventTest.class);
		testSuite.addTestSuite(AddWeekDayRepeatingEventTest.class);
		testSuite.addTestSuite(AddMonthlyDateRepeatingEventTest.class);
		testSuite.addTestSuite(AddMonthlyDayRepeatingEventTest.class);
		testSuite.addTestSuite(AddYearlyDateRepeatingEventTest.class);
		testSuite.addTestSuite(AddYearlyDayRepeatingEventTest.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}