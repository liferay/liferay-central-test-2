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

package com.liferay.portalweb.portlet.messageboards.message;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessage.AddCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagebodynull.AddCategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagemultiple.AddCategoryMessageMultipleTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagereply.AddCategoryMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagereplymultiple.AddCategoryMessageReplyMultipleTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagesubjectnull.AddCategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.message.addsubcategorymessage.AddSubcategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.addsubcategorymessagebodynull.AddSubcategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.message.addsubcategorymessagemultiple.AddSubcategoryMessageMultipleTests;
import com.liferay.portalweb.portlet.messageboards.message.addsubcategorymessagesubjectnull.AddSubcategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.message.deletecategorymessage.DeleteCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.editcategorymessagebody.EditCategoryMessageBodyTests;
import com.liferay.portalweb.portlet.messageboards.message.editcategorymessagebodynull.EditCategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.message.editcategorymessagesubject.EditCategoryMessageSubjectTests;
import com.liferay.portalweb.portlet.messageboards.message.editcategorymessagesubjectnull.EditCategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.message.movecategorymessagetocategory.MoveCategoryMessageToCategoryTests;
import com.liferay.portalweb.portlet.messageboards.message.movecategorymessagetocategoryexplanation.MoveCategoryMessageToCategoryExplanationTests;
import com.liferay.portalweb.portlet.messageboards.message.searchcategorymessage.SearchCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.splitthreadcategorymessagereply.SplitThreadCategoryMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.message.splitthreadcategorymessagereplybackbutton.SplitThreadCategoryMessageReplyBackButtonTests;
import com.liferay.portalweb.portlet.messageboards.message.splitthreadcategorymessagereplyexplanation.SplitThreadCategoryMessageReplyExplanationTests;
import com.liferay.portalweb.portlet.messageboards.message.splitthreadcategorymessagereplymultiple.SplitThreadCategoryMessageReplyMultipleTests;
import com.liferay.portalweb.portlet.messageboards.message.viewcategorymessagemyposts.ViewCategoryMessageMyPostsTests;
import com.liferay.portalweb.portlet.messageboards.message.viewcategorymessagerecentposts.ViewCategoryMessageRecentPostsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="MessageTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MessageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCategoryMessageTests.suite());
		testSuite.addTest(AddCategoryMessageBodyNullTests.suite());
		testSuite.addTest(AddCategoryMessageMultipleTests.suite());
		testSuite.addTest(AddCategoryMessageReplyTests.suite());
		testSuite.addTest(AddCategoryMessageReplyMultipleTests.suite());
		testSuite.addTest(AddCategoryMessageSubjectNullTests.suite());
		testSuite.addTest(AddSubcategoryMessageTests.suite());
		testSuite.addTest(AddSubcategoryMessageBodyNullTests.suite());
		testSuite.addTest(AddSubcategoryMessageMultipleTests.suite());
		testSuite.addTest(AddSubcategoryMessageSubjectNullTests.suite());
		testSuite.addTest(DeleteCategoryMessageTests.suite());
		testSuite.addTest(EditCategoryMessageBodyTests.suite());
		testSuite.addTest(EditCategoryMessageBodyNullTests.suite());
		testSuite.addTest(EditCategoryMessageSubjectTests.suite());
		testSuite.addTest(EditCategoryMessageSubjectNullTests.suite());
		testSuite.addTest(MoveCategoryMessageToCategoryTests.suite());
		testSuite.addTest(
			MoveCategoryMessageToCategoryExplanationTests.suite());
		testSuite.addTest(SearchCategoryMessageTests.suite());
		testSuite.addTest(SplitThreadCategoryMessageReplyTests.suite());
		testSuite.addTest(
			SplitThreadCategoryMessageReplyBackButtonTests.suite());
		testSuite.addTest(
			SplitThreadCategoryMessageReplyExplanationTests.suite());
		testSuite.addTest(SplitThreadCategoryMessageReplyMultipleTests.suite());
		testSuite.addTest(ViewCategoryMessageMyPostsTests.suite());
		testSuite.addTest(ViewCategoryMessageRecentPostsTests.suite());

		return testSuite;
	}

}