/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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