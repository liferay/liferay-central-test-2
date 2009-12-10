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

package com.liferay.portal.search;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;

import java.util.Date;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="BaseOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public abstract class BaseOpenSearchImpl implements OpenSearch {

	public boolean isEnabled() {
		return _enabled;
	}

	public String search(HttpServletRequest request, String url)
		throws SearchException {

		try {
			long userId = PortalUtil.getUserId(request);

			if (userId == 0) {
				long companyId = PortalUtil.getCompanyId(request);

				userId = UserLocalServiceUtil.getDefaultUserId(companyId);
			}

			String keywords = GetterUtil.getString(
				HttpUtil.getParameter(url, "keywords", false));
			int startPage = GetterUtil.getInteger(
				HttpUtil.getParameter(url, "p", false), 1);
			int itemsPerPage = GetterUtil.getInteger(
				HttpUtil.getParameter(url, "c", false),
				SearchContainer.DEFAULT_DELTA);
			String format = GetterUtil.getString(
				HttpUtil.getParameter(url, "format", false));

			return search(
				request, userId, keywords, startPage, itemsPerPage, format);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public String search(
			HttpServletRequest request, long userId, String keywords,
			int startPage, int itemsPerPage, String format)
		throws SearchException {

		return search(
			request, 0, userId, keywords, startPage, itemsPerPage, format);
	}

	public abstract String search(
			HttpServletRequest request, long groupId, long userId,
			String keywords, int startPage, int itemsPerPage, String format)
		throws SearchException;

	protected void addSearchResult(
		Element root, String title, String link, Date updated,
		String summary, double score, String format) {

		addSearchResult(
			root, title, link, updated, summary, new String[0], 0, score,
			format);
	}

	protected void addSearchResult(
		Element root, String title, String link, Date updated, String summary,
		String[] tags, double ratings, double score, String format) {

		if (format.equals("rss")) {
			addSearchResultRSS(
				root, title, link, updated, summary, tags, ratings, score);
		}
		else {
			addSearchResultAtom(
				root, title, link, updated, summary, tags, ratings, score);
		}
	}

	protected void addSearchResultAtom(
		Element root, String title, String link, Date updated, String summary,
		String[] tags, double ratings, double score) {

		// entry

		Element entry = OpenSearchUtil.addElement(
			root, "entry", OpenSearchUtil.DEFAULT_NAMESPACE);

		// title

		OpenSearchUtil.addElement(
			entry, "title", OpenSearchUtil.DEFAULT_NAMESPACE, title);

		// link

		Element entryLink = OpenSearchUtil.addElement(
			entry, "link", OpenSearchUtil.DEFAULT_NAMESPACE);

		entryLink.addAttribute("href", link);

		// id

		OpenSearchUtil.addElement(
			entry, "id", OpenSearchUtil.DEFAULT_NAMESPACE,
			"urn:uuid:" + PortalUUIDUtil.generate());

		// updated

		OpenSearchUtil.addElement(
			entry, "updated", OpenSearchUtil.DEFAULT_NAMESPACE, updated);

		// summary

		OpenSearchUtil.addElement(
			entry, "summary", OpenSearchUtil.DEFAULT_NAMESPACE, summary);

		// tags

		OpenSearchUtil.addElement(
			entry, "tags", OpenSearchUtil.DEFAULT_NAMESPACE,
			StringUtil.merge(tags));

		// ratings

		OpenSearchUtil.addElement(
			entry, "ratings", OpenSearchUtil.DEFAULT_NAMESPACE, ratings);

		// relevance:score

		OpenSearchUtil.addElement(
			entry, "score", OpenSearchUtil.RELEVANCE_NAMESPACE, score);
	}

	protected void addSearchResultRSS(
		Element root, String title, String link, Date updated, String summary,
		String[] tags, double ratings, double score) {

		// item

		Element item = root.addElement("item");

		// title

		OpenSearchUtil.addElement(
			item, "title", OpenSearchUtil.NO_NAMESPACE, title);

		// link

		OpenSearchUtil.addElement(
			item, "link", OpenSearchUtil.NO_NAMESPACE, link);

		// summary

		OpenSearchUtil.addElement(
			item, "description", OpenSearchUtil.NO_NAMESPACE, summary);

		// tags

		OpenSearchUtil.addElement(
			item, "tags", OpenSearchUtil.NO_NAMESPACE, StringUtil.merge(tags));

		// ratings

		OpenSearchUtil.addElement(
			item, "ratings", OpenSearchUtil.NO_NAMESPACE, ratings);

		// relevance:score

		OpenSearchUtil.addElement(
			item, "score", OpenSearchUtil.RELEVANCE_NAMESPACE, score);
	}

	/**
	 * @deprecated
	 */
	protected Object[] addSearchResults(
		String keywords, int startPage, int itemsPerPage, int total, int start,
		String title, String searchPath, String format,
		ThemeDisplay themeDisplay) {

		return addSearchResults(
			new String[0], keywords, startPage, itemsPerPage, total, start,
			title, searchPath, format, themeDisplay);
	}

	protected Object[] addSearchResults(
		String[] queryTerms, String keywords, int startPage, int itemsPerPage,
		int total, int start, String title, String searchPath, String format,
		ThemeDisplay themeDisplay) {

		int totalPages = 0;

		if (total % itemsPerPage == 0) {
			totalPages = total / itemsPerPage;
		}
		else {
			totalPages = (total / itemsPerPage) + 1;
		}

		int previousPage = startPage - 1;
		int nextPage = startPage + 1;

		Document doc = SAXReaderUtil.createDocument();

		if (format.equals("rss")) {
			return addSearchResultsRSS(
				doc, queryTerms, keywords, startPage, itemsPerPage, total,
				start, totalPages, previousPage, nextPage, title, searchPath,
				themeDisplay);
		}
		else {
			return addSearchResultsAtom(
				doc, queryTerms, keywords, startPage, itemsPerPage, total,
				start, totalPages, previousPage, nextPage, title, searchPath,
				themeDisplay);
		}
	}

	protected Object[] addSearchResultsAtom(
		Document doc, String[] queryTerms, String keywords, int startPage,
		int itemsPerPage, int total, int start, int totalPages,
		int previousPage, int nextPage, String title, String searchPath,
		ThemeDisplay themeDisplay) {

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

		// queryTerms

		OpenSearchUtil.addElement(
			root, "queryTerms", OpenSearchUtil.DEFAULT_NAMESPACE,
			StringUtil.merge(queryTerms, StringPool.COMMA_AND_SPACE));

		// opensearch:totalResults

		OpenSearchUtil.addElement(
			root, "totalResults", OpenSearchUtil.OS_NAMESPACE, total);

		// opensearch:startIndex

		OpenSearchUtil.addElement(
			root, "startIndex", OpenSearchUtil.OS_NAMESPACE, start + 1);

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

	protected Object[] addSearchResultsRSS(
		Document doc, String[] queryTerms, String keywords, int startPage,
		int itemsPerPage, int total, int start, int totalPages,
		int previousPage, int nextPage, String title, String searchPath,
		ThemeDisplay themeDisplay) {

		// rss

		Element root = doc.addElement("rss");

		root.addAttribute("version", "2.0");
		root.add(
			SAXReaderUtil.createNamespace(
				"atom", "http://www.w3.org/2005/Atom"));
		root.add(OpenSearchUtil.getNamespace(OpenSearchUtil.OS_NAMESPACE));
		root.add(
			OpenSearchUtil.getNamespace(OpenSearchUtil.RELEVANCE_NAMESPACE));

		// channel

		Element channel = root.addElement("channel");

		// title

		OpenSearchUtil.addElement(
			channel, "title", OpenSearchUtil.NO_NAMESPACE, title);

		// link

		OpenSearchUtil.addElement(
			channel, "link", OpenSearchUtil.NO_NAMESPACE,
			themeDisplay.getURLPortal() + searchPath);

		// description

		OpenSearchUtil.addElement(
			channel, "description", OpenSearchUtil.NO_NAMESPACE, title);

		// queryTerms

		OpenSearchUtil.addElement(
			channel, "queryTerms", OpenSearchUtil.NO_NAMESPACE,
			StringUtil.merge(queryTerms, StringPool.COMMA_AND_SPACE));

		// opensearch:totalResults

		OpenSearchUtil.addElement(
			channel, "totalResults", OpenSearchUtil.OS_NAMESPACE, total);

		// opensearch:startIndex

		OpenSearchUtil.addElement(
			channel, "startIndex", OpenSearchUtil.OS_NAMESPACE, start + 1);

		// opensearch:itemsPerPage

		OpenSearchUtil.addElement(
			channel, "itemsPerPage", OpenSearchUtil.OS_NAMESPACE, itemsPerPage);

		// opensearch:Query

		Element query = OpenSearchUtil.addElement(
			channel, "Query", OpenSearchUtil.OS_NAMESPACE);

		query.addAttribute("role", "request");
		query.addAttribute("searchTerms", keywords);
		query.addAttribute("startPage", String.valueOf(startPage));

		return new Object[] {doc, channel};
	}

	protected PortletURL getPortletURL(
			HttpServletRequest request, String portletId)
		throws PortletModeException, SystemException, WindowStateException {

		return getPortletURL(request, portletId, 0);
	}

	protected PortletURL getPortletURL(
			HttpServletRequest request, String portletId, long groupId)
		throws PortletModeException, SystemException, WindowStateException {

		long plid = LayoutLocalServiceUtil.getDefaultPlid(
			groupId, false, portletId);

		if (plid == 0) {
			plid = LayoutLocalServiceUtil.getDefaultPlid(
				groupId, true, portletId);
		}

		if (plid == 0) {
			Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

			if (layout != null) {
				plid = layout.getPlid();
			}
		}

		PortletURL portletURL = new PortletURLImpl(
			request, portletId, plid, PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);
		portletURL.setPortletMode(PortletMode.VIEW);

		return portletURL;
	}

	private boolean _enabled = GetterUtil.getBoolean(
		PropsUtil.get(getClass().getName()), true);

}