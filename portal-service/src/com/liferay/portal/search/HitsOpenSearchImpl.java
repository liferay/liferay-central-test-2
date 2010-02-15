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

package com.liferay.portal.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="HitsOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public abstract class HitsOpenSearchImpl extends BaseOpenSearchImpl {

	public abstract String getPortletId();

	public abstract String getSearchPath();

	public abstract String getTitle(String keywords);

	public String search(
			HttpServletRequest request, long groupId, long userId,
			String keywords, int startPage, int itemsPerPage, String format)
		throws SearchException {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			int start = (startPage * itemsPerPage) - itemsPerPage;
			int end = startPage * itemsPerPage;

			SearchContext searchContext = SearchContextFactory.getInstance(
				request);

			searchContext.setGroupId(groupId);
			searchContext.setEnd(end);
			searchContext.setKeywords(keywords);
			searchContext.setStart(start);
			searchContext.setScopeStrict(false);
			searchContext.setUserId(userId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), getPortletId());

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				portlet.getIndexerClass());

			Hits results = indexer.search(searchContext);

			String[] queryTerms = results.getQueryTerms();

			int total = results.getLength();

			Object[] values = addSearchResults(
				queryTerms, keywords, startPage, itemsPerPage, total, start,
				getTitle(keywords), getSearchPath(), format, themeDisplay);

			com.liferay.portal.kernel.xml.Document doc =
				(com.liferay.portal.kernel.xml.Document)values[0];
			Element root = (Element)values[1];

			for (int i = 0; i < results.getDocs().length; i++) {
				Document result = results.doc(i);

				String portletId = result.get(Field.PORTLET_ID);

				String snippet = results.snippet(i);

				long resultGroupId = GetterUtil.getLong(
					result.get(Field.GROUP_ID));

				PortletURL portletURL = getPortletURL(
					request, portletId, resultGroupId);

				Summary summary = indexer.getSummary(
					result, snippet, portletURL);

				String title = summary.getTitle();
				String url = getURL(
					themeDisplay, resultGroupId, result, portletURL);
				Date modifedDate = result.getDate(Field.MODIFIED);
				String content = summary.getContent();

				String[] tags = new String[0];

				Field assetTagNamesField = result.getFields().get(
					Field.ASSET_TAG_NAMES);

				if (assetTagNamesField != null) {
					tags = assetTagNamesField.getValues();
				}

				double ratings = 0.0;

				String entryClassName = result.get(Field.ENTRY_CLASS_NAME);
				long entryClassPK = GetterUtil.getLong(
					result.get(Field.ENTRY_CLASS_PK));

				if ((Validator.isNotNull(entryClassName)) &&
					(entryClassPK > 0)) {

					RatingsStats stats = RatingsStatsLocalServiceUtil.getStats(
						entryClassName, entryClassPK);

					ratings = stats.getTotalScore();
				}

				double score = results.score(i);

				addSearchResult(
					root, title, url, modifedDate, content, tags, ratings,
					score, format);
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

	protected String getURL(
			ThemeDisplay themeDisplay, long groupId, Document result,
			PortletURL portletURL)
		throws Exception {

		return portletURL.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(HitsOpenSearchImpl.class);

}