/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.util.InstancePool;
import com.liferay.util.Validator;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.id.uuid.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.DateTools;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="PortalOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class PortalOpenSearchImpl implements OpenSearch {

	public String search(HttpServletRequest req, String url)
		throws SearchException {

		return null;
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

		Company company = themeDisplay.getCompany();

		Hits results = null;

		int total = 0;
		int begin = (startPage * itemsPerPage) - itemsPerPage;
		int end = startPage * itemsPerPage;

		Hits hits = CompanyLocalServiceUtil.search(
			themeDisplay.getCompanyId(), keywords);

		total = hits.getLength();

		if (end > total) {
			end = total;
		}

		results = hits.subset(begin, end);

		int totalPages = 0;

		if (total % itemsPerPage == 0) {
			totalPages = total / itemsPerPage;
		}
		else {
			totalPages = (total / itemsPerPage) + 1;
		}

		int previousPage = startPage - 1;
		int nextPage = startPage + 1;

		SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:sszzz");

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
			root, "title", OpenSearchUtil.DEFAULT_NAMESPACE,
			"Liferay Search: " + keywords);

		// updated

		OpenSearchUtil.addElement(
			root, "updated", OpenSearchUtil.DEFAULT_NAMESPACE,
			sdf.format(new Date()));

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
			"urn:uuid:" + UUID.timeUUID());

		// opensearch:totalResults

		OpenSearchUtil.addElement(
			root, "totalResults", OpenSearchUtil.OS_NAMESPACE,
			String.valueOf(total));

		// opensearch:startIndex

		OpenSearchUtil.addElement(
			root, "startIndex", OpenSearchUtil.OS_NAMESPACE,
			String.valueOf(begin + 1));

		// opensearch:itemsPerPage

		OpenSearchUtil.addElement(
			root, "itemsPerPage", OpenSearchUtil.OS_NAMESPACE,
			String.valueOf(itemsPerPage));

		// opensearch:Query

		Element query = OpenSearchUtil.addElement(
			root, "Query", OpenSearchUtil.OS_NAMESPACE);

		query.addAttribute("role", "request");
		query.addAttribute("searchTerms", keywords);
		query.addAttribute("startPage", String.valueOf(startPage));

		// links

		OpenSearchUtil.addLink(
			root, company.getPortalURL(), "self", keywords, startPage,
			itemsPerPage);
		OpenSearchUtil.addLink(
			root, company.getPortalURL(), "first", keywords, 1, itemsPerPage);

		if (previousPage > 0) {
			OpenSearchUtil.addLink(
				root, company.getPortalURL(), "previous", keywords,
				previousPage, itemsPerPage);
		}

		if (nextPage <= totalPages) {
			OpenSearchUtil.addLink(
				root, company.getPortalURL(), "next", keywords, nextPage,
				itemsPerPage);
		}

		OpenSearchUtil.addLink(
			root, company.getPortalURL(), "last", keywords, totalPages,
			itemsPerPage);

		Element link = OpenSearchUtil.addElement(
			root, "link", OpenSearchUtil.DEFAULT_NAMESPACE);

		link.addAttribute("rel", "search");
		link.addAttribute(
			"href", OpenSearchUtil.SEARCH_PATH + "/description.xml");
		link.addAttribute("type", "application/opensearchdescription+xml");

		// entries

		for (int i = 0; i < results.getLength(); i++) {
			Document result = results.doc(i);

			String portletId = (String)result.get(LuceneFields.PORTLET_ID);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);

			if (portlet == null) {
				continue;
			}

			String portletTitle = PortalUtil.getPortletTitle(
				portletId, themeDisplay.getUser());

			String groupId = (String)result.get(LuceneFields.GROUP_ID);

			String title = null;
			String content = null;

			String portletLayoutId = null;

			if (themeDisplay.getLayout().isPrivateLayout()) {
				portletLayoutId = LayoutImpl.PRIVATE + groupId + ".1";
			}
			else {
				portletLayoutId = LayoutImpl.PRIVATE + groupId + ".1";
			}

			PortletURL rowURL = new PortletURLImpl(
				req, portletId, portletLayoutId, false);

			rowURL.setWindowState(WindowState.MAXIMIZED);
			rowURL.setPortletMode(PortletMode.VIEW);

			String url = rowURL.toString();

			if (Validator.isNotNull(portlet.getIndexerClass())) {
				Indexer indexer = (Indexer)InstancePool.get(
					portlet.getIndexerClass());

				DocumentSummary docSummary =
					indexer.getDocumentSummary(result, rowURL);

				title = docSummary.getTitle();
				content = docSummary.getContent();
				url = rowURL.toString();

				if (portlet.getPortletId().equals(PortletKeys.JOURNAL)) {
					String articleId = result.get("articleId");
					String version = result.get("version");

					StringBuffer sb = new StringBuffer();

					sb.append(themeDisplay.getPathMain());
					sb.append("/journal/view_article_content?articleId=");
					sb.append(articleId);
					sb.append("&version=");
					sb.append(version);
					sb.append("&groupId=");
					sb.append(groupId);

					url = sb.toString();
				}
			}

			// entry

			Element entry = OpenSearchUtil.addElement(
				root, "entry", OpenSearchUtil.DEFAULT_NAMESPACE);

			// title

			OpenSearchUtil.addElement(
				entry, "title", OpenSearchUtil.DEFAULT_NAMESPACE,
				portletTitle + " &raquo; " + title);

			// link

			Element entryLink = OpenSearchUtil.addElement(
				entry, "link", OpenSearchUtil.DEFAULT_NAMESPACE);

			entryLink.addAttribute("href", url);

			// id

			OpenSearchUtil.addElement(
				entry, "id", OpenSearchUtil.DEFAULT_NAMESPACE,
				"urn:uuid:" + UUID.timeUUID());

			// updated

			Date entryDate = DateTools.stringToDate(
				(String)result.get(LuceneFields.MODIFIED));

			OpenSearchUtil.addElement(
				entry, "updated", OpenSearchUtil.DEFAULT_NAMESPACE,
				sdf.format(entryDate));

			// summary

			OpenSearchUtil.addElement(
				entry, "summary", OpenSearchUtil.DEFAULT_NAMESPACE, content);

			// relevance:score

			OpenSearchUtil.addElement(
				entry, "score", OpenSearchUtil.RELEVANCE_NAMESPACE,
				String.valueOf(hits.score(i)));
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return\n" + doc.asXML());
		}

		return doc.asXML();
	}

	private static Log _log = LogFactory.getLog(PortalOpenSearchImpl.class);

}