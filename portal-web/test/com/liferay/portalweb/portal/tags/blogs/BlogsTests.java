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

package com.liferay.portalweb.portal.tags.blogs;

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

		testSuite.addTestSuite(SetupTest.class);
		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(AssertNoTagsInTagsAdminTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);
		testSuite.addTestSuite(AssertNoTagsInSelectTagsTest.class);
		testSuite.addTestSuite(AddTagTest.class);
		testSuite.addTestSuite(AddAmpersandTagTest.class);
		testSuite.addTestSuite(AddApostropheTagTest.class);
		testSuite.addTestSuite(AddAsteriskTagTest.class);
		testSuite.addTestSuite(AddAtTagTest.class);
		testSuite.addTestSuite(AddBackSlashTagTest.class);
		testSuite.addTestSuite(AddBracketTagTest.class);
		testSuite.addTestSuite(AddCompareCharacterTagTest.class);
		testSuite.addTestSuite(AddCurlyBraceTagTest.class);
		testSuite.addTestSuite(AddColonTagTest.class);
		testSuite.addTestSuite(AddDuplicateTagTest.class);
		testSuite.addTestSuite(AddEqualSignTagTest.class);
		testSuite.addTestSuite(AddForwardSlashTagTest.class);
		testSuite.addTestSuite(AddNullTagTest.class);
		testSuite.addTestSuite(AddPercentTagTest.class);
		testSuite.addTestSuite(AddPlusTagTest.class);
		testSuite.addTestSuite(AddPoundTagTest.class);
		testSuite.addTestSuite(AddQuestionTagTest.class);
		testSuite.addTestSuite(AddQuoteTagTest.class);
		testSuite.addTestSuite(AddSemiColonTagTest.class);
		testSuite.addTestSuite(AddTildeTagTest.class);
		testSuite.addTestSuite(AddTagThroughJavaScriptTest.class);
		testSuite.addTestSuite(AddMultipleTagsTest.class);
		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(AssertTagsInTagsAdminTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);
		testSuite.addTestSuite(AssertTagsInSelectTagsTest.class);
		testSuite.addTestSuite(SelectTagsTest.class);
		testSuite.addTestSuite(RemoveTagThroughSelectTagTest.class);
		testSuite.addTestSuite(AssertAutoSuggestionDropDownTest.class);
		testSuite.addTestSuite(SelectTagThroughAutoSuggestionTest.class);
		testSuite.addTestSuite(SearchTagsTest.class);
		testSuite.addTestSuite(SearchPartialTagsTest.class);
		testSuite.addTestSuite(RemoveTagThroughJavaScriptTest.class);
		testSuite.addTestSuite(SearchRemovedTagTest.class);
		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(DeleteTagTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);
		testSuite.addTestSuite(AssertDeletedTagDoesNotPersistTest.class);
		testSuite.addTestSuite(SuggestionTagTest.class);
		testSuite.addTestSuite(AddUTFTagTest.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}