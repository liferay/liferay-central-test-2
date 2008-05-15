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

package com.liferay.portlet.bookmarks.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.search.HitsOpenSearchImpl;
import com.liferay.portal.search.OpenSearchUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.DateTools;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="BookmarksOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BookmarksOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/bookmarks/open_search";

	public static final String TITLE = "Liferay Bookmarks Search: ";

	/**
	 * @deprecated
	 */
	public Hits getHits(long companyId, String keywords) throws Exception {
		return null;
	}

	public Hits getHits(
			long companyId, String keywords, int begin, int end)
	throws Exception {

		return BookmarksFolderLocalServiceUtil.search(
			companyId, 0, null, keywords, begin, end);
	}

	public String search(
			HttpServletRequest req, String keywords, int startPage,
			int itemsPerPage)
		throws SearchException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			int begin = (startPage * itemsPerPage) - itemsPerPage;
			int end = startPage * itemsPerPage;

			Hits results = getHits(
				themeDisplay.getCompanyId(), keywords, begin, end);

			int total = results.getLength();

			if (end > total) {
				end = total;
			}

			Object[] values = addSearchResults(
				keywords, startPage, itemsPerPage, total, begin,
				getTitle(keywords), getSearchPath(), themeDisplay);

			org.dom4j.Document doc = (org.dom4j.Document)values[0];
			Element root = (Element)values[1];

			for (int i = 0; i < results.getDocs().length; i++) {
				Document result = results.doc(i);

				String portletId = result.get(LuceneFields.PORTLET_ID);

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					themeDisplay.getCompanyId(), portletId);

				//String portletTitle = PortalUtil.getPortletTitle(
				//	portletId, themeDisplay.getUser());

				long groupId = GetterUtil.getLong(
					result.get(LuceneFields.GROUP_ID));

				PortletURL portletURL = getPortletURL(req, portletId, groupId);

				Indexer indexer = (Indexer)InstancePool.get(
					portlet.getIndexerClass());

				DocumentSummary docSummary = indexer.getDocumentSummary(
					result, portletURL);

				String title = docSummary.getTitle();
				String url = getURL(themeDisplay, groupId, result, portletURL);
				Date modifedDate = DateTools.stringToDate(
					result.get(LuceneFields.MODIFIED));
				String content = docSummary.getContent();
				double score = results.score(i);

				addSearchResult(root, title, url, modifedDate, content, score);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Return\n" + doc.asXML());
			}

			return doc.asXML();
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	protected Object[] addSearchResults(
		String keywords, int startPage, int itemsPerPage, int total, int begin,
		String title, String searchPath, ThemeDisplay themeDisplay) {

		int totalPages = 0;

		if (total % itemsPerPage == 0) {
			totalPages = total / itemsPerPage;
		}
		else {
			totalPages = (total / itemsPerPage) + 1;
		}

		int previousPage = startPage - 1;
		int nextPage = startPage + 1;

		// Create document

		org.dom4j.Document doc = DocumentHelper.createDocument();

		// feed

		Element root = doc.addElement("feed");

		root.add(OpenSearchUtil.getNamespace(OpenSearchUtil.DEFAULT_NAMESPACE));
		root.add(OpenSearchUtil.getNamespace(OpenSearchUtil.OS_NAMESPACE));
		root.add(
			OpenSearchUtil.getNamespace(OpenSearchUtil.RELEVANCE_NAMESPACE));

		// title

		OpenSearchUtil.addElement(
			root, "title", OpenSearchUtil.DEFAULT_NAMESPACE, title);

		// updated

		OpenSearchUtil.addElement(
			root, "updated", OpenSearchUtil.DEFAULT_NAMESPACE, new Date());

		// author

		Element author = OpenSearchUtil.addElement(
			root, "author", OpenSearchUtil.DEFAULT_NAMESPACE);

		// name

		OpenSearchUtil.addElement(
			author, "name", OpenSearchUtil.DEFAULT_NAMESPACE,
			themeDisplay.getUserId());

		// id

		OpenSearchUtil.addElement(
			root, "id", OpenSearchUtil.DEFAULT_NAMESPACE,
			"urn:uuid:" + PortalUUIDUtil.generate());

		// opensearch:totalResults

		OpenSearchUtil.addElement(
			root, "totalResults", OpenSearchUtil.OS_NAMESPACE, total);

		// opensearch:startIndex

		OpenSearchUtil.addElement(
			root, "startIndex", OpenSearchUtil.OS_NAMESPACE, begin + 1);

		// opensearch:itemsPerPage

		OpenSearchUtil.addElement(
			root, "itemsPerPage", OpenSearchUtil.OS_NAMESPACE, itemsPerPage);

		// opensearch:Query

		Element query = OpenSearchUtil.addElement(
			root, "Query", OpenSearchUtil.OS_NAMESPACE);

		query.addAttribute("role", "request");
		query.addAttribute("searchTerms", keywords);
		query.addAttribute("startPage", String.valueOf(startPage));

		// links

		String searchURL = themeDisplay.getURLPortal() + searchPath;

		OpenSearchUtil.addLink(
			root, searchURL, "self", keywords, startPage, itemsPerPage);
		OpenSearchUtil.addLink(
			root, searchURL, "first", keywords, 1, itemsPerPage);

		if (previousPage > 0) {
			OpenSearchUtil.addLink(
				root, searchURL, "previous", keywords, previousPage,
				itemsPerPage);
		}

		if (nextPage <= totalPages) {
			OpenSearchUtil.addLink(
				root, searchURL, "next", keywords, nextPage, itemsPerPage);
		}

		OpenSearchUtil.addLink(
			root, searchURL, "last", keywords, totalPages, itemsPerPage);

		Element link = OpenSearchUtil.addElement(
			root, "link", OpenSearchUtil.DEFAULT_NAMESPACE);

		link.addAttribute("rel", "search");
		link.addAttribute("href", searchPath + "_description.xml");
		link.addAttribute("type", "application/opensearchdescription+xml");

		return new Object[] {doc, root};
	}

	public String getSearchPath() {
		return SEARCH_PATH;
	}

	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

	private static Log _log = LogFactory.getLog(HitsOpenSearchImpl.class);
}