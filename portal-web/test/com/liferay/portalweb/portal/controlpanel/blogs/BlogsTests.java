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

package com.liferay.portalweb.portal.controlpanel.blogs;

import com.liferay.portalweb.portal.BaseTests;

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

		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(AddEntryTest.class);
		testSuite.addTestSuite(AddEntryCommentTest.class);
		testSuite.addTestSuite(AssertViewCountTest.class);
		testSuite.addTestSuite(AddRatingTest.class);
		testSuite.addTestSuite(AddSecondEntryTest.class);
		testSuite.addTestSuite(AddSecondEntryCommentTest.class);
		testSuite.addTestSuite(EditSecondEntryTest.class);
		testSuite.addTestSuite(EditSecondCommentTest.class);
		testSuite.addTestSuite(SearchBlogsTest.class);
		testSuite.addTestSuite(DeleteSecondCommentTest.class);
		testSuite.addTestSuite(DeleteSecondEntryTest.class);
		testSuite.addTestSuite(AddNullEntryTest.class);
		testSuite.addTestSuite(AddNullTitleTest.class);
		testSuite.addTestSuite(AddEscapeCharacterEntryTest.class);
		testSuite.addTestSuite(DeleteEscapeCharacterEntryTest.class);
		testSuite.addTestSuite(ConfigureDisplaySettingsTest.class);
		testSuite.addTestSuite(DeleteEntryTest.class);
		testSuite.addTestSuite(AddDraftEntryTest.class);
		testSuite.addTestSuite(PublishDraftEntryTest.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(TearDownTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);

		return testSuite;
	}

}