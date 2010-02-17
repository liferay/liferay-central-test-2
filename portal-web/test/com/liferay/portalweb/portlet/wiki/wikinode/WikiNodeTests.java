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

package com.liferay.portalweb.portlet.wiki.wikinode;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.wiki.wikinode.addwikinode.AddWikiNodeTests;
import com.liferay.portalweb.portlet.wiki.wikinode.addwikinodemultiple.AddWikiNodeMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikinode.addwikinodenameduplicate.AddWikiNodeNameDuplicateTests;
import com.liferay.portalweb.portlet.wiki.wikinode.addwikinodenamenull.AddWikiNodeNameNullTests;
import com.liferay.portalweb.portlet.wiki.wikinode.addwikinodenamenumber.AddWikiNodeNameNumberTests;
import com.liferay.portalweb.portlet.wiki.wikinode.addwikinodenamesymbol.AddWikiNodeNameSymbolTests;
import com.liferay.portalweb.portlet.wiki.wikinode.deletewikinode.DeleteWikiNodeTests;
import com.liferay.portalweb.portlet.wiki.wikinode.editwikinode.EditWikiNodeTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="WikiNodeTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WikiNodeTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWikiNodeTests.suite());
		testSuite.addTest(AddWikiNodeMultipleTests.suite());
		testSuite.addTest(AddWikiNodeNameDuplicateTests.suite());
		testSuite.addTest(AddWikiNodeNameNullTests.suite());
		testSuite.addTest(AddWikiNodeNameNumberTests.suite());
		testSuite.addTest(AddWikiNodeNameSymbolTests.suite());
		testSuite.addTest(DeleteWikiNodeTests.suite());
		testSuite.addTest(EditWikiNodeTests.suite());

		return testSuite;
	}

}