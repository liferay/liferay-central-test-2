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

package com.liferay.portalweb.portlet.blogs;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.blogs.addentry.AddEntryTests;
import com.liferay.portalweb.portlet.blogs.addentrycomment.AddEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.addentrycontentmultipleword.AddEntryContentMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.addentrycontentnull.AddEntryContentNullTests;
import com.liferay.portalweb.portlet.blogs.addentrydraft.AddEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.addentrymultiple.AddEntryMultipleTests;
import com.liferay.portalweb.portlet.blogs.addentrytitle150characters.AddEntryTitle150CharactersTests;
import com.liferay.portalweb.portlet.blogs.addentrytitle151characters.AddEntryTitle151CharactersTests;
import com.liferay.portalweb.portlet.blogs.addentrytitleescapecharacters.AddEntryTitleEscapeCharactersTests;
import com.liferay.portalweb.portlet.blogs.addentrytitlemultipleword.AddEntryTitleMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.addentrytitlenull.AddEntryTitleNullTests;
import com.liferay.portalweb.portlet.blogs.addportlet.AddPortletTests;
import com.liferay.portalweb.portlet.blogs.addportletduplicate.AddPortletDuplicateTests;
import com.liferay.portalweb.portlet.blogs.configureportletdisplaystyleabstract.ConfigurePortletDisplayStyleAbstractTests;
import com.liferay.portalweb.portlet.blogs.configureportletdisplaystylefullcontent.ConfigurePortletDisplayStyleFullContentTests;
import com.liferay.portalweb.portlet.blogs.configureportletdisplaystyletitle.ConfigurePortletDisplayStyleTitleTests;
import com.liferay.portalweb.portlet.blogs.deleteentry.DeleteEntryTests;
import com.liferay.portalweb.portlet.blogs.deleteentrycomment.DeleteEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.deleteentrycontentmultipleword.DeleteEntryContentMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.deleteentrydraft.DeleteEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.deleteentrytitle150characters.DeleteEntryTitle150CharactersTests;
import com.liferay.portalweb.portlet.blogs.deleteentrytitleescapecharacters.DeleteEntryTitleEscapeCharactersTests;
import com.liferay.portalweb.portlet.blogs.deleteentrytitlemultipleword.DeleteEntryTitleMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.editentrycomment.EditEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.editentrycontent.EditEntryContentTests;
import com.liferay.portalweb.portlet.blogs.editentrycontententrydetails.EditEntryContentEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.editentrytitle.EditEntryTitleTests;
import com.liferay.portalweb.portlet.blogs.editentrytitleentrydetails.EditEntryTitleEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.importlar.ImportLARTests;
import com.liferay.portalweb.portlet.blogs.publishentrydraft.PublishEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.rateentry.RateEntryTests;
import com.liferay.portalweb.portlet.blogs.rateentryentrydetails.RateEntryEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.viewcountentry.ViewCountEntryTests;
import com.liferay.portalweb.portlet.blogs.viewcountentryentrydetails.ViewCountEntryEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.viewentry.ViewEntryTests;
import com.liferay.portalweb.portlet.blogs.viewentryentrydetails.ViewEntryEntryDetailsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="BlogsTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BlogsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddEntryTests.suite());
		testSuite.addTest(AddEntryCommentTests.suite());
		testSuite.addTest(AddEntryContentMultipleWordTests.suite());
		testSuite.addTest(AddEntryContentNullTests.suite());
		testSuite.addTest(AddEntryDraftTests.suite());
		testSuite.addTest(AddEntryMultipleTests.suite());
		testSuite.addTest(AddEntryTitle150CharactersTests.suite());
		testSuite.addTest(AddEntryTitle151CharactersTests.suite());
		testSuite.addTest(AddEntryTitleEscapeCharactersTests.suite());
		testSuite.addTest(AddEntryTitleMultipleWordTests.suite());
		testSuite.addTest(AddEntryTitleNullTests.suite());
		testSuite.addTest(AddPortletTests.suite());
		testSuite.addTest(AddPortletDuplicateTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleAbstractTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleFullContentTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleTitleTests.suite());
		testSuite.addTest(DeleteEntryTests.suite());
		testSuite.addTest(DeleteEntryCommentTests.suite());
		testSuite.addTest(DeleteEntryContentMultipleWordTests.suite());
		testSuite.addTest(DeleteEntryDraftTests.suite());
		testSuite.addTest(DeleteEntryTitle150CharactersTests.suite());
		testSuite.addTest(DeleteEntryTitleEscapeCharactersTests.suite());
		testSuite.addTest(DeleteEntryTitleMultipleWordTests.suite());
		testSuite.addTest(EditEntryCommentTests.suite());
		testSuite.addTest(EditEntryContentTests.suite());
		testSuite.addTest(EditEntryContentEntryDetailsTests.suite());
		testSuite.addTest(EditEntryTitleTests.suite());
		testSuite.addTest(EditEntryTitleEntryDetailsTests.suite());
		testSuite.addTest(ImportLARTests.suite());
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