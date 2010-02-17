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

package com.liferay.portalweb.portlet.wiki.wikipage;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpage.AddFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpage.AddFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagemultiple.AddFrontPageChildPageMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagename255character.AddFrontPageChildPageName255CharacterTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagenameduplicate.AddFrontPageChildPageNameDuplicateTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagenamenull.AddFrontPageChildPageNameNullTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagenamesymbol.AddFrontPageChildPageNameSymbolTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipage.AddWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagechildpage.AddWikiPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagechildpagemultiple.AddWikiPageChildPageMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipageformathtml.AddWikiPageFormatHTMLTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagemultiple.AddWikiPageMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename255character.AddWikiPageName255CharacterTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename255characterchildpage.AddWikiPageName255CharacterChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename255characterchildpagemultiple.AddWikiPageName255CharacterChildPageMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename256character.AddWikiPageName256CharacterTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagenameduplicate.AddWikiPageNameDuplicateTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagenamenull.AddWikiPageNameNullTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagenamesymbol.AddWikiPageNameSymbolTests;
import com.liferay.portalweb.portlet.wiki.wikipage.canceladdfrontpage.CancelAddFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.canceladdfrontpagechildpage.CancelAddFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentfrontpagechildpagename255charactertonone.ChangeParentFrontPageChildPageName255CharacterToNoneTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentfrontpagechildpagename255charactertowikipage.ChangeParentFrontPageChildPageName255CharacterToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentfrontpagechildpagetonone.ChangeParentFrontPageChildPageToNoneTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentfrontpagechildpagetowikipage.ChangeParentFrontPageChildPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentwikipagename255charactertowikipage.ChangeParentWikiPageName255CharacterToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentwikipagetowikipage.ChangeParentWikiPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.compareversioneditfrontpage.CompareVersionEditFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.compareversioneditfrontpageminorchange.CompareVersionEditFrontPageMinorChangeTests;
import com.liferay.portalweb.portlet.wiki.wikipage.copyfrontpage.CopyFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletefrontpage.DeleteFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletefrontpagechildpage.DeleteFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletewikipage.DeleteWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletewikipagechildpage.DeleteWikiPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletewikipagename255character.DeleteWikiPageName255CharacterTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletewikipagename255characterchildpage.DeleteWikiPageName255CharacterChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.editfrontpage.EditFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.editfrontpageminorchange.EditFrontPageMinorChangeTests;
import com.liferay.portalweb.portlet.wiki.wikipage.linkwikipagetowikipage.LinkWikiPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.previewfrontpage.PreviewFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.previewfrontpagechildpage.PreviewFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.removeredirectwikipage.RemoveRedirectWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamewikipagetitle.RenameWikiPageTitleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamewikipagetitleduplicate.RenameWikiPageTitleDuplicateTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamewikipagetitlenull.RenameWikiPageTitleNullTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamewikipagetitlesame.RenameWikiPageTitleSameTests;
import com.liferay.portalweb.portlet.wiki.wikipage.revertchangeparentfrontpagechildpagetonone.RevertChangeParentFrontPageChildPageToNoneTests;
import com.liferay.portalweb.portlet.wiki.wikipage.revertchangeparentfrontpagechildpagetowikipage.RevertChangeParentFrontPageChildPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.revertchangeparentwikipagetowikipage.RevertChangeParentWikiPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.reverteditfrontpage.RevertEditFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.reverteditfrontpageminorchange.RevertEditFrontPageMinorChangeTests;
import com.liferay.portalweb.portlet.wiki.wikipage.searchwikipagecontent.SearchWikiPageContentTests;
import com.liferay.portalweb.portlet.wiki.wikipage.searchwikipagetitle.SearchWikiPageTitleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageallpages.ViewWikiPageAllPagesTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageincominglinks.ViewWikiPageIncomingLinksTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageorphanpages.ViewWikiPageOrphanPagesTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageoutgoinglinks.ViewWikiPageOutgoingLinksTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipagerecentchanges.ViewWikiPageRecentChangesTests;

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

		testSuite.addTest(AddFrontPageTests.suite());
		testSuite.addTest(AddFrontPageChildPageTests.suite());
		testSuite.addTest(AddFrontPageChildPageMultipleTests.suite());
		testSuite.addTest(AddFrontPageChildPageName255CharacterTests.suite());
		testSuite.addTest(AddFrontPageChildPageNameDuplicateTests.suite());
		testSuite.addTest(AddFrontPageChildPageNameNullTests.suite());
		testSuite.addTest(AddFrontPageChildPageNameSymbolTests.suite());
		testSuite.addTest(AddWikiPageTests.suite());
		testSuite.addTest(AddWikiPageChildPageTests.suite());
		testSuite.addTest(AddWikiPageChildPageMultipleTests.suite());
		testSuite.addTest(AddWikiPageFormatHTMLTests.suite());
		testSuite.addTest(AddWikiPageMultipleTests.suite());
		testSuite.addTest(AddWikiPageName255CharacterTests.suite());
		testSuite.addTest(AddWikiPageName255CharacterChildPageTests.suite());
		testSuite.addTest(
			AddWikiPageName255CharacterChildPageMultipleTests.suite());
		testSuite.addTest(AddWikiPageName256CharacterTests.suite());
		testSuite.addTest(AddWikiPageNameDuplicateTests.suite());
		testSuite.addTest(AddWikiPageNameNullTests.suite());
		testSuite.addTest(AddWikiPageNameSymbolTests.suite());
		testSuite.addTest(CancelAddFrontPageTests.suite());
		testSuite.addTest(CancelAddFrontPageChildPageTests.suite());
		testSuite.addTest(
			ChangeParentFrontPageChildPageName255CharacterToNoneTests.suite());
		testSuite.addTest(
			ChangeParentFrontPageChildPageName255CharacterToWikiPageTests.
			suite());
		testSuite.addTest(ChangeParentFrontPageChildPageToNoneTests.suite());
		testSuite.addTest(
			ChangeParentFrontPageChildPageToWikiPageTests.suite());
		testSuite.addTest(
			ChangeParentWikiPageName255CharacterToWikiPageTests.suite());
		testSuite.addTest(ChangeParentWikiPageToWikiPageTests.suite());
		testSuite.addTest(CompareVersionEditFrontPageTests.suite());
		testSuite.addTest(CompareVersionEditFrontPageMinorChangeTests.suite());
		testSuite.addTest(CopyFrontPageTests.suite());
		testSuite.addTest(DeleteFrontPageTests.suite());
		testSuite.addTest(DeleteFrontPageChildPageTests.suite());
		testSuite.addTest(DeleteWikiPageTests.suite());
		testSuite.addTest(DeleteWikiPageChildPageTests.suite());
		testSuite.addTest(DeleteWikiPageName255CharacterTests.suite());
		testSuite.addTest(DeleteWikiPageName255CharacterChildPageTests.suite());
		testSuite.addTest(EditFrontPageTests.suite());
		testSuite.addTest(EditFrontPageMinorChangeTests.suite());
		testSuite.addTest(LinkWikiPageToWikiPageTests.suite());
		testSuite.addTest(PreviewFrontPageTests.suite());
		testSuite.addTest(PreviewFrontPageChildPageTests.suite());
		testSuite.addTest(RemoveRedirectWikiPageTests.suite());
		testSuite.addTest(RenameWikiPageTitleTests.suite());
		testSuite.addTest(RenameWikiPageTitleDuplicateTests.suite());
		testSuite.addTest(RenameWikiPageTitleNullTests.suite());
		testSuite.addTest(RenameWikiPageTitleSameTests.suite());
		testSuite.addTest(
			RevertChangeParentFrontPageChildPageToNoneTests.suite());
		testSuite.addTest(
			RevertChangeParentFrontPageChildPageToWikiPageTests.suite());
		testSuite.addTest(RevertChangeParentWikiPageToWikiPageTests.suite());
		testSuite.addTest(RevertEditFrontPageTests.suite());
		testSuite.addTest(RevertEditFrontPageMinorChangeTests.suite());
		testSuite.addTest(SearchWikiPageContentTests.suite());
		testSuite.addTest(SearchWikiPageTitleTests.suite());
		testSuite.addTest(ViewWikiPageAllPagesTests.suite());
		testSuite.addTest(ViewWikiPageIncomingLinksTests.suite());
		testSuite.addTest(ViewWikiPageOrphanPagesTests.suite());
		testSuite.addTest(ViewWikiPageOutgoingLinksTests.suite());
		testSuite.addTest(ViewWikiPageRecentChangesTests.suite());

		return testSuite;
	}

}