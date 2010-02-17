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

package com.liferay.portalweb.portlet.wikidisplay.wikinode.selectwikinodemultiple;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="SelectWikiNodeMultipleTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SelectWikiNodeMultipleTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageWikiTest.class);
		testSuite.addTestSuite(AddPortletWikiTest.class);
		testSuite.addTestSuite(AddPageWDTest.class);
		testSuite.addTestSuite(AddPortletWDTest.class);
		testSuite.addTestSuite(AddWikiNode1Test.class);
		testSuite.addTestSuite(AddWikiNode2Test.class);
		testSuite.addTestSuite(AddWikiNode3Test.class);
		testSuite.addTestSuite(AddWikiFrontPage1Test.class);
		testSuite.addTestSuite(AddWikiFrontPage2Test.class);
		testSuite.addTestSuite(AddWikiFrontPage3Test.class);
		testSuite.addTestSuite(SelectWikiNode1Test.class);
		testSuite.addTestSuite(SelectWikiNode2Test.class);
		testSuite.addTestSuite(SelectWikiNode3Test.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}