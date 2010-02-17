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

package com.liferay.portalweb.portlet.wikidisplay.wikipage;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpage.AddWDFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpageformathtml.AddWDFrontPageChildPageFormatHTMLTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpagemultiple.AddWDFrontPageChildPageMultipleTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpagenameduplicate.AddWDFrontPageChildPageNameDuplicateTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpagenameinvalid.AddWDFrontPageChildPageNameInvalidTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpagenamenull.AddWDFrontPageChildPageNameNullTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.canceladdwdfrontpagechildpage.CancelAddWDFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.changeparentwdfrontpagechildpagetonone.ChangeParentWDFrontPageChildPageToNoneTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.compareversioneditwikifrontpage.CompareVersionEditWikiFrontPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.compareversioneditwikifrontpageminorchange.CompareVersionEditWikiFrontPageMinorChangeTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.copywdfrontpage.CopyWDFrontPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.deletewdfrontpagechildpage.DeleteWDFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.editwikifrontpage.EditWikiFrontPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.editwikifrontpageminorchange.EditWikiFrontPageMinorChangeTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.linkwdfrontpagechildpagetowdfrontpagechildpage.LinkWDFrontPageChildPageToWDFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.previewwdfrontpagechildpage.PreviewWDFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.removeredirectwdfrontpagechildpage.RemoveRedirectWDFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.renamewdfrontpagechildpage.RenameWDFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.reverteditwikifrontpage.RevertEditWikiFrontPageTests;
import com.liferay.portalweb.portlet.wikidisplay.wikipage.reverteditwikifrontpageminorchange.RevertEditWikiFrontPageMinorChangeTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="WikiPageTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WikiPageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWDFrontPageChildPageTests.suite());
		testSuite.addTest(AddWDFrontPageChildPageFormatHTMLTests.suite());
		testSuite.addTest(AddWDFrontPageChildPageMultipleTests.suite());
		testSuite.addTest(AddWDFrontPageChildPageNameDuplicateTests.suite());
		testSuite.addTest(AddWDFrontPageChildPageNameInvalidTests.suite());
		testSuite.addTest(AddWDFrontPageChildPageNameNullTests.suite());
		testSuite.addTest(CancelAddWDFrontPageChildPageTests.suite());
		testSuite.addTest(ChangeParentWDFrontPageChildPageToNoneTests.suite());
		testSuite.addTest(CompareVersionEditWikiFrontPageTests.suite());
		testSuite.addTest(
			CompareVersionEditWikiFrontPageMinorChangeTests.suite());
		testSuite.addTest(CopyWDFrontPageTests.suite());
		testSuite.addTest(DeleteWDFrontPageChildPageTests.suite());
		testSuite.addTest(EditWikiFrontPageTests.suite());
		testSuite.addTest(EditWikiFrontPageMinorChangeTests.suite());
		testSuite.addTest(
			LinkWDFrontPageChildPageToWDFrontPageChildPageTests.suite());
		testSuite.addTest(PreviewWDFrontPageChildPageTests.suite());
		testSuite.addTest(RemoveRedirectWDFrontPageChildPageTests.suite());
		testSuite.addTest(RenameWDFrontPageChildPageTests.suite());
		testSuite.addTest(RevertEditWikiFrontPageTests.suite());
		testSuite.addTest(RevertEditWikiFrontPageMinorChangeTests.suite());

		return testSuite;
	}

}