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
import com.liferay.portalweb.portlet.blogs.add_entry.AddEntryTests;
import com.liferay.portalweb.portlet.blogs.add_entry_draft.AddEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.add_entry_multiple.AddEntryMultipleTests;
import com.liferay.portalweb.portlet.blogs.add_entrycomment.AddEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.add_entrycontent_multipleword.AddEntryContentMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.add_entrycontent_null.AddEntryContentNullTests;
import com.liferay.portalweb.portlet.blogs.add_entrytitle_150characters.AddEntryTitle150CharactersTests;
import com.liferay.portalweb.portlet.blogs.add_entrytitle_151characters.AddEntryTitle151CharactersTests;
import com.liferay.portalweb.portlet.blogs.add_entrytitle_escapecharacters.AddEntryTitleEscapeCharactersTests;
import com.liferay.portalweb.portlet.blogs.add_entrytitle_multipleword.AddEntryTitleMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.add_entrytitle_null.AddEntryTitleNullTests;
import com.liferay.portalweb.portlet.blogs.add_portlet.AddPortletTests;
import com.liferay.portalweb.portlet.blogs.add_portlet_duplicate.AddPortletDuplicateTests;
import com.liferay.portalweb.portlet.blogs.configure_portletdisplaystyle_abstract.ConfigurePortletDisplayStyleAbstractTests;
import com.liferay.portalweb.portlet.blogs.configure_portletdisplaystyle_fullcontent.ConfigurePortletDisplayStyleFullContentTests;
import com.liferay.portalweb.portlet.blogs.configure_portletdisplaystyle_title.ConfigurePortletDisplayStyleTitleTests;
import com.liferay.portalweb.portlet.blogs.delete_entry.DeleteEntryTests;
import com.liferay.portalweb.portlet.blogs.delete_entry_draft.DeleteEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.delete_entrycomment.DeleteEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.delete_entrycontent_multipleword.DeleteEntryContentMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.delete_entrytitle_150characters.DeleteEntryTitle150CharactersTests;
import com.liferay.portalweb.portlet.blogs.delete_entrytitle_escapecharacters.DeleteEntryTitleEscapeCharactersTests;
import com.liferay.portalweb.portlet.blogs.delete_entrytitle_multipleword.DeleteEntryTitleMultipleWordTests;
import com.liferay.portalweb.portlet.blogs.edit_entrycomment.EditEntryCommentTests;
import com.liferay.portalweb.portlet.blogs.edit_entrycontent.EditEntryContentTests;
import com.liferay.portalweb.portlet.blogs.edit_entrycontent_entrydetails.EditEntryContentEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.edit_entrytitle.EditEntryTitleTests;
import com.liferay.portalweb.portlet.blogs.edit_entrytitle_entrydetails.EditEntryTitleEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.import_lar.ImportLARTests;
import com.liferay.portalweb.portlet.blogs.publish_entry_draft.PublishEntryDraftTests;
import com.liferay.portalweb.portlet.blogs.rate_entry.RateEntryTests;
import com.liferay.portalweb.portlet.blogs.rate_entry_entrydetails.RateEntryEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.view_entry.ViewEntryTests;
import com.liferay.portalweb.portlet.blogs.view_entry_entrydetails.ViewEntryEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.viewcount_entry.ViewCountEntryTests;
import com.liferay.portalweb.portlet.blogs.viewcount_entry_entrydetails.ViewCountEntryEntryDetailsTests;

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
		testSuite.addTest(AddEntryDraftTests.suite());
		testSuite.addTest(AddEntryMultipleTests.suite());
		testSuite.addTest(AddEntryCommentTests.suite());
		testSuite.addTest(AddEntryContentMultipleWordTests.suite());
		testSuite.addTest(AddEntryContentNullTests.suite());
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
		testSuite.addTest(DeleteEntryDraftTests.suite());
		testSuite.addTest(DeleteEntryCommentTests.suite());
		testSuite.addTest(DeleteEntryContentMultipleWordTests.suite());
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
		testSuite.addTest(ViewEntryTests.suite());
		testSuite.addTest(ViewEntryEntryDetailsTests.suite());
		testSuite.addTest(ViewCountEntryTests.suite());
		testSuite.addTest(ViewCountEntryEntryDetailsTests.suite());

		return testSuite;
	}

}