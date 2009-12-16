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

package com.liferay.portalweb.portlet.announcements.entry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.announcements.entry.addentrycontentnull.AddEntryContentNullTests;
import com.liferay.portalweb.portlet.announcements.entry.addentrygeneral.AddEntryGeneralTests;
import com.liferay.portalweb.portlet.announcements.entry.addentrypriorityimportant.AddEntryPriorityImportantTests;
import com.liferay.portalweb.portlet.announcements.entry.addentryprioritynormal.AddEntryPriorityNormalTests;
import com.liferay.portalweb.portlet.announcements.entry.assertpriorityorder.AssertPriorityOrderTests;
import com.liferay.portalweb.portlet.announcements.entry.deleteentrygeneral.DeleteEntryGeneralTests;
import com.liferay.portalweb.portlet.announcements.entry.editentrygeneral.EditEntryGeneralTests;
import com.liferay.portalweb.portlet.announcements.entry.hideentry.HideEntryTests;
import com.liferay.portalweb.portlet.announcements.entry.markasreadentry.MarkAsReadEntryTests;
import com.liferay.portalweb.portlet.announcements.entry.showentry.ShowEntryTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="EntryTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddEntryContentNullTests.suite());
		testSuite.addTest(AddEntryGeneralTests.suite());
		testSuite.addTest(AddEntryPriorityImportantTests.suite());
		testSuite.addTest(AddEntryPriorityNormalTests.suite());
		testSuite.addTest(AssertPriorityOrderTests.suite());
		testSuite.addTest(DeleteEntryGeneralTests.suite());
		testSuite.addTest(EditEntryGeneralTests.suite());
		testSuite.addTest(HideEntryTests.suite());
		testSuite.addTest(MarkAsReadEntryTests.suite());
		testSuite.addTest(ShowEntryTests.suite());

		return testSuite;
	}

}