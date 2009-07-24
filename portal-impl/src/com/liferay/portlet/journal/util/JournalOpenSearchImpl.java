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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.model.Layout;
import com.liferay.portal.search.HitsOpenSearchImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletURL;

public class JournalOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/journal/open_search";

	public static final String TITLE = "Liferay Journal Search: ";

	public Hits getHits(
			long companyId, long groupId, long userId, String keywords,
			int start, int end)
		throws Exception {

		return JournalArticleLocalServiceUtil.search(
			companyId, groupId, userId, keywords, start, end);
	}

	public String getSearchPath() {
		return SEARCH_PATH;
	}

	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

	protected String getURL(
			ThemeDisplay themeDisplay, long groupId, Document result,
			PortletURL portletURL)
		throws Exception {

		Layout layout = themeDisplay.getLayout();

		String articleId = result.get(Field.ENTRY_CLASS_PK);
		String version = result.get("version");

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(), articleId);

		if (hitLayoutIds.size() > 0) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				hitLayoutId.longValue());

			return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
		}
		else {
			StringBuilder sb = new StringBuilder();

			sb.append(themeDisplay.getPathMain());
			sb.append("/journal/view_article_content?groupId=");
			sb.append(groupId);
			sb.append("&articleId=");
			sb.append(articleId);
			sb.append("&version=");
			sb.append(version);

			return sb.toString();
		}
	}

}