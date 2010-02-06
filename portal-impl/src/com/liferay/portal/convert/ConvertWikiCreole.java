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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.translators.ClassicToCreoleTranslator;

import java.util.List;

/**
 * <a href="ConvertWikiCreole.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class ConvertWikiCreole extends ConvertProcess {

	public String getDescription() {
		return "convert-wiki-pages-from-classic-wiki-to-creole-format";
	}

	public boolean isEnabled() {
		boolean enabled = false;

		try {
			int pagesCount = WikiPageLocalServiceUtil.getPagesCount(
				"classic_wiki");

			if (pagesCount > 0) {
				enabled = true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return enabled;
	}

	protected void doConvert() throws Exception {
		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			"classic_wiki");

		ClassicToCreoleTranslator translator = new ClassicToCreoleTranslator();

		MaintenanceUtil.appendStatus(
			"Converting " + pages.size() +
				" Wiki pages from Classic Wiki to Creole format");

		for (int i = 0; i < pages.size(); i++) {
			if ((i > 0) && (i % (pages.size() / 4) == 0)) {
				MaintenanceUtil.appendStatus((i * 100. / pages.size()) + "%");
			}

			WikiPage page = pages.get(i);

			page.setFormat("creole");

			page.setContent(translator.translate(page.getContent()));

			WikiPageLocalServiceUtil.updateWikiPage(page);
		}
	}

	private static final Log _log =
		LogFactoryUtil.getLog(ConvertWikiCreole.class);

}