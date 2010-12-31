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

package com.liferay.portalweb.portlet.blogs.entry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.blogs.entry.addentry.AddEntryTests;
import com.liferay.portalweb.portlet.blogs.entry.addentryautodraft.AddEntryAutoDraftTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrycontentmultipleword.AddEntryContentMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrycontentnull.AddEntryContentNullTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrydraft.AddEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrymultiple.AddEntryMultipleTests;
import com.liferay.portalweb.portlet.blogs.entry.addentryscopepage.AddEntryScopePageTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrytitle150characters.AddEntryTitle150CharactersTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrytitle151characters.AddEntryTitle151CharactersTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrytitleescapecharacters.AddEntryTitleEscapeCharactersTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrytitlemultipleword.AddEntryTitleMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrytitlenull.AddEntryTitleNullTests;
import com.liferay.portalweb.portlet.blogs.entry.deleteentry.DeleteEntryTests;
import com.liferay.portalweb.portlet.blogs.entry.deleteentrycontentmultipleword.DeleteEntryContentMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.entry.deleteentrydraft.DeleteEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.entry.deleteentrytitle150characters.DeleteEntryTitle150CharactersTests;
import com.liferay.portalweb.portlet.blogs.entry.deleteentrytitleescapecharacters.DeleteEntryTitleEscapeCharactersTests;
import com.liferay.portalweb.portlet.blogs.entry.deleteentrytitlemultipleword.DeleteEntryTitleMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.entry.editentrycontent.EditEntryContentTests;
import com.liferay.portalweb.portlet.blogs.entry.editentrycontententrydetails.EditEntryContentEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.entry.editentrytitle.EditEntryTitleTests;
import com.liferay.portalweb.portlet.blogs.entry.editentrytitleentrydetails.EditEntryTitleEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.entry.publishentrydraft.PublishEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.entry.rateentry.RateEntryTests;
import com.liferay.portalweb.portlet.blogs.entry.rateentryentrydetails.RateEntryEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.entry.viewcountentryentrydetails.ViewCountEntryEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.entry.viewentry.ViewEntryTests;
import com.liferay.portalweb.portlet.blogs.entry.viewentryentrydetails.ViewEntryEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.entry.viewentryscopepage.ViewEntryScopePageTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddEntryTests.suite());
		testSuite.addTest(AddEntryAutoDraftTests.suite());
		testSuite.addTest(AddEntryContentMultipleWordTests.suite());
		testSuite.addTest(AddEntryContentNullTests.suite());
		testSuite.addTest(AddEntryDraftTests.suite());
		testSuite.addTest(AddEntryMultipleTests.suite());
		testSuite.addTest(AddEntryScopePageTests.suite());
		testSuite.addTest(AddEntryTitle150CharactersTests.suite());
		testSuite.addTest(AddEntryTitle151CharactersTests.suite());
		testSuite.addTest(AddEntryTitleEscapeCharactersTests.suite());
		testSuite.addTest(AddEntryTitleMultipleWordTests.suite());
		testSuite.addTest(AddEntryTitleNullTests.suite());
		testSuite.addTest(DeleteEntryTests.suite());
		testSuite.addTest(DeleteEntryContentMultipleWordTests.suite());
		testSuite.addTest(DeleteEntryDraftTests.suite());
		testSuite.addTest(DeleteEntryTitle150CharactersTests.suite());
		testSuite.addTest(DeleteEntryTitleEscapeCharactersTests.suite());
		testSuite.addTest(DeleteEntryTitleMultipleWordTests.suite());
		testSuite.addTest(EditEntryContentTests.suite());
		testSuite.addTest(EditEntryContentEntryDetailsTests.suite());
		testSuite.addTest(EditEntryTitleTests.suite());
		testSuite.addTest(EditEntryTitleEntryDetailsTests.suite());
		testSuite.addTest(PublishEntryDraftTests.suite());
		testSuite.addTest(RateEntryTests.suite());
		testSuite.addTest(RateEntryEntryDetailsTests.suite());
		testSuite.addTest(ViewCountEntryEntryDetailsTests.suite());
		testSuite.addTest(ViewEntryTests.suite());
		testSuite.addTest(ViewEntryEntryDetailsTests.suite());
		testSuite.addTest(ViewEntryScopePageTests.suite());

		return testSuite;
	}

}