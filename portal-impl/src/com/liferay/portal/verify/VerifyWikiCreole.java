/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.translators.ClassicToCreoleTranslator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="VerifyWikiCreole.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class converts all existing wiki pages in the Classic Wiki format to
 * Creole. Do not run this unless you want to do this.
 * </p>
 *
 * @author Jorge Ferrer
 *
 */
public class VerifyWikiCreole extends VerifyProcess {

	public void verify() throws VerifyException {
		_log.info("Verifying");

		try {
			verifyWikiCreole();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	protected void verifyWikiCreole() throws Exception {
		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			"classic_wiki");

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + pages.size() + " Classic Wiki pages");
		}

		ClassicToCreoleTranslator translator = new ClassicToCreoleTranslator();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Converting " + pages.size() +
					" pages from Classic Wiki to Creole format");
		}

		for (WikiPage page : pages) {
			page.setFormat("creole");

			page.setContent(translator.translate(page.getContent()));

			WikiPageLocalServiceUtil.updateWikiPage(page);
		}
	}

	private static Log _log = LogFactory.getLog(VerifyWikiCreole.class);

}