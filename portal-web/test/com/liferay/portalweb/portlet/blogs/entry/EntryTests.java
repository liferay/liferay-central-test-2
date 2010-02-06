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

package com.liferay.portalweb.portlet.blogs.entry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.blogs.entry.addentry.AddEntryTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrycontentmultipleword.AddEntryContentMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrycontentnull.AddEntryContentNullTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrydraft.AddEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.entry.addentrymultiple.AddEntryMultipleTests;
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
import com.liferay.portalweb.portlet.blogs.entry.viewcountentry.ViewCountEntryTests;
import com.liferay.portalweb.portlet.blogs.entry.viewcountentryentrydetails.ViewCountEntryEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.entry.viewentry.ViewEntryTests;
import com.liferay.portalweb.portlet.blogs.entry.viewentryentrydetails.ViewEntryEntryDetailsTests;

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

		testSuite.addTest(AddEntryTests.suite());
		testSuite.addTest(AddEntryContentMultipleWordTests.suite());
		testSuite.addTest(AddEntryContentNullTests.suite());
		testSuite.addTest(AddEntryDraftTests.suite());
		testSuite.addTest(AddEntryMultipleTests.suite());
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
		testSuite.addTest(ViewCountEntryTests.suite());
		testSuite.addTest(ViewCountEntryEntryDetailsTests.suite());
		testSuite.addTest(ViewEntryTests.suite());
		testSuite.addTest(ViewEntryEntryDetailsTests.suite());

		return testSuite;
	}

}