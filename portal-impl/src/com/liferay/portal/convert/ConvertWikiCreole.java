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

package com.liferay.portal.convert;

import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.translators.ClassicToCreoleTranslator;

import java.util.List;

/**
 * <a href="ConvertWikiCreole.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class converts all existing wiki pages in the Classic Wiki format to
 * Creole. Do not run this unless you want to do this.
 * </p>
 *
 * @author Jorge Ferrer
 *
 */
public class ConvertWikiCreole extends ConvertProcess {

	public boolean isEnabled() throws ConvertException {
		try {
			boolean enabled = false;

			if (WikiPageLocalServiceUtil.getPagesCount("classic_wiki") > 0) {
				enabled = true;
			}

			return enabled;
		}
		catch (Exception e) {
			throw new ConvertException(e);
		}
	}

	public String getDescription() {
		return "convert-pages-from-classic-wiki-to-creole-format";
	}

	protected void doConvert() throws Exception {
		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			"classic_wiki");

		ClassicToCreoleTranslator translator = new ClassicToCreoleTranslator();

		MaintenanceUtil.appendStatus(
			"Converting " + pages.size() +
				" pages from Classic Wiki to Creole format");

		long twentyFivePercent = pages.size() / 4;

		for (int i = 0; i < pages.size(); i++) {
			if ((i > 0) && (i % twentyFivePercent == 0)) {
				MaintenanceUtil.appendStatus(
					(i * 100. / pages.size()) + "% complete");
			}

			WikiPage page = pages.get(i);

			page.setFormat("creole");

			page.setContent(translator.translate(page.getContent()));

			WikiPageLocalServiceUtil.updateWikiPage(page);
		}
	}

}