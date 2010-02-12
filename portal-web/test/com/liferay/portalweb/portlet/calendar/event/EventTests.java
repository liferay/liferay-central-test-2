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

package com.liferay.portalweb.portlet.calendar.event;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.calendar.event.addevent.AddEventTests;
import com.liferay.portalweb.portlet.calendar.event.addeventdateendinvalid.AddEventDateEndInvalidTests;
import com.liferay.portalweb.portlet.calendar.event.addeventdaterepeatinvalid.AddEventDateRepeatInvalidTests;
import com.liferay.portalweb.portlet.calendar.event.addeventdatestartinvalid.AddEventDateStartInvalidTests;
import com.liferay.portalweb.portlet.calendar.event.addeventdurationinvalid.AddEventDurationInvalidTests;
import com.liferay.portalweb.portlet.calendar.event.addeventrepeatingdaily.AddEventRepeatingDailyTests;
import com.liferay.portalweb.portlet.calendar.event.addeventrepeatingdailyweekday.AddEventRepeatingDailyWeekDayTests;
import com.liferay.portalweb.portlet.calendar.event.addeventrepeatingmonthlydate.AddEventRepeatingMonthlyDateTests;
import com.liferay.portalweb.portlet.calendar.event.addeventrepeatingmonthlyday.AddEventRepeatingMonthlyDayTests;
import com.liferay.portalweb.portlet.calendar.event.addeventrepeatingweekly.AddEventRepeatingWeeklyTests;
import com.liferay.portalweb.portlet.calendar.event.addeventrepeatingyearlydate.AddEventRepeatingYearlyDateTests;
import com.liferay.portalweb.portlet.calendar.event.addeventrepeatingyearlyday.AddEventRepeatingYearlyDayTests;
import com.liferay.portalweb.portlet.calendar.event.addeventtitlenull.AddEventTitleNullTests;
import com.liferay.portalweb.portlet.calendar.event.addeventtypeappointment.AddEventTypeAppointmentTests;
import com.liferay.portalweb.portlet.calendar.event.addeventtypeconcert.AddEventTypeConcertTests;
import com.liferay.portalweb.portlet.calendar.event.addeventtypevacation.AddEventTypeVacationTests;
import com.liferay.portalweb.portlet.calendar.event.deleteevent.DeleteEventTests;
import com.liferay.portalweb.portlet.calendar.event.editevent.EditEventTests;
import com.liferay.portalweb.portlet.calendar.event.vieweventtypeappointment.ViewEventTypeAppointmentTests;
import com.liferay.portalweb.portlet.calendar.event.vieweventtypeconcert.ViewEventTypeConcertTests;
import com.liferay.portalweb.portlet.calendar.event.vieweventtypevacation.ViewEventTypeVacationTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="EventTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EventTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddEventTests.suite());
		testSuite.addTest(AddEventDateEndInvalidTests.suite());
		testSuite.addTest(AddEventDateRepeatInvalidTests.suite());
		testSuite.addTest(AddEventDateStartInvalidTests.suite());
		testSuite.addTest(AddEventDurationInvalidTests.suite());
		testSuite.addTest(AddEventRepeatingDailyTests.suite());
		testSuite.addTest(AddEventRepeatingDailyWeekDayTests.suite());
		testSuite.addTest(AddEventRepeatingMonthlyDateTests.suite());
		testSuite.addTest(AddEventRepeatingMonthlyDayTests.suite());
		testSuite.addTest(AddEventRepeatingWeeklyTests.suite());
		testSuite.addTest(AddEventRepeatingYearlyDateTests.suite());
		testSuite.addTest(AddEventRepeatingYearlyDayTests.suite());
		testSuite.addTest(AddEventTitleNullTests.suite());
		testSuite.addTest(AddEventTypeAppointmentTests.suite());
		testSuite.addTest(AddEventTypeConcertTests.suite());
		testSuite.addTest(AddEventTypeVacationTests.suite());
		testSuite.addTest(DeleteEventTests.suite());
		testSuite.addTest(EditEventTests.suite());
		testSuite.addTest(ViewEventTypeAppointmentTests.suite());
		testSuite.addTest(ViewEventTypeConcertTests.suite());
		testSuite.addTest(ViewEventTypeVacationTests.suite());

		return testSuite;
	}

}