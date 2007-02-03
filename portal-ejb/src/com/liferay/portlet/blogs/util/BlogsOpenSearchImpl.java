/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.search.BaseOpenSearchImpl;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.util.InstancePool;

import java.util.Date;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.DateTools;

import org.dom4j.Element;

/**
 * <a href="BlogsOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsOpenSearchImpl extends BaseOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/blogs/open_search";

	public boolean isEnabled() {
		return true;
	}

	public String search(
			HttpServletRequest req, String keywords, int startPage,
			int itemsPerPage)
		throws SearchException {

		try {
			return _search(req, keywords, startPage, itemsPerPage);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private String _search(
			HttpServletRequest req, String keywords, int startPage,
			int itemsPerPage)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		Hits hits = BlogsEntryLocalServiceUtil.search(
			themeDisplay.getCompanyId(), 0, null, null, keywords);

		Object[] values = addSearchResults(
			keywords, startPage, itemsPerPage, hits,
			"Liferay Blogs Search: " + keywords, SEARCH_PATH, themeDisplay);

		Hits results = (Hits)values[0];
		org.dom4j.Document doc = (org.dom4j.Document)values[1];
		Element root = (Element)values[2];

		for (int i = 0; i < results.getLength(); i++) {
			Document result = results.doc(i);

			String portletId = (String)result.get(LuceneFields.PORTLET_ID);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);

			String portletTitle = PortalUtil.getPortletTitle(
				portletId, themeDisplay.getUser());

			String groupId = (String)result.get(LuceneFields.GROUP_ID);

			PortletURL portletURL = getPortletURL(req, portletId, groupId);

			Indexer indexer = (Indexer)InstancePool.get(
				portlet.getIndexerClass());

			DocumentSummary docSummary =
				indexer.getDocumentSummary(result, portletURL);

			String title = docSummary.getTitle();
			String url = portletURL.toString();
			Date modifedDate = DateTools.stringToDate(
				(String)result.get(LuceneFields.MODIFIED));
			String content = docSummary.getContent();
			double score = hits.score(i);

			addSearchResult(
				root, portletTitle + " &raquo; " + title, url, modifedDate,
				content, score);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return\n" + doc.asXML());
		}

		return doc.asXML();
	}

	private static Log _log = LogFactory.getLog(BlogsOpenSearchImpl.class);

}