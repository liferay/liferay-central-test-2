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

package com.liferay.portalweb.portlet.assetpublisher;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.archivedsetup.ArchivedSetupTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.BlogsEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentrycomment.BlogsEntryCommentTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.BookmarksEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.DLDocumentTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocumentcomment.DLDocumentCommentTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.IGImageTests;
import com.liferay.portalweb.portlet.assetpublisher.lar.LARTests;
import com.liferay.portalweb.portlet.assetpublisher.mbmessage.MBMessageTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.PortletTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.WebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontentcomment.WebContentCommentTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.WikiPageTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipagecomment.WikiPageCommentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="AssetPublisherTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetPublisherTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ArchivedSetupTests.suite());
		testSuite.addTest(BlogsEntryTests.suite());
		testSuite.addTest(BlogsEntryCommentTests.suite());
		testSuite.addTest(BookmarksEntryTests.suite());
		testSuite.addTest(DLDocumentTests.suite());
		testSuite.addTest(DLDocumentCommentTests.suite());
		testSuite.addTest(IGImageTests.suite());
		testSuite.addTest(LARTests.suite());
		testSuite.addTest(MBMessageTests.suite());
		testSuite.addTest(PortletTests.suite());
		testSuite.addTest(WebContentTests.suite());
		testSuite.addTest(WebContentCommentTests.suite());
		testSuite.addTest(WikiPageTests.suite());
		testSuite.addTest(WikiPageCommentTests.suite());

		return testSuite;
	}

}