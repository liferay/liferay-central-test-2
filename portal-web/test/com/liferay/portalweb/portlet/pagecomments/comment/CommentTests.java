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

package com.liferay.portalweb.portlet.pagecomments.comment;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.pagecomments.comment.addcomment.AddCommentTests;
import com.liferay.portalweb.portlet.pagecomments.comment.addcommentbodynull.AddCommentBodyNullTests;
import com.liferay.portalweb.portlet.pagecomments.comment.addcommentbodyspace.AddCommentBodySpaceTests;
import com.liferay.portalweb.portlet.pagecomments.comment.addcommentmultiple.AddCommentMultipleTests;
import com.liferay.portalweb.portlet.pagecomments.comment.addcommentreply.AddCommentReplyTests;
import com.liferay.portalweb.portlet.pagecomments.comment.addcommentreplybodynull.AddCommentReplyBodyNullTests;
import com.liferay.portalweb.portlet.pagecomments.comment.addcommentreplybodyspace.AddCommentReplyBodySpaceTests;
import com.liferay.portalweb.portlet.pagecomments.comment.addcommentreplymultiple.AddCommentReplyMultipleTests;
import com.liferay.portalweb.portlet.pagecomments.comment.canceladdcomment.CancelAddCommentTests;
import com.liferay.portalweb.portlet.pagecomments.comment.canceladdcommentreply.CancelAddCommentReplyTests;
import com.liferay.portalweb.portlet.pagecomments.comment.deletecomment.DeleteCommentTests;
import com.liferay.portalweb.portlet.pagecomments.comment.deletecommentreply.DeleteCommentReplyTests;
import com.liferay.portalweb.portlet.pagecomments.comment.editcommentbody.EditCommentBodyTests;
import com.liferay.portalweb.portlet.pagecomments.comment.editcommentbodynull.EditCommentBodyNullTests;
import com.liferay.portalweb.portlet.pagecomments.comment.editcommentbodyspace.EditCommentBodySpaceTests;
import com.liferay.portalweb.portlet.pagecomments.comment.editcommentreplybody.EditCommentReplyBodyTests;
import com.liferay.portalweb.portlet.pagecomments.comment.editcommentreplybodynull.EditCommentReplyBodyNullTests;
import com.liferay.portalweb.portlet.pagecomments.comment.editcommentreplybodyspace.EditCommentReplyBodySpaceTests;
import com.liferay.portalweb.portlet.pagecomments.comment.ratecomment.RateCommentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="CommentTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CommentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCommentTests.suite());
		testSuite.addTest(AddCommentBodyNullTests.suite());
		testSuite.addTest(AddCommentBodySpaceTests.suite());
		testSuite.addTest(AddCommentMultipleTests.suite());
		testSuite.addTest(AddCommentReplyTests.suite());
		testSuite.addTest(AddCommentReplyBodyNullTests.suite());
		testSuite.addTest(AddCommentReplyBodySpaceTests.suite());
		testSuite.addTest(AddCommentReplyMultipleTests.suite());
		testSuite.addTest(CancelAddCommentTests.suite());
		testSuite.addTest(CancelAddCommentReplyTests.suite());
		testSuite.addTest(DeleteCommentTests.suite());
		testSuite.addTest(DeleteCommentReplyTests.suite());
		testSuite.addTest(EditCommentBodyTests.suite());
		testSuite.addTest(EditCommentBodyNullTests.suite());
		testSuite.addTest(EditCommentBodySpaceTests.suite());
		testSuite.addTest(EditCommentReplyBodyTests.suite());
		testSuite.addTest(EditCommentReplyBodyNullTests.suite());
		testSuite.addTest(EditCommentReplyBodySpaceTests.suite());
		testSuite.addTest(RateCommentTests.suite());

		return testSuite;
	}

}